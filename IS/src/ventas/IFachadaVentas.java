package ventas;

import excepciones.CantidadExcesiva;
import excepciones.SaldoInsuficiente;
import excepciones.StockInsuficiente;
import objetosTransfer.Producto;
import objetosTransfer.Socio;

public interface IFachadaVentas {

	public void comprarProducto(Producto p, int cantidad, Socio socio) throws SaldoInsuficiente;
	public void venderProducto(Producto p, int cantidad, Socio socio) throws StockInsuficiente;
	public void venderProducto(Producto p, int cantidad) throws StockInsuficiente;
	public void devolverProducto(Socio s, Producto p) throws SaldoInsuficiente;
	public void comprarProveedor(Producto p, int cantidad, boolean alquiler) throws SaldoInsuficiente;
	public void devolverProveedor(Producto p, int cantidad) throws CantidadExcesiva;
}
