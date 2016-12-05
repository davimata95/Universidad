package objetosTransfer;

public class Socio {
	
	
	private String nombre;
	private String apellidos;
	private String DNI;
	private String tlf;
	private CategoriaJuego categoria;
	private int alquileres;
	private double puntos; //Los puntos se usan como conversion directa a euros y se van consiguiendo en base a una constante con cada transaccion del socio
	private int Idsocio;
	
	public Socio(String name, String surname, String ID,
			String telefono, CategoriaJuego catInteres) {
		this.alquileres = 0;
		this.nombre = name;
		this.apellidos = surname;
		this.DNI = ID;
		this.tlf = telefono;
		this.categoria = catInteres;
		this.puntos = 0;
	}
	
	
	public void setNombre(String name) {
		 this.nombre = name;
	}
	
	public void setApellidos(String surname) {
		 this.apellidos= surname;
	}

	
	public void setDNI(String ID) {
		 this.DNI = ID;
	}
	
	public void setTelefono(String telefono) {
		 this.tlf = telefono;
	}
	
	public void setInteres(CategoriaJuego cat) {
		 this.categoria = cat;
	}
	
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getApellidos() {
		return this.apellidos;
	}

	
	public String getDNI() {
		return this.DNI;
	}
	
	public String getTelefono() {
		return this.tlf;
	}
	
	public CategoriaJuego getInteres() {
		return this.categoria;
	}
	
	public int getAlquileres(){
		return this.alquileres;
	}
	
	public void setAlquiler(int al){
		this.alquileres = al;
	}
	
	public int getNumAlquileres(Alquiler[] al){
		return al.length;
	}
	
	public double getPuntos() {
		
		return this.puntos;
	}

	public void setPuntos(double p) {
		this.puntos = p;
		
	}
	
	public int getIdsocio() {
		return Idsocio;
	}
	
	public void setIdsocio(int idsocio) {
		Idsocio = idsocio;
	}
	
	public String toString(){
		return this.nombre + " " + this.DNI;
	}
}
