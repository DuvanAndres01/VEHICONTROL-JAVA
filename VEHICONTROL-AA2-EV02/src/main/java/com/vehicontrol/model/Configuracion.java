package com.vehicontrol.model;

public class Configuracion {

    private int id;

    private String nombreConjunto;
    private String direccion;
    private String telefono;
    private int capacidadParqueaderos;

    private boolean registroAutomatico;
    private boolean notificaciones;
    private boolean controlVisitantes;
    private boolean controlParqueaderos;

    private int tiempoSesion;
    private int intentosLogin;

    private String idioma;
    private String zonaHoraria;
    private String formatoFecha;


    public Configuracion() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNombreConjunto() {
        return nombreConjunto;
    }

    public void setNombreConjunto(String nombreConjunto) {
        this.nombreConjunto = nombreConjunto;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public int getCapacidadParqueaderos() {
        return capacidadParqueaderos;
    }

    public void setCapacidadParqueaderos(int capacidadParqueaderos) {
        this.capacidadParqueaderos = capacidadParqueaderos;
    }


    public boolean isRegistroAutomatico() {
        return registroAutomatico;
    }

    public void setRegistroAutomatico(boolean registroAutomatico) {
        this.registroAutomatico = registroAutomatico;
    }


    public boolean isNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(boolean notificaciones) {
        this.notificaciones = notificaciones;
    }


    public boolean isControlVisitantes() {
        return controlVisitantes;
    }

    public void setControlVisitantes(boolean controlVisitantes) {
        this.controlVisitantes = controlVisitantes;
    }


    public boolean isControlParqueaderos() {
        return controlParqueaderos;
    }

    public void setControlParqueaderos(boolean controlParqueaderos) {
        this.controlParqueaderos = controlParqueaderos;
    }


    public int getTiempoSesion() {
        return tiempoSesion;
    }

    public void setTiempoSesion(int tiempoSesion) {
        this.tiempoSesion = tiempoSesion;
    }


    public int getIntentosLogin() {
        return intentosLogin;
    }

    public void setIntentosLogin(int intentosLogin) {
        this.intentosLogin = intentosLogin;
    }


    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }


    public String getZonaHoraria() {
        return zonaHoraria;
    }

    public void setZonaHoraria(String zonaHoraria) {
        this.zonaHoraria = zonaHoraria;
    }


    public String getFormatoFecha() {
        return formatoFecha;
    }

    public void setFormatoFecha(String formatoFecha) {
        this.formatoFecha = formatoFecha;
    }
}