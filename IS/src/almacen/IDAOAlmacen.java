package almacen;

import objetosTransfer.Producto;

public interface IDAOAlmacen {
	
	
	/**
	 * Muestra los productos de nuestro almacén
	 * @param p -Producto para realizar el filtrado
	 * @return Array con los productos de nuestro almacén
	 */
	Producto[] mostrarAlmacen(Producto p);
	
	/**
	 * Muestra las novedades de nuestro almacén (últimos 10)
	 * @return Array con los ultimos 10 productos de nuestro almacén
	 */
	Producto[] mostrarNovedades();
	
	/**
	 * Muestra los productos del almacén ventas
	 * @param p -Producto para realizar el filtrado
	 * @return Array con los productos de nuestro almacén ventas
	 */
	Producto[] mostrarAlmacenVentas(Producto p);
	
	/**
	 * Añade el saldo a nuestra tienda
	 * @param saldo -Saldo a añadir a nuestra tienda
	 */
	void  anadirSaldo (double saldo);
	
	/**
	 * Muestra las últimas transacciones
	 * @return Array con las últimas transacciones
	 */
	String[] verUltimasTransacciones();

}
