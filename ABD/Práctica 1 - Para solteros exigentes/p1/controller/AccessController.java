package abd.p1.controller;

import javax.swing.JOptionPane;

import abd.p1.bd.DBUsuario;
import abd.p1.model.Usuario;
import abd.p1.view.AccessDialog;
import abd.p1.view.EditarPerfilDialog;

public class AccessController {

	private DBUsuario DB;
	private Usuario usuario;
	
	// Constructor sin parámetros
	public AccessController() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Constructor con parámetros
	public AccessController(DBUsuario dB, Usuario usuario) {
		super();
		this.DB = dB;
		this.usuario = usuario;
	}
	
	// Getters and setters
	public Usuario getUsuario() {
		return usuario;
	}

	// Se llama al pulsar el botón 'Aceptar' y se comprueba el correo y la contraseña
	public void comprobarAceptar(AccessDialog parent, String correo, String password) {
		Usuario u = DB.obtenerUsuarioLogin(correo, password);
		
		if (u != null) {
			/*ListUserController controller2 = new ListUserController(DB, usuario);
			PrincipalFrame principalFrame = new PrincipalFrame(controller2);*/
			usuario = u;
			parent.setVisible(false);
			/*principalFrame.setVisible(true);*/
		} else {
			JOptionPane.showMessageDialog(null, "Por favor, comprueba que has introducido los datos correctamente.", "Login incorrecto", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// Se llama al pulsar el botón 'Nuevo usuario' y se comprueba el correo
	public void comprobarNuevoUsuario(String correo, String password) {
		if (correo.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Asegúrate de rellenar los campos antes de continuar.", "Error", JOptionPane.ERROR_MESSAGE);
		} else if (this.DB.obtenerCorreoLogin(correo) == null) {
			// Asignamos al usuario el correo y de la contraseña
			usuario.setCorreo(correo);
			usuario.setPassword(password);
			// Creamos la ventana de "EditarPerfil"
			EditarPerfilController controller2 = new EditarPerfilController(DB, this.usuario);
			EditarPerfilDialog ep = new EditarPerfilDialog(controller2);
			ep.setEditable(true);
			// Lanzamos la nueva ventana de "EditarPerfil"
			ep.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Por favor, inicie sesión o regístrese con un correo diferente.", "El correo ya está registrado", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
