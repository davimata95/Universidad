package alquiler;

import java.util.Date;

import excepciones.ErrorAlquiler;
import objetosTransfer.Alquiler;
import objetosTransfer.Producto;
import objetosTransfer.Socio;

public class SAAlquiler implements ISAAlquiler {
	private IDAOAlquiler dao;
	private final double PUNTOS = 0.1;
	
	public SAAlquiler(IDAOAlquiler idao){
		this.dao = idao;
	}
	
	@Override
	public void DevolverAlquiler(Alquiler[] alquiler, int num) throws ErrorAlquiler {
		this.dao.DevolverAlquiler(alquiler[num]);
		double penal = hayRetraso(alquiler[num]);
		if(penal!= 0){
			this.dao.actualizarSaldo(penal);
			throw new ErrorAlquiler("Este alquiler tiene una penalizacion de" + penal + ".");
		}
	}

	@Override
	public void AlquilarProducto(Alquiler alquiler) throws ErrorAlquiler {
		Alquiler alquileres[] = this.dao.VerAlquiler(alquiler.getSocioAlquiler());
		double penal = 0;
		for(int i = 0; i < alquileres.length; i++){
			penal += hayRetraso(alquileres[i]);
		}
		
		if(penal == 0){
			this.dao.AlquilarProducto(alquiler);
			double precio = alquiler.getProductoAlquiler().getPrecio();
			this.dao.actualizarSaldo(precio);
			alquiler.getSocioAlquiler().setPuntos(precio*PUNTOS);
		}else{
			throw new ErrorAlquiler("Error. El socio tiene algun retraso en sus alquileres.");
		}
	}
		
	public int hayRetraso(Alquiler alquiler){
		int penalizacion = 0;	
		Date fechaActual = new Date();
		Date fecha = alquiler.getFechaDevolucion();
		
		if(fechaActual.after(fecha)){
			int x = (int) fechaActual.getTime();
			int y = (int) fecha.getTime();
			int resta = x - y;
			penalizacion = resta/86400000;
		}
		return penalizacion;
	}

	
	@Override
	public Alquiler[] getAlquileres(Socio socio) {
		return this.dao.VerAlquiler(socio);
	}
	
	@Override
	public Producto[] getEnAlquiler(Producto p) {
		return this.dao.DevolverEnAlquiler(p);
	}
}
