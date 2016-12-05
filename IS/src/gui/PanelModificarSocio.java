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
import excepciones.RadioButtonsNoPinchados;
import excepciones.SocioInvalido;

public class PanelModificarSocio extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JTextField textFieldDNI;
	private JTextField textFieldTelefono;
	private JRadioButton rdbtnRol;
	private JRadioButton rdbtnShooters;
	private JRadioButton rdbtnArcade;
	private JRadioButton rdbtnDeportes;
	private JRadioButton rdbtnEstrategia;
	private JRadioButton rdbtnAventura;
	private JRadioButton rdbtnPlataformas;
	private JRadioButton rdbtnCarreras;
	private JRadioButton rdbtnOtros;
	JRadioButton rdbtnCualquiera;
	private ControladorSocio c;
	private Socio s;
	private Principal p;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * @param prin - Panel Principal.
	 * @param socio - Un socio.
	 * @param contr - Controlador Socio.
	 */
	public PanelModificarSocio(Principal prin,Socio socio, ControladorSocio contr) {
setLayout(null);
		
		this.c = contr;
		this.s = socio;
		this.p = prin;
		JLabel label = new JLabel("Nombre:");
		label.setBounds(10, 39, 92, 14);
		add(label);
		
		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setBounds(10, 63, 85, 14);
		add(lblApellidos);
		
		JLabel label_2 = new JLabel("Tel\u00E9fono:");
		label_2.setBounds(10, 88, 84, 14);
		add(label_2);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(104, 35, 161, 20);
		textFieldNombre.setText(s.getNombre());
		add(textFieldNombre);
		
		textFieldApellidos = new JTextField();
		textFieldApellidos.setColumns(10);
		textFieldApellidos.setBounds(104, 61, 362, 20);
		textFieldApellidos.setText(s.getApellidos());
		add(textFieldApellidos);
		
		textFieldDNI = new JTextField();
		textFieldDNI.setColumns(10);
		textFieldDNI.setBounds(305, 34, 161, 20);
		textFieldDNI.setText(s.getDNI());
		add(textFieldDNI);
		
		JLabel label_3 = new JLabel("DNI:");
		label_3.setBounds(276, 37, 46, 14);
		add(label_3);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setColumns(10);
		textFieldTelefono.setBounds(104, 86, 148, 20);
		textFieldTelefono.setText(s.getTelefono());
		add(textFieldTelefono);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Juegos de Inter\u00E9s", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 133, 536, 152);
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
		
		pincharRadioButtonsCategoria(s.getInteres());
		
		JButton buttonAtras = new JButton("Atr\u00E1s");
		buttonAtras.setBackground(Color.WHITE);
		buttonAtras.setBounds(104, 311, 104, 36);
		buttonAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelCliente(s,1);
			}
		});
		add(buttonAtras);
		
		JButton buttonModificar = new JButton("Modificar");
		buttonModificar.setBackground(Color.WHITE);
		buttonModificar.setBounds(353, 311, 113, 36);
		buttonModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					Socio so = new Socio(textFieldNombre.getText(), textFieldApellidos.getText(), textFieldDNI.getText(), textFieldTelefono.getText(), revisarRadioButtonsCategoria());
					
					c.modificarSocio(so);
					JOptionPane.showMessageDialog(
							null,
							"Socio Modificado",
							null,
							JOptionPane.INFORMATION_MESSAGE
					);
					//Socio soa = c.getSocio();
					p.onCambiarAPanelCliente(c.getSocio(),1);
					} catch (RadioButtonsNoPinchados r) {
					JOptionPane.showMessageDialog(
							null,
							r.getMessage(),
							null,
							JOptionPane.INFORMATION_MESSAGE
					);
				} catch (SocioInvalido e1) {
					JOptionPane.showMessageDialog(
							null,
							e1.getMessage(),
							null,
							JOptionPane.INFORMATION_MESSAGE
					);
					}
			}
		});
		add(buttonModificar);

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
	
	public void pincharRadioButtonsCategoria(CategoriaJuego cat){
		if(cat != null){
			switch(cat){
			case  ROL : rdbtnRol.setSelected(true);break;
			case  ARCADE : rdbtnArcade.setSelected(true);break;
			case  AVENTURA : rdbtnAventura.setSelected(true);break;
			case  CARRERAS : rdbtnCarreras.setSelected(true);break;
			case  DEPORTES : rdbtnDeportes.setSelected(true);break;
			case  ESTRATEGIA : rdbtnEstrategia.setSelected(true);break;
			case  OTROS : rdbtnOtros.setSelected(true);break;
			case  PLATAFORMA : rdbtnPlataformas.setSelected(true);break;
			case  SHOOTERS : rdbtnShooters.setSelected(true);break;
			case  CUALQUIERA : rdbtnCualquiera.setSelected(true);break;
			default: break;
			}
		}
	}
}
