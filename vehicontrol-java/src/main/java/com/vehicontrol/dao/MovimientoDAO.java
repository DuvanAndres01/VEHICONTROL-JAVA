package com.vehicontrol.dao;

import com.vehicontrol.config.Conexion;
import com.vehicontrol.model.Movimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) encargado de gestionar las operaciones
 * relacionadas con los movimientos de entrada y salida de vehículos.
 *
 * Esta clase permite:
 * - Consultar el último movimiento de un vehículo.
 * - Registrar entradas y salidas.
 * - Validar la secuencia de movimientos.
 * - Actualizar el estado del vehículo.
 * - Listar el historial de movimientos.
 * - Obtener estadísticas de entradas, salidas y vehículos dentro.
 *
 * @author Duvan Arias
 */
public class MovimientoDAO {

    /**
     * Obtiene el tipo del último movimiento registrado
     * para un vehículo específico.
     *
     * La consulta ordena los movimientos desde el más reciente
     * hasta el más antiguo.
     *
     * @param vehiculoId identificador del vehículo
     * @return tipo del último movimiento o null si no existen movimientos
     */
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

    /**
     * Registra un movimiento de entrada o salida de un vehículo.
     *
     * Antes de registrar el movimiento se consulta el último movimiento
     * del vehículo para evitar dos entradas o dos salidas consecutivas.
     *
     * También actualiza el estado actual del vehículo:
     * - ENTRADA: estado "dentro".
     * - SALIDA: estado "fuera".
     *
     * La operación se ejecuta dentro de una transacción. Si ocurre
     * algún error, se realiza un rollback para evitar inconsistencias
     * entre el movimiento registrado y el estado del vehículo.
     *
     * @param movimiento objeto que contiene los datos del movimiento
     * @return true si el movimiento fue registrado correctamente
     */
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

            // Inicia una transacción para garantizar la integridad
            // entre el registro del movimiento y el estado del vehículo.
            conexion.setAutoCommit(false);

            try {

                /*
                 * 1. CONSULTAR ÚLTIMO MOVIMIENTO
                 *
                 * Se obtiene el último movimiento registrado para
                 * determinar si el vehículo se encuentra dentro o fuera.
                 */
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

                /*
                 * 2. VALIDAR SECUENCIA ENTRADA / SALIDA
                 *
                 * Se normaliza el tipo recibido para permitir valores
                 * escritos con espacios o diferentes combinaciones
                 * de mayúsculas y minúsculas.
                 */
                String nuevoTipo =
                        movimiento.getTipo();

                if (nuevoTipo == null) {

                    throw new IllegalArgumentException(
                            "El tipo de movimiento es obligatorio."
                    );
                }

                nuevoTipo =
                        nuevoTipo.trim().toUpperCase();

                // Validación para evitar dos entradas consecutivas.
                if ("ENTRADA".equals(nuevoTipo)
                        && "ENTRADA".equalsIgnoreCase(
                                ultimoTipo)) {

                    throw new IllegalArgumentException(
                            "El vehículo ya se encuentra dentro del conjunto. "
                            + "Debe registrar una SALIDA antes de registrar otra ENTRADA."
                    );
                }

                // Validación para evitar dos salidas consecutivas.
                if ("SALIDA".equals(nuevoTipo)
                        && "SALIDA".equalsIgnoreCase(
                                ultimoTipo)) {

                    throw new IllegalArgumentException(
                            "El vehículo ya se encuentra fuera del conjunto. "
                            + "Debe registrar una ENTRADA antes de registrar otra SALIDA."
                    );
                }

                // Verifica que el tipo recibido sea válido.
                if (!"ENTRADA".equals(nuevoTipo)
                        && !"SALIDA".equals(nuevoTipo)) {

                    throw new IllegalArgumentException(
                            "El tipo de movimiento no es válido."
                    );
                }

                /*
                 * 3. REGISTRAR MOVIMIENTO
                 *
                 * Inserta el nuevo movimiento en la tabla movimientos.
                 */
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

                /*
                 * 4. ACTUALIZAR ESTADO DEL VEHÍCULO
                 *
                 * El estado se sincroniza con el movimiento registrado.
                 */
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

                /*
                 * 5. CONFIRMAR TRANSACCIÓN
                 *
                 * Confirma los cambios realizados en la base de datos.
                 */
                conexion.commit();

                return true;

            } catch (Exception e) {

                // Revierte todos los cambios si ocurre un error.
                conexion.rollback();

                throw e;
            }

        } catch (Exception e) {

            // Los errores de validación se devuelven con su mensaje.
            if (e instanceof IllegalArgumentException) {

                throw new RuntimeException(
                        e.getMessage()
                );
            }

            // Manejo general de errores de registro.
            throw new RuntimeException(
                    "ERROR AL REGISTRAR MOVIMIENTO: "
                    + e.getMessage(),
                    e
            );
        }
    }

    /**
     * Obtiene todos los movimientos registrados.
     *
     * La consulta relaciona la tabla de movimientos con la tabla
     * de vehículos para mostrar información adicional como placa,
     * marca y modelo.
     *
     * @return lista de movimientos ordenados desde el más reciente
     */
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

            // Recorre todos los registros obtenidos.
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

                // Convierte la fecha SQL a LocalDateTime.
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

    /**
     * Cuenta la cantidad total de movimientos de tipo ENTRADA.
     *
     * @return cantidad de entradas registradas
     */
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

    /**
     * Cuenta la cantidad total de movimientos de tipo SALIDA.
     *
     * @return cantidad de salidas registradas
     */
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

    /**
     * Cuenta los vehículos que actualmente se encuentran dentro
     * del conjunto residencial.
     *
     * Para determinar el estado actual, se toma el último movimiento
     * registrado de cada vehículo. Si dicho movimiento corresponde
     * a una ENTRADA, el vehículo se considera actualmente dentro.
     *
     * @return cantidad de vehículos actualmente dentro
     */
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