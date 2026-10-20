# Vehicontrol API — GA7-220501096-AA5-EV03

API REST local para las funciones principales del proyecto formativo Vehicontrol. Está desarrollada en Java 17 usando el servidor HTTP incluido en el JDK, sin dependencias externas.

## Requisitos y ejecución

- JDK 17 o superior.
- Maven 3.8 o superior.

Desde esta carpeta, compilar y ejecutar:

```powershell
mvn clean package
java -jar target/vehicontrol-api-1.0.0.jar
```

La API queda disponible en `http://127.0.0.1:8081/api`. Para cambiar el puerto, define la variable de entorno `PORT` antes de iniciar. La documentación completa de cada servicio está en [`DOCUMENTACION_API.md`](DOCUMENTACION_API.md), y el contrato OpenAPI se encuentra en [`openapi.yaml`](openapi.yaml).

## Recursos

| Recurso | Servicios |
|---|---|
| Vehículos | Listar, crear, consultar, actualizar y eliminar |
| Usuarios | Listar, crear, consultar, actualizar y eliminar |
| Movimientos | Registrar entrada/salida y consultar historial |
| Configuración | Consultar y ajustar la capacidad del parqueadero |
| Salud | Verificar disponibilidad del servicio |

## Versionamiento

Repositorio: <https://github.com/DuvanAndres01/VEHICONTROL-JAVA>

Rama de trabajo de esta evidencia: `aa5-ev03-vehicontrol-api`.

## Alcance del prototipo

Los datos se guardan en memoria mientras el proceso está activo y se reinician al cerrar la API. Esta versión permite demostrar las operaciones y reglas principales; para producción se debe integrar almacenamiento persistente con la base de datos, autenticar y autorizar cada solicitud, y configurar HTTPS.
