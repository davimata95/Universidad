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

import objetosTransfer.TipoUsuario;
import objetosTransfer.Usuario;
import controladores.ControladorUsuario;
import excepciones.FormatoInvalido;
import excepciones.UsuarioInvalido;

public class PanelNuevoAdmin extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombreUsu;
	private JPasswordField passwordField;
	private ControladorUsuario c;
	private Principal p;

	/**
	 * @param prin - Panel Principal.
	 * @param contr - Controlador Usuario.
	 */
	public PanelNuevoAdmin(Principal prin, ControladorUsuario contr) {
setLayout(null);
		
		JLabel lblNombreDeUsuario = new JLabel("Nombre de Usuario:");
		this.c= contr;
		this.p = prin;
		lblNombreDeUsuario.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNombreDeUsuario.setBounds(88, 110, 182, 14);
		add(lblNombreDeUsuario);
		
		JLabel label_2 = new JLabel("Contrase\u00F1a:");
		label_2.setFont(new Font("Dialog", Font.BOLD, 16));
		label_2.setBounds(90, 198, 157, 14);
		add(label_2);
		
		textFieldNombreUsu = new JTextField();
		textFieldNombreUsu.setColumns(10);
		textFieldNombreUsu.setBounds(262, 109, 201, 20);
		add(textFieldNombreUsu);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(265, 194, 204, 20);
		add(passwordField);
		
		JButton buttonAtras = new JButton("Atr\u00E1s");
		buttonAtras.setBackground(Color.WHITE);
		buttonAtras.setBounds(86, 301, 148, 51);
		buttonAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelAdmin();
			}
		});
		add(buttonAtras);
		
		JButton buttonAnyadirAdmin = new JButton("A\u00F1adir Admin");
		buttonAnyadirAdmin.setBackground(Color.WHITE);
		buttonAnyadirAdmin.setBounds(314, 299, 148, 51);
		buttonAnyadirAdmin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					String pass = "";
					for(int i=0; i< passwordField.getPassword().length; i++){
						pass+= passwordField.getPassword()[i];
					}
						Usuario usu = new Usuario(textFieldNombreUsu.getText(), pass, TipoUsuario.ADMINISTRADOR );
						c.registrarUsuario(usu);
						JOptionPane.showMessageDialog(
								null,
								"Se ha registrado correctamnente el nuevo administrador",
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
		add(buttonAnyadirAdmin);
		
		JLabel label = new JLabel("Identificado como: ");
		label.setBounds(88, 39, 131, 14);
		add(label);
		
		JLabel label_1 = new JLabel("Administrador");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_1.setBounds(240, 36, 160, 14);
		add(label_1);

	}
}
