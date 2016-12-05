package almacen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import excepciones.FalloConexionBD;
import objetosTransfer.CategoriaJuego;
import objetosTransfer.Producto;
import objetosTransfer.TipoProducto;

public class DAOAlmacen implements IDAOAlmacen {
	private Connection conexion;

	public DAOAlmacen()throws FalloConexionBD{
		try{
		
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/gamesql", "gameadmin", "admin"); 
		}catch(Exception e){
			throw new FalloConexionBD();
		}
	
	}
	
	//Saldo insuficiente y cantidad excesiva
	/*
	public boolean saldoSuficiente(double precio)
	{
		boolean valido = false;
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("select Total from contabilidad order by Total desc limit 1");
			
			if(rs.next()){
				if(rs.getDouble("Total") > precio)
				valido=true;
			}
		}catch(Exception e){
			System.out.println("Error al buscar el total");
		}
		
		return valido;
	}
	
	public boolean cantidadSuficiente(int cantidad, Producto p){
		boolean valido = false;
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("select Cantidad from almacenventas where Id='"+p.getId()+"')");
			
			if(rs.next()){
				if(rs.getInt("Cantidad") > cantidad)
					valido=true;
			}
		}catch(Exception e){
			System.out.println("Error al buscar la cantidad del producto");
		}
		
		return valido;
	}
	*/
	
	//compra y devolucion proveedor
	/*
	@Override
	public void comprarProveedor(Producto p, double precio) {
		try{
			Statement st = conexion.createStatement();
			//ResultSet rs = st.executeQuery("SELECT * FROM producto WHERE Categoria= '"+ p.getTipo()+"')");
			
			st.executeUpdate("INSERT INTO producto (Nombre, Categoria, Precio, Tema, Edad, Fecha) VALUES('"
					+p.getNombre()+"','"+p.getTipo()+"','"+p.getPrecio()+"','"+p.getCategoria()+"','"+p.getEdad()+"','"
					+p.getFecha()+"',)");
		
			}catch(Exception e){
			
			System.out.println("Fallo insertar");
		}
	}

	@Override
	public void devolverProveedor(Producto p) {
		try{
			Statement st = conexion.createStatement();
			//ResultSet rs = st.executeQuery("SELECT * FROM producto WHERE Categoria= '"+ p.getTipo()+"')");
			
			st.executeUpdate("DELETE FROM producto WHERE Nombre='"+p.getNombre()+"'");
		
			}catch(Exception e){
			
			System.out.println("Error al Eliminar");
		}
	}

	*/
	
