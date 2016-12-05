package controladores;

import java.util.ArrayList;

import excepciones.FormatoInvalido;
import excepciones.SocioInvalido;
import objetosTransfer.Alquiler;
import objetosTransfer.Socio;
import socio.IFachadaSocio;


public class ControladorSocio {
	
	//private Socio socio;
	private IFachadaSocio iFachada;
	

	/**
	 * Constructor de la clase
	 * @param iFa - interfaz de fachada socio
	 */
	public ControladorSocio(IFachadaSocio iFa) {
		iFachada = iFa;
	}
	
	//A�adir excepci�n por si no hay socio conectado
	/**
	 * Devuelve el socio conectado
	 * @return devuelve un objeto socio
	 */
	public Socio getSocio() {
		return this.iFachada.getSocioConectado();
	}
	
	/**
	 * Inicia sesión con el socio que le pasan por parámetro
	 * @param socio - socio que se va a conectar
	 */
	public void iniciarSesionSocio(Socio socio) {
		this.iFachada.inicioSocio(socio);
		//Informar a la vista que se cambie a la interfaz de socio/cliente
	}
	
	/**
	 * Cierra sesión con el socio concetado
	 * @throws SocioInvalido si no hay ningún socio conectado
	 */
	public void cerrarSesionSocio() throws SocioInvalido {
		this.iFachada.cerrarSocio();
		//Informar a la vista de volver a la interfaz de vendedor
	}
	
	/**
	 * Devuelve los alquileres que tiene el socio conectado
	 * @return devuelve un array de alquileres
	 */
	public Alquiler[] verAlquileres() {
		//Mostrar la lista de alquileres devuelta por el m�todo
		return this.iFachada.verAlquiler();
	}
	
	
	
	public void registrarSocio(Socio socio) throws SocioInvalido, FormatoInvalido {
		
	
			this.iFachada.registrarSocio(socio);
			//Mostrar mensaje de �xito e informar a la vista que vuelva a la interfaz
			//vendedor
			
		
		
	}
	
	
	public void eliminarSocio(Socio socio) {
		this.iFachada.eliminarSocio(socio);
		//Volver a la interfaz vendedor
	}
	
	public Socio ConsultarDatosSocio() throws SocioInvalido {
		//Mostrar los datos del objeto socio devuelto por el m�todo
		return this.iFachada.consultarDatosSocio();
	}
	
	
	public ArrayList <Socio> buscarListaSocios(Socio socioBuscar) {
		return this.iFachada.buscarListaSocios(socioBuscar);
	}
	
	
	
	public void modificarSocio(Socio socio) throws SocioInvalido {
		this.iFachada.modificarSocio(socio);
	}


	

	
	
	

}
