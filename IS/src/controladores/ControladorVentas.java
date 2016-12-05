package controladores;

import objetosTransfer.Producto;
import objetosTransfer.Socio;
import excepciones.CantidadExcesiva;
import excepciones.SaldoInsuficiente;
import excepciones.SocioInvalido;
import excepciones.StockInsuficiente;
import socio.IFachadaSocio;
import ventas.IFachadaVentas;

public class ControladorVentas{
	private IFachadaVentas fachadaVentas;
	private IFachadaSocio fachadaSocio;
	
	/**
	 * Constructor de la clase
	 * @param fv interfaz de la fachada de ventas
	 * @param fs interfaz de la fachada socio
	 */
	public ControladorVentas(IFachadaVentas fv, IFachadaSocio fs)
	{
		this.fachadaVentas = fv;
		this.fachadaSocio = fs;
	}
	
	
	/**
	 * Compra un producto y si es socio le suma puntos
	 * @param p - Objeto producto
	 * @param cantidad - Numero de productos que quiere comprar
	 * @param socio - el socio que está conectado
	 * @throws SaldoInsuficiente Si la tienda no tiene saldo suficiente
	 * @throws SocioInvalido si hubo problemas con el socio.
	 */
	public void comprarProducto(Producto p, int cantidad, Socio socio) throws SaldoInsuficiente, SocioInvalido
	{
		//Compra un producto a un socio y modifica después sus puntos
		this.fachadaVentas.comprarProducto(p, cantidad, socio);
		this.fachadaSocio.actualizarPuntos(socio.getPuntos());
		
	}
	
	/**
	 * Vende un producto al socio y le suma puntos
	 * @param p - producto que se quiere comprar
	 * @param cantidad - Numero de productos que quiere comprar
	 * @param socio - el socio que está conectado, si está conectado
	 * @throws StockInsuficiente si no hay stock suficiente
	 * @throws SocioInvalido si hubo problemas con el socio.
	 */
	public void venderProducto(Producto p, int cantidad, Socio socio) throws StockInsuficiente, SocioInvalido
	{
		this.fachadaVentas.venderProducto(p, cantidad, socio);
		
		if(socio != null)
			this.fachadaSocio.actualizarPuntos(socio.getPuntos());
	}
	
	/**
	 * Vende un producto a un cliente
	 * @param p - producto que se quiere comprar
	 * @param cantidad - numero de productos que se quieren comprar
	 * @throws StockInsuficiente si no hay stock suficiente
	 */
	public void venderProducto(Producto p, int cantidad) throws StockInsuficiente
	{
		this.fachadaVentas.venderProducto(p, cantidad);
	}
	
	/**
	 * Devuelve un producto que compro un cliente a la tienda y modifica después sus puntos
	 * @param s - Objeto socio
	 * @param p - producto que se quiere devolver
	 * @throws SaldoInsuficiente si la tienda no tiene saldo suficiente
	 * @throws SocioInvalido si hubo problemas con el socio.
	 */
	public void devolverProducto(Socio s, Producto p) throws SaldoInsuficiente, SocioInvalido
	{
		this.fachadaVentas.devolverProducto(s, p);
		
		if(s != null)
			this.fachadaSocio.actualizarPuntos(s.getPuntos());
	}
	
	/**
	 * Compra productos al proveedor, los añade al almacén y decrementa el saldo de la tienda
	 *  en caso de que este sea suficiente
	 * @param p - producto a comprar
	 * @param cantidad - numero de productos
	 * @param alquiler - si es para alquilar o para ventas
	 * @throws SaldoInsuficiente si la tienda no tiene saldo suficiente
	 */
	public void comprarProveedor(Producto p, int cantidad, boolean alquiler) throws SaldoInsuficiente{
		this.fachadaVentas.comprarProveedor(p, cantidad, alquiler);
	}
	/**
	 * Devuelve productos que se hayan comprado al proveedor, los elimina del almacén
	 *  y devuelve el saldo a la tienda
	 * @param p - Producto a devolver
	 * @param cantidad - numero de productos
	 * @throws CantidadExcesiva si la cantidad es mayor que el numero máximo a devolver
	 */
	public void devolverProveedor(Producto p, int cantidad) throws CantidadExcesiva{
		this.fachadaVentas.devolverProveedor(p, cantidad);
	}

	
}
