# API de registro e inicio de sesión Vehicontrol

Proyecto Java 17 que expone un servicio HTTP JSON para el caso de autenticación. Incluye comentarios en el código y no requiere dependencias externas para ejecutarse.

## Requisitos

- JDK 17 o superior
- Maven 3.8 o superior

## Compilar y ejecutar

Desde la carpeta del proyecto:

```bash
mvn clean package
java -jar target/auth-api-1.0.0.jar
```

El servicio escucha en `http://127.0.0.1:8080`. Para cambiar el puerto, define la variable de entorno `PORT`.

## Endpoints

### Registrar usuario

`POST /api/auth/register`

```json
{"usuario":"duvan01","contrasena":"ClaveSegura123"}
```

Respuestas principales: `201` registro satisfactorio; `400` datos o formato inválido; `409` usuario existente. El usuario admite de 3 a 32 caracteres alfanuméricos, punto, guion y guion bajo. La contraseña admite de 8 a 128 caracteres.

### Iniciar sesión

`POST /api/auth/login`

Usa el mismo JSON. Devuelve `200` y “Autenticación satisfactoria” si los datos coinciden; de lo contrario devuelve `401` y “Error en la autenticación”.

Ejemplo con curl:

```bash
curl -i -X POST http://127.0.0.1:8080/api/auth/register -H "Content-Type: application/json" -d "{\"usuario\":\"duvan01\",\"contrasena\":\"ClaveSegura123\"}"
curl -i -X POST http://127.0.0.1:8080/api/auth/login -H "Content-Type: application/json" -d "{\"usuario\":\"duvan01\",\"contrasena\":\"ClaveSegura123\"}"
```

## Consideraciones del prototipo

Las cuentas se guardan en memoria y se eliminan al detener el servicio. Las contraseñas se almacenan como derivaciones PBKDF2 con sal aleatoria. El servidor se enlaza a `127.0.0.1` para ejecución local; antes de desplegarlo en un entorno real se debe añadir almacenamiento persistente, HTTPS, control de abuso y configuración de despliegue.
