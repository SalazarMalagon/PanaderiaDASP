package com.panaderia.modelo;

/**
 * Clase que representa a un administrador en el sistema.
 * Extiende de la clase `usuario` y añade atributos y métodos específicos para administradores.
 */
public class administrador extends usuario {

    private String clave; // Contraseña del administrador
    
    /**
     * Constructor de la clase administrador.
     * Inicializa los atributos heredados de `usuario` y la clave del administrador.
     * @param id El ID del administrador.
     * @param nombre El nombre del administrador.
     * @param correo El correo electrónico del administrador.
     * @param clave La contraseña del administrador.
     */
    public administrador(int id, String nombre, String correo, String clave) {
        super(id, nombre, correo);
        this.clave = clave;
    }
    
    /**
     * Obtiene la clave del administrador.
     * @return La contraseña del administrador.
     */
    public String getClave() {
        return clave;
    }
    
    /**
     * Establece una nueva clave para el administrador.
     * @param clave La nueva contraseña.
     */
    public void setClave(String clave) {
        this.clave = clave;
    }
    
    /**
     * Verifica si la contraseña ingresada coincide con la clave del administrador.
     * @param claveIngresada La contraseña ingresada.
     * @return `true` si la contraseña es correcta, `false` en caso contrario.
     */
    public boolean verificarContraseña(String claveIngresada) {
        return this.clave.equals(claveIngresada);
    }

    /**
     * Representación en forma de cadena del objeto administrador.
     * Incluye los datos del usuario y la clave.
     * @return Una cadena con la información del administrador.
     */
    @Override
    public String toString() {
        return "Administrador{" +
                "usuario=" + super.toString() +
                ", clave='" + clave + '\'' +
                '}';
    }
}