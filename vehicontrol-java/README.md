# VEHICONTROL

## Evidencia GA7-220501096-AA3-EV01

### Codificación de módulos del software stand-alone, web y móvil

VEHICONTROL es un sistema web desarrollado para gestionar el control de entrada y salida de vehículos en un conjunto residencial.

El proyecto permite registrar vehículos, controlar sus movimientos de entrada y salida, administrar usuarios y consultar información del sistema.

## Tecnologías utilizadas

- Java 17
- Jakarta Servlet
- JSP
- Maven
- Apache Tomcat
- MySQL
- HTML5
- CSS3
- JavaScript
- Git
- GitHub

## Módulos desarrollados

El sistema cuenta con los siguientes módulos:

- Inicio de sesión
- Dashboard
- Registro de vehículos
- Edición de vehículos
- Eliminación de vehículos
- Registro de entrada y salida
- Historial de movimientos
- Administración de usuarios
- Configuración del sistema

## Arquitectura

El proyecto utiliza una arquitectura basada en:

- **Model:** clases que representan las entidades del sistema.
- **DAO:** clases encargadas de la comunicación con la base de datos.
- **Servlet:** controladores encargados de recibir y procesar las solicitudes.
- **JSP:** vistas utilizadas para la interfaz web.
- **MySQL:** sistema gestor de base de datos.

### Estructura principal

```text
src/main/java/com/vehicontrol/
├── config/
│   └── Conexion.java
├── dao/
│   ├── ConfiguracionDAO.java
│   ├── MovimientoDAO.java
│   ├── UsuarioDAO.java
│   └── VehiculoDAO.java
├── model/
│   ├── Configuracion.java
│   ├── Movimiento.java
│   ├── Usuario.java
│   └── Vehiculo.java
└── servlet/
    ├── ConfiguracionServlet.java
    ├── DashboardServlet.java
    ├── HistorialServlet.java
    ├── LoginServlet.java
    ├── LogoutServlet.java
    ├── MovimientoServlet.java
    ├── NuevoVehiculoServlet.java
    ├── UsuarioServlet.java
    └── VehiculoServlet.java