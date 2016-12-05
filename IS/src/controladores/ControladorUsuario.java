package controladores;

import java.util.ArrayList;

import excepciones.FormatoInvalido;
import excepciones.UsuarioInvalido;
import objetosTransfer.Usuario;
import usuario.IFachadaUsuario;


public class ControladorUsuario {
	
	//private Usuario usuario;
	private IFachadaUsuario iFachada;
	
	
	/**
	 * Constructor de la clase
	 * @param iFa - interfaz de fachada usuario
	 */
	public ControladorUsuario( IFachadaUsuario iFa) {
		iFachada = iFa;
	}

	/**
	 * Recibe un usuario con los campos por los que filtrar y devuelve una lista
		de usuarios filtrada
	 * @param user - Objeto usuario
	 * @return devuelve una lista de usuarios
	 */
	public ArrayList <Usuario> getListaUsuarios(Usuario user) {
		
		return this.iFachada.getListaUsuarios(user);
	}
	
	
	/**
	 * Inicia sesión con el usuario seleccionado
	 * @param user - objeto usuario
	 * @return el usuario conectado
	 * @throws FormatoInvalido si no rellena algún campo
	 * @throws UsuarioInvalido si el usuario o la contraseña son incorrectas
	 */
	public Usuario iniciarSesion(Usuario user) throws FormatoInvalido, UsuarioInvalido {
		return this.iFachada.iniciarSesion(user);
			
	}
	
	/**
	 * Se cierra la sesión del socio conectado
	 */
	public void cerrarSesion() {
		
			this.iFachada.cerrarSesion();
			//Se muestra mensaje de sesi�n cerrada con �xito y se informa a la vista
			//que se debe volver a la interfaz de inicio de sesi�n.
	}
	
	/**
	 * Registra un nuevo usuario
	 * @param usuario objeto usuario
	 * @throws FormatoInvalido  si no rellena algún campo
	 * @throws UsuarioInvalido si ya existe un usuario con el nombre introducido
	 */
	public void registrarUsuario(Usuario usuario) throws FormatoInvalido, UsuarioInvalido {

			this.iFachada.registrarUsuario(usuario);
	}	
	
	
	/**
	 * Elimina a un usuario
	 * @param usuario - Objeto usuario
	 * @throws UsuarioInvalido si el administrador se intenta eliminar a si mismo
	 */
	public void eliminarUsuario(Usuario usuario) throws UsuarioInvalido {
			this.iFachada.eliminarUsuario(usuario);
	
	}
	
	
	
	
}
