package almacen;

import objetosTransfer.Producto;

public interface ISAAlmacen {
	
	/**
	 * Llama a mostrarAlmacen de IDAOAlmacen
	 * @param p -Producto para realizar el filtrado
	 * @return Un array con los productos del almacén
	 */
	Producto[] mostrarAlmacen(Producto p);
	
	/**
	 * Llama a mostrarNovedades de IDAOAlmacen
	 * @return Un array con los 10 ultimos productos de nuestro almacén
	 */
	Producto[] mostrarNovedades();
	
	//public Producto buscarProductos(Producto p) throws ProductoNULL;
	
	
	/**
	 * Llama a anadirSaldo de IDAOAalmacen
	 * @param saldo -Saldo a añadir a nuestra tienda
	 */
	void  anadirSaldo (double saldo);
	
	
	/**
	 * Llama a verUltimasTransacciones de IDAOAlmacen
	 * @return Un String con las transacciones
	 */
	String[] verUltimasTransacciones();
	
	
	/**
	 * Llama a mostrarAlmacenVentas de IDAOAlmacen
	 * @param p -Producto para realizar el filtrado
	 * @return Un array con los productos del almacenVentas
	 */
	Producto[] mostrarAlmacenVentas(Producto p);

}
