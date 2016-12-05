package objetosTransfer;

import java.util.Calendar;

public class Venta {
	private Calendar fecha;
	private Producto producto;
	private int cantidad;
	private float importe;
	
	public Venta(Calendar f, Producto p, int c, float i)
	{
		this.fecha = f;
		this.producto = p;
		this.cantidad = c;
		this.importe = i;
	}
	
	public Calendar getFecha()
	{
		return this.fecha;
	}
	
	public Producto getProducto()
	{
		return this.producto;
	}
	
	public int getCantidad()
	{
		return this.cantidad;
	}
	
	public float getImporte()
	{
		return this.importe;
	}

}
