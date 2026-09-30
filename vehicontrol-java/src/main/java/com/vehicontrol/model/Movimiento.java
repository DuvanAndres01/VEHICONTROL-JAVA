package com.vehicontrol.model;

import java.time.LocalDateTime;

/**
 * Modelo que representa un movimiento de entrada o salida
 * de un vehículo dentro del sistema VEHICONTROL.
 *
 * Contiene la información del vehículo, tipo de movimiento,
 * fecha, hora y observaciones relacionadas con el registro.
 *
 * @author Duvan Arias
 */
public class Movimiento {

    private int id;
    private int vehiculoId;
    private String tipo;
    private LocalDateTime fechaHora;
    private String observacion;
    private String placa;
    private String marca;
    private String modelo;

    /**
     * Constructor vacío utilizado para crear un objeto
     * Movimiento y asignar posteriormente sus propiedades.
     */
    public Movimiento() {
    }

    /**
     * Constructor utilizado para crear un movimiento
     * con la información básica necesaria para registrarlo.
     *
     * @param vehiculoId identificador del vehículo.
     * @param tipo tipo de movimiento: ENTRADA o SALIDA.
     * @param observacion observación asociada al movimiento.
     */
    public Movimiento(
            int vehiculoId,
            String tipo,
            String observacion) {

        this.vehiculoId = vehiculoId;
        this.tipo = tipo;
        this.observacion = observacion;
    }

    /**
     * Obtiene el identificador del movimiento.
     *
     * @return identificador del movimiento.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del movimiento.
     *
     * @param id identificador del movimiento.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador del vehículo asociado.
     *
     * @return identificador del vehículo.
     */
    public int getVehiculoId() {
        return vehiculoId;
    }

    /**
     * Establece el identificador del vehículo asociado.
     *
     * @param vehiculoId identificador del vehículo.
     */
    public void setVehiculoId(int vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    /**
     * Obtiene el tipo de movimiento.
     *
     * @return ENTRADA o SALIDA.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de movimiento.
     *
     * @param tipo tipo de movimiento.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la fecha y hora en que se registró el movimiento.
     *
     * @return fecha y hora del movimiento.
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece la fecha y hora del movimiento.
     *
     * @param fechaHora fecha y hora del movimiento.
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene la observación registrada para el movimiento.
     *
     * @return observación del movimiento.
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Establece la observación del movimiento.
     *
     * @param observacion observación asociada al movimiento.
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * Obtiene la placa del vehículo.
     *
     * @return placa del vehículo.
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Establece la placa del vehículo.
     *
     * @param placa placa del vehículo.
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Obtiene la marca del vehículo.
     *
     * @return marca del vehículo.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Establece la marca del vehículo.
     *
     * @param marca marca del vehículo.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene el modelo del vehículo.
     *
     * @return modelo del vehículo.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo del vehículo.
     *
     * @param modelo modelo del vehículo.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}