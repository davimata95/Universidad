package ventas;

import objetosTransfer.Producto;

public interface IDAOVentas {

	/**
	 * Añadimos a la base de datos el producto comprado
	 * @param p -Producto que queremos comprar
	 * @param cantidad -Cantidad de productos 
	 * @param precio -Precio de los productos
	 */
	void comprarProducto(Producto p, int cantidad, double precio);
	
	/**
	 * Modificamos la base de datos con los productos a vender
	 * @param p -Producto que vamos a vender
	 * @param cantidad -Cantidad de productos que venderemos
	 * @param precio -Precio de los productos
	 */
	void venderProducto(Producto p, int cantidad, double precio);
	
	/**
	 * Devolvemos el producto al proveedor
	 * @param p - Producto a devolver
	 */
	void devolverProducto(Producto p);
	
	/**
	 * Comprueba si el saldo de la tienda es suficiente para realizar la compra
	 * @param precio -Precio que cuesta realizar la compra
	 * @return Devuelve un booleano dependiendo si hay saldo suficiente o no para comprarla
	 */
	boolean saldoSuficiente(double precio);
	
	/**
	 * Comprueba si la cantidad de productos es valida
	 * @param cantidad -Cantidad de productos a devolver
	 * @param p -Producto que se desea comprar
	 * @return Devuelve un booleano dependiendo si hay cantidad de productos suficiente o no 
	 */
	boolean cantidadSuficiente(int cantidad, Producto p);
	
	/**
	 * Devuelve stock al proveedor
	 * @param p -Producto que se desea devolver
	 * @param cantidad -Cantidad de productos que se desean devolver
	 * @return - se ha podido devolver o no.
	 */
	boolean devolverProveedor(Producto p, int cantidad);
	
	/**
	 * Comprar productos al proveedor
	 * @param p -Producto que deseas comprar
	 * @param total -Cantidad de productos que deseas comprar
	 * @param alquiler -Si los juegos son para almacenVentas o almacenAlquiler
	 */
	void comprarProveedor(Producto p, int total, boolean alquiler);

}
