package usuario;

import java.util.ArrayList;

import objetosTransfer.TipoUsuario;
import objetosTransfer.Usuario;
import excepciones.FormatoInvalido;
import excepciones.UsuarioInvalido;

public class SAUsuario implements ISAUsuario {
	private IDAOUsuario dao;
	final int MAX_USUARIOS = 5;
	//Atributo que indica qu� usuario tiene la sesi�n iniciada
	
	
	public SAUsuario(IDAOUsuario d){
		this.dao = d;
	}
	@Override
	public Usuario iniciarSesion(Usuario usuario) throws FormatoInvalido, UsuarioInvalido {
		if(usuario.getNombre().equals("")){
			throw new FormatoInvalido("Introduzca un nombre");
		}
		if(usuario.getContrasena().equals("")){
			throw new FormatoInvalido("Introduzca una contrase�a");
		}
		
		Usuario iniciada = this.dao.buscaUsuario(usuario);
		
		if(iniciada == null)
			throw new UsuarioInvalido("Nombre de usuario o contrase�a incorrectos");
		else 
			this.dao.iniciarSesion(usuario);
		
		
		 return iniciada;
	}

	
	
	
	public void cerrarSesion() {
		
		this.dao.cerrarSesion();
		
		
	}
	
	
	
	@Override
	public void registrarUsuario(Usuario usuario) throws FormatoInvalido, UsuarioInvalido {
		//Lo primero comprobamos si los campos introducidos son correctos
		if(usuario.getNombre().equals("")){
			throw new FormatoInvalido("Introduzca un nombre");
		}
		if(usuario.getContrasena().equals("")){
			throw new FormatoInvalido("Introduzca una contrase�a");
		}
		
		//Lo segundo comprobamos si el nombre del usuario ya existe en la base de datos
		if(this.dao.userEnBD(usuario.getNombre())) {
			throw new UsuarioInvalido("Nombre de usuario ya existente");
		}
		
		
		//Lo tercero comprobamos que no se ha alcanzado el m�ximo de usuarios de ese
		//tipo registrados, y si es as� lo a�adimos a la base de datos
		if(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)
			registrarAdmin(usuario);
		else
			registrarVendedor(usuario);
		
			
		
	}
	
	
	private void registrarAdmin(Usuario usuario) throws UsuarioInvalido {
		if(this.dao.cuantosAdmin() == MAX_USUARIOS)
			throw new UsuarioInvalido("Error.N�mero m�ximo de administradores alcanzado");
		else
			this.dao.crearUsuario(usuario);
	}
	
	
	private void registrarVendedor(Usuario usuario) throws UsuarioInvalido {
		if(this.dao.cuantosVende() == MAX_USUARIOS)
			throw new UsuarioInvalido("Error.N�mero m�ximo de vendedores alcanzado");
		else
			this.dao.crearUsuario(usuario);
	}
	
	
	public void eliminarUsuario(Usuario usuario) throws UsuarioInvalido {
		//Comprobar que el usuario que se pretende eliminar y el que tiene 
		//la sesi�n iniciada no es el mismo
		
		if(this.dao.sesionIniciada(usuario))
			throw new UsuarioInvalido("Error.El administrador con sesi�n iniciada no puede eliminarse a s� mismo.");
		
		this.dao.eliminarUsuario(usuario.getNombre());
		
		
	}
	
	
	
	public ArrayList <Usuario> getListaUsuarios(Usuario usuario) {
		ArrayList <Usuario> listaUsers = this.dao.listaUsuarios();
		
		ArrayList <Usuario> listaFiltrada = new ArrayList <Usuario>();
		
		
		
		//Si hay que filtrar por el nombre solo podrá haber un usuario con ese nombre
		if(!usuario.getNombre().equals("")) {
			Usuario user = filtraPorNombre(listaUsers, usuario.getNombre());
			if(user != null)
				listaFiltrada.add(user);
			//Si hay que filtrar por TipoUsuario
			
			if(!listaFiltrada.isEmpty() && usuario.getTipoUsuario() != TipoUsuario.CUALQUIERA &&
					usuario.getTipoUsuario() != listaFiltrada.get(0).getTipoUsuario())
				listaFiltrada.remove(0);
	
		}
		//Si solo hay que filtrar por TipoUsuario
		else if(usuario.getTipoUsuario() == TipoUsuario.CUALQUIERA)
			listaFiltrada = listaUsers;
		else
			listaFiltrada = filtraPorTipo(listaUsers, usuario);
				
		
		
		return listaFiltrada;
}
	
	
	
	private ArrayList <Usuario> filtraPorTipo(ArrayList <Usuario> listaUsers, Usuario user) {
		ArrayList <Usuario> listaFiltrada = new ArrayList <Usuario>();
		
		for(int i = 0; i < listaUsers.size(); i++) {
			if(listaUsers.get(i).getTipoUsuario() == user.getTipoUsuario())
				listaFiltrada.add(listaUsers.get(i));
		}
		
		return listaFiltrada;
	}
	
	
private Usuario filtraPorNombre(ArrayList <Usuario> listaUsers, String nombre) {
		boolean encontrado = false;
		int i = 0;
		Usuario user = null;
		
		while(!encontrado && i < listaUsers.size()) {
			if(listaUsers.get(i).getNombre().equals(nombre)) {
				encontrado = true;
				user =listaUsers.get(i);
			}
			i++;
			
		}
		
		return user;
	}

}
