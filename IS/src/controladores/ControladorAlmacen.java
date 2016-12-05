package controladores;

import objetosTransfer.Producto;
import almacen.IFachadaAlmacen;

public class ControladorAlmacen {

	private IFachadaAlmacen iFachada;

	/**
	 * Controlador de la clase
	 * @param iFa - interfaz de la fachada alquiler
	 */
	public ControladorAlmacen (IFachadaAlmacen iFa){
		
		iFachada = iFa;
	}
	
	/**
	 * Devuelve un array de todos los productos de almacén que cumplen los campos rellenados
	 * @param p - Objeto producto
	 * @return Devuelve un array de productos
	 */
	public Producto[] mostrarAlmacen(Producto p){
		return this.iFachada.mostrarAlmacen(p);
	}
	

	/**
	 * Devuelve un array de todos los productos de almacen ventas que cumplen los campos rellenados
	 * @param p - Objeto producto
	 * @return Devuelve un array de productos
	 */
	public Producto[] mostrarAlmacenVentas(Producto p){
		return this.iFachada.mostrarAlmacenVentas(p);
	}
	
	/**
	 * Devuelve los ultimos 10 productos en añadirse al almacén
	 * @return Devuelve un array de productos
	 */
	public Producto[] mostrarNovedades(){
		return this.iFachada.mostrarNovedades();
	}
	
	/**
	 * Añade dinero al saldo de la tienda
	 * @param saldo - Dinero a sumar al saldo
	 */
	public void  anadirSaldo (double saldo){
		this.iFachada.anadirSaldo(saldo);
	}
	/**
	 * Devuelve las 10 últimas transacciones 
	 * @return Devuelve un array de strings
	 */
	public String[] verUltimasTransacciones(){
		return this.iFachada.verUltimasTransacciones();
	}
}
