package gui;

import java.awt.Color;
import java.awt.Font;
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
public class PanelBuscarSocio extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JTextField textFieldDNI;
	private JTextField textFieldApellidos;
	private JTextField textFieldTelefono;
	private JButton btnBuscar, btnSeleccionar, btnAtras;
	private JList<Socio> listSocios;
	private final DefaultListModel<Socio> modelListaSocios = new DefaultListModel<Socio>();
	private JComboBox<CategoriaJuego> comboBoxCategoriaJuego;
	private final DefaultComboBoxModel<CategoriaJuego> modelCategoria = new DefaultComboBoxModel<CategoriaJuego>(CategoriaJuego.values());
	
	private ControladorSocio controlador;
	private Principal principal;
	private JLabel lblIntereses;
	private JLabel lblBuscarYSeleccionar;
	private JScrollPane scrollPane;

	/**
	 * @param control - Controlador Socio.
	 * @param p - Panel Principal
	 */
	public PanelBuscarSocio(ControladorSocio control, Principal p) {
		this.controlador = control;
		this.principal = p;
		setLayout(null);
		
		JLabel label_2 = new JLabel("Nombre");
		label_2.setBounds(51, 70, 66, 14);
		add(label_2);
		
		JLabel label_3 = new JLabel("DNI");
		label_3.setBounds(51, 111, 46, 14);
		add(label_3);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(127, 67, 110, 20);
		add(textFieldNombre);
		
		textFieldDNI = new JTextField();
		textFieldDNI.setColumns(10);
		textFieldDNI.setBounds(127, 108, 110, 20);
		add(textFieldDNI);
		
		JLabel label_4 = new JLabel("Apellidos");
		label_4.setBounds(277, 70, 66, 14);
		add(label_4);
		
		JLabel label_5 = new JLabel("Teléfono");
		label_5.setBounds(277, 111, 66, 14);
		add(label_5);
		
		textFieldApellidos = new JTextField();
		textFieldApellidos.setColumns(10);
		textFieldApellidos.setBounds(342, 67, 198, 20);
		add(textFieldApellidos);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setColumns(10);
		textFieldTelefono.setBounds(342, 108, 198, 20);
		add(textFieldTelefono);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(Color.white);
		btnBuscar.setBounds(277, 136, 89, 23);
		add(btnBuscar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(51, 170, 489, 164);
		add(scrollPane);
		
		listSocios = new JList<Socio>(modelListaSocios);
		scrollPane.setViewportView(listSocios);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setBackground(Color.WHITE);
		btnSeleccionar.setBounds(374, 366, 166, 23);
		add(btnSeleccionar);
		
		comboBoxCategoriaJuego = new JComboBox<CategoriaJuego>(modelCategoria);
		comboBoxCategoriaJuego.setBackground(Color.WHITE);
		comboBoxCategoriaJuego.setBounds(127, 139, 110, 20);
		add(comboBoxCategoriaJuego);
		
		lblIntereses = new JLabel("Intereses");
		lblIntereses.setBounds(51, 142, 66, 14);
		add(lblIntereses);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(51, 366, 166, 23);
		add(btnAtras);
		
		lblBuscarYSeleccionar = new JLabel("Buscar y Seleccionar un socio para acceder a su panel.");
		lblBuscarYSeleccionar.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblBuscarYSeleccionar.setBounds(51, 26, 505, 30);
		add(lblBuscarYSeleccionar);
		
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
				for(int i = 0; i<listaSocios.size(); i++){
			       modelo.addElement(listaSocios.get(i));
				}
				listSocios.setModel(modelo);
			}
		});
		
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{	if(!listSocios.isSelectionEmpty()){
						controlador.iniciarSesionSocio(listSocios.getSelectedValue());
						JOptionPane.showMessageDialog(btnBuscar, "Sesión del socio iniciada.");
						principal.onCambiarAPanelCliente(listSocios.getSelectedValue(), 1);
					}
				}
				catch (ArrayIndexOutOfBoundsException e1)
				{
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