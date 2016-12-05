package socio;

import java.util.ArrayList;

import excepciones.FormatoInvalido;
import excepciones.SocioInvalido;
import objetosTransfer.Alquiler;
import objetosTransfer.Socio;

public interface ISASocio {

	/**
	 * Llama a verAlquiler de IDAOSocio 
	 * @return Un array de alquileres de un socio
	 */
	Alquiler[] verAlquiler();
	/**
	 * Se encarga de eliminar el socio que le pasen por parámetro
	 * @param socio -El socio que se quiere eliminar
	 */
	void eliminarSocio(Socio socio);
	/**
	 * Registra un nuevo socio
	 * @param socio - El socio que se quiere registrar
	 * @throws SocioInvalido Se lanza si ya se han alcanzado el número máximo de socios
	 * o si el socio ya está registrado
	 * @throws FormatoInvalido Se lanza si no se rellenan todos los campos
	 */
	void registrarSocio(Socio socio) throws SocioInvalido, FormatoInvalido;
	/**
	 * Devuelve un lista de todos los socios registrados
	 * @param socioBuscar - Objeto socio
	 * @return Una lista de socios
	 */
	ArrayList <Socio> buscarListaSocios(Socio socioBuscar);
	/**
	 * Se inicia sesión con el socio que viene por parametro
	 * @param socio - Socio con el que se quiere iniciar sesión
	 */
	void inicioSocio(Socio socio);
	/**
	 * Se cierra la sesión del socio conectado
	 * @throws SocioInvalido Se lanza si no hay ningún socio conectado
	 */
	void cerrarSocio() throws SocioInvalido;
	/**
	 * Se modifican los datos del socio seleccionado
	 * @param socio - Socio que se quiere modificar
	 * @throws SocioInvalido Se lanza si el socio no tiene una sesión iniciado
	 */
	void modificarSocio(Socio socio) throws SocioInvalido;
	/**
	 * Devuelve el socio el cual se quiere consultar sus datos
	 * @return Un objeto Socio del cual se quieren consultar sus datos
	 * @throws SocioInvalido Se lanza si el socio no tiene una sesión iniciado
	 */
	Socio consultarDatosSocio() throws SocioInvalido;
	/**
	 * @return Devuelve el socio que está conectado
	 */
	Socio getSocioConectado();
	void actualizarPuntos(double puntos);
}
