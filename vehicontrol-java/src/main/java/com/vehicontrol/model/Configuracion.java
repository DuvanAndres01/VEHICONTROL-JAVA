package com.vehicontrol.model;

/**
 * Modelo que representa la configuración general del sistema VEHICONTROL.
 *
 * Contiene la información del conjunto residencial, la capacidad
 * de parqueaderos y las diferentes opciones de funcionamiento
 * del sistema.
 *
 * @author Duvan Arias
 */
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

    /**
     * Constructor vacío utilizado para crear una configuración
     * y posteriormente asignar sus propiedades mediante setters.
     */
    public Configuracion() {
    }

    /**
     * Obtiene el identificador de la configuración.
     *
     * @return identificador de la configuración.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la configuración.
     *
     * @param id identificador de la configuración.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del conjunto residencial.
     *
     * @return nombre del conjunto.
     */
    public String getNombreConjunto() {
        return nombreConjunto;
    }

    /**
     * Establece el nombre del conjunto residencial.
     *
     * @param nombreConjunto nombre del conjunto.
     */
    public void setNombreConjunto(String nombreConjunto) {
        this.nombreConjunto = nombreConjunto;
    }

    /**
     * Obtiene la dirección del conjunto residencial.
     *
     * @return dirección del conjunto.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del conjunto residencial.
     *
     * @param direccion dirección del conjunto.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el número telefónico del conjunto.
     *
     * @return teléfono del conjunto.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número telefónico del conjunto.
     *
     * @param telefono teléfono del conjunto.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la capacidad total de parqueaderos.
     *
     * @return cantidad de parqueaderos disponibles en el conjunto.
     */
    public int getCapacidadParqueaderos() {
        return capacidadParqueaderos;
    }

    /**
     * Establece la capacidad total de parqueaderos.
     *
     * @param capacidadParqueaderos cantidad de parqueaderos.
     */
    public void setCapacidadParqueaderos(int capacidadParqueaderos) {
        this.capacidadParqueaderos = capacidadParqueaderos;
    }

    /**
     * Indica si el registro automático se encuentra habilitado.
     *
     * @return true si está habilitado, false en caso contrario.
     */
    public boolean isRegistroAutomatico() {
        return registroAutomatico;
    }

    /**
     * Activa o desactiva el registro automático.
     *
     * @param registroAutomatico estado del registro automático.
     */
    public void setRegistroAutomatico(boolean registroAutomatico) {
        this.registroAutomatico = registroAutomatico;
    }

    /**
     * Indica si las notificaciones del sistema están habilitadas.
     *
     * @return true si están habilitadas, false en caso contrario.
     */
    public boolean isNotificaciones() {
        return notificaciones;
    }

    /**
     * Activa o desactiva las notificaciones.
     *
     * @param notificaciones estado de las notificaciones.
     */
    public void setNotificaciones(boolean notificaciones) {
        this.notificaciones = notificaciones;
    }

    /**
     * Indica si el control de visitantes está habilitado.
     *
     * @return true si está habilitado, false en caso contrario.
     */
    public boolean isControlVisitantes() {
        return controlVisitantes;
    }

    /**
     * Activa o desactiva el control de visitantes.
     *
     * @param controlVisitantes estado del control de visitantes.
     */
    public void setControlVisitantes(boolean controlVisitantes) {
        this.controlVisitantes = controlVisitantes;
    }

    /**
     * Indica si el control de parqueaderos está habilitado.
     *
     * @return true si está habilitado, false en caso contrario.
     */
    public boolean isControlParqueaderos() {
        return controlParqueaderos;
    }

    /**
     * Activa o desactiva el control de parqueaderos.
     *
     * @param controlParqueaderos estado del control de parqueaderos.
     */
    public void setControlParqueaderos(boolean controlParqueaderos) {
        this.controlParqueaderos = controlParqueaderos;
    }

    /**
     * Obtiene el tiempo de duración de la sesión.
     *
     * @return tiempo de sesión configurado.
     */
    public int getTiempoSesion() {
        return tiempoSesion;
    }

    /**
     * Establece el tiempo de duración de la sesión.
     *
     * @param tiempoSesion tiempo de sesión.
     */
    public void setTiempoSesion(int tiempoSesion) {
        this.tiempoSesion = tiempoSesion;
    }

    /**
     * Obtiene el número máximo de intentos permitidos para iniciar sesión.
     *
     * @return cantidad de intentos de inicio de sesión.
     */
    public int getIntentosLogin() {
        return intentosLogin;
    }

    /**
     * Establece el número máximo de intentos de inicio de sesión.
     *
     * @param intentosLogin cantidad máxima de intentos.
     */
    public void setIntentosLogin(int intentosLogin) {
        this.intentosLogin = intentosLogin;
    }

    /**
     * Obtiene el idioma configurado para el sistema.
     *
     * @return idioma configurado.
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Establece el idioma del sistema.
     *
     * @param idioma idioma seleccionado.
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Obtiene la zona horaria configurada.
     *
     * @return zona horaria.
     */
    public String getZonaHoraria() {
        return zonaHoraria;
    }

    /**
     * Establece la zona horaria del sistema.
     *
     * @param zonaHoraria zona horaria seleccionada.
     */
    public void setZonaHoraria(String zonaHoraria) {
        this.zonaHoraria = zonaHoraria;
    }

    /**
     * Obtiene el formato de fecha utilizado por el sistema.
     *
     * @return formato de fecha.
     */
    public String getFormatoFecha() {
        return formatoFecha;
    }

    /**
     * Establece el formato de fecha utilizado por el sistema.
     *
     * @param formatoFecha formato de fecha.
     */
    public void setFormatoFecha(String formatoFecha) {
        this.formatoFecha = formatoFecha;
    }
}