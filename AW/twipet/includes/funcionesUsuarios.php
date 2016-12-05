<?php 
		
	//Obtengo los datos del usuario
	function obtenerDatos($mysqli, $id){
		
		$query="SELECT * 
				FROM usuarios 
				WHERE id = $id ";
		$resultado=$mysqli->query($query)
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$registro = $resultado->fetch_assoc();
			
			$resultado->free();
			
		return $registro;
	}
	
	//Obtengo mis mascotas subidas
	function mostrarMisMascotas($mysqli, $idUsuario){

		$query="SELECT * 
				FROM mascota 
				WHERE propietario = $idUsuario";
		$resultado=$mysqli->query($query)
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$result = null;
			
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
			
			$resultado->free();
			
		return $result;
	}
	
	//Obtengo mis productos subidos a la tienda
	function mostrarMisProductos($mysqli, $idUsuario){
			
		$query="SELECT * 
				FROM productos 
				WHERE propietario = $idUsuario ";
		$resultado=$mysqli->query($query)
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$result = null;
			
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
			
			$resultado->free();
			
		return $result;
	}

	//Obtiene las mascotas
	function obtenerMascotasAdmin($mysqli){
		//Obtengo el perro de la base de datos
		$query="SELECT * 
				FROM mascota 
				WHERE estado LIKE 'subido' OR estado LIKE 'pendiente'
				ORDER BY estado DESC";
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$result = null;
			
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
			
			$resultado->free();
		
		return $result;
	}
	
	//Obtiene los productos
	function obtenerProductosAdmin($mysqli){
		
		//Obtengo el producto de la base de datos
		$query="SELECT  * 
				FROM productos
				ORDER BY estado DESC";
		$resultado=$mysqli->query($query)  
				or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$result = null;
			
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
				
			$resultado->free();
		
		return $result;
	}

	//Modifia la informacion del usuario
	function hacerCambio($mysqli, $cambio, $valor, $id ){
		
		$query="UPDATE usuarios 
				SET $cambio = '$valor' 
				WHERE id = $id";
			
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	
	
?>