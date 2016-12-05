package usuario;

import java.util.ArrayList;

import objetosTransfer.Usuario;
import excepciones.FormatoInvalido;
import excepciones.UsuarioInvalido;

public interface ISAUsuario {

	/**
	 * Inicia sesión
	 * @param usuario - usuario con el que se quiere iniciar sesión
	 * @return Usuario con el que se inicia sesión
	 * @throws FormatoInvalido Se lanza si no se rellena algún campo
	 * @throws UsuarioInvalido Se lanza si el usuario o la contraseña no coinciden
	 */
	Usuario iniciarSesion(Usuario usuario) throws FormatoInvalido, UsuarioInvalido;

	/**
	 * Cierra sesión del usuario conectado
	 */
	void cerrarSesion();
	
	
	
	/**
	 * Registra un nuevo usuario
	 * @param usuario - Usuario que se quiere registrar
	 * @throws FormatoInvalido Se lanza si no se rellena algún campo
	 * @throws UsuarioInvalido Se lanza si el nombre del usuario ya existe
	 */
	void registrarUsuario(Usuario usuario) throws FormatoInvalido, UsuarioInvalido;
		
		
	/**
	 * @param usuario - usuario que se quiere eliminar
	 * @throws UsuarioInvalido Se lanza si el administrador con sesión iniciada intenta eliminarse a si mismo
	 */
	void eliminarUsuario(Usuario usuario)throws UsuarioInvalido;
	
	/**
	 * @param usuario - Objeto Usuario
	 * @return Devuelve los usuarios que cumplen las características de los campos rellenados
	 */
	ArrayList <Usuario> getListaUsuarios(Usuario usuario);
		
}
