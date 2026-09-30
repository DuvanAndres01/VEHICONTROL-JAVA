package com.vehicontrol.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión entre la aplicación
 * VEHICONTROL y la base de datos MySQL.
 *
 * La clase utiliza JDBC para establecer la conexión con la base
 * de datos y proporciona un método estático que puede ser utilizado
 * por las clases DAO del sistema.
 *
 * @author Duvan Arias
 */
public class Conexion {

    /**
     * URL de conexión a la base de datos MySQL.
     *
     * useSSL=false:
     * Desactiva el uso de SSL para la conexión local.
     *
     * serverTimezone=UTC:
     * Define la zona horaria utilizada por la conexión.
     *
     * allowPublicKeyRetrieval=true:
     * Permite la recuperación de la clave pública cuando
     * es requerida por el controlador MySQL.
     */
    private static final String URL =
            "jdbc:mysql://localhost:3306/vehicontrol_java"
            + "?useSSL=false"
            + "&serverTimezone=UTC"
            + "&allowPublicKeyRetrieval=true";

    /**
     * Usuario utilizado para conectarse a MySQL.
     */
    private static final String USUARIO = "root";

    /**
     * Contraseña utilizada para conectarse a MySQL.
     *
     * En el entorno local actual no se ha configurado
     * una contraseña para el usuario root.
     */
    private static final String PASSWORD = "";

    /**
     * Establece una conexión con la base de datos VEHICONTROL.
     *
     * Primero carga el controlador JDBC de MySQL y posteriormente
     * utiliza DriverManager para crear la conexión.
     *
     * @return objeto Connection con la conexión activa a MySQL
     * @throws SQLException si ocurre un error al establecer
     * la conexión o si no se encuentra el controlador MySQL
     */
    public static Connection conectar() throws SQLException {

        try {
            // Carga el controlador JDBC de MySQL.
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            // Convierte el error de carga del controlador
            // en una excepción compatible con JDBC.
            throw new SQLException(
                    "No se encontró el driver MySQL Connector/J",
                    e
            );
        }

        // Establece y devuelve la conexión con MySQL.
        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }
}