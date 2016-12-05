package almacen;

import objetosTransfer.Producto;


public class SAAlmacen implements ISAAlmacen {
	private IDAOAlmacen idao;

	public SAAlmacen(IDAOAlmacen dao){
		this.idao = dao;
	}


	@Override
	public Producto[] mostrarAlmacen(Producto p) {
		Producto[] productosAlmacen = idao.mostrarAlmacen(p);

		return productosAlmacen;
	}
	
	
	public Producto[] mostrarAlmacenVentas(Producto p) {
		Producto[] productos = idao.mostrarAlmacenVentas(p);
		
		return productos;
	}

	@Override
	public Producto[] mostrarNovedades() {
		Producto[] novedades = idao.mostrarNovedades();
		return novedades;
	}

	/*
	@Override
	public Producto buscarProductos(Producto p) throws ProductoNULL{
		Producto productoBuscado = idao.buscarProducto(p);
		
		if (productoBuscado == null){
			throw new ProductoNULL("Producto no encontrado");
		}
		
		return productoBuscado;
	}
	*/
	public void  anadirSaldo (double saldo){
		idao.anadirSaldo(saldo);
	}
	public String[] verUltimasTransacciones(){
		String[] transacciones = idao.verUltimasTransacciones();
		return transacciones;
	}

}
