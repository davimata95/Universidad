package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

import objetosTransfer.CategoriaJuego;
import objetosTransfer.Socio;
import controladores.ControladorSocio;

/**
 * @author Sorin
 */
public class PanelEliminarSocio extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JTextField textFieldDNI;
	private JTextField textFieldApellidos;
	private JTextField textFieldTelefono;
	private JButton btnBuscar, btnEliminar, btnAtras;
	private JList<Socio> listSocios;
	private final DefaultListModel<Socio> modelListaSocios = new DefaultListModel<Socio>();
	private JComboBox<CategoriaJuego> comboBoxCategoriaJuego;
	private final DefaultComboBoxModel<CategoriaJuego> modelCategoria = new DefaultComboBoxModel<CategoriaJuego>(CategoriaJuego.values());
	
	private ControladorSocio controlador;
	private Principal principal;
	private JLabel lblIntereses;
	private JScrollPane scrollPane;

	/**
	 * @param control - Controlador Socio.
	 * @param p - Panel Principal.
	 */
	public PanelEliminarSocio(ControladorSocio control, Principal p) {
		super.setName("Eliminar Socio");
		this.controlador = control;
		this.principal = p;
		setLayout(null);
		
		JLabel label_2 = new JLabel("Nombre");
		label_2.setBounds(38, 70, 73, 14);
		add(label_2);
		
		JLabel label_3 = new JLabel("DNI");
		label_3.setBounds(38, 111, 73, 14);
		add(label_3);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(114, 67, 110, 20);
		add(textFieldNombre);
		
		textFieldDNI = new JTextField();
		textFieldDNI.setColumns(10);
		textFieldDNI.setBounds(114, 108, 110, 20);
		add(textFieldDNI);
		
		JLabel label_4 = new JLabel("Apellidos");
		label_4.setBounds(247, 70, 83, 14);
		add(label_4);
		
		JLabel label_5 = new JLabel("Teléfono");
		label_5.setBounds(247, 111, 83, 14);
		add(label_5);
		
		textFieldApellidos = new JTextField();
		textFieldApellidos.setColumns(10);
		textFieldApellidos.setBounds(329, 67, 166, 20);
		add(textFieldApellidos);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setColumns(10);
		textFieldTelefono.setBounds(329, 108, 86, 20);
		add(textFieldTelefono);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setBounds(438, 107, 89, 23);
		add(btnBuscar);
		
		JLabel label_6 = new JLabel("Seleccionar Socio");
		label_6.setBounds(38, 172, 186, 14);
		add(label_6);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(114, 191, 392, 164);
		add(scrollPane);
		
		listSocios = new JList<Socio>(modelListaSocios);
		scrollPane.setViewportView(listSocios);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(Color.WHITE);
		btnEliminar.setBounds(329, 366, 166, 41);
		add(btnEliminar);
		
		comboBoxCategoriaJuego = new JComboBox<CategoriaJuego>(modelCategoria);
		comboBoxCategoriaJuego.setBounds(151, 139, 133, 20);
		add(comboBoxCategoriaJuego);
		
		lblIntereses = new JLabel("Intereses");
		lblIntereses.setBounds(38, 142, 103, 14);
		add(lblIntereses);
		
		btnAtras = new JButton("Atras");
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(124, 366, 166, 41);
		add(btnAtras);
		
		initEventos();
	}

	/**
	 * Inicializar los eventos de los componentes.
	 */
	private void initEventos() {
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = textFieldNombre.getText();
				String apellidos = textFieldApellidos.getText();
				String dni = textFieldDNI.getText();
				String telefono = textFieldTelefono.getText();
				CategoriaJuego categoria = modelCategoria.getElementAt(comboBoxCategoriaJuego.getSelectedIndex());
				
				Socio socio = new Socio(nombre, apellidos, dni, telefono, categoria);
				ArrayList <Socio> listaSocios = controlador.buscarListaSocios(socio);
				
				DefaultListModel<Socio> modelo = new DefaultListModel<Socio>();
				for(int i = 0; i<listaSocios.size(); i++)
					if(listaSocios.get(i) != null)
						modelo.addElement(listaSocios.get(i));
				
				listSocios.setModel(modelo);
			}
		});
		
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listSocios.isSelectionEmpty())
				{
					controlador.eliminarSocio(listSocios.getSelectedValue());
					JOptionPane.showMessageDialog(null, "Socio eliminado");
					
					principal.onCambiarAPanelVendedor();
				}
			}
		});
		
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal.onCambiarAPanelVendedor();
			}
		});
	}
}