package almacen;

import objetosTransfer.Producto;

public interface IFachadaAlmacen {

	Producto[] mostrarAlmacen(Producto p);
	Producto[] mostrarNovedades();
	//public Producto buscarProductos(Producto p) throws ProductoNULL;
	
	void  anadirSaldo (double saldo);
	String[] verUltimasTransacciones();
	Producto[] mostrarAlmacenVentas(Producto p);
	
}
