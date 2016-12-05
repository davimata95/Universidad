package usuario;

import java.util.ArrayList;

import objetosTransfer.Usuario;
import excepciones.FormatoInvalido;
import excepciones.UsuarioInvalido;

public class FachadaUsuario implements IFachadaUsuario {
	private ISAUsuario sa;
	
	public FachadaUsuario(ISAUsuario isa){
		this.sa = isa;
	}

	
	public Usuario iniciarSesion(Usuario usuario) throws FormatoInvalido, UsuarioInvalido{
		return sa.iniciarSesion(usuario);
		
	}

	
	public void cerrarSesion() {
		sa.cerrarSesion();
		
	}


	
	public void registrarUsuario(Usuario usuario) throws FormatoInvalido, UsuarioInvalido {
		sa.registrarUsuario(usuario);
		
	}


	
	
	public void eliminarUsuario(Usuario usuario) throws UsuarioInvalido {
		sa.eliminarUsuario(usuario);
		
	}


	public ArrayList <Usuario> getListaUsuarios(Usuario usuario) {
		
		return this.sa.getListaUsuarios(usuario);
	}
	

}
