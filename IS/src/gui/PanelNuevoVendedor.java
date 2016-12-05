package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import objetosTransfer.TipoUsuario;
import objetosTransfer.Usuario;
import controladores.ControladorUsuario;
import excepciones.FormatoInvalido;
import excepciones.UsuarioInvalido;

public class PanelNuevoVendedor extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JPasswordField passwordField;
	private ControladorUsuario c;
	private Principal p;

	/**
	 * @param prin - Panel Principal.
	 * @param contr - Controlador Usuario.
	 */
	public PanelNuevoVendedor(Principal prin, ControladorUsuario contr) {
setLayout(null);
		
		JLabel lblNombreDeUsuario = new JLabel("Nombre de Usuario:");
		this.c = contr;
		this.p = prin;
		lblNombreDeUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNombreDeUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblNombreDeUsuario.setBounds(51, 164, 176, 14);
		add(lblNombreDeUsuario);
		
		JLabel label_2 = new JLabel("Contrase\u00F1a:");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setBounds(51, 235, 120, 14);
		add(label_2);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(242, 162, 204, 20);
		add(textFieldNombre);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(244, 229, 204, 20);
		add(passwordField);
		
		JButton buttonAtras = new JButton("Atr\u00E1s");
		buttonAtras.setBackground(Color.WHITE);
		buttonAtras.setBounds(90, 307, 150, 50);
		buttonAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelAdmin();
			}
		});
		add(buttonAtras);
		
		JButton buttonAnyadirVend = new JButton("A\u00F1adir Vendedor");
		buttonAnyadirVend.setBackground(Color.WHITE);
		buttonAnyadirVend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					String pass = "";
					for(int i=0; i< passwordField.getPassword().length; i++){
						pass+= passwordField.getPassword()[i];
					}
						Usuario usu = new Usuario(textFieldNombre.getText(), pass, TipoUsuario.VENDEDOR );
						c.registrarUsuario(usu);
						JOptionPane.showMessageDialog(
								null,
								"Se ha registrado correctamnente el nuevo vendendor",
								null,
								JOptionPane.INFORMATION_MESSAGE);
						p.onCambiarAPanelAdmin();
				}catch(FormatoInvalido f){
					JOptionPane.showMessageDialog(
							null,
							f.getMessage(),
							null,
							JOptionPane.INFORMATION_MESSAGE
					);
				}				
				catch(UsuarioInvalido u) {
					JOptionPane.showMessageDialog(
							null,
							u.getMessage(),
							null,
							JOptionPane.INFORMATION_MESSAGE
					);
				}
			}
		});
		buttonAnyadirVend.setBounds(302, 305, 150, 50);
		add(buttonAnyadirVend);
		
		JLabel label = new JLabel("Identificado como: ");
		label.setBounds(51, 75, 131, 14);
		add(label);
		
		JLabel label_1 = new JLabel("Administrador");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_1.setBounds(203, 72, 160, 14);
		add(label_1);

	}

}
