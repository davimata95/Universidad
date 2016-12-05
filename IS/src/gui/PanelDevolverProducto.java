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
import objetosTransfer.Socio;
import objetosTransfer.TipoProducto;
import controladores.ControladorAlmacen;
import controladores.ControladorVentas;
import excepciones.SaldoInsuficiente;
import excepciones.SocioInvalido;

/**
 * @author Sorin
 */
public class PanelDevolverProducto extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombreProducto;
	private JButton btnDevolver, btnBuscar, btnAtrs;
	private JList<Producto> listProductos;
	private DefaultListModel<Producto> modelProductos = new DefaultListModel<Producto>();
	private JComboBox<TipoProducto> comboBoxTipoProducto;
	private final DefaultComboBoxModel<TipoProducto> modelTipo = new DefaultComboBoxModel<TipoProducto>(TipoProducto.values());
	private JComboBox<Integer> comboBoxEdad;

	private ControladorAlmacen controladorAlmacen;
	private ControladorVentas controladorVentas;
	private Principal principal;
	private Socio socio;
	private int num;
	private JScrollPane scrollPane;
	
	/**
	 * @param controlAlmacen -Controlador Almacen
 	 * @param controlVentas - Controlador Ventas.
	 * @param p - Panel Principal.
	 * @param s - Un socio.
	 * @param n - Indica si el usuario es un administrador o vendedor.
	 */
	public PanelDevolverProducto(ControladorAlmacen controlAlmacen, ControladorVentas controlVentas, Principal p, Socio s, int n) {
		this.controladorAlmacen = controlAlmacen;
		this.controladorVentas = controlVentas;
		this.principal = p;
		this.socio = s;
		this.num = n;
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Producto");
		lblNewLabel.setBounds(45, 76, 110, 14);
		add(lblNewLabel);
		
		textFieldNombreProducto = new JTextField();
		textFieldNombreProducto.setBounds(45, 104, 109, 20);
		add(textFieldNombreProducto);
		textFieldNombreProducto.setColumns(10);
		
		comboBoxTipoProducto = new JComboBox<TipoProducto>(modelTipo);
		comboBoxTipoProducto.setBounds(175, 104, 140, 21);
		add(comboBoxTipoProducto);
		
		btnDevolver = new JButton("Devolver");
		btnDevolver.setBackground(Color.WHITE);
		btnDevolver.setBounds(45, 292, 89, 23);
		add(btnDevolver);
		
		JLabel lblDevolverProducto = new JLabel("Devolver Producto");
		lblDevolverProducto.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblDevolverProducto.setHorizontalAlignment(SwingConstants.CENTER);
		lblDevolverProducto.setBounds(210, 38, 138, 23);
		add(lblDevolverProducto);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 135, 476, 146);
		add(scrollPane);
		
		listProductos = new JList<Producto>(modelProductos);
		scrollPane.setViewportView(listProductos);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setBounds(432, 103, 89, 23);
		add(btnBuscar);
		
		btnAtrs = new JButton("Atrás");
		btnAtrs.setBackground(Color.WHITE);
		btnAtrs.setBounds(430, 292, 91, 23);
		add(btnAtrs);
		
		comboBoxEdad = new JComboBox<Integer>(new Integer[] {3, 7, 12, 16, 18});
		comboBoxEdad.setBounds(379, 104, 43, 20);
		add(comboBoxEdad);
		
		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEdad.setBounds(323, 107, 46, 14);
		add(lblEdad);

		initEventos();
	}

	/**
	 * Inicializar los eventos de los componentes.
	 */
	private void initEventos() {
		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{	if(!listProductos.isSelectionEmpty())
					{
						controladorVentas.devolverProducto(socio, listProductos.getSelectedValue());
						JOptionPane.showMessageDialog(btnDevolver, "Producto devuelto");
					}
				} 
				catch (SaldoInsuficiente e1) {
					JOptionPane.showMessageDialog(btnDevolver, e1.getMessage());
				}
				catch (SocioInvalido e2) {
					JOptionPane.showMessageDialog(btnDevolver, e2.getMessage());
				}
			}
		});
		
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = textFieldNombreProducto.getText();
				TipoProducto tipo = modelTipo.getElementAt(comboBoxTipoProducto.getSelectedIndex());
				int edad = comboBoxEdad.getItemAt(comboBoxEdad.getSelectedIndex());
				
				Producto prod = new Producto(null, tipo, CategoriaJuego.CUALQUIERA, nombre, edad, null, null);
				
				Producto[] productos = controladorAlmacen.mostrarAlmacenVentas(prod);
				
				modelProductos = new DefaultListModel<Producto>();
				
				for(Producto p: productos)
					modelProductos.addElement(p);
				
				listProductos.setModel(modelProductos);
			}
		});
		
		btnAtrs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal.onCambiarAPanelCliente(socio, num);
			}
		});
	}
}