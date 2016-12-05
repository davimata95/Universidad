package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import objetosTransfer.Alquiler;
import objetosTransfer.CategoriaJuego;
import objetosTransfer.Producto;
import objetosTransfer.Socio;
import objetosTransfer.TipoProducto;
import controladores.ControladorAlmacen;
import controladores.ControladorAlquiler;
import excepciones.ErrorAlquiler;
import excepciones.SocioInvalido;

public class PanelAlquilarProducto extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JTextField textFieldPrecio;
	private JTextField textFieldImporteTotal;
	private JTextField textFieldFechaInicio;
	private JTextField textFieldFechaFinal;
	private Date fechaAlquilado ;
	private Date fechaDevolucion ;
	private JComboBox<Integer> comboBoxEdad;
	private JComboBox<CategoriaJuego> Categoria;
	private JComboBox<TipoProducto> TipoProduct;
	private final DefaultComboBoxModel<TipoProducto> modelTipo = new DefaultComboBoxModel<TipoProducto>(TipoProducto.values());
	private final DefaultComboBoxModel<Integer> modelEdad = new DefaultComboBoxModel<Integer>(new Integer[] {3, 7, 12, 16, 18});
	private final DefaultComboBoxModel<CategoriaJuego> modelCategoria = new DefaultComboBoxModel<CategoriaJuego>(CategoriaJuego.values());
	private ControladorAlquiler cQ;
	@SuppressWarnings("unused")
	private ControladorAlmacen cA;
	private Principal p;
	private Socio s;
	private JList<Producto> listSeleccionProd;
	

	
	/**
	 * @param prin - Panel Principal
	 * @param conAlmacen - Controlador Almacen
	 * @param cona - Controlador Alquiler.
	 * @param socio - El socio con el que se ha llegado a este Panel.
	 */
	public PanelAlquilarProducto(Principal prin, ControladorAlmacen conAlmacen, ControladorAlquiler cona, Socio socio) {
		setLayout(null);
		
		this.cQ = cona;
		this.p = prin;
		this.s = socio;
		this.cA = conAlmacen;
		
		
		JLabel label = new JLabel("Nombre: ");
		label.setBounds(295, 11, 62, 14);
		add(label);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(367, 8, 177, 20);
		add(textFieldNombre);
		
		JLabel label_1 = new JLabel("Precio M\u00E1ximo: ");
		label_1.setBounds(295, 42, 155, 14);
		add(label_1);
		
		textFieldPrecio = new JTextField();
		textFieldPrecio.setColumns(10);
		textFieldPrecio.setBounds(460, 39, 86, 20);
		add(textFieldPrecio);
		
		JLabel label_2 = new JLabel("Edad M\u00E1xima:");
		label_2.setBounds(10, 48, 120, 14);
		add(label_2);
		
		comboBoxEdad = new JComboBox<Integer>(modelEdad);
		comboBoxEdad.setBounds(140, 42, 145, 20);
		add(comboBoxEdad);
		
		Categoria = new JComboBox<CategoriaJuego>(modelCategoria);
		Categoria.setBounds(140, 73, 145, 20);
		add(Categoria);
		
		JLabel lblCategoriaJuego = new JLabel("Categoria Juego:");
		lblCategoriaJuego.setBounds(10, 76, 120, 14);
		add(lblCategoriaJuego);
		
		TipoProduct = new JComboBox<TipoProducto>(modelTipo);
		TipoProduct.setBounds(140, 8, 145, 20);
		add(TipoProduct);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(108, 130, 436, 130);
		add(scrollPane);
		
		listSeleccionProd = new JList<Producto>();
		scrollPane.setViewportView(listSeleccionProd);
		listSeleccionProd.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		
		
		JButton buttonBuscar = new JButton("Buscar");
		buttonBuscar.setBackground(Color.WHITE);
		buttonBuscar.setBounds(367, 67, 177, 23);
		buttonBuscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int edad = modelEdad.getElementAt(comboBoxEdad.getSelectedIndex());
				TipoProducto tp = modelTipo.getElementAt(TipoProduct.getSelectedIndex());
				CategoriaJuego cat = modelCategoria.getElementAt(Categoria.getSelectedIndex());
				Double precio = textFieldPrecio.getText().equals("") ? null : Double.parseDouble(textFieldPrecio.getText());
				Producto p = new Producto(0, tp,cat, textFieldNombre.getText(), edad,  precio, null);
				Producto [] lp = cQ.getEnAlquiler(p);
				DefaultListModel<Producto> modelo = new DefaultListModel<Producto>();
				for(int i = 0; i<lp.length; i++){
			       modelo.addElement(lp[i]);
				}
				listSeleccionProd.setModel(modelo);
			}
		});
		add(buttonBuscar);
		
		JLabel label_3 = new JLabel("Seleccionar Producto:");
		label_3.setBounds(10, 105, 177, 14);
		add(label_3);
		
		JLabel label_4 = new JLabel("Importe Total:");
		label_4.setBounds(10, 275, 85, 14);
		add(label_4);
		
		textFieldImporteTotal = new JTextField();
		textFieldImporteTotal.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldImporteTotal.setFont(new Font("Tahoma", Font.BOLD, 13));
		textFieldImporteTotal.setForeground(Color.BLACK);
		textFieldImporteTotal.setEditable(false);
		textFieldImporteTotal.setColumns(10);
		textFieldImporteTotal.setBounds(114, 268, 86, 20);
		add(textFieldImporteTotal);
		
		listSeleccionProd.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!listSeleccionProd.isSelectionEmpty()){
					Producto p = listSeleccionProd.getSelectedValue();
					String pr = new Double(p.getPrecio()*0.3- s.getPuntos()).toString();
					textFieldImporteTotal.setText(pr);
				}
			}
		});
		
		JLabel label_5 = new JLabel("Fecha Inicio: ");
		label_5.setBounds(10, 299, 85, 25);
		add(label_5);
		
		JLabel label_6 = new JLabel("Fecha Final:");
		label_6.setBounds(10, 332, 85, 14);
		add(label_6);
		
		fechaAlquilado = new Date();
		long semana = 604800000;
		fechaDevolucion = new Date(fechaAlquilado.getTime() + semana);
		SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
		
		
		textFieldFechaInicio = new JTextField();
		textFieldFechaInicio.setEditable(false);
		textFieldFechaInicio.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldFechaInicio.setFont(new Font("Tahoma", Font.BOLD, 13));
		textFieldFechaInicio.setForeground(Color.BLACK);
		textFieldFechaInicio.setColumns(10);
		textFieldFechaInicio.setBounds(113, 296, 216, 20);
		textFieldFechaInicio.setText(form.format(fechaAlquilado));
		
		add(textFieldFechaInicio);
		
		textFieldFechaFinal = new JTextField();
		textFieldFechaFinal.setEditable(false);
		textFieldFechaFinal.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldFechaFinal.setFont(new Font("Tahoma", Font.BOLD, 13));
		textFieldFechaFinal.setForeground(Color.BLACK);
		textFieldFechaFinal.setColumns(10);
		textFieldFechaFinal.setBounds(114, 326, 215, 20);
		textFieldFechaFinal.setText(form.format(fechaDevolucion));
		add(textFieldFechaFinal);
		
		JButton buttonAtras = new JButton("Atr\u00E1s");
		buttonAtras.setBackground(Color.WHITE);
		buttonAtras.setBounds(10, 358, 144, 59);
		buttonAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelCliente(s,1);
			}
		});
		add(buttonAtras);
		
		JButton buttonAlquilar = new JButton("Alquilar");
		buttonAlquilar.setBackground(Color.WHITE);
		buttonAlquilar.setBounds(396, 357, 150, 60);
		buttonAlquilar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(!listSeleccionProd.isSelectionEmpty()){
						Producto prodAl = listSeleccionProd.getSelectedValue();
						prodAl.setPrecio(prodAl.getPrecio()*0.3);
						Alquiler alquiler = new Alquiler(0, prodAl,fechaDevolucion,fechaAlquilado, s);
						try 
						{
							cQ.alquilarProducto(alquiler);
						} catch (SocioInvalido e1) 
						{
							JOptionPane.showMessageDialog( null, e1.getMessage(), null, JOptionPane.INFORMATION_MESSAGE);
						}
						JOptionPane.showMessageDialog(
							null,
							"Producto Alquilado",
							null,
							JOptionPane.INFORMATION_MESSAGE
								);
						p.onCambiarAPanelCliente(s,1);
					}
				} catch (ErrorAlquiler e1) {
					JOptionPane.showMessageDialog(
							null,
							e1.getMessage(),
							null,
							JOptionPane.INFORMATION_MESSAGE
					);
				}
			}
		});
		
		add(buttonAlquilar);
		
		JLabel lblTipoDeProducto = new JLabel("Tipo de Producto:");
		lblTipoDeProducto.setBounds(10, 11, 144, 14);
		add(lblTipoDeProducto);
		
		

	}
}
