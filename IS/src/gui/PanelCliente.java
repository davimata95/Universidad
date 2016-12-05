package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import objetosTransfer.Socio;
import controladores.ControladorSocio;
import excepciones.SocioInvalido;

public class PanelCliente extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombreSocio;
	private Socio s;
	private Principal p;
	private int n;
	private ControladorSocio control;

	/**
	 * @param prin - Panel Principal.
	 * @param socio - Un socio.
	 * @param num - Indica si el Usuario conectado es un administrador o no (1 admin; 0 vendedor)
	 * @param cSocio - Controlador Socio.
	 */
	public PanelCliente(Principal prin , Socio socio, int num, ControladorSocio cSocio) {
		setLayout(null);
		this.control = cSocio;
		JLabel label = new JLabel("CLIENTE");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 50));
		label.setBounds(10, 11, 520, 52);
		add(label);
		this.s = socio;
		this.p = prin;
		this.n = num;
		
		JButton buttonComprar = new JButton("Comprar");
		buttonComprar.setBackground(Color.WHITE);
		buttonComprar.setBounds(10, 74, 120, 49);
		buttonComprar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelVentaProducto(s);
			}
		});
		add(buttonComprar);
		
		JButton buttonDevolverProducto = new JButton("Devolver Producto");
		buttonDevolverProducto.setBackground(Color.WHITE);
		buttonDevolverProducto.setBounds(140, 74, 286, 49);
		buttonDevolverProducto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelDevolverProducto(s, n);
			}
		});
		add(buttonDevolverProducto);
		
		if(n == 1){
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(null);
		panel.setBounds(10, 136, 536, 226);
		add(panel);
		
		JLabel label_1 = new JLabel("SOCIO");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 50));
		label_1.setBounds(0, 11, 520, 61);
		panel.add(label_1);
		
		textFieldNombreSocio = new JTextField();
		textFieldNombreSocio.setFont(new Font("Tahoma", Font.BOLD, 14));
		textFieldNombreSocio.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNombreSocio.setEditable(false);
		textFieldNombreSocio.setText(s.getNombre());
		textFieldNombreSocio.setColumns(10);
		textFieldNombreSocio.setBounds(366, 21, 144, 42);
		panel.add(textFieldNombreSocio);
		
		JButton buttonDatosSocio = new JButton("Datos Socio");
		buttonDatosSocio.setBackground(Color.WHITE);
		buttonDatosSocio.setBounds(389, 83, 137, 42);
		buttonDatosSocio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelDatosSocio(s);
			}
		});
		panel.add(buttonDatosSocio);
		
		JButton buttonCompraSocio = new JButton("Vender");
		buttonCompraSocio.setBackground(Color.WHITE);
		buttonCompraSocio.setBounds(162, 83, 197, 42);
		buttonCompraSocio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelCompraSocio(s);
			}
		});
		panel.add(buttonCompraSocio);
		
		JButton buttonAlquilarProducto = new JButton("Alquilar Producto");
		buttonAlquilarProducto.setBackground(Color.WHITE);
		buttonAlquilarProducto.setBounds(98, 170, 137, 42);
		buttonAlquilarProducto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelAlquilarProducto(s);
			}
		});
		panel.add(buttonAlquilarProducto);
		
		JButton buttonEstadoAlqui = new JButton("Consultar Estado Alquiler");
		buttonEstadoAlqui.setBackground(Color.WHITE);
		buttonEstadoAlqui.setBounds(274, 170, 197, 42);
		buttonEstadoAlqui.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelConsultarAlquiler(s);
			}
		});
		panel.add(buttonEstadoAlqui);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(Color.WHITE);
		btnModificar.setBounds(10, 83, 137, 42);
		btnModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelModificarSocio(s);
			}
		});
		panel.add(btnModificar);
		
		}
		JButton btnAtras = new JButton("Atras");
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(436, 74, 110, 49);
		add(btnAtras);
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(n==1){
						control.cerrarSesionSocio();
					}
				} catch (SocioInvalido e1) {
					
					e1.printStackTrace();
				}
				p.onCambiarAPanelVendedor();
				
			}
		});
		

	}
}
