\# VEHICONTROL



Sistema web para el control de entrada y salida de vehículos de un conjunto residencial.



\## Descripción



VEHICONTROL es una aplicación web desarrollada para gestionar el registro de vehículos, controlar sus movimientos de entrada y salida, consultar el historial y administrar los usuarios y la configuración del sistema.



El proyecto fue desarrollado como parte de la formación del SENA y aplica una arquitectura basada en Java Web utilizando Servlets, JSP, DAO y MySQL.



\## Tecnologías utilizadas



\- Java 17

\- Jakarta Servlet

\- JSP

\- Maven

\- Apache Tomcat

\- MySQL

\- HTML5

\- CSS3

\- JavaScript

\- Git y GitHub



\## Módulos desarrollados



\### Login

Permite la autenticación de los usuarios registrados en el sistema.



\### Dashboard

Muestra información general del sistema:



\- Total de vehículos

\- Entradas registradas

\- Salidas registradas

\- Vehículos actualmente dentro del conjunto

\- Capacidad de parqueaderos

\- Espacios disponibles

\- Porcentaje de ocupación

\- Últimos movimientos



\### Registro de vehículos

Permite:



\- Registrar vehículos

\- Consultar vehículos

\- Editar vehículos

\- Eliminar vehículos

\- Validar placas duplicadas

\- Controlar el estado del vehículo



\### Entrada / Salida

Permite registrar los movimientos de los vehículos.



El sistema valida que un vehículo no pueda registrar dos entradas consecutivas ni dos salidas consecutivas.



\### Historial

Permite consultar los movimientos registrados con información de:



\- Placa

\- Marca

\- Modelo

\- Tipo de movimiento

\- Fecha y hora

\- Observación



\### Usuarios

Permite administrar los usuarios del sistema:



\- Crear usuarios

\- Editar usuarios

\- Eliminar usuarios

\- Asignar roles

\- Activar o desactivar usuarios

\- Validar correos duplicados



\### Configuración

Permite configurar información general del conjunto y parámetros del sistema.



\## Arquitectura



El proyecto utiliza una estructura basada en:



\- Model: clases que representan los datos.

\- DAO: acceso y operaciones sobre la base de datos.

\- Servlet: procesamiento de las solicitudes HTTP.

\- JSP: interfaz web.

\- MySQL: almacenamiento de información.



\## Métodos HTTP



El sistema utiliza los métodos HTTP:



\- GET para consultar información y mostrar formularios.

\- POST para registrar y modificar información.



\## Base de datos



El sistema utiliza MySQL como sistema gestor de base de datos.



Base de datos:



```text

vehicontrol



Usuario de prueba

Usuario administrador:

Correo: admin@vehicontrol.com
Contraseña: admin123
Ejecución del proyecto
1. Clonar el repositorio
git clone https://github.com/DuvanAndres01/VEHICONTROL-JAVA.git
2. Ingresar al proyecto
cd VEHICONTROL-JAVA
3. Configurar MySQL

Crear la base de datos:

CREATE DATABASE vehicontrol;

Configurar las credenciales de conexión utilizadas por el proyecto.

4. Compilar el proyecto

Ejecutar:

mvn clean package
5. Ejecutar en Apache Tomcat

Desplegar el archivo generado:

target/vehicontrol.war

en Apache Tomcat.

6. Acceder al sistema
http://localhost:8080/vehicontrol/
Control de versiones

El proyecto utiliza Git para el control de versiones y GitHub como repositorio remoto.

Repositorio:

https://github.com/DuvanAndres01/VEHICONTROL-JAVA.git

Evidencia

Proyecto correspondiente a:

GA7-220501096-AA2-EV02
Módulos de software codificados y probados
Autor

Duvan Arias

