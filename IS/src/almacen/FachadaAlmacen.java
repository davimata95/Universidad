package almacen;

import objetosTransfer.Producto;

public class FachadaAlmacen implements IFachadaAlmacen{
	private ISAAlmacen sa;
	
	public FachadaAlmacen(ISAAlmacen isa){
		this.sa = isa;
	}

	@Override
	public Producto[] mostrarAlmacen(Producto p) {
		Producto[] productosAlmacen = sa.mostrarAlmacen(p);
		return productosAlmacen;
	}

	@Override
	public Producto[] mostrarNovedades() {
		Producto[] novedades = sa.mostrarNovedades();
		return novedades;
	}

	
	
	public Producto[] mostrarAlmacenVentas(Producto p) {
		return this.sa.mostrarAlmacenVentas(p);
	}
	
	/*
	@Override
	public Producto buscarProductos(Producto p) throws ProductoNULL {
		Producto productoBuscado = sa.buscarProductos(p);
		return productoBuscado;
	}
	*/
	
	public void  anadirSaldo (double saldo){
		this.sa.anadirSaldo(saldo);
	}
	public String[] verUltimasTransacciones(){
	
		String transacciones[] = sa.verUltimasTransacciones();

		return transacciones ;
		
	}
}
