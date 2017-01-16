package fdi.ucm.cuentacuentas;

import java.io.Serializable;

/**
 * Created by Pikup on 03/05/2016.
 */
public class usuario implements Serializable{
    private final static usuario usu=new usuario();
    private int id;
    private String nickname;
    private String password;
    private String telefono;
    private String nombre;
    private String apellidos;
    private byte[] imagen;

    public static final usuario getIntsance(){
        return usu;
    }
    // Constructor sin parámetros
    public usuario() {

    }

    // Constructor con parámetros
    public usuario(int id, String nickname, String password, String telefono, String nombre, String apellidos) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.telefono = telefono;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    // Getters and setters


    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
