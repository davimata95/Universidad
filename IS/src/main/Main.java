package main;

import javax.swing.JOptionPane;

import almacen.DAOAlmacen;
import almacen.FachadaAlmacen;
import almacen.IDAOAlmacen;
import almacen.IFachadaAlmacen;
import almacen.ISAAlmacen;
import almacen.SAAlmacen;
import alquiler.DAOAlquiler;
import alquiler.FachadaAlquiler;
import alquiler.IDAOAlquiler;
import alquiler.IFachadaAlquiler;
import alquiler.ISAAlquiler;
import alquiler.SAAlquiler;
import controladores.ControladorAlmacen;
import controladores.ControladorAlquiler;
import controladores.ControladorSocio;
import controladores.ControladorUsuario;
import controladores.ControladorVentas;
import excepciones.FalloConexionBD;
import excepciones.SocioInvalido;
import gui.Principal;
import socio.DAOSocio;
import socio.FachadaSocio;
import socio.IDAOSocio;
import socio.IFachadaSocio;
import socio.ISASocio;
import socio.SASocio;
import usuario.DAOUsuario;
import usuario.FachadaUsuario;
import usuario.IDAOUsuario;
import usuario.IFachadaUsuario;
import usuario.ISAUsuario;
import usuario.SAUsuario;
import ventas.DAOVentas;
import ventas.FachadaVentas;
import ventas.IDAOVentas;
import ventas.IFachadaVentas;
import ventas.ISAVentas;
import ventas.SAVentas;

public class Main {

	public static void main(String[] args)
	{
		try
		{
			IDAOSocio IdaoS = new DAOSocio();
			ISASocio iSaS = new SASocio(IdaoS);
			IFachadaSocio iFaS = new FachadaSocio(iSaS);
			ControladorSocio cS = new ControladorSocio(iFaS);
			
			IDAOUsuario IdaoU = new DAOUsuario();
			ISAUsuario iSaU = new SAUsuario(IdaoU);
			IFachadaUsuario iFaU = new FachadaUsuario(iSaU);
			ControladorUsuario cU = new ControladorUsuario(iFaU);
			
			IDAOAlmacen IdaoA = new DAOAlmacen();
			ISAAlmacen iSaA = new SAAlmacen(IdaoA);
			IFachadaAlmacen iFaA = new FachadaAlmacen(iSaA);
			ControladorAlmacen cA = new ControladorAlmacen(iFaA);
			
			IDAOVentas IdaoV = new DAOVentas();
			ISAVentas iSaV = new SAVentas(IdaoV);
			IFachadaVentas iFaV = new FachadaVentas(iSaV);
			ControladorVentas cV = new ControladorVentas(iFaV, iFaS);
			
			IDAOAlquiler IdaoAlq = new DAOAlquiler();
			ISAAlquiler iSaAlq = new SAAlquiler(IdaoAlq);
			IFachadaAlquiler iFaAlq = new FachadaAlquiler(iSaAlq);
			ControladorAlquiler cAlq = new ControladorAlquiler(iFaAlq, iFaS);
			
			
			//Se inicializan los valores de usuarioIniciado y socioIniciado
			//por si la ejecuci�n anterior acab� de forma repentina
			cU.cerrarSesion();
			try {
				cS.cerrarSesionSocio();
			}
			catch(SocioInvalido e) {
				
			}
			
			
			
			new Principal (cA, cS, cU, cV, cAlq);

		}
		catch(FalloConexionBD e1)
		{
			JOptionPane.showMessageDialog(null, "No se pudo conectar con la base.");
		}
	}
}