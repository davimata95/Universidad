package socio;

import java.util.ArrayList;

import objetosTransfer.Alquiler;
import objetosTransfer.Socio;

public interface IDAOSocio {
	
	void actualizarPuntos(double puntos);
	
	/**
	 * Devuelve una todos los socios de la BD
	 * @return Una lista de todos los socios
	 */
	ArrayList <Socio> getListaSocios();
	
	
	/**
	 * Devuelve una todos los socios de la BD
	 * @param SocioBuscar - Objeto Socio
	 * @return Una lista de todos los socios
	 */
	ArrayList <Socio>getListaSocio(Socio SocioBuscar);
	///////////////////////////////////////////////////////////////////////////////
	//
	/**
	 * Pone el campo de socioIniciado del socio pasado por par�metro a true
	 * @param socio - Socio con el se quiere iniciar sesión
	 */
	void inicioSocio(Socio socio);
	
	//
	/**
	 * Busca el socio con la sesión iniciada y pone su campo a false
	 *  Se cierra la sesión del socio conectado
	 */
	void cerrarSocio();
	
	
	/**
	 *  Busca en la BD de socios el socio conectado y busca en la BD de alquileres
	 *  los alquileres de ese socio y devuelve una lista de sus alquileres
	 * @return una lista de  alquileres
	 */
	Alquiler[] VerAlquiler();
	
	
	//
	/**
	 * Método que dado un socio lo elimina de la base de datos
	 * @param socio - socio que se quiere eliminar
	 */
	void eliminarSocio(Socio socio);
	
	//
	/**
	 * Método que añade un socio al final de la lista de socios
	 * @param socio - Socio que se quiere registrar
	 */
	void registrarSocio(Socio socio);
	
	/**
	 * @return devuelve el socio conectado
	 */
	Socio getSocioConectado();
	

	/**
	 * @return devuelve cuantos socios hay registrados en la base de datos
	 */
	int cuantosSocios();

	/**
	 * @return devuelve true si algún socio tiene la sesión iniciada
	 */
	boolean socioConectado();

	/**
	 * busca el socio conectado y cambia sus campos
	 * @param socio - Socio que se quiere modificar
	 */
	void modificaSocioConectado(Socio socio);
}
