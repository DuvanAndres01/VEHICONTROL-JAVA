package com.vehicontrol.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/** API REST local de Vehicontrol: vehículos, usuarios, movimientos y configuración. */
public final class VehicontrolApi {
    private static final int MAX_BODY = 16_384;
    private static final Store STORE = new Store();

    private VehicontrolApi() { }

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8081"));
        //HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        server.createContext("/api", VehicontrolApi::handle);
        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
        System.out.println("Vehicontrol API activa en http://127.0.0.1:" + port + "/api");
    }

    /** Enruta cada solicitud, valida los datos y traduce errores a códigos HTTP. */
    private static void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod().toUpperCase(Locale.ROOT);
            String[] path = exchange.getRequestURI().getPath().split("/");
            if ("GET".equals(method) && "/api/health".equals(exchange.getRequestURI().getPath())) {
                send(exchange, 200, Map.of("estado", "activo", "servicio", "Vehicontrol API"));
                return;
            }
            if (path.length < 3 || !"api".equals(path[1])) {
                send(exchange, 404, error("Recurso no encontrado."));
                return;
            }
            Map<String, Object> body = needsBody(method) ? readBody(exchange) : Map.of();
            Object result = route(method, path, body, exchange.getRequestURI().getRawQuery());
            if (result == null) {
                exchange.getResponseHeaders().set("Cache-Control", "no-store");
                exchange.sendResponseHeaders(204, -1);
            } else {
                int status = method.equals("POST") ? 201 : 200;
                send(exchange, status, result);
            }
        } catch (BadRequest ex) {
            send(exchange, 400, error(ex.getMessage()));
        } catch (NotFound ex) {
            send(exchange, 404, error(ex.getMessage()));
        } catch (Conflict ex) {
            send(exchange, 409, error(ex.getMessage()));
        } catch (MethodNotAllowed ex) {
            exchange.getResponseHeaders().set("Allow", ex.allowed);
            send(exchange, 405, error("Método no permitido. Métodos válidos: " + ex.allowed));
        } catch (Exception ex) {
            send(exchange, 500, error("Error interno del servicio."));
        } finally {
            exchange.close();
        }
    }

    private static Object route(String method, String[] path, Map<String, Object> body, String query) {
        String resource = path[2];
        String id = path.length > 3 ? path[3] : null;

        if ("vehicles".equals(resource)) {
            if (id == null && "GET".equals(method)) return STORE.vehicles();
            if (id == null && "POST".equals(method)) return STORE.createVehicle(body);
            if (id != null && path.length == 4 && "GET".equals(method)) return STORE.vehicle(id);
            if (id != null && path.length == 4 && "PUT".equals(method)) return STORE.updateVehicle(id, body);
            if (id != null && path.length == 4 && "DELETE".equals(method)) { STORE.deleteVehicle(id); return null; }
            throw new MethodNotAllowed("GET, POST, PUT, DELETE");
        }
        if ("users".equals(resource)) {
            if (id == null && "GET".equals(method)) return STORE.users();
            if (id == null && "POST".equals(method)) return STORE.createUser(body);
            if (id != null && path.length == 4 && "GET".equals(method)) return STORE.user(id);
            if (id != null && path.length == 4 && "PUT".equals(method)) return STORE.updateUser(id, body);
            if (id != null && path.length == 4 && "DELETE".equals(method)) { STORE.deleteUser(id); return null; }
            throw new MethodNotAllowed("GET, POST, PUT, DELETE");
        }
        if ("movements".equals(resource)) {
            if (id == null && "GET".equals(method)) return STORE.movements(query);
            if (path.length == 4 && "POST".equals(method) && "entries".equals(path[3])) return STORE.entry(body);
            if (path.length == 4 && "POST".equals(method) && "exits".equals(path[3])) return STORE.exit(body);
            throw new MethodNotAllowed("GET, POST");
        }
        if ("settings".equals(resource)) {
            if (path.length == 3 && "GET".equals(method)) return STORE.settings();
            if (path.length == 3 && "PUT".equals(method)) return STORE.updateSettings(body);
            throw new MethodNotAllowed("GET, PUT");
        }
        throw new NotFound("Recurso no encontrado.");
    }

    private static boolean needsBody(String method) { return "POST".equals(method) || "PUT".equals(method); }

    private static Map<String, Object> readBody(HttpExchange exchange) throws IOException {
        byte[] bytes = exchange.getRequestBody().readNBytes(MAX_BODY + 1);
        if (bytes.length > MAX_BODY) throw new BadRequest("El cuerpo supera el tamaño máximo permitido.");
        try { return Json.object(new String(bytes, StandardCharsets.UTF_8)); }
        catch (IllegalArgumentException ex) { throw new BadRequest("El cuerpo debe ser un objeto JSON válido."); }
    }

    private static Map<String, Object> error(String message) { return Map.of("mensaje", message); }

    private static void send(HttpExchange exchange, int status, Object value) throws IOException {
        byte[] bytes = Json.stringify(value).getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.getResponseHeaders().set("Cache-Control", "no-store");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
    }

    /** Almacén de demostración. Los datos se conservan durante la ejecución del servidor. */
    private static final class Store {
        private final Map<Integer, Map<String, Object>> vehicles = new ConcurrentHashMap<>();
        private final Map<Integer, Map<String, Object>> users = new ConcurrentHashMap<>();
        private final List<Map<String, Object>> movements = new ArrayList<>();
        private final AtomicInteger vehicleIds = new AtomicInteger();
        private final AtomicInteger userIds = new AtomicInteger();
        private final AtomicInteger movementIds = new AtomicInteger();
        private int capacity = 50;

        synchronized List<Map<String, Object>> vehicles() { return new ArrayList<>(vehicles.values()); }
        synchronized Map<String, Object> vehicle(String id) { return require(vehicles, id, "Vehículo"); }

        synchronized Map<String, Object> createVehicle(Map<String, Object> body) {
            String plate = required(body, "placa").toUpperCase(Locale.ROOT);
            if (!plate.matches("[A-Z0-9-]{5,10}")) throw new BadRequest("La placa debe tener entre 5 y 10 letras, números o guiones.");
            if (vehicles.values().stream().anyMatch(v -> plate.equals(v.get("placa")))) throw new Conflict("La placa ya está registrada.");
            Map<String, Object> v = new LinkedHashMap<>();
            v.put("id", vehicleIds.incrementAndGet()); v.put("placa", plate);
            v.put("marca", required(body, "marca")); v.put("modelo", required(body, "modelo"));
            v.put("color", required(body, "color")); v.put("propietario", required(body, "propietario"));
            v.put("estado", "FUERA"); vehicles.put((Integer) v.get("id"), v); return v;
        }

        synchronized Map<String, Object> updateVehicle(String id, Map<String, Object> body) {
            Map<String, Object> old = require(vehicles, id, "Vehículo");
            String plate = required(body, "placa").toUpperCase(Locale.ROOT);
            if (!plate.matches("[A-Z0-9-]{5,10}")) throw new BadRequest("La placa debe tener entre 5 y 10 letras, números o guiones.");
            if (vehicles.values().stream().anyMatch(v -> !v.get("id").equals(old.get("id")) && plate.equals(v.get("placa")))) throw new Conflict("La placa ya está registrada.");
            Map<String, Object> v = new LinkedHashMap<>(old); v.put("placa", plate);
            v.put("marca", required(body, "marca")); v.put("modelo", required(body, "modelo"));
            v.put("color", required(body, "color")); v.put("propietario", required(body, "propietario"));
            vehicles.put((Integer) old.get("id"), v); return v;
        }

        synchronized void deleteVehicle(String id) {
            Map<String, Object> v = require(vehicles, id, "Vehículo");
            if ("DENTRO".equals(v.get("estado"))) throw new Conflict("No se puede eliminar un vehículo que está dentro del parqueadero.");
            vehicles.remove((Integer) v.get("id"));
        }

        synchronized List<Map<String, Object>> users() { return new ArrayList<>(users.values()); }
        synchronized Map<String, Object> user(String id) { return require(users, id, "Usuario"); }

        synchronized Map<String, Object> createUser(Map<String, Object> body) {
            String username = required(body, "usuario"); String email = required(body, "correo");
            if (!username.matches("[A-Za-z0-9._-]{3,32}")) throw new BadRequest("El usuario debe tener entre 3 y 32 caracteres válidos.");
            if (!email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) throw new BadRequest("El correo no tiene un formato válido.");
            if (users.values().stream().anyMatch(u -> username.equalsIgnoreCase((String) u.get("usuario")) || email.equalsIgnoreCase((String) u.get("correo")))) throw new Conflict("El usuario o correo ya existe.");
            Map<String, Object> u = new LinkedHashMap<>(); u.put("id", userIds.incrementAndGet());
            u.put("usuario", username); u.put("correo", email); u.put("rol", role(body));
            u.put("activo", true); users.put((Integer) u.get("id"), u); return u;
        }

        synchronized Map<String, Object> updateUser(String id, Map<String, Object> body) {
            Map<String, Object> old = require(users, id, "Usuario");
            String username = required(body, "usuario"); String email = required(body, "correo");
            if (!username.matches("[A-Za-z0-9._-]{3,32}")) throw new BadRequest("El usuario debe tener entre 3 y 32 caracteres válidos.");
            if (!email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) throw new BadRequest("El correo no tiene un formato válido.");
            if (users.values().stream().anyMatch(u -> !u.get("id").equals(old.get("id")) && (username.equalsIgnoreCase((String) u.get("usuario")) || email.equalsIgnoreCase((String) u.get("correo"))))) throw new Conflict("El usuario o correo ya existe.");
            Map<String, Object> u = new LinkedHashMap<>(old); u.put("usuario", username); u.put("correo", email); u.put("rol", role(body));
            users.put((Integer) old.get("id"), u); return u;
        }

        synchronized void deleteUser(String id) { Map<String, Object> u = require(users, id, "Usuario"); users.remove((Integer) u.get("id")); }

        synchronized Map<String, Object> entry(Map<String, Object> body) {
            Map<String, Object> v = require(vehicles, String.valueOf(integer(body, "vehiculoId")), "Vehículo");
            if ("DENTRO".equals(v.get("estado"))) throw new Conflict("El vehículo ya tiene una entrada sin salida.");
            long inside = vehicles.values().stream().filter(x -> "DENTRO".equals(x.get("estado"))).count();
            if (inside >= capacity) throw new Conflict("El parqueadero alcanzó su capacidad máxima.");
            v = new LinkedHashMap<>(v); v.put("estado", "DENTRO"); vehicles.put((Integer) v.get("id"), v);
            return recordMovement(v, "ENTRADA", optional(body, "usuario"));
        }

        synchronized Map<String, Object> exit(Map<String, Object> body) {
            Map<String, Object> v = require(vehicles, String.valueOf(integer(body, "vehiculoId")), "Vehículo");
            if (!"DENTRO".equals(v.get("estado"))) throw new Conflict("El vehículo no tiene una entrada pendiente.");
            v = new LinkedHashMap<>(v); v.put("estado", "FUERA"); vehicles.put((Integer) v.get("id"), v);
            return recordMovement(v, "SALIDA", optional(body, "usuario"));
        }

        private Map<String, Object> recordMovement(Map<String, Object> v, String type, String username) {
            Map<String, Object> m = new LinkedHashMap<>(); m.put("id", movementIds.incrementAndGet());
            m.put("vehiculoId", v.get("id")); m.put("placa", v.get("placa")); m.put("tipo", type);
            m.put("usuario", username); m.put("fechaHora", Instant.now().toString()); movements.add(m); return m;
        }

        synchronized List<Map<String, Object>> movements(String query) {
            String type = null;
            if (query != null) for (String part : query.split("&")) if (part.startsWith("tipo=")) type = part.substring(5).toUpperCase(Locale.ROOT);
            final String filter = type;
            return movements.stream().filter(m -> filter == null || filter.equals(m.get("tipo"))).toList();
        }

        synchronized Map<String, Object> settings() { return Map.of("capacidad", capacity, "ocupados", vehicles.values().stream().filter(v -> "DENTRO".equals(v.get("estado"))).count()); }
        synchronized Map<String, Object> updateSettings(Map<String, Object> body) {
            int next = integer(body, "capacidad");
            if (next < 1 || next > 10_000) throw new BadRequest("La capacidad debe ser un número entre 1 y 10000.");
            long inside = vehicles.values().stream().filter(v -> "DENTRO".equals(v.get("estado"))).count();
            if (next < inside) throw new Conflict("La capacidad no puede ser menor que la ocupación actual.");
            capacity = next; return settings();
        }

        private static String role(Map<String, Object> body) {
            String value = optional(body, "rol"); if (value.isBlank()) value = "OPERADOR";
            if (!List.of("ADMIN", "OPERADOR").contains(value.toUpperCase(Locale.ROOT))) throw new BadRequest("El rol debe ser ADMIN u OPERADOR.");
            return value.toUpperCase(Locale.ROOT);
        }
    }

    private static Map<String, Object> require(Map<Integer, Map<String, Object>> source, String id, String label) {
        try { Map<String, Object> value = source.get(Integer.parseInt(id)); if (value != null) return value; }
        catch (NumberFormatException ignored) { /* El identificador se reporta como no encontrado. */ }
        throw new NotFound(label + " no encontrado.");
    }
    private static String required(Map<String, Object> body, String key) {
        Object value = body.get(key); if (!(value instanceof String text) || text.isBlank()) throw new BadRequest("El campo '" + key + "' es obligatorio y debe ser texto.");
        return text.trim();
    }
    private static String optional(Map<String, Object> body, String key) { Object value = body.get(key); return value instanceof String text ? text.trim() : ""; }
    private static int integer(Map<String, Object> body, String key) {
        Object value = body.get(key); if (value instanceof Number number) return number.intValue();
        throw new BadRequest("El campo '" + key + "' es obligatorio y debe ser numérico.");
    }

    private static class BadRequest extends RuntimeException { BadRequest(String message) { super(message); } }
    private static class NotFound extends RuntimeException { NotFound(String message) { super(message); } }
    private static class Conflict extends RuntimeException { Conflict(String message) { super(message); } }
    private static class MethodNotAllowed extends RuntimeException { final String allowed; MethodNotAllowed(String allowed) { super(); this.allowed = allowed; } }

    /** Parser y serializador pequeño para JSON sin dependencias externas. */
    private static final class Json {
        private final String text; private int pos;
        private Json(String text) { this.text = text; }
        static Map<String, Object> object(String text) { Object value = new Json(text).parse(); if (!(value instanceof Map<?, ?> map)) throw new IllegalArgumentException(); return (Map<String, Object>) map; }
        private Object parse() { Object value = value(); ws(); if (pos != text.length()) fail(); return value; }
        private Object value() {
            ws(); if (pos >= text.length()) return fail(); char c = text.charAt(pos);
            if (c == '{') return objectValue(); if (c == '[') return array(); if (c == '"') return string();
            if (c == 't') { literal("true"); return true; } if (c == 'f') { literal("false"); return false; } if (c == 'n') { literal("null"); return null; }
            if (c == '-' || Character.isDigit(c)) return number(); return fail();
        }
        private Map<String, Object> objectValue() { pos++; ws(); Map<String, Object> map = new LinkedHashMap<>(); if (take('}')) return map; do { ws(); String key = string(); ws(); expect(':'); if (map.putIfAbsent(key, value()) != null) fail(); ws(); if (take('}')) return map; expect(','); } while (true); }
        private List<Object> array() { pos++; ws(); List<Object> list = new ArrayList<>(); if (take(']')) return list; do { list.add(value()); ws(); if (take(']')) return list; expect(','); } while (true); }
        private String string() { expect('"'); StringBuilder out = new StringBuilder(); while (pos < text.length()) { char c = text.charAt(pos++); if (c == '"') return out.toString(); if (c < 32) return fail(); if (c != '\\') { out.append(c); continue; } if (pos >= text.length()) return fail(); char e = text.charAt(pos++); switch (e) { case '"', '\\', '/' -> out.append(e); case 'b' -> out.append('\b'); case 'f' -> out.append('\f'); case 'n' -> out.append('\n'); case 'r' -> out.append('\r'); case 't' -> out.append('\t'); case 'u' -> { if (pos + 4 > text.length()) return fail(); try { out.append((char) Integer.parseInt(text.substring(pos, pos + 4), 16)); } catch (NumberFormatException ex) { return fail(); } pos += 4; } default -> { return fail(); } } } return fail(); }
        private Number number() { int start = pos; if (take('-')) { } while (pos < text.length() && Character.isDigit(text.charAt(pos))) pos++; if (take('.')) while (pos < text.length() && Character.isDigit(text.charAt(pos))) pos++; try { return Integer.parseInt(text.substring(start, pos)); } catch (NumberFormatException ex) { try { return Double.parseDouble(text.substring(start, pos)); } catch (NumberFormatException ignored) { return fail(); } } }
        private void literal(String word) { if (!text.startsWith(word, pos)) fail(); pos += word.length(); }
        private void ws() { while (pos < text.length() && Character.isWhitespace(text.charAt(pos))) pos++; }
        private boolean take(char c) { if (pos < text.length() && text.charAt(pos) == c) { pos++; return true; } return false; }
        private void expect(char c) { if (!take(c)) fail(); }
        private static <T> T fail() { throw new IllegalArgumentException("JSON inválido"); }
        static String stringify(Object value) { StringBuilder out = new StringBuilder(); write(value, out); return out.toString(); }
        private static void write(Object value, StringBuilder out) {
            if (value == null) { out.append("null"); return; }
            if (value instanceof String s) { out.append('"'); for (char c : s.toCharArray()) { switch (c) { case '"' -> out.append("\\\""); case '\\' -> out.append("\\\\"); case '\n' -> out.append("\\n"); case '\r' -> out.append("\\r"); case '\t' -> out.append("\\t"); default -> { if (c < 32) out.append(String.format("\\u%04x", (int) c)); else out.append(c); } } } out.append('"'); return; }
            if (value instanceof Number || value instanceof Boolean) { out.append(value); return; }
            if (value instanceof Map<?, ?> map) { out.append('{'); boolean first = true; for (Map.Entry<?, ?> e : map.entrySet()) { if (!first) out.append(','); first = false; write(String.valueOf(e.getKey()), out); out.append(':'); write(e.getValue(), out); } out.append('}'); return; }
            if (value instanceof Iterable<?> items) { out.append('['); boolean first = true; for (Object item : items) { if (!first) out.append(','); first = false; write(item, out); } out.append(']'); return; }
            write(String.valueOf(value), out);
        }
    }
}
