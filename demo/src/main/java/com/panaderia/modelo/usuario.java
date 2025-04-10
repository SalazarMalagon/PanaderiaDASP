package com.panaderia.modelo;

/**
 * Clase que representa un usuario genérico en el sistema.
 * Contiene atributos básicos como ID, nombre y correo electrónico.
 */
public class usuario {

    private int idUsuario; // Identificador único del usuario
    private String nombre; // Nombre del usuario
    private String correo; // Correo electrónico del usuario

    /**
     * Constructor de la clase usuario.
     * Inicializa los atributos del usuario.
     * @param idUsuario El ID del usuario.
     * @param nombre El nombre del usuario.
     * @param correo El correo electrónico del usuario.
     */
    public usuario(int idUsuario, String nombre, String correo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
    }

    /**
     * Obtiene el ID del usuario.
     * @return El ID del usuario.
     */
    public int getidUsuario() {
        return idUsuario;
    }

    /**
     * Establece el ID del usuario.
     * @param idUsuario El nuevo ID del usuario.
     */
    public void setidUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return El nombre del usuario.
     */
    public String getnombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre El nuevo nombre del usuario.
     */
    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return El correo electrónico del usuario.
     */
    public String getcorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     * @param correo El nuevo correo electrónico del usuario.
     */
    public void setcorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Representación en forma de cadena del objeto usuario.
     * Incluye el ID, nombre y correo del usuario.
     * @return Una cadena con la información del usuario.
     */
    @Override
    public String toString() {
        return "usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}