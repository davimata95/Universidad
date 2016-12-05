package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import objetosTransfer.Socio;
import objetosTransfer.TipoUsuario;
import controladores.ControladorAlmacen;
import controladores.ControladorAlquiler;
//import controladores.ControladorAlquiler;
import controladores.ControladorSocio;
import controladores.ControladorUsuario;
import controladores.ControladorVentas;

/**
 * @author Sorin
 */
public class Principal extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenuItem mntmCerrarSesion, mntmSalir, mntmAcercaDe;
	private JSeparator separator;

	private ControladorAlmacen controlAlmacen;
	private ControladorAlquiler controlAlquiler;
	private ControladorSocio controlSocio;
	private ControladorUsuario controlUsuario;
	private ControladorVentas controlVentas;
	private final JFrame acercaDe = new FrameAcercaDe();
	
	/**
	 * @param cA - Controlador Almacen.
 	 * @param cS - Controlador Socio.
	 * @param cU - Controlador Usuario.
	 * @param cV - Controlador Ventas.
	 * @param cAlq - Controlador Alquiler.
	 */
	public Principal(ControladorAlmacen cA, ControladorSocio cS, ControladorUsuario cU, ControladorVentas cV, ControladorAlquiler cAlq)
	{
		super("GAME");
		this.controlAlmacen = cA;
		this.controlSocio = cS;
		this.controlUsuario = cU;
		this.controlVentas = cV;
		this.controlAlquiler = cAlq;
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAcciones = new JMenu("Acciones");
		menuBar.add(mnAcciones);
		
		mntmCerrarSesion = new JMenuItem("Cerrar Sesión");
		mntmCerrarSesion.setEnabled(false);
		mnAcciones.add(mntmCerrarSesion);
		
		mntmCerrarSesion.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				controlUsuario.cerrarSesion();
				onCambiarAPanelLogin();
			}
		});
		
		separator = new JSeparator();
		mnAcciones.add(separator);
		
		mntmSalir = new JMenuItem("Salir");
		mnAcciones.add(mntmSalir);
		
		mntmSalir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int salir = JOptionPane.showConfirmDialog(contentPane, "¿Salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
				
				if(salir == 0)
				{
					controlUsuario.cerrarSesion();
					System.exit(0);
				}
			}
		});
		
		JMenu mnAcercaDe = new JMenu("Ayuda");
		menuBar.add(mnAcercaDe);
		
		mntmAcercaDe = new JMenuItem("Acerca de");
		mnAcercaDe.add(mntmAcercaDe);
		
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acercaDe.setVisible(true);
			}
		});
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.contentPane.add(new PanelLogin(controlUsuario, this));
		
		this.setSize(600, 500);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocation(100, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/*
	 * *********************************
	 * Métodos para el cambio de paneles.
	 * *********************************
	 * */
	
	public void onCambiarAPanelAdmin()
	{
		this.setTitle("Panel Administrador");
		mntmCerrarSesion.setEnabled(true);
		this.contentPane.removeAll();
		this.contentPane.add(new PanelAdmin(this, controlUsuario, controlAlmacen));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelAlmacen(TipoUsuario tipoUsuario)
	{
		this.setTitle("Almacén");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelAlmacen(controlAlmacen, this, tipoUsuario));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelAlquilarProducto(Socio socio)
	{
		this.setTitle("Alquiler Producto");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelAlquilarProducto(this, controlAlmacen, controlAlquiler, socio));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelBuscarAlmacen(TipoUsuario tipoUsuario)
	{
		this.setTitle("Buscar en Almacén");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelBuscarAlmacen(controlAlmacen, this, tipoUsuario));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelBuscarSocio()
	{
		this.setTitle("Buscar Socio");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelBuscarSocio(controlSocio, this));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelCliente(Socio socio, int num)
	{
		this.setTitle("Panel Cliente");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelCliente(this, socio, num,this.controlSocio));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelComprarStock(TipoUsuario tipoUsuario)
	{
		this.setTitle("Compra Stock");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelComprarStock(controlVentas, this, tipoUsuario));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelCompraSocio(Socio s)
	{
		this.setTitle("Compra Socio");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelCompraSocio(this, controlSocio, controlVentas,controlAlmacen, s));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelConsultarAlquiler(Socio s) {
		this.setTitle("Consultar Alquiler");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelConsultarAlquiler(controlAlquiler, s, this));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelDatosSocio(Socio s)
	{
		this.setTitle("Socio");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelDatosSocio(this, s));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelDevolucionStock(TipoUsuario tipoUsuario)
	{
		this.setTitle("Devolver Stock");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelDevolucionStock(controlAlmacen, controlVentas, this, tipoUsuario));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelDevolverProducto(Socio s, int num)
	{
		this.setTitle("Devolver Producto");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelDevolverProducto(controlAlmacen, controlVentas, this, s, num));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelEliminarUsuario()
	{
		this.setTitle("Eliminar Usuario");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelEliminarUsuario(this, controlUsuario));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelLogin()
	{
		this.setTitle("GAME");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelLogin(controlUsuario, this));
		this.mntmCerrarSesion.setEnabled(false);
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelModificarSocio(Socio socio)
	{
		this.setTitle("Modificar Datos Socio");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelModificarSocio(this, socio, controlSocio));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelNovedades(TipoUsuario tipo)
	{
		this.setTitle("Novedades");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelNovedades(controlAlmacen, this, tipo));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelNuevoAdmin()
	{
		this.setTitle("Nuevo Administrador");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelNuevoAdmin(this, controlUsuario));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelNuevoSocio()
	{
		this.setTitle("Nuevo Socio");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelNuevoSocio(this, controlSocio));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelNuevoVendedor()
	{
		this.setTitle("Nuevo Vendedor");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelNuevoVendedor(this, controlUsuario));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelVendedor()
	{
		this.setTitle("Panel Vendedor");
		mntmCerrarSesion.setEnabled(true);
		this.contentPane.removeAll();
		this.contentPane.add(new PanelVendedor(this, controlSocio, controlUsuario));
		this.contentPane.revalidate();
	}
	
	public void onCambiarAPanelVentaProducto(Socio s)
	{
		this.setTitle("Venta Producto");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelVentaProducto(controlVentas, controlAlmacen, this, s));
		this.contentPane.revalidate();
	}
	public void onCambiarAPanelEliminarSocio() {
		this.setTitle("Eliminar Socio");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelEliminarSocio(controlSocio, this));
		this.contentPane.revalidate();
		
	}

	public void onCambiarAPanelTransacciones(ControladorAlmacen controlAlmacen, Principal principal, TipoUsuario tipo)
	{
		this.setTitle("Transacciones");
		this.contentPane.removeAll();
		this.contentPane.add(new PanelTransacciones(this, controlAlmacen, tipo));
		this.contentPane.revalidate();
	}
}