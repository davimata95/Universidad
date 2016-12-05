package controladores;

import excepciones.ErrorAlquiler;
import excepciones.SocioInvalido;
import objetosTransfer.Alquiler;
import objetosTransfer.Producto;
import objetosTransfer.Socio;
import socio.IFachadaSocio;
import alquiler.IFachadaAlquiler;

public class ControladorAlquiler {
	private IFachadaAlquiler ifa;
	private IFachadaSocio so;
	
	/**
	 * Constructor de la clase 
	 * @param fa - interfaz de fachada alquiler
	 * @param iso - interfaz de fachada socio
	 */
	public ControladorAlquiler(IFachadaAlquiler fa, IFachadaSocio iso){
		this.ifa = fa;
		this.so = iso;
	}
	
	/**
	 * Alquila al socio un producto y le añade puntos
	 * @param alquiler - Objeto alquiler
	 * @throws ErrorAlquiler - la lanza si no se puede realizar el alquiler	
	 * @throws SocioInvalido - la lanza si el socio tiene algún retraso en un alquiler
	 */
	public void alquilarProducto(Alquiler alquiler) throws ErrorAlquiler, SocioInvalido{
		 this.ifa.AlquilarProducto(alquiler);
		 this.so.actualizarPuntos(alquiler.getSocioAlquiler().getPuntos());
	}
	
	/**
	 * Devuelve el producto
	 * @param al - array de todos los alquileres del socio
	 * @param num - el numero del alquiler que quiere devolver
	 * @throws ErrorAlquiler la lanza si el socio tiene penalización 
	 */
	public void devolverAlquilado( Alquiler[] al, int num) throws ErrorAlquiler{
		this.ifa.DevolverAlquiler(al, num);
	}
	
	/**
	 * Devuelve los alquileres del socio 
	 * @param socio - el socio que quiere devolver el alquiler
	 * @return Devuelve un array de alquileres
	 */
	public Alquiler[] getAlquileres(Socio socio){
		return this.ifa.getAlquileres(socio);
	}
	
	/**
	 * Devuelve los productos que están en el almacén alquiler y coinciden con los campos rellenados
	 * @param p - Objeto producto 	
	 * @return devuelve un array de productos
	 */
	public Producto[] getEnAlquiler(Producto p)
	{
		return this.ifa.getEnAlquiler(p);
	}
}
