package alquiler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import objetosTransfer.Alquiler;
import objetosTransfer.CategoriaJuego;
import objetosTransfer.Producto;
import objetosTransfer.Socio;
import objetosTransfer.TipoProducto;

public class DAOAlquiler implements IDAOAlquiler {

	private Connection conexion;
	
	public DAOAlquiler(){
		
		try{
			
			
			Class.forName("com.mysql.jdbc.Driver");
		   conexion = DriverManager.getConnection("jdbc:mysql://localhost/gamesql", "gameadmin", "admin"); 
		}catch(Exception e){
			
			
		}
	}
	
	public Alquiler[] VerAlquiler(Socio socio) {//
		// TODO Auto-generated method stub
		ArrayList<Alquiler> alquileresarr= new ArrayList<Alquiler>();
		Alquiler[] alquileres=null;
		
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM almacenalquilados WHERE DNISocio= '"+ socio.getDNI()+"'");
	
			while(rs.next()){
				Statement st1 = conexion.createStatement();
				ResultSet rs1=st1.executeQuery("SELECT * FROM producto WHERE Id='" + rs.getInt("Producto")+ "'");
				rs1.next();
				CategoriaJuego cate=devuelveCategoria(rs1.getString("Tema"));
				TipoProducto tipo = devuelveTipoJuego(rs1.getString("TipoProducto"));
				
			Producto producto=new Producto(rs1.getInt("Id"),   tipo , cate, rs1.getString("Nombre"), rs1.getInt("Edad"), rs1.getDouble("Precio"), rs1.getDate("Fecha"));
				Alquiler alquiler=new Alquiler(rs.getInt("IdAlquilados"), producto,rs.getDate("FechaDevolucion") ,rs.getDate("FechaAlquiler"), socio);
				
				alquileresarr.add(alquiler);
			
			}
			rs.close();
			st.close();
			int y=0;
			alquileres=new Alquiler[alquileresarr.size()];
			while(y<alquileresarr.size()){
				
				alquileres[y]=alquileresarr.get(y);
				
				y++;
			}
		}catch(Exception e){
			
			System.out.println("Fallo al eliminar");
			System.out.println(e.getMessage());
		}
		
