package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import objetosTransfer.TipoUsuario;
import controladores.ControladorAlmacen;

/**
 * @author Sorin
 */
public class PanelAlmacen extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private Principal principal;

	private JButton btnComprarStock, btnDevolverStock, btnNovedades,btnBuscarAlmacen, btnSalir, btnVerTransacciones;
	private TipoUsuario tipoUsuario;
	private ControladorAlmacen controlAlmacen;
	
	/**
	 * @param cAlmacen - Controlador Almacen.
	 * @param p - Panel Principal.
	 * @param tipoUsuario - TipoUsuario con el que se ha llegado a este Panel.
	 */
	public PanelAlmacen(ControladorAlmacen cAlmacen, Principal p, TipoUsuario tipoUsuario) {
		super.setName("Almacén");
		this.controlAlmacen = cAlmacen;
		this.tipoUsuario = tipoUsuario;
		this.principal = p;
		
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Almacén");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(202, 11, 187, 62);
		add(lblNewLabel);
		
		btnComprarStock = new JButton("Comprar Stock");
		btnComprarStock.setBackground(Color.WHITE);
		btnComprarStock.setBounds(70, 111, 162, 40);
		add(btnComprarStock);
		
		btnDevolverStock = new JButton("Devolver Stock");
		btnDevolverStock.setBackground(Color.WHITE);
		btnDevolverStock.setBounds(70, 193, 162, 40);
		add(btnDevolverStock);
		
		btnNovedades = new JButton("Novedades");
		btnNovedades.setBackground(Color.WHITE);
		btnNovedades.setBounds(357, 111, 162, 40);
		add(btnNovedades);
		
		btnBuscarAlmacen = new JButton("Buscar Almacén");
		btnBuscarAlmacen.setBackground(Color.WHITE);
		btnBuscarAlmacen.setBounds(357, 193, 162, 40);
		add(btnBuscarAlmacen);
		
		btnSalir = new JButton("Salir del Almacén");
		btnSalir.setBackground(Color.WHITE);
		btnSalir.setBounds(215, 263, 162, 28);
		add(btnSalir);
		
		btnVerTransacciones = new JButton("Ver transacciones");
		btnVerTransacciones.setBackground(Color.WHITE);
		btnVerTransacciones.setBounds(215, 161, 162, 23);
		add(btnVerTransacciones);
		
		//Un vendedor no puede comprar Stock ni devolverlo.
		if(this.tipoUsuario==TipoUsuario.VENDEDOR){
			btnComprarStock.setEnabled(false);
			btnDevolverStock.setEnabled(false);
		}
		
		initEventos();
	}

	/**
	 * Inicializacion de los eventos de los componentes.
	 */
	private void initEventos()
	{
		btnBuscarAlmacen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				principal.onCambiarAPanelBuscarAlmacen(tipoUsuario);
			}
		});
		
		btnComprarStock.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				principal.onCambiarAPanelComprarStock(tipoUsuario);
			}
		});
		
		btnDevolverStock.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				principal.onCambiarAPanelDevolucionStock(tipoUsuario);
			}
		});
		
		btnNovedades.addActionListener( new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				principal.onCambiarAPanelNovedades(tipoUsuario);
			}
		});
		
		btnSalir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(tipoUsuario == TipoUsuario.VENDEDOR)
					principal.onCambiarAPanelVendedor();
				else
					principal.onCambiarAPanelAdmin();
			}
		});
		
		btnVerTransacciones.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				principal.onCambiarAPanelTransacciones(controlAlmacen, principal, tipoUsuario);
			}
		});
	}
}