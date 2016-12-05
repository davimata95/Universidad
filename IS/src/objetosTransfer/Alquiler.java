package objetosTransfer;

import java.util.Date;

public class Alquiler {
	
	private Producto ProdAlquilado;
	private Date fechaDevolucion;
	private Date fechaAlquiler;
	private Socio SocioAlquiler;
	private int idalq;

	
	public Alquiler ( int Id ,Producto alquiler, Date devolucion, Date alquilado,Socio soc){
		this.idalq=Id;
		this.ProdAlquilado=alquiler;
		this.fechaDevolucion=devolucion;
		this.fechaAlquiler=alquilado;
		this.SocioAlquiler=soc;
	}
	
	public int getIdalq() {
		return idalq;
	}

	public void setIdalq(int idalq) {
		this.idalq = idalq;
	}

	public Socio getSocioAlquiler() {
		return SocioAlquiler;
	}

	public void setSocioAlquiler(Socio socioAlquiler) {
		SocioAlquiler = socioAlquiler;
	}

	public Date getFechaAlquiler() {
		return fechaAlquiler;
	}

	
	public void setProductoAlquiler(Producto alquiler) {
		 this.ProdAlquilado = alquiler;
	}
	
	public Producto getProductoAlquiler() {
		return this.ProdAlquilado;
	}
	
	public Date getFechaDevolucion() {
		return this.fechaDevolucion;
	}

	public void setFechaDevolucion(Date devolucion) {
		 this.fechaDevolucion = devolucion;
	}
	public void setFechaAlquiler(Date fechAlq){
		this.fechaAlquiler=fechAlq;
		
	}
	
	public String toString()
	{
		return this.ProdAlquilado.getNombre();
	}
	
}