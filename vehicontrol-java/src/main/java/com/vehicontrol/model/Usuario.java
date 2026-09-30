package com.vehicontrol.model;

/**
 * Modelo que representa un usuario del sistema VEHICONTROL.
 *
 * Contiene la información necesaria para la autenticación
 * y administración de los usuarios del sistema, incluyendo
 * nombre, correo, rol y estado.
 *
 * @author Duvan Arias
 */
public class Usuario {

    private int id;
    private String nombre;
    private String correo;
    private String password;
    private String rol;
    private String estado;

    /**
     * Constructor vacío utilizado para crear un usuario
     * y asignar posteriormente sus propiedades.
     */
    public Usuario() {
    }

    /**
     * Obtiene el identificador del usuario.
     *
     * @return identificador del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario.
     *
     * @param id identificador del usuario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return correo electrónico.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param correo correo electrónico.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol asignado al usuario.
     *
     * @return rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol rol asignado.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el estado actual del usuario.
     *
     * @return estado del usuario.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del usuario.
     *
     * @param estado estado del usuario, por ejemplo activo o inactivo.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}