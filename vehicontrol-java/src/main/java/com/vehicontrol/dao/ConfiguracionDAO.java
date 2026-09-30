package com.vehicontrol.dao;

import com.vehicontrol.config.Conexion;
import com.vehicontrol.model.Configuracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * DAO (Data Access Object) encargado de gestionar las operaciones
 * de acceso a datos relacionadas con la configuración del sistema.
 *
 * Esta clase permite consultar y actualizar la información almacenada
 * en la tabla configuracion de la base de datos VEHICONTROL.
 *
 * @author Duvan Arias
 */
public class ConfiguracionDAO {

    /**
     * Obtiene la configuración principal almacenada en la base de datos.
     *
     * La consulta obtiene el primer registro de configuración ordenado
     * por su identificador.
     *
     * @return objeto Configuracion con los datos encontrados,
     *         o null si no existe ningún registro.
     */
    public Configuracion obtener() {

        // Consulta SQL para obtener la configuración principal.
        String sql = """
                SELECT
                    id,
                    nombre_conjunto,
                    direccion,
                    telefono,
                    capacidad_parqueaderos,
                    registro_automatico,
                    notificaciones,
                    control_visitantes,
                    control_parqueaderos,
                    tiempo_sesion,
                    intentos_login,
                    idioma,
                    zona_horaria,
                    formato_fecha
                FROM configuracion
                ORDER BY id
                LIMIT 1
                """;

        try (
            // Establece la conexión con la base de datos.
            Connection conexion = Conexion.conectar();

            // Prepara la consulta SQL.
            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            // Ejecuta la consulta.
            ResultSet rs =
                    ps.executeQuery()
        ) {

            // Verifica si existe un registro de configuración.
            if (rs.next()) {

                // Crea el objeto que almacenará la información.
                Configuracion configuracion =
                        new Configuracion();

                configuracion.setId(
                        rs.getInt("id")
                );

                configuracion.setNombreConjunto(
                        rs.getString("nombre_conjunto")
                );

                configuracion.setDireccion(
                        rs.getString("direccion")
                );

                configuracion.setTelefono(
                        rs.getString("telefono")
                );

                configuracion.setCapacidadParqueaderos(
                        rs.getInt("capacidad_parqueaderos")
                );

                configuracion.setRegistroAutomatico(
                        rs.getBoolean("registro_automatico")
                );

                configuracion.setNotificaciones(
                        rs.getBoolean("notificaciones")
                );

                configuracion.setControlVisitantes(
                        rs.getBoolean("control_visitantes")
                );

                configuracion.setControlParqueaderos(
                        rs.getBoolean("control_parqueaderos")
                );

                configuracion.setTiempoSesion(
                        rs.getInt("tiempo_sesion")
                );

                configuracion.setIntentosLogin(
                        rs.getInt("intentos_login")
                );

                configuracion.setIdioma(
                        rs.getString("idioma")
                );

                configuracion.setZonaHoraria(
                        rs.getString("zona_horaria")
                );

                configuracion.setFormatoFecha(
                        rs.getString("formato_fecha")
                );

                // Devuelve la configuración obtenida.
                return configuracion;
            }

        } catch (Exception e) {

            // Convierte el error en una excepción de ejecución
            // para informar el problema a la capa superior.
            throw new RuntimeException(
                    "ERROR AL CONSULTAR CONFIGURACIÓN: "
                    + e.getMessage(),
                    e
            );
        }

        // Se retorna null cuando no existe configuración.
        return null;
    }

    /**
     * Actualiza la configuración existente en la base de datos.
     *
     * Los valores se establecen mediante parámetros preparados
     * para evitar la construcción directa de datos dentro
     * de la consulta SQL.
     *
     * @param configuracion objeto con los nuevos valores
     * @return true si se actualizó al menos un registro;
     *         false si no se realizó ninguna actualización.
     */
    public boolean actualizar(
            Configuracion configuracion) {

        // Consulta SQL utilizada para actualizar la configuración.
        String sql = """
                UPDATE configuracion
                SET
                    nombre_conjunto = ?,
                    direccion = ?,
                    telefono = ?,
                    capacidad_parqueaderos = ?,
                    registro_automatico = ?,
                    notificaciones = ?,
                    control_visitantes = ?,
                    control_parqueaderos = ?,
                    tiempo_sesion = ?,
                    intentos_login = ?,
                    idioma = ?,
                    zona_horaria = ?,
                    formato_fecha = ?
                WHERE id = ?
                """;

        try (
            // Establece la conexión con la base de datos.
            Connection conexion = Conexion.conectar();

            // Prepara la consulta de actualización.
            PreparedStatement ps =
                    conexion.prepareStatement(sql)
        ) {

            // Asigna los valores del objeto a los parámetros SQL.
            ps.setString(
                    1,
                    configuracion.getNombreConjunto()
            );

            ps.setString(
                    2,
                    configuracion.getDireccion()
            );

            ps.setString(
                    3,
                    configuracion.getTelefono()
            );

            ps.setInt(
                    4,
                    configuracion.getCapacidadParqueaderos()
            );

            ps.setBoolean(
                    5,
                    configuracion.isRegistroAutomatico()
            );

            ps.setBoolean(
                    6,
                    configuracion.isNotificaciones()
            );

            ps.setBoolean(
                    7,
                    configuracion.isControlVisitantes()
            );

            ps.setBoolean(
                    8,
                    configuracion.isControlParqueaderos()
            );

            ps.setInt(
                    9,
                    configuracion.getTiempoSesion()
            );

            ps.setInt(
                    10,
                    configuracion.getIntentosLogin()
            );

            ps.setString(
                    11,
                    configuracion.getIdioma()
            );

            ps.setString(
                    12,
                    configuracion.getZonaHoraria()
            );

            ps.setString(
                    13,
                    configuracion.getFormatoFecha()
            );

            ps.setInt(
                    14,
                    configuracion.getId()
            );

            // Ejecuta la actualización y verifica si afectó registros.
            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            // Informa cualquier error producido durante la actualización.
            throw new RuntimeException(
                    "ERROR AL ACTUALIZAR CONFIGURACIÓN: "
                    + e.getMessage(),
                    e
            );
        }
    }
}