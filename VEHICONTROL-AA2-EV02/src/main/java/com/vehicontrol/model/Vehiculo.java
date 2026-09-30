package com.vehicontrol.model;

public class Vehiculo {

    private int id;
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private String propietario;
    private String estado;

    public Vehiculo() {
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}