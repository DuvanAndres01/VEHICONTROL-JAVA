package com.vehicontrol.auth;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

/**
 * API REST de ejemplo para registrar cuentas e iniciar sesión.
 * Inicia el servidor local en el puerto indicado por PORT (8080 por defecto).
 */
public final class AuthApi {
    private static final int MAX_BODY_BYTES = 16_384;
    private static final UserStore USERS = new UserStore();

    private AuthApi() { }

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        if (port < 1 || port > 65_535) {
            throw new IllegalArgumentException("PORT debe estar entre 1 y 65535");
        }

        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
        server.createContext("/api/auth/register", exchange -> handle(exchange, true));
        server.createContext("/api/auth/login", exchange -> handle(exchange, false));
        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
        System.out.println("Servicio de autenticación activo en http://127.0.0.1:" + port);
    }

    /** Valida el método y el JSON, ejecuta la operación y responde en español. */
    private static void handle(HttpExchange exchange, boolean registering) throws IOException {
        try (exchange) {
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                respond(exchange, 405, "{\"mensaje\":\"Método no permitido. Use POST.\"}");
                return;
            }
            if (!exchange.getRequestURI().getPath().equals(registering
                    ? "/api/auth/register" : "/api/auth/login")) {
                respond(exchange, 404, "{\"mensaje\":\"Recurso no encontrado.\"}");
                return;
            }

            byte[] body = exchange.getRequestBody().readNBytes(MAX_BODY_BYTES + 1);
            if (body.length > MAX_BODY_BYTES) {
                respond(exchange, 413, "{\"mensaje\":\"La solicitud supera el tamaño permitido.\"}");
                return;
            }

            Map<String, String> fields;
            try {
                fields = JsonStrings.parseObject(new String(body, StandardCharsets.UTF_8));
            } catch (IllegalArgumentException ex) {
                respond(exchange, 400, "{\"mensaje\":\"El cuerpo debe ser un objeto JSON válido con valores de texto.\"}");
                return;
            }

            String username = fields.getOrDefault("usuario", "").trim();
            String password = fields.getOrDefault("contrasena", "");
            if (username.isEmpty() || password.isEmpty()) {
                respond(exchange, 400, "{\"mensaje\":\"Usuario y contraseña son obligatorios.\"}");
                return;
            }

            if (registering) {
                if (!username.matches("[A-Za-z0-9._-]{3,32}")) {
                    respond(exchange, 400, "{\"mensaje\":\"El usuario debe tener entre 3 y 32 caracteres: letras, números, punto, guion o guion bajo.\"}");
                    return;
                }
                if (password.length() < 8 || password.length() > 128) {
                    respond(exchange, 400, "{\"mensaje\":\"La contraseña debe tener entre 8 y 128 caracteres.\"}");
                    return;
                }
                if (!USERS.register(username, password.toCharArray())) {
                    respond(exchange, 409, "{\"mensaje\":\"El usuario ya está registrado.\"}");
                    return;
                }
                respond(exchange, 201, "{\"mensaje\":\"Registro satisfactorio.\"}");
                return;
            }

            if (USERS.authenticate(username, password.toCharArray())) {
                respond(exchange, 200, "{\"mensaje\":\"Autenticación satisfactoria.\"}");
            } else {
                respond(exchange, 401, "{\"mensaje\":\"Error en la autenticación.\"}");
            }
        }
    }

    /** Envía una respuesta JSON UTF-8 con el estado HTTP correspondiente. */
    private static void respond(HttpExchange exchange, int status, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.getResponseHeaders().set("Cache-Control", "no-store");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
    }

    /** Almacena credenciales derivadas, nunca contraseñas en texto plano. */
    private static final class UserStore {
        private final Map<String, Credential> records = new ConcurrentHashMap<>();

        boolean register(String username, char[] password) {
            String key = username.toLowerCase(java.util.Locale.ROOT);
            try {
                Credential credential = Credential.from(password);
                return records.putIfAbsent(key, credential) == null;
            } finally {
                Arrays.fill(password, '\0');
            }
        }

        boolean authenticate(String username, char[] password) {
            String key = username.toLowerCase(java.util.Locale.ROOT);
            Credential credential = records.get(key);
            try {
                return credential != null && credential.matches(password);
            } finally {
                Arrays.fill(password, '\0');
            }
        }
    }

    /** Hash PBKDF2 con sal aleatoria para evitar almacenar la contraseña original. */
    private record Credential(byte[] salt, byte[] hash) {
        private static final int ITERATIONS = 210_000;
        private static final int KEY_BITS = 256;
        private static final SecureRandom RANDOM = new SecureRandom();

        static Credential from(char[] password) {
            byte[] salt = new byte[16];
            RANDOM.nextBytes(salt);
            return new Credential(salt, derive(password, salt));
        }

        boolean matches(char[] password) {
            return MessageDigest.isEqual(hash, derive(password, salt));
        }

        private static byte[] derive(char[] password, byte[] salt) {
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_BITS);
            try {
                return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                        .generateSecret(spec).getEncoded();
            } catch (GeneralSecurityException ex) {
                throw new IllegalStateException("No se pudo verificar la contraseña", ex);
            } finally {
                spec.clearPassword();
            }
        }
    }

    /** Parser pequeño para objetos JSON cuyos valores son cadenas; no acepta estructuras anidadas. */
    private static final class JsonStrings {
        private final String input;
        private int position;

        private JsonStrings(String input) { this.input = input; }

        static Map<String, String> parseObject(String input) {
            return new JsonStrings(input).parse();
        }

        private Map<String, String> parse() {
            Map<String, String> result = new HashMap<>();
            whitespace(); expect('{'); whitespace();
            if (take('}')) { finish(); return result; }
            do {
                whitespace(); String key = string(); whitespace(); expect(':'); whitespace();
                String value = string();
                if (result.putIfAbsent(key, value) != null) fail();
                whitespace();
                if (take('}')) { finish(); return result; }
                expect(',');
            } while (true);
        }

        private String string() {
            expect('"'); StringBuilder value = new StringBuilder();
            while (position < input.length()) {
                char current = input.charAt(position++);
                if (current == '"') return value.toString();
                if (current < 0x20) fail();
                if (current != '\\') { value.append(current); continue; }
                if (position >= input.length()) fail();
                char escaped = input.charAt(position++);
                switch (escaped) {
                    case '"', '\\', '/' -> value.append(escaped);
                    case 'b' -> value.append('\b');
                    case 'f' -> value.append('\f');
                    case 'n' -> value.append('\n');
                    case 'r' -> value.append('\r');
                    case 't' -> value.append('\t');
                    case 'u' -> {
                        if (position + 4 > input.length()) fail();
                        try { value.append((char) Integer.parseInt(input.substring(position, position + 4), 16)); }
                        catch (NumberFormatException ex) { fail(); }
                        position += 4;
                    }
                    default -> fail();
                }
            }
            fail(); return "";
        }

        private void finish() { whitespace(); if (position != input.length()) fail(); }
        private void whitespace() { while (position < input.length() && Character.isWhitespace(input.charAt(position))) position++; }
        private boolean take(char expected) { if (position < input.length() && input.charAt(position) == expected) { position++; return true; } return false; }
        private void expect(char expected) { if (!take(expected)) fail(); }
        private static void fail() { throw new IllegalArgumentException("JSON no válido"); }
    }
}
