package alquiler;

import excepciones.ErrorAlquiler;
import objetosTransfer.Alquiler;
import objetosTransfer.Producto;
import objetosTransfer.Socio;

public class FachadaAlquiler implements IFachadaAlquiler {
	private ISAAlquiler sa;
	public FachadaAlquiler(ISAAlquiler isa){
		this.sa = isa;
	}
	@Override
	public void DevolverAlquiler(Alquiler[] alquiler, int num) throws ErrorAlquiler {
		this.sa.DevolverAlquiler(alquiler, num);
	}

	@Override
	public void AlquilarProducto( Alquiler alquiler) throws ErrorAlquiler {
		this.sa.AlquilarProducto(alquiler);
		
	}
	@Override
	public Alquiler[] getAlquileres(Socio socio) {
		return this.sa.getAlquileres(socio);
	}
	@Override
	public Producto[] getEnAlquiler(Producto p) {
		return this.sa.getEnAlquiler(p);
	}
}
