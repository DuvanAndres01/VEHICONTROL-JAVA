package com.vehicontrol.dao;

import com.vehicontrol.config.Conexion;
import com.vehicontrol.model.Movimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO {

    // =========================================================
    // OBTENER ÚLTIMO MOVIMIENTO DEL VEHÍCULO
    // =========================================================

    public String obtenerUltimoTipo(int vehiculoId) {

        String sql = """
                SELECT tipo
                FROM movimientos
                WHERE vehiculo_id = ?
                ORDER BY fecha_hora DESC, id DESC
                LIMIT 1
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, vehiculoId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getString("tipo");
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL OBTENER EL ÚLTIMO MOVIMIENTO: "
                    + e.getMessage(),
                    e
            );
        }

        return null;
    }


    // =========================================================
    // REGISTRAR MOVIMIENTO
    // =========================================================

    public boolean registrar(Movimiento movimiento) {

        String sqlUltimoMovimiento = """
                SELECT tipo
                FROM movimientos
                WHERE vehiculo_id = ?
                ORDER BY fecha_hora DESC, id DESC
                LIMIT 1
                """;

        String sqlMovimiento = """
                INSERT INTO movimientos
                (vehiculo_id, tipo, observacion)
                VALUES (?, ?, ?)
                """;

        String sqlEstado = """
                UPDATE vehiculos
                SET estado = ?
                WHERE id = ?
                """;

        try (
            Connection conexion = Conexion.conectar()
        ) {

            conexion.setAutoCommit(false);

            try {

                // =================================================
                // 1. CONSULTAR ÚLTIMO MOVIMIENTO
                // =================================================

                String ultimoTipo = null;

                try (
                    PreparedStatement psUltimo =
                            conexion.prepareStatement(
                                    sqlUltimoMovimiento
                            )
                ) {

                    psUltimo.setInt(
                            1,
                            movimiento.getVehiculoId()
                    );

                    try (
                        ResultSet rs =
                                psUltimo.executeQuery()
                    ) {

                        if (rs.next()) {

                            ultimoTipo =
                                    rs.getString("tipo");
                        }
                    }
                }


                // =================================================
                // 2. VALIDAR SECUENCIA ENTRADA / SALIDA
                // =================================================

                String nuevoTipo =
                        movimiento.getTipo();

                if (nuevoTipo == null) {

                    throw new IllegalArgumentException(
                            "El tipo de movimiento es obligatorio."
                    );
                }

                nuevoTipo =
                        nuevoTipo.trim().toUpperCase();


                // -----------------------------------------------
                // ENTRADA
                // -----------------------------------------------

                if ("ENTRADA".equals(nuevoTipo)
                        && "ENTRADA".equalsIgnoreCase(
                                ultimoTipo)) {

                    throw new IllegalArgumentException(
                            "El vehículo ya se encuentra dentro del conjunto. "
                            + "Debe registrar una SALIDA antes de registrar otra ENTRADA."
                    );
                }


                // -----------------------------------------------
                // SALIDA
                // -----------------------------------------------

                if ("SALIDA".equals(nuevoTipo)
                        && "SALIDA".equalsIgnoreCase(
                                ultimoTipo)) {

                    throw new IllegalArgumentException(
                            "El vehículo ya se encuentra fuera del conjunto. "
                            + "Debe registrar una ENTRADA antes de registrar otra SALIDA."
                    );
                }


                // -----------------------------------------------
                // VALIDAR TIPO
                // -----------------------------------------------

                if (!"ENTRADA".equals(nuevoTipo)
                        && !"SALIDA".equals(nuevoTipo)) {

                    throw new IllegalArgumentException(
                            "El tipo de movimiento no es válido."
                    );
                }


                // =================================================
                // 3. REGISTRAR MOVIMIENTO
                // =================================================

                try (
                    PreparedStatement psMovimiento =
                            conexion.prepareStatement(
                                    sqlMovimiento
                            )
                ) {

                    psMovimiento.setInt(
                            1,
                            movimiento.getVehiculoId()
                    );

                    psMovimiento.setString(
                            2,
                            nuevoTipo
                    );

                    psMovimiento.setString(
                            3,
                            movimiento.getObservacion()
                    );

                    psMovimiento.executeUpdate();
                }


                // =================================================
                // 4. ACTUALIZAR ESTADO DEL VEHÍCULO
                // =================================================

                String nuevoEstado;

                if ("ENTRADA".equals(nuevoTipo)) {

                    nuevoEstado = "dentro";

                } else {

                    nuevoEstado = "fuera";
                }


                try (
                    PreparedStatement psEstado =
                            conexion.prepareStatement(
                                    sqlEstado
                            )
                ) {

                    psEstado.setString(
                            1,
                            nuevoEstado
                    );

                    psEstado.setInt(
                            2,
                            movimiento.getVehiculoId()
                    );

                    psEstado.executeUpdate();
                }


                // =================================================
                // 5. CONFIRMAR
                // =================================================

                conexion.commit();

                return true;

            } catch (Exception e) {

                conexion.rollback();

                throw e;
            }

        } catch (Exception e) {

            if (e instanceof IllegalArgumentException) {
                throw new RuntimeException(
                        e.getMessage()
                );
            }

            throw new RuntimeException(
                    "ERROR AL REGISTRAR MOVIMIENTO: "
                    + e.getMessage(),
                    e
            );
        }
    }


    // =========================================================
    // LISTAR MOVIMIENTOS
    // =========================================================

    public List<Movimiento> listar() {

        List<Movimiento> lista =
                new ArrayList<>();

        String sql = """
                SELECT
                    m.id,
                    m.vehiculo_id,
                    v.placa,
                    v.marca,
                    v.modelo,
                    m.tipo,
                    m.fecha_hora,
                    m.observacion
                FROM movimientos m
                INNER JOIN vehiculos v
                    ON m.vehiculo_id = v.id
                ORDER BY m.fecha_hora DESC
                """;

        try (
            Connection conexion =
                    Conexion.conectar();

            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            while (rs.next()) {

                Movimiento movimiento =
                        new Movimiento();

                movimiento.setId(
                        rs.getInt("id")
                );

                movimiento.setVehiculoId(
                        rs.getInt("vehiculo_id")
                );

                movimiento.setPlaca(
                        rs.getString("placa")
                );

                movimiento.setMarca(
                        rs.getString("marca")
                );

                movimiento.setModelo(
                        rs.getString("modelo")
                );

                movimiento.setTipo(
                        rs.getString("tipo")
                );

                if (rs.getTimestamp(
                        "fecha_hora") != null) {

                    movimiento.setFechaHora(
                            rs.getTimestamp(
                                    "fecha_hora"
                            ).toLocalDateTime()
                    );
                }

                movimiento.setObservacion(
                        rs.getString("observacion")
                );

                lista.add(movimiento);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL CONSULTAR MOVIMIENTOS: "
                    + e.getMessage(),
                    e
            );
        }

        return lista;
    }


    // =========================================================
    // CONTAR ENTRADAS
    // =========================================================

    public int contarEntradas() {

        String sql = """
                SELECT COUNT(*)
                FROM movimientos
                WHERE tipo = 'ENTRADA'
                """;

        try (
            Connection conexion =
                    Conexion.conectar();

            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL CONTAR ENTRADAS: "
                    + e.getMessage(),
                    e
            );
        }

        return 0;
    }


    // =========================================================
    // CONTAR SALIDAS
    // =========================================================

    public int contarSalidas() {

        String sql = """
                SELECT COUNT(*)
                FROM movimientos
                WHERE tipo = 'SALIDA'
                """;

        try (
            Connection conexion =
                    Conexion.conectar();

            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL CONTAR SALIDAS: "
                    + e.getMessage(),
                    e
            );
        }

        return 0;
    }


    // =========================================================
    // CONTAR VEHÍCULOS DENTRO
    // =========================================================

    public int contarVehiculosDentro() {

        String sql = """
                SELECT COUNT(*)
                FROM (
                    SELECT m.vehiculo_id
                    FROM movimientos m
                    INNER JOIN (
                        SELECT
                            vehiculo_id,
                            MAX(fecha_hora) AS ultima_fecha
                        FROM movimientos
                        GROUP BY vehiculo_id
                    ) ultimo
                        ON m.vehiculo_id = ultimo.vehiculo_id
                        AND m.fecha_hora = ultimo.ultima_fecha
                    WHERE m.tipo = 'ENTRADA'
                ) vehiculos_dentro
                """;

        try (
            Connection conexion =
                    Conexion.conectar();

            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL CONTAR VEHICULOS DENTRO: "
                    + e.getMessage(),
                    e
            );
        }

        return 0;
    }
}