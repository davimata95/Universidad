package ventas;

import excepciones.CantidadExcesiva;
import excepciones.SaldoInsuficiente;
import excepciones.StockInsuficiente;
import objetosTransfer.Producto;
import objetosTransfer.Socio;

public class SAVentas implements ISAVentas{

	private IDAOVentas dao;
	private final double DECREMENTO = 0.7;
	private final double PUNTOS = 10;
	
	public SAVentas(IDAOVentas idao)
	{
		this.dao = idao;
	}
	
	@Override
	public void comprarProducto(Producto p, int cantidad, Socio socio) throws SaldoInsuficiente{
		
		//Funcion que compra un producto a un cliente
		if(!this.dao.saldoSuficiente(p.getPrecio() * cantidad * DECREMENTO))
		{
			throw new SaldoInsuficiente("Saldo de la tienda insuficiente.");
		}
			
		this.dao.comprarProducto(p, cantidad, p.getPrecio() * cantidad * DECREMENTO); //Aqui se aumenta el numero de productos que se indica y se decrementa el saldo. Si no hay saldo suficiente, se lanza una excepcion SaldoInsuficiente
		
		//Se le anaden los puntos que acaba de conseguir
		socio.setPuntos(socio.getPuntos() + p.getPrecio() * cantidad * DECREMENTO / PUNTOS);
		
	}

	@Override
	public void venderProducto(Producto p, int cantidad, Socio socio) throws StockInsuficiente{
		
		if(!this.dao.cantidadSuficiente(cantidad, p))
		{
			throw new StockInsuficiente("Stock de " + p.getNombre() + " insuficiente.");
		}
		
		//Se modifican sus puntos y se efectua la venta del producto
		double importe = p.getPrecio() * cantidad;
		
		if(socio != null)
		{
			if(socio.getPuntos() > p.getPrecio() * cantidad)
			{
				importe = 0;
				socio.setPuntos(p.getPrecio() * cantidad / PUNTOS);
			}
			
			else
			{
				importe -= socio.getPuntos();
				double puntos = p.getPrecio() / PUNTOS;
				
				socio.setPuntos(puntos);
			}
		}
		
		this.dao.venderProducto(p, cantidad, importe); //Aqui se disminuye el numero de productos que se indica y se incrementa el saldo
	}

	@Override
	public void devolverProducto(Socio s, Producto p) throws SaldoInsuficiente{
		
		if(!this.dao.saldoSuficiente(p.getPrecio()))
		{
			throw new SaldoInsuficiente("Saldo de la tienda insuficiente.");
		}
		
		//Devuelve un producto que ha comprado previamente un socio. Usa la misma funcion que comprarProducto para modificar la base de datos
		this.dao.comprarProducto(p, 1, p.getPrecio());
		
		if(s != null && s.getPuntos() > p.getPrecio() / PUNTOS)
			s.setPuntos(s.getPuntos() - p.getPrecio() / PUNTOS);
	}

	@Override
	public void venderProducto(Producto p, int cantidad) throws StockInsuficiente{
		//Equivalente de vender producto para clientes, no se hace nada con la venta
		
		if(!this.dao.cantidadSuficiente(cantidad, p))
		{
			throw new StockInsuficiente("Stock de " + p.getNombre() + " insuficiente.");
		}
		
		this.dao.venderProducto(p, cantidad, p.getPrecio() * cantidad);
		
	}
	
	@Override
	public void comprarProveedor(Producto p, int cantidad, boolean alquiler) throws SaldoInsuficiente {
		//Compra productos al proveedor, los añade al almacén y decrementa el saldo de la tienda en caso de que este sea suficiente
		
		boolean saldoSuficiente = dao.saldoSuficiente(p.getPrecio() * cantidad);
		
		if (!saldoSuficiente){
			throw new SaldoInsuficiente("Saldo insuficiente");
		}
		
		this.dao.comprarProveedor(p, cantidad, alquiler);
	}

	@Override
	public void devolverProveedor(Producto p, int cantidad) throws CantidadExcesiva {
		//Devuelve productos que se hayan comprado al proveedor, los elimina del almacén y devuelve el saldo a la tienda
		
		boolean cantidadSuficiente = dao.cantidadSuficiente(cantidad, p);
		
		if (!cantidadSuficiente){
			throw new CantidadExcesiva ("Cantidad a devolver excesiva");
		}
		
		if(!dao.devolverProveedor(p, cantidad))
			throw new CantidadExcesiva("El producto no se puede devolver, ya está alquilado");
	}

}
