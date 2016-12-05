package usuario;

import java.util.ArrayList;

import objetosTransfer.Usuario;
import excepciones.FormatoInvalido;
import excepciones.UsuarioInvalido;

public interface IFachadaUsuario {
	Usuario iniciarSesion(Usuario usuario) throws FormatoInvalido, UsuarioInvalido;
	void cerrarSesion();
	void registrarUsuario(Usuario usuario) throws FormatoInvalido, UsuarioInvalido;
	void eliminarUsuario(Usuario usuario) throws UsuarioInvalido;
	ArrayList <Usuario> getListaUsuarios(Usuario usuario);
	
}