		return alquileres;
	}
	
	
	
	
	@Override
	public void DevolverAlquiler(Alquiler alquiler) {
		try{
			Statement st = conexion.createStatement();

			Statement st1= conexion.createStatement();
			
			ResultSet rs=st1.executeQuery("SELECT * FROM almacenalquilados WHERE ( Producto='" + alquiler.getProductoAlquiler().getId() +"') AND (DNISocio='" + alquiler.getSocioAlquiler().getDNI()+"')");
		rs.next();
				st.executeUpdate("UPDATE almacenalquilados  SET Alquilado= '0', FechaAlquiler='0000/00/00', FechaDevolucion ='0000/00/00', DNISocio='0' WHERE IdAlquilados='"+rs.getInt("IdAlquilados")+"'");
			
				st1.close();
				rs.close();
				st.close();
			}catch(Exception e){
			
			System.out.println("Fallo insertar");
		}
	}

	@Override
	public void AlquilarProducto(Alquiler alquiler) {
		try{
			Statement st = conexion.createStatement();
		
			Statement st1= conexion.createStatement();
			
			ResultSet rs=st1.executeQuery("SELECT * FROM almacenalquilados WHERE ( Producto='" + alquiler.getProductoAlquiler().getId() +"') AND (Alquilado='0')");
		
			rs.next();
				st.executeUpdate("UPDATE almacenalquilados  SET "+
						"Alquilado='1', FechaAlquiler='"+ new java.sql.Date(alquiler.getFechaAlquiler().getTime())+"' , FechaDevolucion ='"+new java.sql.Date(alquiler.getFechaDevolucion().getTime())+
						"' , DNISocio='"+ alquiler.getSocioAlquiler().getDNI() + "' WHERE IdAlquilados='"+rs.getInt("IdAlquilados")+"'");
				st.close();
				st1.close();
				rs.close();
			}catch(Exception e){
			
			System.out.println("Fallo insertar");
		}
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
		return prod;
	}

	@Override
	public void actualizarSaldo(double penalizacion) {
		try{
			
			Statement st1= conexion.createStatement();
			
			ResultSet rs1=st1.executeQuery("SELECT * FROM contabilidad ORDER BY Id DESC");
			rs1.next();
			
			double total= rs1.getDouble("FinalTienda");
			total=total+penalizacion;
			rs1.close();
			st1.close();
			
			
			Statement st= conexion.createStatement();	
		 st.executeUpdate("INSERT INTO contabilidad (Operacion,Cantidad,FinalTienda, Total) VALUES('Ingreso Alquiler', '1','"+total+"', '" + penalizacion + "')");
		 st.close();
	
		 

			}catch(Exception e){
			
			System.out.println(e.getMessage());
		}
		
	}
	

	@Override
	public Producto[] DevolverEnAlquiler(Producto p) {

			
	Producto[] product = null;
	
	
	try{
		
		// String sql1="SELECT * FROM producto WHERE ";
		 String sql="";
		 boolean mas=false;
		 String a;
		 if(!p.getNombre().equals("")){
			 if(mas==false){
				sql=sql + " WHERE ";
				 a=("(Nombre='"+ p.getNombre() +"')");
				 sql=sql+a;
				 mas=true;
			 }else{
				 a=(" AND (Nombre='"+ p.getNombre() +"')");
				 sql=sql+a;
			 }
			 
			 
		 }
		 if (p.getTipo() != TipoProducto.CUALQUIERA){
			 if(mas==false){
				 sql=sql + " WHERE ";
				 a=("(TipoProducto='"+ p.getTipo() +"')");
				 sql=sql+a;
				 mas=true;
			 }else{
				 a=(" AND (TipoProducto='"+ p.getTipo() +"')");
				 sql=sql+a;
			 }
			 
		 }
		 if(p.getPrecio() != null){
			 if(mas==false){
				 sql=sql + " WHERE ";
				 a=("(Precio <='"+ p.getPrecio() +"')");
				 sql=sql+a;
				 mas=true;
			 }else{
				 a=(" AND (Precio <='"+ p.getPrecio() +"')");
				 sql=sql+a;
			 }
		 }
		 if(p.getCategoria() != CategoriaJuego.CUALQUIERA){
			 if(mas==false){
				 sql=sql + " WHERE ";
				 a=("(Tema='"+ p.getCategoria()+"')");
				 sql=sql+a;
				 mas=true;
			 }else{
				 a=(" AND (Tema='"+ p.getCategoria() +"')");
				 sql=sql+a;
			 }
		 }
		 if ( p.getEdad()!=0){
			 if(mas==false){
				 sql=sql + " WHERE ";
				 a=("(Edad <= '"+ p.getEdad() +"')");
				 sql=sql+a;
				 mas=true;
			 }else{
				 a=(" AND (Edad <= '"+ p.getEdad() +"')");
				 sql=sql+a;
			 }
		 }
			
		 if (p.getFecha()!=null){
			 
			 if(mas==false){
				 sql=sql + " WHERE ";
				 a=("(Fecha <= '"+ p.getFecha() +"')");
				 sql=sql+a;
				 mas=true;
			 }else{
				 a=(" AND (Juegosinteres='"+ p.getFecha() +"')");
				 sql=sql+a;
			 }
		 }
		 
		 Statement st = conexion.createStatement();
			
			ResultSet rs1 = st.executeQuery("SELECT COUNT(*) AS count FROM producto " +sql);
			rs1.next();
			 int count = rs1.getInt("count") ;
			 product=new Producto[count];
			 st.close();
			 rs1.close();
			 
			 Statement st1=conexion.createStatement();
				 
		 ResultSet rs = st1.executeQuery("SELECT * FROM producto  " +sql); 
		 ArrayList<Producto> productosarr =new ArrayList<Producto>();
		
		while (rs.next())
			
		{
			Statement st2=conexion.createStatement();
	       ResultSet rs2= st2.executeQuery("SELECT * FROM  almacenalquilados WHERE Producto= '" + rs.getInt("Id")+ "'");
	       
	       while(rs2.next()){
	    	   	if(rs2.getInt("Alquilado")==0){
	    	   		
	    	   		CategoriaJuego cate=devuelveCategoria(rs.getString("Tema"));
	    			TipoProducto tipo = devuelveTipoJuego(rs.getString("TipoProducto"));
	    			Producto producto=new Producto(rs.getInt("Id"), tipo, cate, rs.getString("Nombre"), rs.getInt("Edad"), rs.getDouble("Precio"), rs.getDate("Fecha"));
	    	   		productosarr.add(producto);
	    	   		
	    	   	}
	    	   
	    	   
	       }
			rs2.close();
			st2.close();
		}
		
		rs.close();
		st.close();
		int y=0;
		product= new Producto[productosarr.size()];
		
		while(y<productosarr.size()){
			
			product[y]=productosarr.get(y);
			
			
			y++;
		}
			}catch(Exception e){
				System.out.println("Error al Mostrar");
			}
	
	return product;

	}
	
}
