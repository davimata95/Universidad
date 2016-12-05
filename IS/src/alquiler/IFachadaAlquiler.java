package alquiler;

import excepciones.ErrorAlquiler;
import objetosTransfer.Alquiler;
import objetosTransfer.Producto;
import objetosTransfer.Socio;

public interface IFachadaAlquiler {

	public void AlquilarProducto (Alquiler alquiler) throws ErrorAlquiler;
	public void DevolverAlquiler(Alquiler[] alquiler, int num) throws ErrorAlquiler;
	public Alquiler[] getAlquileres(Socio socio);
	public Producto[] getEnAlquiler(Producto p);
}
