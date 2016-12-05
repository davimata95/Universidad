package ventas;

import excepciones.CantidadExcesiva;
import excepciones.SaldoInsuficiente;
import excepciones.StockInsuficiente;
import objetosTransfer.Producto;
import objetosTransfer.Socio;

public class FachadaVentas implements IFachadaVentas {
	private ISAVentas sa;
	
	public FachadaVentas(ISAVentas isa)
	{
		this.sa = isa;
	}

	public void comprarProducto(Producto p, int cantidad, Socio socio) throws SaldoInsuficiente
	{
		//Llama a comprarProducto del servicio de aplicación de Ventas
		sa.comprarProducto(p, cantidad, socio);
	}
	public void venderProducto(Producto p, int cantidad, Socio socio) throws StockInsuficiente
	{
		//Llama a venderProducto del servicio de aplicación de Ventas
		sa.venderProducto(p, cantidad, socio);
	}
	public void devolverProducto(Socio s, Producto p) throws SaldoInsuficiente
	{
		//Llama a venderProducto del servicio de aplicación de Ventas
		sa.devolverProducto(s, p);
	}

	@Override
	public void venderProducto(Producto p, int cantidad)  throws StockInsuficiente
	{
		//Equivalente a venderProducto pero para clientes que no son socios
		sa.venderProducto(p, cantidad);
		
	}
	
	@Override
	public void comprarProveedor(Producto p, int cantidad, boolean alquiler) throws SaldoInsuficiente {
		//Llama a comprarProveedor del servicio de aplicación
		sa.comprarProveedor(p, cantidad, alquiler);
	}

	@Override
	public void devolverProveedor(Producto p, int cantidad) throws CantidadExcesiva {
		//Llama a devolverProveedor del servicio de aplicación
		sa.devolverProveedor(p, cantidad);
	}
}
