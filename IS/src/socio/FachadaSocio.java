package socio;

import java.util.ArrayList;

import excepciones.FormatoInvalido;
import excepciones.SocioInvalido;
import objetosTransfer.Alquiler;
import objetosTransfer.Socio;

public class FachadaSocio implements IFachadaSocio {

	ISASocio iSaSocio;
	
	public FachadaSocio (ISASocio iSa) {
		this.iSaSocio = iSa;
	}
	
	public void actualizarPuntos(double puntos) {
		this.iSaSocio.actualizarPuntos(puntos);
	}
	
	public Socio getSocioConectado() {
		return this.iSaSocio.getSocioConectado();
	}
	
	
	public Alquiler[] verAlquiler() {
		return this.iSaSocio.verAlquiler();
		
	}


	public void eliminarSocio(Socio socio) {
		this.iSaSocio.eliminarSocio(socio);
		
	}

	
	public void registrarSocio(Socio socio) throws SocioInvalido, FormatoInvalido {
		this.iSaSocio.registrarSocio(socio);
		
	}



	
	public void inicioSocio(Socio socio) {
		this.iSaSocio.inicioSocio(socio);
		
	}


	
	public void cerrarSocio() throws SocioInvalido {
		this.iSaSocio.cerrarSocio();
		
	}


	@Override
	public void modificarSocio(Socio socio) throws SocioInvalido {
		this.iSaSocio.modificarSocio(socio);
		
	}


	@Override
	public Socio consultarDatosSocio() throws SocioInvalido {
		return this.iSaSocio.consultarDatosSocio();
		
	}


	@Override
	public ArrayList <Socio> buscarListaSocios(Socio socioBuscar) {
		return this.iSaSocio.buscarListaSocios(socioBuscar);
	}

}
