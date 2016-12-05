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

public class PanelLogin extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldUsuario;
	private JPasswordField passwordField;
	private ControladorUsuario c;
	private Principal p;

	/**
	 * @param contr - Controlador Usuario.
	 * @param princ - Panel Principal.
	 */
	public PanelLogin(ControladorUsuario contr, Principal princ) {
		setLayout(null);
		
		JLabel label = new JLabel("GAME");
		this.c = contr;
		this.p = princ;
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.MAGENTA);
		label.setFont(new Font("", Font.BOLD, 69));
		label.setBounds(10, 42, 536, 70);
		add(label);
		
		JLabel label_1 = new JLabel("Nombre:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_1.setBounds(136, 168, 120, 14);
		add(label_1);
		
		JLabel label_2 = new JLabel("Contrasena:");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_2.setBounds(136, 218, 120, 14);
		add(label_2);
		
		
		final JButton buttonInicio = new JButton("Iniciar Sesion");
		buttonInicio.setForeground(Color.BLACK);
		buttonInicio.setBackground(Color.GREEN);
		buttonInicio.setBounds(136, 282, 313, 42);
		buttonInicio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					String pass = "";
					for(int i=0; i< passwordField.getPassword().length; i++){
						pass+= passwordField.getPassword()[i];
					}
					Usuario usu = new Usuario(textFieldUsuario.getText(), pass, null);
					
					TipoUsuario t = c.iniciarSesion(usu).getTipoUsuario();
					if(t == TipoUsuario.ADMINISTRADOR){
						p.onCambiarAPanelAdmin();
					}else{
						p.onCambiarAPanelVendedor();
					}
					mensajeInicioSesion("Se ha Iniciado Sesión correctamente");
				}catch(FormatoInvalido f){
					JOptionPane.showMessageDialog(
							null,
							f.getMessage(),
							null,
							JOptionPane.INFORMATION_MESSAGE
					);
				} catch (UsuarioInvalido e1) {
					JOptionPane.showMessageDialog(
							null,
							e1.getMessage(),
							null,
							JOptionPane.INFORMATION_MESSAGE
					);
				}
				
			}
		});
		add(buttonInicio);

		textFieldUsuario = new JTextField();
		textFieldUsuario.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				buttonInicio.doClick();
			}
		});
		textFieldUsuario.setColumns(10);
		textFieldUsuario.setBounds(266, 167, 183, 20);
		add(textFieldUsuario);
		
		JButton buttonSalir = new JButton("Salir");
		buttonSalir.setBackground(Color.RED);
		buttonSalir.setBounds(135, 367, 313, 42);
		buttonSalir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(null,
						"¿Desea Salir?",
						"Salir",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE
						);
				if(i == JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});
		add(buttonSalir);
		
		passwordField = new JPasswordField();
		passwordField.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				buttonInicio.doClick();
			}
		});
		passwordField.setBounds(266, 217, 183, 20);
		add(passwordField);

	}
	
	public void mensajeInicioSesion(String texto){
		JOptionPane.showMessageDialog(
				null,
				texto,
				null,
				JOptionPane.INFORMATION_MESSAGE
		);
	}

}
