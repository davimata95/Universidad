package abd.p1.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Usuario {
    
	@Id
    private String correo;
	@Column(nullable = false, length = 100)
    private String password;
	@Column(nullable = false, length = 100)
    private String nombre;
	@Enumerated(EnumType.STRING)
    private Genero genero;
	@Enumerated(EnumType.STRING)
    private Interes interes;
	@Column(nullable = false)
    private double latitud;
	@Column(nullable = false)
    private double longitud;
    @Temporal(TemporalType.DATE)
    private Date nacimiento;
    @Lob
    @Column(nullable = true)
    private byte[] avatar;
    @Column(nullable = true, length = 250)
    private String descripcion;
    @ManyToMany
    private Set<Usuario> amigos;
    @ElementCollection (fetch = FetchType.EAGER)
    private List<String> aficiones;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Contesta> contestaciones;
    /*@OneToMany(mappedBy = "usuario")
    private List<Mensaje> mensajes;*/ //Puede que no sea necesaria la lista de mensajes
    
    // Constructor sin parámetros
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Usuario() {
		this.aficiones = new ArrayList();
	}
	
	// Constructor con parámetros
	public Usuario(String correo, String password, String nombre, Genero genero, Interes interes, double latitud,
			double longitud, Date nacimiento, byte[] avatar, String descripcion, Set<Usuario> amigos,
			List<String> aficion, List<Contesta> contestaciones) {
		this.correo = correo;
		this.password = password;
		this.nombre = nombre;
		this.genero = genero;
		this.interes = interes;
		this.latitud = latitud;
		this.longitud = longitud;
		this.nacimiento = nacimiento;
		this.avatar = avatar;
		this.descripcion = descripcion;
		this.amigos = amigos;
		this.aficiones = aficion;
		this.contestaciones = contestaciones;
	}
	
	// Getters y setters
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

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Usuario> getAmigos() {
		return amigos;
	}

	public void setAmigos(Set<Usuario> amigos) {
		this.amigos = amigos;
	}

	public List<String> getAficiones() {
		return aficiones;
	}

	public void setAficiones(List<String> aficiones) {
		this.aficiones = aficiones;
	}

	public List<Contesta> getContestaciones() {
		return contestaciones;
	}

	public void setContestaciones(List<Contesta> contestaciones) {
		this.contestaciones = contestaciones;
	}  
	
	public void addAficion(String aficion) {
		this.aficiones.add(aficion);
	}
	
	public void removeAficion(int index) {
		this.aficiones.remove(index);
	}
	
	public void addContestacion(Contesta contestacion) {
		this.contestaciones.add(contestacion);
	}
     
}