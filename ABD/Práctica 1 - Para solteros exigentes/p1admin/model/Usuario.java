package p1admin.model;

import java.sql.Blob;
import java.sql.Date;
import java.util.List;

import p1admin.model.Mensaje;

public class Usuario {
    
    private String correo;
    private String password;
    private String nombre;
    private Genero genero;
    private Interes interes;
    private double latitud;
    private double longitud;
    private Date nacimiento;
    private Blob avatar;
    private String descripcion;
    private List<Mensaje> mensajes;
    private List<EsAmigo> amigos;
    private List<Aficion> aficiones;
    private List<Contesta> contestaciones;
     
     
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Genero getGenero() {
        return genero;
    }
    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    public Interes getInteres() {
        return interes;
    }
    public void setInteres(Interes interes) {
        this.interes = interes;
    }
    public double getLatitud() {
        return latitud;
    }
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
    public double getLongitud() {
        return longitud;
    }
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    public Date getNacimiento() {
        return nacimiento;
    }
    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }
    public Blob getAvatar() {
        return avatar;
    }
    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
	public List<Mensaje> getMensajes() {
		return mensajes;
	}
	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	public List<EsAmigo> getAmigos() {
		return amigos;
	}
	public void setAmigos(List<EsAmigo> amigos) {
		this.amigos = amigos;
	}
	public List<Aficion> getAficiones() {
		return aficiones;
	}
	public void setAficiones(List<Aficion> aficiones) {
		this.aficiones = aficiones;
	}
	public List<Contesta> getContestaciones() {
		return contestaciones;
	}
	public void setContestaciones(List<Contesta> contestaciones) {
		this.contestaciones = contestaciones;
	}
     
}