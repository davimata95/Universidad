package socio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import excepciones.FalloConexionBD;
import objetosTransfer.Alquiler;
import objetosTransfer.CategoriaJuego;
import objetosTransfer.Producto;
import objetosTransfer.Socio;
import objetosTransfer.TipoProducto;


public class DAOSocio implements IDAOSocio {
	private Connection conexion;
	
	public DAOSocio() throws FalloConexionBD{
		
		try{
			
		
			Class.forName("com.mysql.jdbc.Driver");
		   conexion = DriverManager.getConnection("jdbc:mysql://localhost/gamesql", "gameadmin", "admin"); 
		}catch(Exception e){
			
			 throw new FalloConexionBD();
			
		}
	}
	
	

	
	public void eliminarSocio(Socio socio) {//
		// TODO Auto-generated method stub
		try{
			Statement st = conexion.createStatement();
		st.executeUpdate("DELETE FROM socio WHERE DNI='"+socio.getDNI()+"'");
		
		st.close();
		}catch(Exception e){
			
			System.out.println("Fallo al eliminar");
		}
		
		
	}

	public void registrarSocio(Socio socio) {//
		try{
			Statement st = conexion.createStatement();
			
			
		
				st.executeUpdate("INSERT INTO socio (Nombre, Apellidos, DNI,Juegosinteres,Telefono) VALUES('"+socio.getNombre()+"','"+socio.getApellidos()+
						"','"+socio.getDNI()+"','"+socio.getInteres() + "', '"+ socio.getTelefono()+ "')");
		st.close();
			}catch(Exception e){
			
			System.out.println("Fallo insertar");
		}
	}

