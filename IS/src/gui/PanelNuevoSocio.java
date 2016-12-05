package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import objetosTransfer.CategoriaJuego;
import objetosTransfer.Socio;
import controladores.ControladorSocio;
import excepciones.FormatoInvalido;
import excepciones.RadioButtonsNoPinchados;
import excepciones.SocioInvalido;

public class PanelNuevoSocio extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldDNI;
	private JTextField textFieldApellidos;
	private JTextField textFieldNombre;
	private JTextField textFieldTelefono;
	private ControladorSocio c;
	private JRadioButton rdbtnRol;
	private JRadioButton rdbtnShooters;
	private JRadioButton rdbtnArcade;
	private JRadioButton rdbtnDeportes;
	private JRadioButton rdbtnEstrategia;
	private JRadioButton rdbtnAventura;
	private JRadioButton rdbtnPlataformas;
	private JRadioButton rdbtnCarreras;
	private JRadioButton rdbtnOtros;
	private Principal p;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnCualquiera;

	/**
	 * @param prin - Panel Principal.
	 * @param contr - Controlador Socio.
	 */
	public PanelNuevoSocio(Principal prin, ControladorSocio contr) {
setLayout(null);
		
		this.c = contr;
		this.p = prin;
		
		
		JLabel label = new JLabel("Nombre:");
		label.setBounds(10, 11, 93, 14);
		add(label);
		
		JLabel label_1 = new JLabel("Apellidos");
		label_1.setBounds(10, 48, 93, 14);
		add(label_1);
		
		JLabel label_2 = new JLabel("Tel\u00E9fono:");
		label_2.setBounds(10, 85, 93, 14);
		add(label_2);
		
		textFieldDNI = new JTextField();
		textFieldDNI.setColumns(10);
		textFieldDNI.setBounds(339, 11, 136, 20);
		add(textFieldDNI);
		
		textFieldApellidos = new JTextField();
		textFieldApellidos.setColumns(10);
		textFieldApellidos.setBounds(113, 47, 362, 20);
		add(textFieldApellidos);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(114, 11, 161, 20);
		add(textFieldNombre);
		
		JLabel label_3 = new JLabel("DNI:");
		label_3.setBounds(293, 14, 46, 14);
		add(label_3);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setColumns(10);
		textFieldTelefono.setBounds(113, 81, 148, 20);
		add(textFieldTelefono);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Juegos de Inter\u00E9s", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 140, 536, 198);
		add(panel);
		
		rdbtnRol = new JRadioButton("Rol");
		buttonGroup.add(rdbtnRol);
		rdbtnRol.setBounds(6, 33, 109, 23);
		panel.add(rdbtnRol);
		
		rdbtnShooters = new JRadioButton("Shooters");
		buttonGroup.add(rdbtnShooters);
		rdbtnShooters.setBounds(6, 72, 109, 23);
		panel.add(rdbtnShooters);
		
		rdbtnArcade = new JRadioButton("Arcade");
		buttonGroup.add(rdbtnArcade);
		rdbtnArcade.setBounds(6, 115, 109, 23);
		panel.add(rdbtnArcade);
		
		rdbtnDeportes = new JRadioButton("Deportes");
		buttonGroup.add(rdbtnDeportes);
		rdbtnDeportes.setBounds(150, 33, 109, 23);
		panel.add(rdbtnDeportes);
		
		rdbtnEstrategia = new JRadioButton("Estrategia");
		buttonGroup.add(rdbtnEstrategia);
		rdbtnEstrategia.setBounds(150, 72, 109, 23);
		panel.add(rdbtnEstrategia);
		
		rdbtnAventura = new JRadioButton("Aventura");
		buttonGroup.add(rdbtnAventura);
		rdbtnAventura.setBounds(150, 115, 109, 23);
		panel.add(rdbtnAventura);
		
		rdbtnPlataformas = new JRadioButton("Plataformas");
		buttonGroup.add(rdbtnPlataformas);
		rdbtnPlataformas.setBounds(302, 33, 109, 23);
		panel.add(rdbtnPlataformas);
		
		rdbtnCarreras = new JRadioButton("Carreras");
		buttonGroup.add(rdbtnCarreras);
		rdbtnCarreras.setBounds(302, 72, 109, 23);
		panel.add(rdbtnCarreras);
		
		rdbtnOtros = new JRadioButton("Otros");
		buttonGroup.add(rdbtnOtros);
		rdbtnOtros.setBounds(299, 115, 109, 23);
		panel.add(rdbtnOtros);
		
		rdbtnCualquiera = new JRadioButton("Cualquiera");
		buttonGroup.add(rdbtnCualquiera);
		rdbtnCualquiera.setBounds(421, 72, 109, 23);
		panel.add(rdbtnCualquiera);
		
		JButton buttonAtras = new JButton("Atr\u00E1s");
		buttonAtras.setBackground(Color.WHITE);
		buttonAtras.setBounds(10, 353, 155, 56);
		buttonAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelVendedor();
			}
		});
		add(buttonAtras);
		
		JButton buttonAnyadirSocio = new JButton("A\u00F1adir Socio");
		buttonAnyadirSocio.setBackground(Color.WHITE);
		buttonAnyadirSocio.setBounds(391, 349, 155, 56);
		buttonAnyadirSocio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
					Socio s = new Socio(textFieldNombre.getText(), textFieldApellidos.getText(), textFieldDNI.getText(), textFieldTelefono.getText(), revisarRadioButtonsCategoria());
					c.registrarSocio(s);
					JOptionPane.showMessageDialog(
							null,
							"Socio Registrado",
							null,
							JOptionPane.INFORMATION_MESSAGE
					);
					p.onCambiarAPanelVendedor();
				}catch(RadioButtonsNoPinchados r){
					JOptionPane.showMessageDialog(
							null,
							r.getMessage(),
							null,
							JOptionPane.INFORMATION_MESSAGE
					);
				}
				catch(FormatoInvalido f){
					JOptionPane.showMessageDialog(
							null,
							f.getMessage(),
							null,
							JOptionPane.INFORMATION_MESSAGE
					);
				}
				catch(SocioInvalido s) {
					JOptionPane.showMessageDialog(
							null,
							s.getMessage(),
							null,
							JOptionPane.INFORMATION_MESSAGE
					);
				}
			}
		});
		add(buttonAnyadirSocio);

	}
	
	public CategoriaJuego revisarRadioButtonsCategoria() throws RadioButtonsNoPinchados{
		if(rdbtnRol.isSelected())return CategoriaJuego.ROL;
		if(rdbtnShooters.isSelected())return CategoriaJuego.SHOOTERS;
		if(rdbtnArcade.isSelected())return CategoriaJuego.ARCADE;
		if(rdbtnDeportes.isSelected())return CategoriaJuego.DEPORTES;
		if(rdbtnEstrategia.isSelected())return CategoriaJuego.ESTRATEGIA;
		if(rdbtnAventura.isSelected())return CategoriaJuego.AVENTURA;
		if(rdbtnPlataformas.isSelected())return CategoriaJuego.PLATAFORMA;
		if(rdbtnCarreras.isSelected())return CategoriaJuego.CARRERAS;
		if(rdbtnOtros.isSelected())return CategoriaJuego.OTROS;
		if(rdbtnCualquiera.isSelected())return CategoriaJuego.CUALQUIERA;
		else{
			throw new RadioButtonsNoPinchados("Seleccione alguna Categor�a");
		}
	}
}
