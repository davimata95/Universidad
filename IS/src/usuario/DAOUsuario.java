package usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import objetosTransfer.TipoUsuario;
import objetosTransfer.Usuario;
import excepciones.FalloConexionBD;
import excepciones.UsuarioInvalido;

public class DAOUsuario implements IDAOUsuario {
	private Connection conexion;

	public DAOUsuario() throws FalloConexionBD{
		
	try{
		
	
		Class.forName("com.mysql.jdbc.Driver");
	   conexion = DriverManager.getConnection("jdbc:mysql://localhost/gamesql", "gameadmin", "admin"); 
	}catch(Exception e){
		throw new FalloConexionBD();
		
	}
	
	}
	
	public void crearUsuario(Usuario usu){//
		try{
			int admin=0;
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM usuarios WHERE Nombre= '"+ usu.getNombre()+"'");
			
			if(!rs.next())
			{
				if(usu.getTipoUsuario()==TipoUsuario.VENDEDOR){
				admin=0;
				}
				if(usu.getTipoUsuario()==TipoUsuario.ADMINISTRADOR){
					admin=1;
				}
				
				st.executeUpdate("INSERT INTO usuarios (Nombre, Contrasena, Admin) VALUES('"+usu.getNombre()+"','"+usu.getContrasena()+"','"+admin+"')");
			rs.close();
			st.close();
			}
			}catch(Exception e){
			
			System.out.println("Fallo insertar");
			System.out.println(e.getMessage());
		}
	}
	
	
	public void modUsuario(Usuario usu ,Usuario usuantiguo){//
		
		try{
			int admin=0;
			Statement st = conexion.createStatement();
	
			
			
				if(usu.getTipoUsuario()==TipoUsuario.VENDEDOR){
					admin=0;
					}
					if(usu.getTipoUsuario()==TipoUsuario.ADMINISTRADOR){
						admin=1;
					}
				
				
				st.executeUpdate("UPDATE usuarios SET Nombre= '"+usu.getNombre()+
						"',Contrasena= '"+usu.getContrasena()+"',Admin='"+admin+"' WHERE Nombre='"+usuantiguo.getNombre()+"'");
		
				st.close();
			}catch(Exception e){
			
			System.out.println("Fallo modificar");
		}
		
		
	}
	
	public void iniciarSesion(Usuario usuario) throws UsuarioInvalido{//

		try{
		Statement st = conexion.createStatement();
		
		
		st.executeUpdate("UPDATE usuarios SET SesionIniciada='1' WHERE Nombre='" + usuario.getNombre() +"'");
		st.close();
	
	}catch(Exception e){
		
		System.out.println("Fallo al inciar sesion");
	}
	
	}
	
	public boolean userEnBD(String NombUsu){//
		boolean esta=false;
		
		try{
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM usuarios WHERE Nombre='" + NombUsu + "'"); 
		
		if(rs.next()){
			
			esta=true;
		}
	
				
		st.close();		
		rs.close();
	}catch(Exception e){
		System.out.println("Fallo");
	}
	
	return 	esta;
	}
	
	public boolean sesionIniciada(Usuario user){//
		boolean sesionIni=false;
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM usuarios WHERE Nombre= '"+ user.getNombre()+ "'");
			rs.next();
			if(rs.getInt("SesionIniciada")==1) sesionIni=true;
			
			rs.close();
			st.close();

		}catch(Exception e){
			
			System.out.println("Fallo al ver inciado");
			System.out.println(e.getMessage());
		}
		
		
		return sesionIni;
		
	}
	public void cerrarSesion(){//
		
		try{
			Statement st = conexion.createStatement();
		
			st.executeUpdate("UPDATE usuarios SET SesionIniciada='0' WHERE SesionIniciada='1'");
			
			st.close();
		}catch(Exception e){
			
			System.out.println("Fallo al cerrar sesion");
		}
		
	}
	
	
	public int cuantosAdmin(){//
	
		int admincount=0;
		
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Usuarios WHERE Admin= '1'");
			
			rs.last();
			admincount=rs.getRow();
		
			
			rs.close();
			st.close();
		}catch(Exception e){
			
			System.out.println("Fallo al contar admin");
		}
		return admincount;
		
		
	}
	
	
	
	public int cuantosVende(){//
		int vendecount=0;
		
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Usuarios WHERE Admin= '0'");
			
			rs.last();
			vendecount=rs.getRow();
		
			
			rs.close();
			st.close();
		}catch(Exception e){
			
			System.out.println("Fallo al contar vendedores");
		}
		return vendecount;
		
		
		
		
	}
	
	public Usuario buscaUsuario(Usuario usuario){//
		
		Usuario Usu=null;
				try{
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM usuarios"); 
		
	
		while (rs.next())
		{
			if(rs.getString("Nombre").equals(usuario.getNombre()) && rs.getString("Contrasena").equals(usuario.getContrasena())){
				Usu= new Usuario("","",null);
				Usu.setNombre(rs.getString("Nombre"));
				Usu.setContrasena(rs.getString("Contrasena"));
				
				if(rs.getInt("Admin")==1){
					TipoUsuario tipo= TipoUsuario.ADMINISTRADOR;
					Usu.setTipoUsuario(tipo);
				}else{
				
					TipoUsuario tipo= TipoUsuario.VENDEDOR;
					Usu.setTipoUsuario(tipo);
				}
			}	
		}
		rs.close();
		st.close();
	
	
	
	
	}catch(Exception e){
		
		
	}
		return Usu;
	}


	@Override
	public void eliminarUsuario(String Nombre) {//
		// TODO Auto-generated method stub
		try{
			Statement st = conexion.createStatement();
		
		
		st.executeUpdate("DELETE FROM usuarios WHERE Nombre='"+Nombre+"'");
		
		st.close();
		}catch(Exception e){
			
			System.out.println("Fallo al eliminar");
		}
	}

	@Override
	public ArrayList<Usuario> listaUsuarios() {//
		
		
		 ArrayList<Usuario> usuarios= new ArrayList<Usuario>();
		try{
		Statement st = conexion.createStatement();
		 ResultSet rs = st.executeQuery("SELECT * FROM usuarios"); 
	
		
		while (rs.next())
		{
			
			Usuario usuario= new Usuario("","",null);
				
				usuario.setNombre(rs.getString("Nombre"));
				usuario.setContrasena(rs.getString("Contrasena"));
				
				if(rs.getInt("Admin")==1){
					TipoUsuario tipo= TipoUsuario.ADMINISTRADOR;
					usuario.setTipoUsuario(tipo);
				}else{
				
					TipoUsuario tipo= TipoUsuario.VENDEDOR;
					usuario.setTipoUsuario(tipo);
				}
			
			usuarios.add(usuario);
			
		}
	
	
	
		rs.close();
		
		st.close();
	}catch(Exception e){
		System.out.println("Fallo lista");
		
	}
		return usuarios;
	}


	
	
	
	
}
