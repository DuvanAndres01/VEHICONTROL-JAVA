package com.vehicontrol.model;

/**
 * Modelo que representa un vehículo registrado en el sistema VEHICONTROL.
 *
 * Esta clase contiene la información básica del vehículo, incluyendo
 * placa, marca, modelo, color, propietario y estado.
 *
 * @author Duvan Arias
 */
public class Vehiculo {

    private int id;
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private String propietario;
    private String estado;

    /**
     * Constructor vacío requerido para crear un objeto Vehiculo
     * y asignar posteriormente sus atributos mediante los métodos setters.
     */
    public Vehiculo() {
    }

    /**
     * Constructor para crear un vehículo con sus datos principales.
     *
     * @param placa placa o identificación del vehículo
     * @param marca marca del vehículo
     * @param modelo modelo o referencia del vehículo
     * @param color color del vehículo
     * @param propietario nombre del propietario
     * @param estado estado actual del vehículo
     */
    public Vehiculo(String placa,
                    String marca,
                    String modelo,
                    String color,
                    String propietario,
                    String estado) {

        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.propietario = propietario;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador del vehículo.
     *
     * @return identificador del vehículo
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificador del vehículo.
     *
     * @param id identificador del vehículo
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la placa del vehículo.
     *
     * @return placa del vehículo
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Asigna la placa del vehículo.
     *
     * @param placa placa del vehículo
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Obtiene la marca del vehículo.
     *
     * @return marca del vehículo
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Asigna la marca del vehículo.
     *
     * @param marca marca del vehículo
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene el modelo del vehículo.
     *
     * @return modelo del vehículo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Asigna el modelo del vehículo.
     *
     * @param modelo modelo del vehículo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene el color del vehículo.
     *
     * @return color del vehículo
     */
    public String getColor() {
        return color;
    }

    /**
     * Asigna el color del vehículo.
     *
     * @param color color del vehículo
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Obtiene el nombre del propietario.
     *
     * @return propietario del vehículo
     */
    public String getPropietario() {
        return propietario;
    }

    /**
     * Asigna el propietario del vehículo.
     *
     * @param propietario nombre del propietario
     */
    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    /**
     * Obtiene el estado actual del vehículo.
     *
     * @return estado del vehículo
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Asigna el estado del vehículo.
     *
     * @param estado estado del vehículo
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}