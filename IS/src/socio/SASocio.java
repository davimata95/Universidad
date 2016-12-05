package socio;

import java.util.ArrayList;

import excepciones.FormatoInvalido;
import excepciones.SocioInvalido;
import objetosTransfer.Alquiler;
import objetosTransfer.CategoriaJuego;
import objetosTransfer.Socio;

public class SASocio implements ISASocio {

	private IDAOSocio dao;
	final int MAX_SOCIOS = 20;
	
	
	
	public SASocio (IDAOSocio iDao) {
		this.dao = iDao;
	}
	
	
	public void inicioSocio(Socio socio) {
		this.dao.inicioSocio(socio);
	}
	
	public void cerrarSocio() throws SocioInvalido {
		if(!this.dao.socioConectado())
			throw new SocioInvalido("No hay ningún socio conectado");
		
		this.dao.cerrarSocio();
	}
	
	
	public void iniciarSocios() {
		this.dao.cerrarSocio();
	}
	
	
	
	public Alquiler[] verAlquiler() {
		return this.dao.VerAlquiler();

	}

	
	public void eliminarSocio(Socio socio) {
		this.dao.eliminarSocio(socio);

	}

	
	public void registrarSocio(Socio socio) throws SocioInvalido, FormatoInvalido {
		if(this.dao.cuantosSocios() >= MAX_SOCIOS)
			throw new SocioInvalido("Error, ya se ha alcanzado el número máximo de socios.");
		if(socio.getNombre().equals("") || socio.getApellidos().equals("") 
				|| socio.getDNI().equals("") || socio.getTelefono().equals(""))
			throw new FormatoInvalido("Error, se deben rellenar todos los campos.");
		
		Socio DNIS = new Socio("","",socio.getDNI(),"",CategoriaJuego.CUALQUIERA);
		
		if(!this.dao.getListaSocio(DNIS).isEmpty())
			throw new SocioInvalido("El socio ya está registrado");
		
		this.dao.registrarSocio(socio);
	}

	
	
	public void actualizarPuntos(double puntos) {
		this.dao.actualizarPuntos(puntos);
	}

	
	public void modificarSocio(Socio socio) throws SocioInvalido {
		
		//M�todo que devuelve true si alg�n socio tiene la sesi�n iniciada(false en caso contrario)
		if(!this.dao.socioConectado())
			throw new SocioInvalido("No hay ningún socio identificado");
		else {
			//Comprobamos si existe algún socio con ese DNI
			Socio DNIS = new Socio("","",socio.getDNI(),"",CategoriaJuego.CUALQUIERA);
			
			ArrayList <Socio> lista = this.dao.getListaSocio(DNIS);

			if(lista.size() == 1 &&  getSocioConectado().getDNI().equals(DNIS.getDNI())) {
			}
			else if(!this.dao.getListaSocio(DNIS).isEmpty()) 
				throw new SocioInvalido("El socio ya está registrado");
			
			//M�todo que cambia todos los campos del socio identificado
			this.dao.modificaSocioConectado(socio);
		
	}
}


	
	public Socio getSocioConectado() {
		return this.dao.getSocioConectado();
	}
	
	
	
	//M�todo que devuelve el socio con la sesi�n iniciada
	public Socio consultarDatosSocio() throws SocioInvalido {
		if(!this.dao.socioConectado())
			throw new SocioInvalido("No hay ning�n socio conectado");
		
		return this.dao.getSocioConectado();
	}
	
	
	//Filtra la lista utilizando los campos pasados por parámetro
	public ArrayList <Socio> buscarListaSocios(Socio socioBuscar) {
		ArrayList <Socio> listaFiltrada = this.dao.getListaSocio(socioBuscar);		
		return listaFiltrada;
	}
	
	
	
	@SuppressWarnings("unused")
	private ArrayList <Socio> filtraPorInteres(ArrayList <Socio> listaSocios, CategoriaJuego cat) {
		
		
		ArrayList <Socio> listaFiltrada = new ArrayList <Socio>();
		

		for(int i = 0; i < listaSocios.size(); i++) {
			if(listaSocios.get(i).getInteres().equals(cat))
				listaFiltrada.add(listaSocios.get(i));
		}
		
		
		return listaFiltrada;
		
	}
	
	
	
	

	@SuppressWarnings("unused")
	private ArrayList <Socio> filtraPorTlf(ArrayList <Socio> listaSocios, String tlf) {
		
		
		ArrayList <Socio> listaFiltrada = new ArrayList <Socio>();
		

		for(int i = 0; i < listaSocios.size(); i++) {
			if(listaSocios.get(i).getTelefono().equals(tlf))
				listaFiltrada.add(listaSocios.get(i));
		}
		
		
		return listaFiltrada;
		
	}
	
	
	
	@SuppressWarnings("unused")
	private ArrayList <Socio> filtraPorDNI(ArrayList <Socio> listaSocios, String DNI) {
		
		
		ArrayList <Socio> listaFiltrada = new ArrayList <Socio>();
		

		for(int i = 0; i < listaSocios.size(); i++) {
			if(listaSocios.get(i).getDNI().equals(DNI))
				listaFiltrada.add(listaSocios.get(i));
		}
		
		
		return listaFiltrada;
		
	}
	
	
	
	@SuppressWarnings("unused")
	private ArrayList <Socio> filtraPorApellidos(ArrayList <Socio> listaSocios, String apellidos) {
		
		
		ArrayList <Socio> listaFiltrada = new ArrayList <Socio>();
		

		for(int i = 0; i < listaSocios.size(); i++) {
			if(listaSocios.get(i).getApellidos().equals(apellidos))
				listaFiltrada.add(listaSocios.get(i));
		}
		
		
		return listaFiltrada;
		
	}
	
	
	@SuppressWarnings("unused")
	private ArrayList <Socio> filtraPorNombre(ArrayList <Socio> listaSocios, String nombre) {
		ArrayList <Socio> listaFiltrada = new ArrayList <Socio>();
		
		for(int i = 0; i < listaSocios.size(); i++) {
			if(listaSocios.get(i).getNombre().equals(nombre))
				listaFiltrada.add(listaSocios.get(i));
		}
		
		
		return listaFiltrada;
		
	}

	

}
