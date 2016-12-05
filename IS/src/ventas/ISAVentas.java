package ventas;

import excepciones.CantidadExcesiva;
import excepciones.SaldoInsuficiente;
import excepciones.StockInsuficiente;
import objetosTransfer.Producto;
import objetosTransfer.Socio;

public interface ISAVentas {

	/**
	 * Llamara a comprarProducto de IDAOVentas
	 * @param p -Producto que se desea comprar
	 * @param cantidad -Cantidad de productos que se quieren comprar
	 * @param socio - Socio al que se quiere comprar
	 * @throws SaldoInsuficiente Se lanzara si no hay saldo para realizar la compra
	 */
	void comprarProducto(Producto p, int cantidad, Socio socio) throws SaldoInsuficiente;
	
	/**
	 * Llamara a venderProducto de IDAOVentas
	 * @param p -Producto que se quiere vender
	 * @param cantidad -Cantidad de productos que se van a vender
	 * @param socio -Socio al que se quiere vender
	 * @throws StockInsuficiente Se lanzara si no tenemos stock para vender
	 */
	void venderProducto(Producto p, int cantidad, Socio socio) throws StockInsuficiente;
	
	/**
	 * Llama a venderProducto de IDAOVentas
	 * @param p -Producto que se desea vender
	 * @param cantidad -Cantidad de productos que se quieren vender
	 * @throws StockInsuficiente Se lanzara si no tenemos stock para vender
	 */
	void venderProducto(Producto p, int cantidad) throws StockInsuficiente;

	/**
	 * Llama a devolverProducto de IDAOVentas
	 * @param s -Socio que quiere devolver el producto
	 * @param p -Producto que se desea devolver
	 * @throws SaldoInsuficiente Se lanza si no hay saldo suficiente para realizar la devolucion
	 */
	void devolverProducto(Socio s, Producto p) throws SaldoInsuficiente;
	
	/**
	 * Llama a compraProveedor de IDAOVentas
	 * @param p -Producto que deseas comprar
	 * @param cantidad -Cantidad de productos que deseas comprar
	 * @param alquiler -Si los juegos son para almacenVentas o almacenAlquiler
	 * @throws SaldoInsuficiente La lanza si no hay saldo suficiente para comprar los productos
	 */
	void comprarProveedor(Producto p, int cantidad, boolean alquiler) throws SaldoInsuficiente;
	
	/**
	 * Llama a devolverProveedor de IDAOVentas
	 * @param p -Producto que se desea devolver
	 * @param cantidad -Cantidad de productos que se desean devolver
	 * @throws CantidadExcesiva La lanza si introduces una cantidad de juegos mayor a la que hay en almacén
	 */
	void devolverProveedor(Producto p, int cantidad) throws CantidadExcesiva;

}
