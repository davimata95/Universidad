package usuario;

import java.util.ArrayList;

import objetosTransfer.Usuario;
import excepciones.UsuarioInvalido;

public interface IDAOUsuario {

	
	//
	/**
	 * @return Una lista con todos los usuarios de la BD
	 */
	ArrayList <Usuario> listaUsuarios();
	/**
	 * Inicia sesión con el usuario seleccionado
	 * @param usuario - usuario con el que se quiere iniciar sesión
	 * @throws UsuarioInvalido si no se puede iniciar sesión
	 */
	void iniciarSesion(Usuario usuario) throws UsuarioInvalido;
	/**
	 * Modifica los datos del usuario seleccionado en la BD
	 * @param usu - Objeto usuario con los atributos nuevos
	 * @param usuAntiguo -Objeto usuario con los atributos viejos
	 */
	void modUsuario(Usuario usu ,Usuario usuAntiguo);
	/**
	 * Crea un nuevo usuario
	 * @param usu - Usuario con el que se quiere crear un nuevo usuario
	 */
	void crearUsuario(Usuario usu);
	/**
	 * Elimina el usuario seleccionado
	 * @param Nombre - Nombre del usuario que se quiere iluminar
	 */
	void eliminarUsuario(String Nombre);
	/**
	 * @param user - Objeto Usuario
	 * @return Los usuarios que cumplen las características de los campos rellenados
	 */
	Usuario buscaUsuario(Usuario user);
	/**
	 * Cierra la sesión del usuario conectado 
	 */
	void cerrarSesion();
	/**
	 * @param usuario - Objeto usuario
	 * @return true si el usuario tiene una sesión iniciada y false si es lo contrario
	 */
	boolean sesionIniciada(Usuario usuario);
	/**
	 * @return El nº de vendedores que hay
	 */
	int cuantosVende();
	/**
	 * @return El nº de administradores que hay
	 */
	int cuantosAdmin();
	/**
	 * @param nombre - nombre del usuario que se busca
	 * @return true si encuentra el usuario con ese nombre y false si pasa lo contrario
	 */
	boolean userEnBD(String nombre);
}
