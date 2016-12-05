package gui;

import java.awt.Color;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import objetosTransfer.CategoriaJuego;
import objetosTransfer.Producto;
import objetosTransfer.Socio;
import objetosTransfer.TipoProducto;
import controladores.ControladorAlmacen;
import controladores.ControladorVentas;
import excepciones.SocioInvalido;
import excepciones.StockInsuficiente;

/**
 * @author Sorin
 */
public class PanelVentaProducto extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombreProd;
	private JTextField textFieldPrecioMax;
	private JTextField textFieldImporte;
	private JTextField textFieldCantidad;
	private JTextField textFieldTotal;
	private JButton btnBuscar, btnAtras, btnEfectuarPago;
	private JComboBox<Integer> comboBoxEdadMax;
	private JList<Producto> listSeleccionarProducto;
	private DefaultListModel<Producto> modelProductos = new DefaultListModel<Producto>();
	private JComboBox<CategoriaJuego> comboBoxCategoria;
	private final DefaultComboBoxModel<CategoriaJuego> modelCategoria = new DefaultComboBoxModel<CategoriaJuego>(CategoriaJuego.values());
	private JComboBox<TipoProducto> comboBoxTipo;
	private final DefaultComboBoxModel<TipoProducto> modelTipo = new DefaultComboBoxModel<TipoProducto>(TipoProducto.values());
	
	private Socio socio;
	private ControladorVentas controladorVentas;
	private Principal principal;
	private ControladorAlmacen controladorAlmacen;
	private JScrollPane scrollPane;
	
	/**
	 * @param cVentas - Controlador Ventas.
	 * @param cAlmacen - Controador Almacen.
	 * @param p - Panel Principal.
	 * @param s - Un socio.
	 */
	public PanelVentaProducto(ControladorVentas cVentas, ControladorAlmacen cAlmacen, Principal p, Socio s) {
		this.socio = s;
		this.controladorAlmacen = cAlmacen;
		this.controladorVentas = cVentas;
		this.principal = p;
		setLayout(null);
		
		comboBoxCategoria = new JComboBox<CategoriaJuego>(modelCategoria);
		comboBoxCategoria.setEnabled(false);
		comboBoxCategoria.setBounds(27, 60, 108, 20);
		add(comboBoxCategoria);
		
		JLabel label = new JLabel("Nombre:");
		label.setBounds(145, 31, 62, 14);
		add(label);
		
		textFieldNombreProd = new JTextField();
		textFieldNombreProd.setColumns(10);
		textFieldNombreProd.setBounds(226, 28, 158, 20);
		add(textFieldNombreProd);
		
		JLabel label_1 = new JLabel("Precio max.");
		label_1.setBounds(394, 31, 78, 14);
		add(label_1);
		
		textFieldPrecioMax = new JTextField();
		textFieldPrecioMax.setColumns(10);
		textFieldPrecioMax.setBounds(465, 28, 56, 20);
		add(textFieldPrecioMax);
		
		JLabel lblEdadMax = new JLabel("Edad");
		lblEdadMax.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEdadMax.setBounds(236, 63, 77, 14);
		add(lblEdadMax);
		
		comboBoxEdadMax = new JComboBox<Integer>();
		comboBoxEdadMax.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {3, 7, 12, 16, 18}));
		comboBoxEdadMax.setBounds(328, 60, 56, 20);
		add(comboBoxEdadMax);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setBounds(394, 59, 127, 23);
		add(btnBuscar);
		
		JLabel label_4 = new JLabel("Importe:");
		label_4.setBounds(27, 274, 56, 14);
		add(label_4);
		
		JLabel label_5 = new JLabel("Cantidad:");
		label_5.setBounds(27, 299, 56, 14);
		add(label_5);
		
		JLabel label_6 = new JLabel("Total:");
		label_6.setBounds(27, 324, 50, 14);
		add(label_6);
		
		textFieldImporte = new JTextField();
		textFieldImporte.setEditable(false);
		textFieldImporte.setColumns(10);
		textFieldImporte.setBounds(81, 271, 86, 20);
		add(textFieldImporte);
		
		textFieldCantidad = new JTextField();
		textFieldCantidad.setText("Pulsar intro");
		textFieldCantidad.setColumns(10);
		textFieldCantidad.setBounds(81, 296, 86, 20);
		add(textFieldCantidad);
		
		textFieldTotal = new JTextField();
		textFieldTotal.setEditable(false);
		textFieldTotal.setColumns(10);
		textFieldTotal.setBounds(81, 321, 86, 20);
		add(textFieldTotal);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(27, 349, 89, 23);
		add(btnAtras);
		
		btnEfectuarPago = new JButton("Efectuar Pago");
		btnEfectuarPago.setBackground(Color.WHITE);
		btnEfectuarPago.setBounds(409, 349, 120, 23);
		add(btnEfectuarPago);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 102, 494, 158);
		add(scrollPane);
		
		listSeleccionarProducto = new JList<Producto>(modelProductos);
		scrollPane.setViewportView(listSeleccionarProducto);
		
		comboBoxTipo = new JComboBox<TipoProducto>(modelTipo);
		comboBoxTipo.setBounds(27, 25, 108, 20);
		add(comboBoxTipo);

		initEventos();
	}

	/**
	 * Inicializar los eventos de los componentes.
	 */
	private void initEventos() {
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					CategoriaJuego categoria = modelCategoria.getElementAt(comboBoxCategoria.getSelectedIndex());
					String nombre = textFieldNombreProd.getText();
					int edad = comboBoxEdadMax.getModel().getElementAt(comboBoxEdadMax.getSelectedIndex());
					Double precio = textFieldPrecioMax.getText().equals("") ? null : Double.parseDouble(textFieldPrecioMax.getText());
					TipoProducto tipo = modelTipo.getElementAt(comboBoxTipo.getSelectedIndex());
					
					Producto prod = new Producto(0, tipo, categoria, nombre, edad, precio, null);
					Producto[] productos = controladorAlmacen.mostrarAlmacenVentas(prod);
					
					modelProductos = new DefaultListModel<Producto>();
					
					for(Producto p: productos)
						if(p != null)
							modelProductos.addElement(p);
					
					listSeleccionarProducto.setModel(modelProductos);
				}
				catch (NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(btnBuscar, e1.getMessage());
				}
			}
		});
		
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal.onCambiarAPanelCliente(socio, socio == null ? 0 : 1);
			}
		});
		
		btnEfectuarPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(!listSeleccionarProducto.isSelectionEmpty())
				{
					try
					{
						Producto p = modelProductos.get(listSeleccionarProducto.getSelectedIndex());
						
						if(Integer.parseInt(textFieldCantidad.getText()) < 1)
							throw new NumberFormatException();
								
						controladorVentas.venderProducto(p, Integer.parseInt(textFieldCantidad.getText()), socio);
						JOptionPane.showMessageDialog(
								null,
								"Venta Realizada",
								null,
								JOptionPane.INFORMATION_MESSAGE);
					} 
					catch(ArrayIndexOutOfBoundsException e1)
					{
						JOptionPane.showMessageDialog(btnEfectuarPago, "Ningún producto seleccionado");
					}
					catch (NumberFormatException e2) 
					{
						JOptionPane.showMessageDialog(btnEfectuarPago, "Cantidad inválida");
					} 
					catch (SocioInvalido e4) 
					{
						JOptionPane.showMessageDialog(btnEfectuarPago, e4.getMessage());
					}
					catch (StockInsuficiente e1) 
					{
						JOptionPane.showMessageDialog(btnEfectuarPago, e1.getMessage());
					}
				}
			}
		});
		
		comboBoxTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(modelTipo.getElementAt(comboBoxTipo.getSelectedIndex()) == TipoProducto.JUEGOS)
					comboBoxCategoria.setEnabled(true);
				else
					comboBoxCategoria.setEnabled(false);
			}
		});
		
		listSeleccionarProducto.addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent e)
			{
				if(!listSeleccionarProducto.isSelectionEmpty())
				{
					Producto p = listSeleccionarProducto.getSelectedValue();
					textFieldImporte.setText(p.getPrecio().toString());
				}
			}
		});
		
		textFieldCantidad.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					double importe = Double.parseDouble(textFieldImporte.getText());
					int cantidad = Integer.parseInt(textFieldCantidad.getText());
					double total = importe * cantidad;
					if(socio == null){
						textFieldTotal.setText(Double.toString(total));
					}else
					{
						Double precioFinal = total- socio.getPuntos();
						textFieldTotal.setText(Double.toString(precioFinal));
					}					
				}
				catch (NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(textFieldCantidad, "Cantidad inválida");
				}
			}
		});
	}
}
