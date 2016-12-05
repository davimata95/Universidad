package alquiler;

import excepciones.ErrorAlquiler;
import objetosTransfer.Alquiler;
import objetosTransfer.Producto;
import objetosTransfer.Socio;

public interface ISAAlquiler {

	/**
	 * Este metodo comprueba si el socio tiene algun retraso en un alquiler. Si no tiene ningun retraso, 
	 * llama a los métodos AlquilarProducto y actualizarSaldo de IDAOAlquiler y además sumará los puntos
	 * correspondientes al socio
	 * @param alquiler - Objeto Alquiler
	 * @throws ErrorAlquiler Excepción que se lanza si el socio tiene algun retraso en un alquiler 
	 **/
	public void AlquilarProducto (Alquiler alquiler) throws ErrorAlquiler;
	/**
	 *  Este método devuelve llama a DevolverAlquiler de IDAOAlquiler, si el socio tiene penalización se 
	 *  le cobrará
	 * @param alquiler - Objeto Alquiler
	 * @param num - Entero
	 * @throws ErrorAlquiler  Lanza la excepción si el alquiler que se quiere devolver tiene retraso
	 */
	public void DevolverAlquiler(Alquiler[] alquiler, int num) throws ErrorAlquiler;
	/**
	 * Devuelve un array de Alquileres
	 * @param socio - Objeto Socio
	 * @return  Un array con todos los alquileres de un socio
	 */
	public Alquiler[] getAlquileres(Socio socio);
	/**
	 * Devuelve un array de Productos
	 * @param p - Objeto Producto
	 * @return  Un array con todos los productos que se pueden alquilar
	 */
	public Producto[] getEnAlquiler(Producto p);
}
