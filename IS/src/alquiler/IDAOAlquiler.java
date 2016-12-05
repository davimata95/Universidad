package alquiler;

import objetosTransfer.Alquiler;
import objetosTransfer.Producto;
import objetosTransfer.Socio;


public interface IDAOAlquiler {
	/**
	 * Devuelve un array de Alquileres
	 * @param socio - Socio con sesión iniciada
	 * @return Un array con todos los alquileres de un socio
	 */
	Alquiler[] VerAlquiler(Socio socio);
	/**
	 * Actualiza el estado del producto en el almacén alquilados y elimina el alquiler del socio
	 * @param alquiler - Alquiler a devolver
	 */
	void DevolverAlquiler(Alquiler alquiler);
	/**
	 * Actualiza el estado del producto en el almacén alquilados y crea un alquiler del socio
	 * @param alquiler -Objeto Alquiler
	 */
	void AlquilarProducto (Alquiler alquiler);
	/**
	 * Añade el saldo introducido por parámetro a la base de datos
	 * @param penal - Saldo a añadir
	 */
	void actualizarSaldo(double penal);
	/**
	 * @param producto - Objeto Producto
	 * @return Un array con todos los productos que se pueden alquilar
	 */
	Producto[] DevolverEnAlquiler(Producto producto);
}
