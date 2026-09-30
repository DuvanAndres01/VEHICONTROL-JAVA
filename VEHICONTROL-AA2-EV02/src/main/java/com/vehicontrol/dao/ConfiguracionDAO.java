package com.vehicontrol.dao;

import com.vehicontrol.config.Conexion;
import com.vehicontrol.model.Configuracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConfiguracionDAO {


    public Configuracion obtener() {

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
            Connection conexion = Conexion.conectar();

            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            if (rs.next()) {

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

                return configuracion;
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL CONSULTAR CONFIGURACIÓN: "
                    + e.getMessage(),
                    e
            );
        }

        return null;
    }


    public boolean actualizar(
            Configuracion configuracion) {

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
            Connection conexion = Conexion.conectar();

            PreparedStatement ps =
                    conexion.prepareStatement(sql)
        ) {

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


            return ps.executeUpdate() > 0;


        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL ACTUALIZAR CONFIGURACIÓN: "
                    + e.getMessage(),
                    e
            );
        }
    }
}