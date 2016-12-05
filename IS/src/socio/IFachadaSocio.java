package socio;

import java.util.ArrayList;

import excepciones.FormatoInvalido;
import excepciones.SocioInvalido;
import objetosTransfer.Alquiler;
import objetosTransfer.Socio;

public interface IFachadaSocio {

	
	Alquiler[] verAlquiler();
	void eliminarSocio(Socio socio);
	void registrarSocio(Socio socio) throws SocioInvalido, FormatoInvalido;
	ArrayList <Socio> buscarListaSocios(Socio socioBuscar);
	void inicioSocio(Socio socio);
	void cerrarSocio() throws SocioInvalido;
	void modificarSocio(Socio socio) throws SocioInvalido;
	Socio consultarDatosSocio() throws SocioInvalido;
	Socio getSocioConectado();
	void actualizarPuntos(double puntos);

}
