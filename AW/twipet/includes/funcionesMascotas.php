<?php
	
	//Obtengo los perros para adotar de la base de datos
	function obtenerMascotas($mysqli){
		$query="SELECT  id, nombre, raza, imagen, sexo, estado 
				FROM mascota 
				WHERE estado LIKE 'subido' ";
				
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$result=NULL;
		
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
			
			$resultado->free();
		
		return $result;
	}
	
	//Sube la mascota a la BD
	function subirMascota($mysqli, $propietario, $nombre, $descripcion, $sexo, $img, $raza, $edad, $adoptar){
	
		$query="INSERT INTO mascota (propietario, nombre, descripcion, sexo, imagen, raza, edad, estado)
				  VALUES ('".$propietario."', '".$nombre." ', '".$descripcion."
						', '".$sexo."', '".$img."', '".$raza."', '".$edad."
						', '".$adoptar."')";
						
			$resultado=$mysqli->query($query)  
				or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	
	//Obtengo la ultima mascota subida a la BD
	function ultimaMascota ($mysqli){
		$query="SELECT Max(id) as mayor 
				FROM mascota";
				
			$resultado=$mysqli->query($query)  
				or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
				
			$num=NULL;	
				
			if(isset($resultado) && $resultado->num_rows>0){
				$row=$resultado->fetch_assoc();
				$num=$row['mayor'];

				$resultado->free();
			}
		return $num;
	}
	
	//Procesara el cambio de dueño de la mascota.
	function adoptaMascota($mysqli, $mascota , $nuevoP){
		
		$query="UPDATE mascota 
				SET estado= 'no', propietario = $nuevoP  
				WHERE id = $mascota";
		
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	
	//Elimina una mascota de la BD
	function deleteMascota ($mysqli, $id){
		
		$query="DELETE FROM mascota 
				WHERE id = $id";
		
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	
	//Muestra la ascota seleccionada
	function mostrarMascota($mysqli, $idPerro){
		
		//Obtengo el perro de la base de datos a traves del idPerro 
		$query="SELECT * 
				FROM mascota 
				WHERE id = $idPerro ";
				
		$resultado=$mysqli->query($query)
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$registro = $resultado->fetch_assoc();
			
			$resultado->free();
			
		return $registro;
	}
	
	//Modifica la mascota
	function modificaMascota ($mysqli, $cambio, $valor, $id){
		$query="UPDATE mascota 
				SET $cambio = '$valor' 
				WHERE id = $id";
				
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}

?>

