package objetosTransfer;

import java.util.Date;

public class Producto {
	
	
	private String nombre;
	private CategoriaJuego categoria;
	private Integer edad;
	private Double precio;
	private TipoProducto tipo;
	private Date fecha; //Preguntar si el tipo fecha lo metemos o no
	private Integer id;
	
	public Producto(Integer ident, TipoProducto t, CategoriaJuego cat, String name, Integer age, Double price, Date f) {
		this.id = ident;
		this.tipo = t;
		this.nombre = name;
		this.categoria = cat;
		this.edad = age;
		//if(price != null)
			this.precio = price;
		this.fecha = f;
	}

	public TipoProducto getTipo(){
		return this.tipo;
	}
	
	public int getId(){
		return this.id;
	}
	public Double getPrecio(){
		return this.precio;
	}
	
	public int getEdad(){
		return this.edad;
	}
	
	@SuppressWarnings("deprecation")
	public void serFecha(int dia, int mes, int anyo){
		this.fecha.setDate(dia);
		this.fecha.setMonth(mes);
		this.fecha.setYear(anyo);
	}
	
	public Date getFecha(){
		return this.fecha;
	}
	public void setNombre(String name) {
		 this.nombre = name;
	}
	
	public void setPrecio(double price) {
		 this.precio = price;
	}
	
	public void setTipoProducto(TipoProducto t) {
		 this.tipo = t;
	}
	
	public void setEdad(int age) {
		 this.edad = age;
	}
	
	
	public String getNombre() {
		return this.nombre;
	}
	
	public CategoriaJuego getCategoria() {
		return this.categoria;
	}
	
	
	public void setCategoria(CategoriaJuego cat) {
		 this.categoria = cat;
	}
	
	public String toString(){
		return this.nombre;
	}
	
	
	
	
}
