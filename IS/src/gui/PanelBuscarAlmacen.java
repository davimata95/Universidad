package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import objetosTransfer.CategoriaJuego;
import objetosTransfer.Producto;
import objetosTransfer.TipoProducto;
import objetosTransfer.TipoUsuario;
import controladores.ControladorAlmacen;

/**
 * @author Sorin
 */
public class PanelBuscarAlmacen extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JTextField textFieldPrecioMax;
	private JComboBox<CategoriaJuego> comboBoxCategoria;
	private final DefaultComboBoxModel<CategoriaJuego> modelCategoria = new DefaultComboBoxModel<CategoriaJuego>(CategoriaJuego.values());
	private JComboBox<Integer> comboBoxEdadMax;
	private final DefaultComboBoxModel<Integer> modelEdad = new DefaultComboBoxModel<Integer>(new Integer[] {3, 7, 12, 16, 18});
	private JComboBox<TipoProducto> comboBoxTipo;
	private final DefaultComboBoxModel<TipoProducto> modelTipo = new DefaultComboBoxModel<TipoProducto>(TipoProducto.values());
	private JButton btnBuscar, btnAtras;
	private JLabel lblBuscarEnAlmacen;
	private JList<Producto> listProductos;
	private final DefaultListModel<Producto> modelProductos = new DefaultListModel<Producto>();
	
	private ControladorAlmacen controlAlmacen;
	private Principal principal;
	
	private TipoUsuario tipoUsuario;
	private JScrollPane scrollPane;

	/**
	 * @param cAlmacen - Controlador Almacen.
	 * @param p - Panel Principal.
	 * @param tipoUsuario - Tipo del Usuario con el que se ha llegado a este Panel.
	 */
	public PanelBuscarAlmacen(ControladorAlmacen cAlmacen, Principal p, TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
		this.controlAlmacen = cAlmacen;
		this.principal = p;
		setLayout(null);
		
		comboBoxCategoria = new JComboBox<CategoriaJuego>();
		comboBoxCategoria.setModel(modelCategoria);
		comboBoxCategoria.setBounds(125, 90, 125, 20);
		comboBoxCategoria.setEnabled(false);
		add(comboBoxCategoria);
		
		JLabel label = new JLabel("Nombre:");
		label.setBounds(53, 124, 62, 14);
		add(label);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(125, 121, 249, 20);
		add(textFieldNombre);
		
		JLabel label_1 = new JLabel("Precio max.");
		label_1.setBounds(39, 152, 78, 14);
		add(label_1);
		
		textFieldPrecioMax = new JTextField();
		textFieldPrecioMax.setColumns(10);
		textFieldPrecioMax.setBounds(125, 149, 40, 20);
		add(textFieldPrecioMax);
		
		JLabel label_2 = new JLabel("Edad max.");
		label_2.setBounds(38, 183, 77, 14);
		add(label_2);
		
		comboBoxEdadMax = new JComboBox<Integer>();
		comboBoxEdadMax.setModel(modelEdad);
		comboBoxEdadMax.setBounds(126, 180, 56, 20);
		add(comboBoxEdadMax);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setBounds(125, 211, 89, 23);
		add(btnBuscar);
		
		lblBuscarEnAlmacen = new JLabel("Buscar en Almacén");
		lblBuscarEnAlmacen.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblBuscarEnAlmacen.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscarEnAlmacen.setBounds(225, 31, 173, 14);
		add(lblBuscarEnAlmacen);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(125, 257, 423, 120);
		add(scrollPane);
		
		listProductos = new JList<Producto>(modelProductos);
		scrollPane.setViewportView(listProductos);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(459, 211, 89, 23);
		add(btnAtras);
		
		comboBoxTipo = new JComboBox<TipoProducto>(modelTipo);
		comboBoxTipo.setBounds(125, 59, 125, 20);
		add(comboBoxTipo);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipo.setBounds(46, 62, 46, 14);
		add(lblTipo);
		
		JLabel lblCategora = new JLabel("Categoría:");
		lblCategora.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCategora.setBounds(10, 93, 82, 14);
		add(lblCategora);
		
		initEventos();
	}

	/**
	 * Inicializar los eventos de los componentes.
	 */
	private void initEventos()
	{
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					TipoProducto tipo = modelTipo.getElementAt(comboBoxTipo.getSelectedIndex());
					CategoriaJuego categoria = modelCategoria.getElementAt(comboBoxCategoria.getSelectedIndex());
					String nombre = textFieldNombre.getText();
					int edad = modelEdad.getElementAt(comboBoxEdadMax.getSelectedIndex());
					String precio = textFieldPrecioMax.getText();
					Double price = precio.equals("") ? null : Double.parseDouble(precio);
					
					Producto prod = new Producto(0, tipo, categoria, nombre, edad, price, null);
					Producto[] productos = controlAlmacen.mostrarAlmacen(prod);
					
					DefaultListModel<Producto> modelProductos = new DefaultListModel<Producto>();
					for(Producto p: productos)
						if(p != null)
							modelProductos.addElement(p);
					
					listProductos.setModel(modelProductos);
				}
				catch(NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(btnBuscar, "Precio inválido");
				}
			}
		});
		
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal.onCambiarAPanelAlmacen(tipoUsuario);
			}
		});
		
		comboBoxTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(modelTipo.getElementAt(comboBoxTipo.getSelectedIndex()) == TipoProducto.JUEGOS)
					comboBoxCategoria.setEnabled(true);
				else
				{
					comboBoxCategoria.setSelectedIndex(0);
					comboBoxCategoria.setEnabled(false);
				}
			}
		});
	}
}