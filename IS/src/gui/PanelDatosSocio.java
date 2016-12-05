package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import objetosTransfer.Socio;

/**
 * @author Sorin
 */
public class PanelDatosSocio extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombreSocio;
	private JTextField textFieldDNI;
	private JTextField textFieldTelefono;
	private JTextField textFieldApellidosSocio;
	private JButton btnAtras;
	private JTextField textFieldPuntos;
	private Socio socio;

	private Principal principal;
	private JTextField textFieldInteres;
	
	/**
	 * @param p - Panel Principal.
	 * @param s - Un socio.
	 */
	public PanelDatosSocio(Principal p, Socio s) {
		this.principal = p;
		this.socio = s;
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(40, 42, 46, 14);
		add(lblNewLabel);
		
		JLabel lblDni = new JLabel("DNI");
		lblDni.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDni.setBounds(40, 80, 46, 14);
		add(lblDni);
		
		JLabel lblTelfono = new JLabel("Teléfono");
		lblTelfono.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelfono.setBounds(28, 127, 58, 14);
		add(lblTelfono);
		
		textFieldNombreSocio = new JTextField();
		textFieldNombreSocio.setEditable(false);
		textFieldNombreSocio.setBounds(96, 39, 118, 20);
		textFieldNombreSocio.setText(socio.getNombre());
		add(textFieldNombreSocio);
		textFieldNombreSocio.setColumns(10);
		
		textFieldDNI = new JTextField();
		textFieldDNI.setEditable(false);
		textFieldDNI.setColumns(10);
		textFieldDNI.setText(socio.getDNI());
		textFieldDNI.setBounds(96, 77, 118, 20);
		add(textFieldDNI);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setEditable(false);
		textFieldTelefono.setColumns(10);
		textFieldTelefono.setText(socio.getTelefono());
		textFieldTelefono.setBounds(96, 124, 118, 20);
		add(textFieldTelefono);
		
		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblApellidos.setBounds(274, 42, 60, 14);
		add(lblApellidos);
		
		textFieldApellidosSocio = new JTextField();
		textFieldApellidosSocio.setEditable(false);
		textFieldApellidosSocio.setColumns(10);
		textFieldApellidosSocio.setText(socio.getApellidos());
		textFieldApellidosSocio.setBounds(350, 39, 118, 20);
		add(textFieldApellidosSocio);
		
		JLabel lblPuntosAcumulados = new JLabel("Puntos Acumulados");
		lblPuntosAcumulados.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPuntosAcumulados.setBounds(214, 87, 120, 14);
		add(lblPuntosAcumulados);
		
		JLabel lblJuegosDeInters = new JLabel("Juegos de Interés");
		lblJuegosDeInters.setHorizontalAlignment(SwingConstants.RIGHT);
		lblJuegosDeInters.setBounds(216, 127, 118, 14);
		add(lblJuegosDeInters);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(96, 350, 118, 23);
		add(btnAtras);
		
		textFieldPuntos = new JTextField();
		textFieldPuntos.setEditable(false);
		textFieldPuntos.setText(Double.toString(socio.getPuntos()));
		textFieldPuntos.setColumns(10);
		textFieldPuntos.setBounds(350, 84, 118, 20);
		add(textFieldPuntos);
		
		textFieldInteres = new JTextField();
		textFieldInteres.setEditable(false);
		textFieldInteres.setText(socio.getInteres().toString());
		textFieldInteres.setBounds(350, 124, 118, 20);
		add(textFieldInteres);

		intiEventos();
	}

	/**
	 * Inicializar los eventos de los componentes.
	 */
	private void intiEventos() {
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal.onCambiarAPanelCliente(socio, 1);
			}
		});
	}
}
