package com.vehicontrol.dao;

import com.vehicontrol.config.Conexion;
import com.vehicontrol.model.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) encargado de gestionar las operaciones
 * relacionadas con los vehículos registrados en el sistema VEHICONTROL.
 *
 * Esta clase permite consultar, insertar, actualizar, eliminar y
 * validar vehículos almacenados en la base de datos.
 *
 * @author Duvan Arias
 */
public class VehiculoDAO {

    /**
     * Obtiene todos los vehículos registrados en la base de datos.
     *
     * Los registros se ordenan de forma descendente utilizando
     * el identificador del vehículo.
     *
     * @return lista de vehículos registrados
     */
    public List<Vehiculo> listar() {

        List<Vehiculo> lista = new ArrayList<>();

        String sql = """
                SELECT id, placa, marca, modelo, color, propietario, estado
                FROM vehiculos
                ORDER BY id DESC
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            // Recorre los registros obtenidos de la base de datos.
            while (rs.next()) {

                Vehiculo vehiculo = new Vehiculo();

                vehiculo.setId(rs.getInt("id"));
                vehiculo.setPlaca(rs.getString("placa"));
                vehiculo.setMarca(rs.getString("marca"));
                vehiculo.setModelo(rs.getString("modelo"));
                vehiculo.setColor(rs.getString("color"));
                vehiculo.setPropietario(rs.getString("propietario"));
                vehiculo.setEstado(rs.getString("estado"));

                lista.add(vehiculo);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL CONSULTAR VEHICULOS: "
                    + e.getMessage(),
                    e
            );
        }

        return lista;
    }

    /**
     * Verifica si una placa ya se encuentra registrada.
     *
     * La comparación ignora espacios al inicio y al final
     * y no diferencia entre mayúsculas y minúsculas.
     *
     * @param placa placa que se desea verificar
     * @return true si la placa existe; false en caso contrario
     */
    public boolean existePlaca(String placa) {

        String sql = """
                SELECT COUNT(*)
                FROM vehiculos
                WHERE UPPER(TRIM(placa)) = UPPER(TRIM(?))
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, placa);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL VERIFICAR PLACA: "
                    + e.getMessage(),
                    e
            );
        }

        return false;
    }

    /**
     * Verifica si una placa pertenece a otro vehículo.
     *
     * Este método se utiliza durante la edición de un vehículo
     * para evitar que dos registros tengan la misma placa.
     *
     * @param placa placa que se desea verificar
     * @param id identificador del vehículo que se debe excluir
     * @return true si la placa pertenece a otro vehículo;
     *         false en caso contrario
     */
    public boolean existePlacaExceptoId(
            String placa,
            int id) {

        String sql = """
                SELECT COUNT(*)
                FROM vehiculos
                WHERE UPPER(TRIM(placa)) = UPPER(TRIM(?))
                AND id <> ?
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, placa);
            ps.setInt(2, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL VERIFICAR PLACA: "
                    + e.getMessage(),
                    e
            );
        }

        return false;
    }

    /**
     * Inserta un nuevo vehículo en la base de datos.
     *
     * @param vehiculo objeto Vehiculo con los datos que se desean registrar
     * @return true si el vehículo fue insertado correctamente;
     *         false si no se realizó la inserción
     */
    public boolean insertar(Vehiculo vehiculo) {

        String sql = """
                INSERT INTO vehiculos
                (placa, marca, modelo, color, propietario, estado)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            // Asigna los datos del vehículo a los parámetros SQL.
            ps.setString(
                    1,
                    vehiculo.getPlaca()
            );

            ps.setString(
                    2,
                    vehiculo.getMarca()
            );

            ps.setString(
                    3,
                    vehiculo.getModelo()
            );

            ps.setString(
                    4,
                    vehiculo.getColor()
            );

            ps.setString(
                    5,
                    vehiculo.getPropietario()
            );

            ps.setString(
                    6,
                    vehiculo.getEstado()
            );

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL INSERTAR VEHICULO: "
                    + e.getMessage(),
                    e
            );
        }
    }

    /**
     * Busca un vehículo mediante su identificador.
     *
     * @param id identificador del vehículo
     * @return objeto Vehiculo si existe; null si no se encuentra
     */
    public Vehiculo buscarPorId(int id) {

        String sql = """
                SELECT id, placa, marca, modelo, color, propietario, estado
                FROM vehiculos
                WHERE id = ?
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Vehiculo vehiculo = new Vehiculo();

                    vehiculo.setId(
                            rs.getInt("id")
                    );

                    vehiculo.setPlaca(
                            rs.getString("placa")
                    );

                    vehiculo.setMarca(
                            rs.getString("marca")
                    );

                    vehiculo.setModelo(
                            rs.getString("modelo")
                    );

                    vehiculo.setColor(
                            rs.getString("color")
                    );

                    vehiculo.setPropietario(
                            rs.getString("propietario")
                    );

                    vehiculo.setEstado(
                            rs.getString("estado")
                    );

                    return vehiculo;
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL BUSCAR VEHICULO: "
                    + e.getMessage(),
                    e
            );
        }

        return null;
    }

    /**
     * Actualiza los datos principales de un vehículo.
     *
     * El estado del vehículo no se modifica mediante este método,
     * ya que su control se realiza mediante los movimientos
     * de entrada y salida.
     *
     * @param vehiculo objeto Vehiculo con los datos actualizados
     * @return true si el vehículo fue actualizado;
     *         false si no se realizó ninguna actualización
     */
    public boolean actualizar(Vehiculo vehiculo) {

        String sql = """
                UPDATE vehiculos
                SET placa = ?,
                    marca = ?,
                    modelo = ?,
                    color = ?,
                    propietario = ?
                WHERE id = ?
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    vehiculo.getPlaca()
            );

            ps.setString(
                    2,
                    vehiculo.getMarca()
            );

            ps.setString(
                    3,
                    vehiculo.getModelo()
            );

            ps.setString(
                    4,
                    vehiculo.getColor()
            );

            ps.setString(
                    5,
                    vehiculo.getPropietario()
            );

            ps.setInt(
                    6,
                    vehiculo.getId()
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL ACTUALIZAR VEHICULO: "
                    + e.getMessage(),
                    e
            );
        }
    }

    /**
     * Elimina un vehículo de la base de datos.
     *
     * @param id identificador del vehículo que se desea eliminar
     * @return true si el vehículo fue eliminado;
     *         false si no se encontró el registro
     */
    public boolean eliminar(int id) {

        String sql = """
                DELETE FROM vehiculos
                WHERE id = ?
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL ELIMINAR VEHICULO: "
                    + e.getMessage(),
                    e
            );
        }
    }
}