	@Override
	public Producto[] mostrarAlmacenVentas(Producto p) {
		Producto[] product = null;
	
		
		try{
			
			// String sql1="SELECT * FROM producto WHERE ";
			 String sql="";
			 boolean mas=false;
			 String a;
			 if(!p.getNombre().equals("")){
				 if(mas==false){
					 a=("(Nombre='"+ p.getNombre() +"')");
					 sql=sql+a;
					 mas=true;
				 }else{
					 a=(" AND (Nombre='"+ p.getNombre() +"')");
					 sql=sql+a;
				 }
				 
				 
			 }
			 if (!(p.getTipo() == TipoProducto.CUALQUIERA)){
				 if(mas==false){
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
					 a=("(Precio <='"+ p.getPrecio() +"')");
					 sql=sql+a;
					 mas=true;
				 }else{
					 a=(" AND (Precio <='"+ p.getPrecio() +"')");
					 sql=sql+a;
				 }
			 }
			 if(!(p.getCategoria() == CategoriaJuego.CUALQUIERA)){
				 if(mas==false){
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
					 a=("(Fecha <= '"+ p.getFecha() +"')");
					 sql=sql+a;
					 mas=true;
				 }else{
					 a=(" AND (Juegosinteres='"+ p.getFecha() +"')");
					 sql=sql+a;
				 }
			 }
			 
			 Statement st = conexion.createStatement();
				
				ResultSet rs1 = st.executeQuery("SELECT COUNT(*) AS count FROM producto WHERE " +sql);
				rs1.next();
				 int count = rs1.getInt("count") ;
				 product=new Producto[count];
				 rs1.close();
				 st.close();
				 Statement st1=conexion.createStatement();
					 
			 ResultSet rs = st1.executeQuery("SELECT * FROM producto WHERE " +sql); 
			 ArrayList<Producto> productosarr =new ArrayList<Producto>();
			 
			while (rs.next())
			{
				Statement st2=conexion.createStatement();
			    ResultSet rs2= st2.executeQuery("SELECT * FROM  almacenventas WHERE IdProducto= '" + rs.getInt("Id")+ "'");
				
			    
			    while(rs2.next()){
			    	CategoriaJuego cate=devuelveCategoria(rs.getString("Tema"));
			    	TipoProducto tipo = devuelveTipoJuego(rs.getString("TipoProducto"));
			    	Producto producto=new Producto(rs.getInt("Id"), tipo, cate, rs.getString("Nombre"), rs.getInt("Edad"), rs.getDouble("Precio"), rs.getDate("Fecha"));
			    	productosarr.add(producto);
			    	
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

	@Override
	public Producto[] mostrarNovedades() {
		
		Producto product;
		
		Producto[] Productos=new Producto[10];
		
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM producto ORDER BY Fecha DESC LIMIT 10"); 
			int i=0;
			while (rs.next())
			{
				CategoriaJuego cate=devuelveCategoria(rs.getString("Tema"));
				TipoProducto tipo = devuelveTipoJuego(rs.getString("TipoProducto"));
				
				product=new Producto(rs.getInt("Id"), tipo, cate, rs.getString("Nombre"), rs.getInt("Edad"), rs.getDouble("Precio"), rs.getDate("Fecha"));
				
							 
				Productos[i]=product;
				i++;
			}
			
			rs.close();
			st.close();
			
		}catch(Exception e){
			System.out.println("Error al Mostrar");
		}
		
		return Productos;
		
		}
	
	
	public Producto[] mostrarAlmacen(Producto p) {
		Producto[] product = null;
	
		
		try{
			
			// String sql1="SELECT * FROM producto WHERE ";
			 String sql="";
			 boolean mas=false;
			 String a;
			 if(!p.getNombre().equals("")){
				 if(mas==false){
					 a=("(Nombre='"+ p.getNombre() +"')");
					 sql=sql+a;
					 mas=true;
				 }else{
					 a=(" AND (Nombre='"+ p.getNombre() +"')");
					 sql=sql+a;
				 }
				 
				 
			 }
			 if (!(p.getTipo() == TipoProducto.CUALQUIERA)){
				 if(mas==false){
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
					 a=("(Precio <='"+ p.getPrecio() +"')");
					 sql=sql+a;
					 mas=true;
				 }else{
					 a=(" AND (Precio <='"+ p.getPrecio() +"')");
					 sql=sql+a;
				 }
			 }
			 if(!(p.getCategoria() == CategoriaJuego.CUALQUIERA)){
				 if(mas==false){
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
					 a=("(Fecha <= '"+ p.getFecha() +"')");
					 sql=sql+a;
					 mas=true;
				 }else{
					 a=(" AND (Juegosinteres='"+ p.getFecha() +"')");
					 sql=sql+a;
				 }
			 }
			 
			 Statement st = conexion.createStatement();
				
				ResultSet rs1 = st.executeQuery("SELECT COUNT(*) AS count FROM producto WHERE " +sql);
				rs1.next();
				 int count = rs1.getInt("count") ;
				 product=new Producto[count];
				 rs1.close();
				 st.close();
				 Statement st1=conexion.createStatement();
					 
			 ResultSet rs = st1.executeQuery("SELECT * FROM producto WHERE " +sql); 
			 ArrayList<Producto> productosarr =new ArrayList<Producto>();
			 
			while (rs.next())
			{
			    	CategoriaJuego cate=devuelveCategoria(rs.getString("Tema"));
			    	TipoProducto tipo = devuelveTipoJuego(rs.getString("TipoProducto"));
			    	Producto producto=new Producto(rs.getInt("Id"), tipo, cate, rs.getString("Nombre"), rs.getInt("Edad"), rs.getDouble("Precio"), rs.getDate("Fecha"));
			    	productosarr.add(producto);
			    	
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
	public void anadirSaldo(double saldo) {
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT FinalTienda FROM contabilidad WHERE Id=(SELECT MAX(Id) FROM contabilidad)");
			
			double total= 0;
			if(rs.next())
				total= rs.getDouble("FinalTienda");
			
			
			st.executeUpdate("INSERT INTO contabilidad (Operacion, Cantidad, Total, FinalTienda) VALUES('Saldo añadido',1,"+saldo+","+(total+saldo)+")");
			
		}catch(Exception e){
			System.out.println("Error al Insertar");
		}
		
	}


	@Override
	public String[] verUltimasTransacciones() {
		
		String cadena;
		
		String[] Cadena=new String[10];
		
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM contabilidad ORDER BY Id DESC LIMIT 10"); 
			int i=0;
			while (rs.next())
			{
				
				cadena=new String(rs.getString("Operacion")+" Cantidad: "+ rs.getInt("Cantidad")+" Total: "+ rs.getDouble("Total")+" Saldo Tienda: "+ rs.getDouble("FinalTienda"));
				
							 
				Cadena[i]=cadena;
				i++;
			}
			
			rs.close();
			st.close();
			
		}catch(Exception e){
			System.out.println("Error al Mostrar");
		}
		
		return Cadena;
	}
	
	
	//Buscar producto
	/*
	public Producto buscarProducto(Producto p) {
		Producto product;
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM producto WHERE Nombre='"+p.getId()+"')");
			
			
			if(rs.next()){
				CategoriaJuego cate=devuelveCategoria(rs.getString("Tema"));
				TipoProducto tipo = devuelveTipoJuego(rs.getString("TipoProducto"));
				
				product=new Producto(rs.getInt("Id"), tipo, cate, rs.getString("Nombre"), rs.getInt("Edad"), rs.getDouble("Precio"), rs.getDate("Fecha"));
				
			}
				
		
			}catch(Exception e){
			
			System.out.println("Error al buscar el producto");
		}
		
		return product;
		
	}
	*/
}
