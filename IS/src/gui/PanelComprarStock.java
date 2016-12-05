package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import objetosTransfer.CategoriaJuego;
import objetosTransfer.Producto;
import objetosTransfer.TipoProducto;
import objetosTransfer.TipoUsuario;
import controladores.ControladorVentas;
import excepciones.SaldoInsuficiente;

/**
 * @author Sorin
 */
public class PanelComprarStock extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombreProd;
	private JTextField textFieldPrecioUnidad;
	private JTextField textFieldPrecioTotal;
	private JTextField textFieldCantidadProd;
	private JButton btnComprar, btnAtras;
	JComboBox<TipoProducto> comboBoxTipo;
	private final DefaultComboBoxModel<TipoProducto> modelTipo = new DefaultComboBoxModel<TipoProducto>(TipoProducto.values());
	
	JComboBox<CategoriaJuego> comboBoxCategoria;
	private final DefaultComboBoxModel<CategoriaJuego> modelCategoria = new DefaultComboBoxModel<CategoriaJuego>(CategoriaJuego.values());
	private Principal principal;
	private ControladorVentas controlVentas;
	private JComboBox<Integer> comboBoxEdad;
	private final DefaultComboBoxModel<Integer> modelEdad = new DefaultComboBoxModel<Integer>(new Integer[] {3, 7, 12, 16, 18});
	private TipoUsuario tipoUsuario;
	private JLabel lblTipo;
	private JLabel lblCategora;
	private JComboBox<String> comboBoxDestino;
	private JLabel lblAadirA;
	
	/**
	 * @param cVentas - Controlador Ventas.
	 * @param p - Panel Principal.
	 * @param tipoUsuario - Tipo del Usuario conectado.
	 */
	public PanelComprarStock(ControladorVentas cVentas, Principal p, TipoUsuario tipoUsuario) {
		super.setName("Comprar Stock");
		this.controlVentas = cVentas;
		this.principal = p;
		this.tipoUsuario = tipoUsuario;
		setLayout(null);
		
		JLabel lblComprarStock = new JLabel("Comprar Stock");
		lblComprarStock.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblComprarStock.setHorizontalAlignment(SwingConstants.CENTER);
		lblComprarStock.setBounds(240, 29, 148, 38);
		add(lblComprarStock);
		
		JLabel lblNewLabel = new JLabel("Nombre Producto");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(61, 169, 100, 14);
		add(lblNewLabel);
		
		textFieldNombreProd = new JTextField();
		textFieldNombreProd.setBounds(192, 166, 279, 20);
		add(textFieldNombreProd);
		textFieldNombreProd.setColumns(10);
		
		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCantidad.setBounds(68, 200, 93, 14);
		add(lblCantidad);
		
		JLabel lblPrecioUnidad = new JLabel("Precio Unidad");
		lblPrecioUnidad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecioUnidad.setBounds(68, 225, 93, 14);
		add(lblPrecioUnidad);
		
		JLabel lblPrecioTotal = new JLabel("Precio Total");
		lblPrecioTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecioTotal.setBounds(68, 250, 93, 14);
		add(lblPrecioTotal);
		
		textFieldCantidadProd = new JTextField();
		textFieldCantidadProd.setBounds(192, 197, 93, 20);
		add(textFieldCantidadProd);
		
		textFieldPrecioUnidad = new JTextField();
		textFieldPrecioUnidad.setText("Pulsar intro");
		textFieldPrecioUnidad.setColumns(10);
		textFieldPrecioUnidad.setBounds(192, 222, 93, 20);
		add(textFieldPrecioUnidad);
		
		textFieldPrecioTotal = new JTextField();
		//textFieldPrecioTotal.setEnabled(false);
		textFieldPrecioTotal.setEditable(false);
		textFieldPrecioTotal.setColumns(10);
		textFieldPrecioTotal.setBounds(192, 247, 93, 20);
		add(textFieldPrecioTotal);
		
		btnComprar = new JButton("Comprar");
		btnComprar.setBackground(Color.WHITE);
		btnComprar.setBounds(106, 313, 107, 38);
		add(btnComprar);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(364, 313, 107, 38);
		add(btnAtras);
		
		comboBoxTipo = new JComboBox<TipoProducto>(modelTipo);
		comboBoxTipo.setBounds(192, 108, 120, 20);
		add(comboBoxTipo);
		
		comboBoxEdad = new JComboBox<Integer>(modelEdad);
		comboBoxEdad.setBounds(192, 139, 56, 20);
		add(comboBoxEdad);
		
		comboBoxCategoria = new JComboBox<CategoriaJuego>(modelCategoria);
		comboBoxCategoria.setEnabled(false);
		comboBoxCategoria.setBounds(344, 108, 127, 20);
		add(comboBoxCategoria);
		
		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEdad.setBounds(61, 138, 100, 14);
		add(lblEdad);
		
		lblTipo = new JLabel("Tipo");
		lblTipo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipo.setBounds(208, 88, 46, 14);
		add(lblTipo);
		
		lblCategora = new JLabel("Categoría");
		lblCategora.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCategora.setBounds(342, 89, 72, 14);
		add(lblCategora);
		
		comboBoxDestino = new JComboBox<String>(new String[] {"ALQUILER", "VENTAS"});
		comboBoxDestino.setBounds(194, 282, 118, 20);
		add(comboBoxDestino);
		
		lblAadirA = new JLabel("Añadir a:");
		lblAadirA.setBounds(115, 285, 50, 14);
		add(lblAadirA);

		initEventos();
	}

	/**
	 * Inicializar los eventos de los componentes.
	 */
	private void initEventos() {
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					String nombreProducto = textFieldNombreProd.getText();
					int cantidad = Integer.parseInt(textFieldCantidadProd.getText());
					CategoriaJuego categoria = modelCategoria.getElementAt(comboBoxCategoria.getSelectedIndex());
					boolean alquiler = true;
					
					if(comboBoxDestino.getSelectedItem().toString().equals("VENTAS"))
						alquiler = false;
					
					if(cantidad < 1)
						throw new NumberFormatException();

					int edad = modelEdad.getElementAt(comboBoxEdad.getSelectedIndex());
					double precioUnidad = Double.parseDouble(textFieldPrecioUnidad.getText());
					
					Producto p = new Producto(null, modelTipo.getElementAt(comboBoxTipo.getSelectedIndex()), categoria, nombreProducto, edad, precioUnidad, new Date());
					
					try 
					{
						controlVentas.comprarProveedor(p, cantidad, alquiler);
						JOptionPane.showMessageDialog(btnComprar, "Producto añadido.");
						principal.onCambiarAPanelAlmacen(tipoUsuario);
					} catch (SaldoInsuficiente e2) {
						JOptionPane.showMessageDialog(btnComprar, e2.getMessage());
					}
				}
				catch(NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(btnComprar, "Datos introducidos inválidos.");
				}
			}
		});
		
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal.onCambiarAPanelAlmacen(tipoUsuario);
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
		
		textFieldPrecioUnidad.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					int cantidad = Integer.parseInt(textFieldCantidadProd.getText());
					double precio = Double.parseDouble(textFieldPrecioUnidad.getText());
					double total = precio * cantidad;
					
					textFieldPrecioTotal.setText(Double.toString(total));
				}
				catch (NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(textFieldCantidadProd, "Datos inválidos");
				}
			}
		});
	}
}