	public int cuantosSocios() {//
		// TODO Auto-generated method stub
		int count=0;
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT COUNT(*) AS count FROM socio");
			rs.next();
			 count = rs.getInt("count") ;
			
		rs.close();
		st.close();
				
		}catch(Exception e){
			
			System.out.println("Fallo al contar");
		}
		return count;
	}
	public void inicioSocio(Socio socio) {//
		// TODO Auto-generated method stub

	
		try{
		Statement st = conexion.createStatement();
	
				st.executeUpdate("UPDATE socio SET SocioIniciado='1' WHERE DNI='" + socio.getDNI() +"'");
		
		st.close();
	}catch(Exception e){
		
	}
	
		
	}
	public void cerrarSocio() {//
		// TODO Auto-generated method stub
		
		try{
			Statement st = conexion.createStatement();
		
			st.executeUpdate("UPDATE socio  SET SocioIniciado='0' WHERE SocioIniciado='1'");
			
			st.close();
		}catch(Exception e){
			
			System.out.println("Fallo al cerrar socio");
		}
		
	}
	public Alquiler[] VerAlquiler() {//
		// TODO Auto-generated method stub
		Socio socio;
		socio=getSocioConectado();
		Alquiler[] alquileres=new Alquiler[1];
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM almacenalquilados WHERE DNISocio= '"+ socio.getDNI()+"'");
		int i=0;
			while(rs.next()){
				Statement st1 = conexion.createStatement();
				ResultSet rs1=st1.executeQuery("SELECT * FROM Producto WHERE Id='" + rs.getInt("Producto")+ "'");
				rs1.next();
				CategoriaJuego cate=devuelveCategoria(rs1.getString("Tema"));
				TipoProducto tipo = devuelveTipoJuego(rs1.getString("TipoProducto"));
				
			Producto producto=new Producto(rs1.getInt("Id"),   tipo , cate, rs1.getString("Nombre"), rs1.getInt("Edad"), rs1.getDouble("Precio"), rs1.getDate("Fecha"));
				Alquiler alquiler= new Alquiler(rs.getInt("IdAlquilados"),producto,rs.getDate("FechaDevolucion") ,rs.getDate("FechaAlquiler"), socio);
				
		
				alquiler.setSocioAlquiler(socio);
				
				alquileres[i]=alquiler;
				i++;
			}
			rs.close();
			st.close();
		}catch(Exception e){
			
			System.out.println("Fallo al eliminar");
			System.out.println(e.getMessage());
		}
		
		return alquileres;
	}

	
	public Socio getSocioConectado() {//
		// TODO Auto-generated method stub
	Socio socio = null;	
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM socio "
					+ "WHERE (SocioIniciado='1')");
		
			if(rs.next()){
				CategoriaJuego cate = null;
						String Cat=rs.getString("Juegosinteres");
						cate = devuelveCategoria(Cat);
						socio=new Socio(rs.getString("Nombre"),rs.getString("Apellidos"),rs.getString("DNI"),rs.getString("Telefono"),cate);
						socio.setPuntos(rs.getDouble("Puntos"));
						
 					
						
				
			}
			
		rs.close();
		st.close();
				
		}catch(Exception e){
		}
		
		return socio;
	}
	
	public CategoriaJuego devuelveCategoria(String Cat){
		CategoriaJuego cate = null;
	
		if(Cat.equals("ROL")){
			cate= CategoriaJuego.ROL;
		}
		if(Cat.equals("DEPORTES")){
			cate= CategoriaJuego.DEPORTES;
		}	if(Cat.equals("PLATAFORMA")){
			cate= CategoriaJuego.PLATAFORMA;
		}	if(Cat.equals("SHOOTERS")){
			cate= CategoriaJuego.SHOOTERS;
		}	if(Cat.equals("ESTRATEGIA")){
			cate= CategoriaJuego.ESTRATEGIA;
		}	if(Cat.equals("CARRERAS")){
			cate= CategoriaJuego.CARRERAS;
		}	if(Cat.equals("ARCADE")){
			cate= CategoriaJuego.ARCADE;
		}	if(Cat.equals("AVENTURA")){
			cate= CategoriaJuego.AVENTURA;
		}	if(Cat.equals("OTROS")){
			cate= CategoriaJuego.OTROS;
		}
		if(Cat.equals("CUALQUIERA")){
			cate= CategoriaJuego.CUALQUIERA;
		}
		
		return cate;
	}
	public TipoProducto devuelveTipoJuego(String tipo){
		
		TipoProducto prod = null;
		//JUEGOS, PELICULAS, VIDEOCONSOLAS, ACCESORIOS
		if(tipo.equals("JUEGOS")){
			prod= TipoProducto.JUEGOS;
		}
		if(tipo.equals("PELICULAS")){
			prod= TipoProducto.PELICULAS;
		}	if(tipo.equals("VIDEOCONSOLAS")){
			prod= TipoProducto.VIDEOCONSOLAS;
		}	if(tipo.equals("ACCESORIOS")){
			prod= TipoProducto.ACCESORIOS;
		}	
		if(tipo.equals("CUALQUIERA"))
			prod = TipoProducto.CUALQUIERA;
		return prod;
	}
	
	
	public boolean socioConectado() {//
		boolean conectado=false;
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT COUNT(*) AS count FROM socio "
					+ "WHERE SocioIniciado='1'");
			rs.next();
			 int count = rs.getInt("count") ;
			 if(count==1) conectado=true;
			 
			
		rs.close();
		st.close();
				
		}catch(Exception e){
			
		
		}
		return conectado;
	}
	public void modificaSocioConectado(Socio socio) {//
		
			
			try{
				//int admin=0;
				Statement st = conexion.createStatement();
					
					st.executeUpdate("UPDATE socio  SET Nombre= '"+socio.getNombre()+
							"',Apellidos= '"+socio.getApellidos()+"',DNI='"+socio.getDNI()+"' , Juegosinteres= '"+socio.getInteres() + 
							"' , Telefono='" + socio.getTelefono()+"', Puntos="+ socio.getPuntos()+ "  WHERE SocioIniciado='1'");
			
					st.close();
				}catch(Exception e){	
			}
	}

	
	
	public void actualizarPuntos(double puntos) {//
		
		
		try{
			//int admin=0;
			Statement st = conexion.createStatement();
				
				st.executeUpdate("UPDATE socio  SET Puntos="+ puntos+ "  WHERE SocioIniciado='1'");
		
				st.close();
			}catch(Exception e){	
		}
}




	@Override
	public ArrayList<Socio> getListaSocios() {
	
		 ArrayList<Socio> socios= new ArrayList<Socio>();
		try{
	
		 Statement st1=conexion.createStatement();
		 ResultSet rs = st1.executeQuery("SELECT * FROM socio"); 
	
		
		while (rs.next())
		{
			Socio socio=new Socio(rs.getString("Nombre"),rs.getString("Apellidos"), rs.getString("DNI"),rs.getString("Telefono"),devuelveCategoria(rs.getString("Juegosinteres")));
				
	
		socio.setNombre(rs.getString("Nombre"));
		socio.setPuntos(rs.getDouble("Puntos"));
			socios.add(socio);
	
		}
	
		rs.close();
		
		st1.close();
	}catch(Exception e){
	
		
	}
		return socios;
	}




	@Override
	public ArrayList<Socio> getListaSocio(Socio socio) {
		
			ArrayList<Socio> socios=new ArrayList<Socio>();
			
				try{
				
				 

				 String sql="";
				 boolean mas=false;
				 String a;
				 if(!socio.getNombre().equals("")){
					 if(mas==false){
						 sql=sql+" WHERE ";
						 a=("(Nombre='"+ socio.getNombre() +"')");
						 sql=sql+a;
						 mas=true;
					 }else{
						 a=(" AND (Nombre='"+ socio.getNombre() +"')");
						 sql=sql+a;
					 }
					 
					 
				 }
				 if (!socio.getApellidos().equals("")){
					 if(mas==false){
						 sql=sql+" WHERE ";
						 a=("(Apellidos='"+ socio.getApellidos() +"')");
						 sql=sql+a;
						 mas=true;
					 }else{
						 a=(" AND (Apellidos='"+ socio.getApellidos() +"')");
						 sql=sql+a;
					 }
					 
				 }
				 if(!socio.getDNI().equals("")){
					 if(mas==false){
						 sql=sql+" WHERE ";
						 a=("(DNI='"+ socio.getDNI() +"')");
						 sql=sql+a;
						 mas=true;
					 }else{
						 a=(" AND (DNI='"+ socio.getDNI() +"')");
						 sql=sql+a;
					 }
				 }
				 if(!socio.getTelefono().equals("")){
					 if(mas==false){
						 sql=sql+" WHERE ";
						 a=("(Telefono='"+ socio.getTelefono()+"')");
						 sql=sql+a;
						 mas=true;
					 }else{
						 a=(" AND (Telefono='"+ socio.getTelefono() +"')");
						 sql=sql+a;
					 }
				 }
				 if ( socio.getPuntos()!=0){
					 
					 
					 if(mas==false){
					sql=sql+" WHERE ";
					 a=("(Puntos='"+ socio.getPuntos() +"')");
					 sql=sql+a;
					 mas=true;
				 }else{
					 a=(" AND (Puntos='"+ socio.getPuntos() +"')");
					 sql=sql+a;
				 }
				 }
					
				 if (socio.getInteres()!=CategoriaJuego.CUALQUIERA){
					 
					 if(mas==false){
						 sql=sql+" WHERE ";
						 a=("(Juegosinteres='"+ socio.getInteres() +"')");
						 sql=sql+a;
						 mas=true;
					 }else{
						 a=(" AND (Juegosinteres='"+ socio.getInteres() +"')");
						 sql=sql+a;
					 }
				 }
				 
				 

					 Statement st1=conexion.createStatement();
						 
				 ResultSet rs = st1.executeQuery("SELECT * FROM socio " +sql); 
				 

				while (rs.next())
				{	
					double puntos =rs.getDouble("Puntos");
					Socio socio1=new Socio(rs.getString("Nombre"),rs.getString("Apellidos"), rs.getString("DNI"),rs.getString("Telefono"),devuelveCategoria(rs.getString("Juegosinteres")));
					socio1.setPuntos(puntos);
					socios.add(socio1);
				
				}
			
				rs.close();
				
				st1.close();
			}catch(Exception e){
			
				
			}
				return socios;					
	}

}




