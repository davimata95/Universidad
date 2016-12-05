package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import objetosTransfer.Producto;
import objetosTransfer.TipoUsuario;
import controladores.ControladorAlmacen;

/**
 * @author Sorin
 */
public class PanelNovedades extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnSalir;
	
	private ControladorAlmacen controladorAlmacen;
	private Principal principal;
	private JList<Producto> listProductos;
	private final DefaultListModel<Producto> modelProductos = new DefaultListModel<Producto>();
	private TipoUsuario tipoUsuario;
	private JScrollPane scrollPane;
	
	/**
	 * @param control - Controlador Almacen.
	 * @param p - Panel Principal.
	 * @param tipo - Tipo de Usuario conectado.
	 */
	public PanelNovedades(ControladorAlmacen control, Principal p, TipoUsuario tipo) {
		this.controladorAlmacen = control;
		this.principal = p;
		this.tipoUsuario = tipo;
		setLayout(null);
		
		JLabel lblNovedades = new JLabel("NOVEDADES");
		lblNovedades.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblNovedades.setHorizontalAlignment(SwingConstants.CENTER);
		lblNovedades.setBounds(181, 32, 236, 54);
		add(lblNovedades);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 119, 525, 198);
		add(scrollPane);

		listProductos = new JList<Producto>(modelProductos);
		scrollPane.setViewportView(listProductos);
		
		listProductos.setModel(modelProductos);
		
		Producto[] productos = controladorAlmacen.mostrarNovedades();
		
		for(int i = 0; i < productos.length; i++)
			if(productos[i] != null)
				modelProductos.addElement(productos[i]);
		
		btnSalir = new JButton("Salir");
		btnSalir.setBackground(Color.WHITE);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal.onCambiarAPanelAlmacen(tipoUsuario);
			}
		});
		btnSalir.setBounds(253, 348, 112, 31);
		add(btnSalir);
	}
}