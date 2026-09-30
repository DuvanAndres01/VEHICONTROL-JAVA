package com.vehicontrol.dao;

import com.vehicontrol.config.Conexion;
import com.vehicontrol.model.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    // ==========================================
    // LISTAR VEHÍCULOS
    // ==========================================
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


    // ==========================================
    // VERIFICAR SI EXISTE UNA PLACA
    // ==========================================
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


    // ==========================================
    // VERIFICAR PLACA EXCEPTO UN ID
    // ==========================================
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


    // ==========================================
    // INSERTAR VEHÍCULO
    // ==========================================
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


    // ==========================================
    // BUSCAR VEHÍCULO POR ID
    // ==========================================
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


    // ==========================================
    // ACTUALIZAR VEHÍCULO
    // ==========================================
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


    // ==========================================
    // ELIMINAR VEHÍCULO
    // ==========================================
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