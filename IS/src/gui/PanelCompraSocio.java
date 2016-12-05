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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import objetosTransfer.CategoriaJuego;
import objetosTransfer.Producto;
import objetosTransfer.Socio;
import objetosTransfer.TipoProducto;
import controladores.ControladorAlmacen;
import controladores.ControladorSocio;
import controladores.ControladorVentas;
import excepciones.SaldoInsuficiente;
import excepciones.SocioInvalido;

public class PanelCompraSocio extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombreProd;
	private JTextField textFieldPrecioMaximo;
	private JTextField textFieldImporteTotal;
	private JTextField textFieldImporteUnitario;
	private JComboBox<Integer> comboBoxEdad;
	private JComboBox<TipoProducto> comboBoxTipoProducto;
	private JComboBox<CategoriaJuego> comboBoxCategoria;
	private final JList<Producto> listSeleccionProd;
	private final JSpinner spinnerCantidad;
	private final DefaultComboBoxModel<TipoProducto> modelTipo = new DefaultComboBoxModel<TipoProducto>(TipoProducto.values());
	private final DefaultComboBoxModel<Integer> modelEdad = new DefaultComboBoxModel<Integer>(new Integer[] {3, 7, 12, 16, 18});
	private final DefaultComboBoxModel<CategoriaJuego> modelCategoria = new DefaultComboBoxModel<CategoriaJuego>(CategoriaJuego.values());
	@SuppressWarnings("unused")
	private ControladorSocio c;
	private ControladorVentas v;
	private ControladorAlmacen cA;
	private Principal p;
	@SuppressWarnings("unused")
	private Producto prod;
	private Socio socio;

	/**
	 * @param prin - Panel Principal.
	 * @param contr - Controlador Socio.
	 * @param contrv - Controlador Ventas.
	 * @param controlA - Controlador Almacen.
	 * @param s - Un socio.
	 */
	public PanelCompraSocio(Principal prin, ControladorSocio contr, ControladorVentas contrv,ControladorAlmacen controlA, Socio s) {
		setLayout(null);
		
		this.c = contr;
		this.cA = controlA;
		this.v = contrv;
		this.p = prin;
		this.socio = s;
		
		JLabel label = new JLabel("Nombre: ");
		label.setBounds(293, 11, 74, 14);
		add(label);
		
		textFieldNombreProd = new JTextField();
		textFieldNombreProd.setColumns(10);
		textFieldNombreProd.setBounds(369, 8, 177, 20);
		add(textFieldNombreProd);
		
		JLabel label_1 = new JLabel("Precio M\u00E1ximo: ");
		label_1.setBounds(293, 33, 105, 14);
		add(label_1);
		
		textFieldPrecioMaximo = new JTextField();
		textFieldPrecioMaximo.setColumns(10);
		textFieldPrecioMaximo.setBounds(441, 30, 105, 20);
		add(textFieldPrecioMaximo);
		
		JLabel label_2 = new JLabel("Edad M\u00E1xima:");
		label_2.setBounds(10, 39, 93, 14);
		add(label_2);
		
		listSeleccionProd = new JList<Producto>();
		listSeleccionProd.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		listSeleccionProd.setBounds(105, 108, 365, 107);
		
		
		spinnerCantidad = new JSpinner();
		spinnerCantidad.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		spinnerCantidad.setBounds(125, 253, 86, 20);
		spinnerCantidad.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				Producto p = listSeleccionProd.getSelectedValue();
				if(p!=null){
					String prT = new Double(p.getPrecio()*(Integer)spinnerCantidad.getValue()).toString();
					textFieldImporteTotal.setText(prT);
				}
				
			}
		});
		add(spinnerCantidad);
		
		comboBoxEdad = new JComboBox<Integer>(modelEdad);
		comboBoxEdad.setBounds(141, 36, 86, 20);
		add(comboBoxEdad);
		
		comboBoxTipoProducto = new JComboBox<TipoProducto>(modelTipo);
		comboBoxTipoProducto.setBounds(141, 8, 142, 20);
		add(comboBoxTipoProducto);
		
		comboBoxCategoria = new JComboBox<CategoriaJuego>(modelCategoria);
		comboBoxCategoria.setBounds(141, 61, 142, 20);
		add(comboBoxCategoria);
		
		listSeleccionProd.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Producto p = listSeleccionProd.getSelectedValue();
				String prU = new Double(p.getPrecio()).toString();
				String prT = new Double(p.getPrecio()*(Integer)spinnerCantidad.getValue()).toString();
				textFieldImporteUnitario.setText(prU);
				textFieldImporteTotal.setText(prT);
			}
		});
		add(listSeleccionProd);
		
		JButton buttonBuscar = new JButton("Buscar");
		buttonBuscar.setBackground(Color.WHITE);
		buttonBuscar.setBounds(369, 61, 177, 23);
		buttonBuscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int edad = modelEdad.getElementAt(comboBoxEdad.getSelectedIndex());
				TipoProducto tp = modelTipo.getElementAt(comboBoxTipoProducto.getSelectedIndex());
				CategoriaJuego cat = modelCategoria.getElementAt(comboBoxCategoria.getSelectedIndex());
				Double precio = textFieldPrecioMaximo.getText().equals("") ? null : Double.parseDouble(textFieldPrecioMaximo.getText());
				Producto p = new Producto(0, tp,cat, textFieldNombreProd.getText(), edad,  precio, null);
				Producto [] lp = cA.mostrarAlmacenVentas(p);
				DefaultListModel<Producto> modelo = new DefaultListModel<Producto>();
				for(int i = 0; i<lp.length; i++){
			       modelo.addElement(lp[i]);
				}
				listSeleccionProd.setModel(modelo);
			}
		});
		add(buttonBuscar);
		
		JLabel label_3 = new JLabel("Seleccionar Producto:");
		label_3.setBounds(10, 92, 182, 14);
		add(label_3);
		
		JLabel label_4 = new JLabel("Importe Total:");
		label_4.setBounds(10, 284, 111, 14);
		add(label_4);
		
		textFieldImporteTotal = new JTextField();
		textFieldImporteTotal.setEditable(false);
		textFieldImporteTotal.setColumns(10);
		textFieldImporteTotal.setBounds(125, 281, 86, 20);
		add(textFieldImporteTotal);
		
		JLabel label_5 = new JLabel("Cantidad:");
		label_5.setBounds(10, 251, 111, 25);
		add(label_5);
		
		JLabel label_6 = new JLabel("Importe compra:");
		label_6.setBounds(10, 226, 111, 14);
		add(label_6);
		
		textFieldImporteUnitario = new JTextField();
		textFieldImporteUnitario.setEditable(false);
		textFieldImporteUnitario.setColumns(10);
		textFieldImporteUnitario.setBounds(126, 226, 86, 20);
		add(textFieldImporteUnitario);
		
		JButton buttonAtras = new JButton("Atr\u00E1s");
		buttonAtras.setBackground(Color.WHITE);
		buttonAtras.setBounds(10, 312, 121, 46);
		buttonAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelCliente(socio,1);
			}
		});
		add(buttonAtras);
		
		JButton buttonEfectuarCompra = new JButton("Efectuar Compra");
		buttonEfectuarCompra.setBackground(Color.WHITE);
		buttonEfectuarCompra.setBounds(379, 312, 167, 46);
		buttonEfectuarCompra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(!listSeleccionProd.isSelectionEmpty()){
						Producto prod = listSeleccionProd.getSelectedValue();
						v.comprarProducto(prod, (Integer)spinnerCantidad.getValue(), socio);
						JOptionPane.showMessageDialog(
								null,
								"Producto/s Comprados",
								null,
								JOptionPane.INFORMATION_MESSAGE
						);
						p.onCambiarAPanelCliente(socio,1);
					}
				} catch (SaldoInsuficiente | SocioInvalido e1) {
					JOptionPane.showMessageDialog(
							null,
							e1.getMessage(),
							null,
							JOptionPane.INFORMATION_MESSAGE
					);
				}
			}
		});
		
		this.add(buttonEfectuarCompra);
		
		JLabel lblTipoDeProducto = new JLabel("Tipo de Producto:");
		lblTipoDeProducto.setBounds(10, 11, 133, 14);
		add(lblTipoDeProducto);
		
		
		JLabel lblCategora = new JLabel("Categoría de Juego:");
		lblCategora.setBounds(10, 64, 121, 14);
		add(lblCategora);
		
		}

}
	

