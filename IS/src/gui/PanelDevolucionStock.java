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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import objetosTransfer.CategoriaJuego;
import objetosTransfer.Producto;
import objetosTransfer.TipoProducto;
import objetosTransfer.TipoUsuario;
import controladores.ControladorAlmacen;
import controladores.ControladorVentas;
import excepciones.CantidadExcesiva;

/**
 * @author Sorin
 */
public class PanelDevolucionStock extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombreJuego;
	private JTextField textFieldPrecioUnidad;
	private JTextField textFieldPrecioTotal;
	private JSpinner spinnerCantidad;
	JButton btnDevolver, btnAtras;
	private JList<Producto> listProductos;
	private DefaultListModel<Producto> modelProductos = new DefaultListModel<Producto>();
	private JComboBox<CategoriaJuego> comboBoxCategoria;
	private final DefaultComboBoxModel<CategoriaJuego> modelCategoria = new DefaultComboBoxModel<CategoriaJuego>(CategoriaJuego.values());
	private JComboBox<TipoProducto> comboBoxTipo;
	private final DefaultComboBoxModel<TipoProducto> modelTipo = new DefaultComboBoxModel<TipoProducto>(TipoProducto.values());	
	private JButton btnBuscar;

	private ControladorAlmacen controlAlmacen;
	private ControladorVentas controlVentas;
	private Principal principal;
	private TipoUsuario tipoUsuario;
	private JScrollPane scrollPane;
	
	/**
	 * @param control - Controlador Almacen.
	 * @param controlVentas - Controlador Ventas.
	 * @param p - Panel Principal.
	 * @param tipoUsuario - Tipo Usuario.
	 */
	public PanelDevolucionStock(ControladorAlmacen control, ControladorVentas controlVentas, Principal p, TipoUsuario tipoUsuario) {
		this.controlAlmacen = control;
		this.controlVentas = controlVentas;
		this.principal = p;
		this.tipoUsuario = tipoUsuario;
		setLayout(null);
		
		JLabel lblDevolucinStock = new JLabel("Devolución Stock");
		lblDevolucinStock.setHorizontalAlignment(SwingConstants.CENTER);
		lblDevolucinStock.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblDevolucinStock.setBounds(203, 1, 148, 38);
		add(lblDevolucinStock);
		
		JLabel label_1 = new JLabel("Nombre Producto");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(69, 44, 100, 14);
		add(label_1);
		
		textFieldNombreJuego = new JTextField();
		textFieldNombreJuego.setColumns(10);
		textFieldNombreJuego.setBounds(211, 41, 186, 20);
		add(textFieldNombreJuego);
		
		JLabel label_2 = new JLabel("Cantidad");
		label_2.setBounds(69, 231, 93, 14);
		add(label_2);
		
		JLabel label_3 = new JLabel("Precio Unidad");
		label_3.setBounds(69, 259, 93, 14);
		add(label_3);
		
		JLabel label_4 = new JLabel("Precio Total");
		label_4.setBounds(69, 287, 93, 14);
		add(label_4);
		
		spinnerCantidad = new JSpinner();
		spinnerCantidad.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerCantidad.setBounds(211, 228, 72, 20);
		add(spinnerCantidad);
		
		textFieldPrecioUnidad = new JTextField();
		textFieldPrecioUnidad.setEditable(false);
		textFieldPrecioUnidad.setColumns(10);
		textFieldPrecioUnidad.setBounds(211, 256, 186, 20);
		add(textFieldPrecioUnidad);
		
		textFieldPrecioTotal = new JTextField();
		textFieldPrecioTotal.setEditable(false);
		textFieldPrecioTotal.setColumns(10);
		textFieldPrecioTotal.setBounds(211, 284, 186, 20);
		add(textFieldPrecioTotal);
		
		btnDevolver = new JButton("Devolver");
		btnDevolver.setBackground(Color.WHITE);
		btnDevolver.setBounds(69, 315, 107, 38);
		add(btnDevolver);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(389, 315, 107, 38);
		add(btnAtras);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(69, 96, 427, 123);
		add(scrollPane);
		
		listProductos = new JList<Producto>(modelProductos);
		scrollPane.setViewportView(listProductos);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setBounds(407, 40, 89, 23);
		add(btnBuscar);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipo.setBounds(94, 69, 46, 14);
		add(lblTipo);
		
		comboBoxTipo = new JComboBox<TipoProducto>(modelTipo);
		comboBoxTipo.setBounds(160, 66, 123, 20);
		add(comboBoxTipo);
		
		JLabel lblCategora = new JLabel("Categoría");
		lblCategora.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCategora.setBounds(275, 72, 72, 14);
		add(lblCategora);
		
		comboBoxCategoria = new JComboBox<CategoriaJuego>(modelCategoria);
		comboBoxCategoria.setEnabled(false);
		comboBoxCategoria.setBounds(357, 66, 139, 20);
		add(comboBoxCategoria);

		initEventos();
	}

	/**
	 * Inicializar los eventos de los componentes.
	 */
	private void initEventos() {
		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{	
					if(!listProductos.isSelectionEmpty())
					{
						int cantidad = (Integer)spinnerCantidad.getValue();
						
						if(cantidad > 0)
						{
							controlVentas.devolverProveedor(modelProductos.get(listProductos.getSelectedIndex()), cantidad);
							JOptionPane.showMessageDialog(btnDevolver, "Producto devuelto.");
							principal.onCambiarAPanelAlmacen(tipoUsuario);
						}
						else
							JOptionPane.showMessageDialog(btnDevolver, "Cantidad inválida");
					}
				} 
				catch (CantidadExcesiva e1)
				{
					JOptionPane.showMessageDialog(btnDevolver, e1.getMessage());
				}
				catch(ArrayIndexOutOfBoundsException e2)
				{
					JOptionPane.showMessageDialog(btnDevolver, e2.getMessage());
				}
			}
		});
		
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal.onCambiarAPanelAlmacen(tipoUsuario);
			}
		});
		
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TipoProducto tipo = modelTipo.getElementAt(comboBoxTipo.getSelectedIndex());
				CategoriaJuego categoria = modelCategoria.getElementAt(comboBoxCategoria.getSelectedIndex());
				Producto prod = new Producto(null, tipo, categoria, textFieldNombreJuego.getText(), 18, null, null);
				Producto[] productos = controlAlmacen.mostrarAlmacen(prod);
				
				modelProductos = new DefaultListModel<Producto>();
				
				for(Producto p: productos)
					if(p != null)
						modelProductos.addElement(p);
				
				listProductos.setModel(modelProductos);
			}
		});
		
		listProductos.addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent e)
			{
				Producto p = listProductos.getSelectedValue();
					
				if(p != null)
				{
					textFieldPrecioUnidad.setText(Double.toString(p.getPrecio()));
					textFieldPrecioTotal.setText(Double.toString((int)spinnerCantidad.getValue() * p.getPrecio()));
				}
			}
		});
		
		spinnerCantidad.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e)
			{
				Producto p = listProductos.getSelectedValue();
			
				if(p!=null)
				{
					String prT = new Double(p.getPrecio()*(Integer)spinnerCantidad.getValue()).toString();
					textFieldPrecioTotal.setText(prT);
				}
				
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