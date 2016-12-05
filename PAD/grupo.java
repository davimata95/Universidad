package fdi.ucm.cuentacuentas;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pikup on 03/05/2016.
 */
public class grupo implements Serializable{

    private int id;
    private String nombre;
    private byte[] imagen;
    private ArrayList<grupousuario> miembros;
    private float pagado;
    private float deben;
    private Double latitud;
    private Double longitud;



    // Constructor sin parámetros
    public grupo() {
    miembros=new ArrayList<>();
    }

    // Constructor con parámetros
    public grupo(int id, String nombre, byte[] imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public float getPagado() {
        return pagado;
    }

    public void setPagado(float pagado) {
        this.pagado = pagado;
    }

    public float getDeben() {
        return deben;
    }

    public void setDeben(float deben) {
        this.deben = deben;
    }

    public ArrayList<grupousuario> getMiembros() {
        return miembros;
    }

    public void setMiembros(ArrayList<grupousuario> miembros) {
        this.miembros = miembros;
    }

    public void addMiembro(grupousuario miembro){

        miembros.add(miembro);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
