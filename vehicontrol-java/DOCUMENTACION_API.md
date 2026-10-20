# Documentación de servicios web — Vehicontrol

**Evidencia:** GA7-220501096-AA5-EV03 — Diseño y desarrollo de servicios web, proyecto  
**Versión de API:** 1.0.0  
**Base URL local:** `http://127.0.0.1:8081/api`  
**Formato:** JSON UTF-8

## Propósito y diseño

La API comunica las funciones del sistema Vehicontrol mediante recursos REST. Cada recurso agrupa operaciones relacionadas con vehículos, usuarios, movimientos de parqueo y configuración. Los identificadores son enteros asignados por el servicio. Las fechas de los movimientos se devuelven en formato ISO-8601 UTC.

Las solicitudes con cuerpo deben enviar `Content-Type: application/json`. Las respuestas de error usan el formato `{"mensaje":"Descripción del error"}`.

## 1. Estado del servicio

### `GET /health`

Comprueba que el proceso está disponible. No requiere parámetros.

**200 OK**

```json
{"estado":"activo","servicio":"Vehicontrol API"}
```

## 2. Vehículos

### `GET /vehicles`

Lista los vehículos registrados.

**200 OK:** arreglo de vehículos con `id`, `placa`, `marca`, `modelo`, `color`, `propietario` y `estado` (`FUERA` o `DENTRO`).

### `POST /vehicles`

Registra un vehículo. `placa`, `marca`, `modelo`, `color` y `propietario` son obligatorios. La placa admite de 5 a 10 letras, números o guiones y se guarda en mayúsculas.

```json
{"placa":"ABC-123","marca":"Renault","modelo":"2022","color":"Gris","propietario":"Duvan Arias"}
```

**201 Created:** vehículo creado, inicia con estado `FUERA`. **400 Bad Request:** campo faltante o placa inválida. **409 Conflict:** placa duplicada.

### `GET /vehicles/{id}`

Consulta un vehículo. Devuelve **200 OK** o **404 Not Found**.

### `PUT /vehicles/{id}`

Actualiza la placa, marca, modelo, color y propietario con el mismo formato de `POST`. Devuelve **200 OK** con el recurso actualizado, **400** si hay datos inválidos, **404** si no existe o **409** si la placa pertenece a otro vehículo.

### `DELETE /vehicles/{id}`

Elimina el vehículo y responde **204 No Content**. Responde **404** si no existe y **409 Conflict** si está dentro del parqueadero.

## 3. Usuarios

### `GET /users`

Lista los usuarios. La respuesta no incluye contraseñas.

### `POST /users`

Registra usuario con `usuario` (3–32 caracteres: letras, números, punto, guion o guion bajo), `correo` con formato de correo y `rol` (`ADMIN` u `OPERADOR`). El rol es opcional y por defecto se asigna `OPERADOR`.

```json
{"usuario":"duvan01","correo":"duvan@example.com","rol":"OPERADOR"}
```

**201 Created**, **400 Bad Request** por validación o **409 Conflict** si el nombre de usuario o el correo ya existe.

### `GET /users/{id}`

Consulta un usuario por identificador. Devuelve **200** o **404**.

### `PUT /users/{id}`

Actualiza usuario, correo y rol. Devuelve **200**, **400**, **404** o **409**, según el resultado.

### `DELETE /users/{id}`

Elimina un usuario y responde **204 No Content**, o **404** si no existe.

## 4. Movimientos de parqueo

### `POST /movements/entries`

Registra la entrada de un vehículo y lo cambia a estado `DENTRO`.

```json
{"vehiculoId":1,"usuario":"duvan01"}
```

`vehiculoId` es obligatorio y numérico; `usuario` es opcional. Responde **201 Created** con el movimiento, fecha y hora. Responde **400** por identificador faltante/no numérico, **404** si el vehículo no existe o **409** si ya está dentro o se alcanzó la capacidad.

### `POST /movements/exits`

Registra la salida de un vehículo, lo cambia a estado `FUERA` y devuelve el movimiento (**201 Created**). Usa el mismo cuerpo de entrada. Responde **409 Conflict** si no hay una entrada pendiente.

### `GET /movements`

Lista el historial de entradas y salidas. Admite filtro opcional `?tipo=ENTRADA` o `?tipo=SALIDA`.

**200 OK:** arreglo con `id`, `vehiculoId`, `placa`, `tipo`, `usuario` y `fechaHora`.

## 5. Configuración del parqueadero

### `GET /settings`

Devuelve la capacidad configurada y la cantidad actual de vehículos dentro.

```json
{"capacidad":50,"ocupados":0}
```

### `PUT /settings`

Actualiza la capacidad con un entero entre 1 y 10000.

```json
{"capacidad":80}
```

Responde **200 OK** con la configuración, **400 Bad Request** si falta el número o está fuera del rango, o **409 Conflict** si la nueva capacidad queda por debajo de la ocupación actual.

## Códigos HTTP comunes

| Código | Uso |
|---|---|
| 200 | Consulta o actualización satisfactoria |
| 201 | Recurso o movimiento creado |
| 204 | Eliminación satisfactoria, sin cuerpo |
| 400 | JSON, campo o validación incorrecta |
| 404 | Recurso o identificador no encontrado |
| 405 | Método HTTP no permitido para la ruta |
| 409 | Conflicto con el estado actual o datos duplicados |
| 500 | Error interno no controlado |

## Uso en Postman

1. Inicia el servicio siguiendo el README.
2. Crea una solicitud y selecciona el método de la tabla.
3. Usa `http://127.0.0.1:8081/api` más la ruta, por ejemplo `/vehicles`.
4. Para `POST` o `PUT`, selecciona **Body → raw → JSON** y copia el ejemplo de la operación.
5. Envía primero `POST /vehicles`, guarda el `id` de la respuesta y úsalo en las solicitudes de movimiento y consulta.

## Límites de esta versión

El almacenamiento es en memoria: los registros se pierden al detener el proceso. El prototipo no incluye sesiones, tokens ni roles aplicados como autorización HTTP; los roles se registran como datos del usuario. Antes de desplegarlo para uso real se debe conectar con la base de datos del proyecto y proteger los servicios.
