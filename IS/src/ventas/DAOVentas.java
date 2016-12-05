package ventas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import excepciones.FalloConexionBD;
import objetosTransfer.Producto;

public class DAOVentas implements IDAOVentas{
	
	private Connection conexion;
	
	public DAOVentas() throws FalloConexionBD{
		try{
		
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/gamesql", "gameadmin", "admin"); 
		}catch(Exception e){
			throw new FalloConexionBD();
		}
	
	}
	@Override
	public void comprarProducto(Producto p, int cantidad, double precio) {
		
		
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM producto WHERE Nombre='"+p.getNombre()+"' AND TipoProducto= '"+ p.getTipo()+"'");
			
			//ResultSet rs = st.executeQuery("select * from producto");
				if(rs.next()){
					//st.executeUpdate("INSERT INTO almacenventas (IdProducto, Cantidad) VALUES('"+rs.getInt("Id")+"','"+(rs.getInt("Cantidad")+cantidad)+"',)");
					
					ResultSet rs1= st.executeQuery("select Cantidad from almacenventas WHERE IdProducto='"+rs.getInt("Id")+"'");
					
					if(rs1.next()){
						int cant=rs1.getInt("Cantidad");
						st.executeUpdate("UPDATE almacenventas SET Cantidad= '"+(cant+cantidad)+"' WHERE IdProducto='"+p.getId()+"'");
					}
					ResultSet rs2 = st.executeQuery("SELECT FinalTienda FROM contabilidad WHERE Id=(SELECT MAX(Id) FROM contabilidad)");
					if(rs2.next()){
						double finalT=0;
						finalT = rs2.getDouble("FinalTienda");
					
						double total=finalT-precio;
	
						st.executeUpdate("INSERT INTO contabilidad (Operacion, Cantidad, Total, FinalTienda) VALUES('Compra Cliente',"+cantidad+","+precio+","+total+")");
						}
					}
				else{
				
					st.executeUpdate("INSERT INTO producto (Nombre, TipoProducto, Precio, Tema, Edad, Fecha) VALUES('"
							+p.getNombre()+"','"+p.getTipo()+"','"+p.getPrecio()+"','"+p.getCategoria()+"','"+p.getEdad()+"','"
							+ new java.sql.Date(p.getFecha().getTime())+"')");
					
					ResultSet rs3 = st.executeQuery("select Id from producto WHERE Nombre='"+p.getNombre()+"' AND TipoProducto='"+ p.getTipo()+"'");
					
					if(rs3.next())
						st.executeUpdate("INSERT INTO almacenventas (IdProducto, Cantidad) VALUES('"+rs3.getInt("Id")+"','"+cantidad+"',)");
					
					ResultSet rs2 = st.executeQuery("SELECT FinalTienda FROM contabilidad WHERE Id=(SELECT MAX(Id) FROM contabilidad)");
					if(rs2.next()){
						double finalT=0;
						finalT = rs2.getDouble("FinalTienda");
					
						double total=finalT-precio;
	
						st.executeUpdate("INSERT INTO contabilidad (Operacion, Cantidad, Total, FinalTienda) VALUES('Compra Cliente',"+cantidad+","+precio+","+total+")");
			//st.executeUpdate("INSERT INTO contabilidad (Operacion, Cantidad, Total) VALUES('"+p.getId()+"','"+cantidad+"',)");
					}
					
				}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void venderProducto(Producto p, int cantidad, double precio) {
		double finalT=0;
		try{
			Statement st = conexion.createStatement();
			
			//st.executeUpdate("INSERT INTO almacenventas (IdProducto, Cantidad) VALUES('"+p.getId()+"','"+cantidad+"',)");
			ResultSet rs1= st.executeQuery("select Cantidad from almacenventas WHERE IdProducto='"+p.getId()+"'");
			if(rs1.next())
				if(rs1.getInt("Cantidad") > 0)
				st.executeUpdate("UPDATE almacenventas SET Cantidad= '"+(rs1.getInt("Cantidad")-cantidad)+"' WHERE IdProducto='"+p.getId()+"'");
				/*ResultSet rs3= st.executeQuery("select Cantidad from almacenventas WHERE IdProducto='"+p.getId()+"'");
					if(rs3.next())
						if(rs3.getInt("Cantidad") == 0)
						{
							st.executeUpdate("DELETE FROM almacenventas where IdProducto = '"+p.getId()+"'");
							st.executeUpdate("DELETE FROM producto where Id = '"+p.getId()+"'");
						}*/
			//ResultSet rs = st.executeQuery("SELECT * FROM socio WHERE Nombre= '"+ usuario.getNombre()+"')");
			//Consulta para saber el total de contabilidad
			ResultSet rs2 = st.executeQuery("SELECT FinalTienda FROM contabilidad WHERE Id=(SELECT MAX(Id) FROM contabilidad)");
			if(rs2.next())
				finalT = rs2.getDouble("FinalTienda");
			
			double total=finalT+precio;
			
			st.executeUpdate("INSERT INTO contabilidad (Operacion, Cantidad, Total, FinalTienda) VALUES('Compra Cliente',"+cantidad+","+precio+","+total+")");
		
			rs1.close();
			rs2.close();
			st.close();
			}catch(Exception e){
			
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void devolverProducto(Producto p) {
		try{
			Statement st = conexion.createStatement();
			
			//Inserto el producto en la base de datos
			//st.executeUpdate("INSERT INTO producto (Nombre, Categoria, Precio, Tema, Edad, Fecha) VALUES('"+p.getNombre()+"','"+p.getTipo()+"','"+p.getPrecio()+"','"+p.getCategoria()+"','"+p.getEdad()+"','"+p.getFecha()+"',)");
			
			//Elimino el producto de almacenventas
			st.executeUpdate("DELETE FROM almacenventas where IdProducto = '"+p.getId()+"')");
			
			//Consulta para saber el total de contabilidad
			ResultSet rs = st.executeQuery("select Total from contabilidad order by Total desc limit 1");
			
			//Inserto la operacion correspondiente en contabilidad y actualizo el saldo
			if(rs.next())
				st.executeUpdate("INSERT INTO contabilidad (Operacion, IdProducto, Cantidad, Total) VALUES('Devolucion', '"+p.getNombre()+"','"+p.getPrecio()+"','"+(rs.getString("Total")+p.getPrecio())+"')");
	
		
			rs.close();
			st.close();
			}catch(Exception e){
			
			System.out.println(e.getMessage());
		}
	}
	
	public boolean saldoSuficiente(double precio)
	{
		boolean valido = false;
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT FinalTienda FROM contabilidad WHERE Id=(SELECT MAX(Id) FROM contabilidad)");
			
			if(rs.next()){
				if(rs.getDouble("FinalTienda") > precio)
				valido=true;
			}
			
			rs.close();
			st.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return valido;
	}
	
	public boolean cantidadSuficiente(int cantidad, Producto p){
		boolean valido = false;
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("select Cantidad from almacenventas where IdProducto='"+p.getId()+"'");
			
			
			if(rs.next()){
				if(rs.getInt("Cantidad") >= cantidad)
					valido=true;
			}
			
			
			rs.close();
			
			ResultSet rs2 = st.executeQuery("select * from almacenalquilados where Producto='"+p.getId()+"'");
			int numFilas=0;
			if (rs2.last()){
				numFilas= rs2.getRow();
				} else {
				numFilas = 0;
			}
			
			if(numFilas >= cantidad)
				valido=true;
			
			rs2.close();
			st.close();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return valido;
	}
	
	//compra y devolucion proveedor
	
	@Override
	public void comprarProveedor(Producto p, int cantidad, boolean alquiler) {
		
		try{
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("select * from producto where Nombre='"+p.getNombre()+"' AND TipoProducto='"+p.getTipo()+"'");
			
			
			if(rs.next()){
				
				int iden=0;
				iden=rs.getInt("Id");
				if(alquiler){
					for(int i = 0; i < cantidad; i++)
						st.executeUpdate("INSERT INTO almacenalquilados (Producto, Alquilado, FechaAlquiler, FechaDevolucion, DNISocio) VALUES("+iden+", 0,'2015-00-00','2015-00-00','"+" "+"')");
				}
				else{
					ResultSet rs1= st.executeQuery("select Cantidad from almacenventas WHERE IdProducto='"+iden+"'");
					
					if(rs1.next()){
						int cant=0;
						cant=rs1.getInt("Cantidad");
						st.executeUpdate("UPDATE almacenventas SET Cantidad= '"+(cant+cantidad)+"' WHERE IdProducto='"+iden+"'");
					}
					else
						st.executeUpdate("INSERT INTO almacenventas (IdProducto, Cantidad) VALUES('"+iden+"','"+cantidad+"')");
					
					rs1.close();
				}
				
				ResultSet rs3 = st.executeQuery("SELECT FinalTienda FROM contabilidad WHERE Id=(SELECT MAX(Id) FROM contabilidad)");
				if(rs3.next()){
					double finalT = 0;
					finalT = rs3.getDouble("FinalTienda");
				
					double total=finalT- p.getPrecio()*cantidad;

					st.executeUpdate("INSERT INTO contabilidad (Operacion, Cantidad, Total, FinalTienda) VALUES('Compra Proveedor',"+cantidad+","+p.getPrecio()+","+total+")");
				}
					rs3.close();
					
			}else{
				st.executeUpdate("INSERT INTO producto (Nombre, TipoProducto, Precio, Tema, Edad, Fecha) VALUES('"
						+p.getNombre()+"','"+p.getTipo()+"','"+p.getPrecio()+"','"+p.getCategoria()+"','"+p.getEdad()+"','"
						+ new java.sql.Date(p.getFecha().getTime())+"')");
				
				ResultSet rs1 = st.executeQuery("select Id, Precio from producto WHERE Nombre='"+p.getNombre()+"'");
					int iden=0;
					if(rs1.next())
					{
						iden= rs1.getInt("Id");
					
						if(alquiler){
							
							for(int i = 0; i < cantidad; i++)
								st.executeUpdate("INSERT INTO almacenalquilados (Producto, Alquilado, FechaAlquiler, FechaDevolucion, DNISocio) VALUES("+iden+", 0,'2015-00-00','2015-00-00','"+" "+"')");
						}
						else{
							st.executeUpdate("INSERT INTO almacenventas (IdProducto, Cantidad) VALUES('"+iden+"','"+cantidad+"')");
						}
				//String tipo = rs1.getString("TipoProducto");
				
				
				
				ResultSet rs3 = st.executeQuery("SELECT FinalTienda FROM contabilidad WHERE Id=(SELECT MAX(Id) FROM contabilidad)");
				if(rs3.next()){
					double finalT = 0;
					finalT = rs3.getDouble("FinalTienda");
					
					double total=finalT- p.getPrecio()*cantidad;

						st.executeUpdate("INSERT INTO contabilidad (Operacion, Cantidad, Total, FinalTienda) VALUES('Compra Proveedor',"+cantidad+","+p.getPrecio()+","+total+")");
						rs3.close();
					}
					
					
				}
			}
					
		
				rs.close();
				st.close();
			}catch(Exception e){
			
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean devolverProveedor(Producto p, int cantidad) {
		double finalT=0;
		boolean a = true;
		try{
			Statement st = conexion.createStatement();
			ResultSet rs1= st.executeQuery("select * from almacenventas WHERE IdProducto='"+p.getId()+"'");
			
			if(rs1.next())
				if(rs1.getInt("Cantidad") > 1)
				st.executeUpdate("UPDATE almacenventas SET Cantidad= '"+(rs1.getInt("Cantidad")-cantidad)+"' WHERE IdProducto='"+p.getId()+"'");
			
				else
					st.executeUpdate("DELETE FROM producto where Id = '"+p.getId()+"')");
			
			rs1.close();
			
			
			int i=0;
			while(i<cantidad){
				ResultSet rs3= st.executeQuery("select * from almacenalquilados WHERE Producto='"+p.getId()+"'");
				
					
				if(rs3.next())  {
					if(rs3.getInt("Alquilado") == 1)
						return false;
						
					st.executeUpdate("DELETE FROM almacenalquilados where IdAlquilados = "+rs3.getInt("IdAlquilados")+"");
				}
					
				
				i++;
				rs3.close();
			}
			
			//ResultSet rs = st.executeQuery("SELECT * FROM socio WHERE Nombre= '"+ usuario.getNombre()+"')");
			//Consulta para saber el total de contabilidad
			ResultSet rs2 = st.executeQuery("SELECT FinalTienda FROM contabilidad WHERE Id=(SELECT MAX(Id) FROM contabilidad)");
			if(rs2.next())
				finalT = rs2.getDouble("FinalTienda");
			
			double total=finalT+p.getPrecio();
			
			st.executeUpdate("INSERT INTO contabilidad (Operacion, Cantidad, Total, FinalTienda) VALUES('Devolucion Cliente',1,"+p.getPrecio()+","+total+")");
		
			rs1.close();
			rs2.close();
			st.close();
			//ResultSet rs = st.executeQuery("SELECT * FROM producto WHERE Categoria= '"+ p.getTipo()+"')");
		
			} catch(Exception e){
			
			
		}
		return a;
	}
	

}
