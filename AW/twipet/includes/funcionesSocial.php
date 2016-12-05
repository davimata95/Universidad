<?php 

	//Muesta la lista de los ultimos mensajes
	function obtenerUltimosMensajes($mysqli, $yo){

		$query="SELECT DISTINCT emisor
				FROM mensajes 
				WHERE receptor = $yo
				ORDER BY id DESC ";
		$resultado=$mysqli->query($query)
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$result=null;
			
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}	
			$resultado->free();

		return $result;
	}
	
	//Obtiene mis publicaciones en el tablon
	function obtenerMisComentarios($mysqli, $yo){

		$query="SELECT * 
				FROM publicaciones 
				WHERE usuario = $yo ORDER BY id DESC LIMIT 10 ";
		$resultado=$mysqli->query($query)
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$result = null;
			
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
			
			$resultado->free();
		
		return $result;
	}
	
	//Obtiene todas las publicaciones
	function obtenerComentarios($mysqli){

		$query="SELECT * 
				FROM publicaciones 
				ORDER BY id DESC LIMIT 10 ";
		$resultado=$mysqli->query($query)
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$result = null;
			
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
			
			$resultado->free();
		
		return $result;
	}
	
	//Obtiene las personas "cercanas"
	function obtenerPersonas($mysqli, $id){
		$query="SELECT  * 
				FROM usuarios 
				WHERE id != $id AND tipo LIKE 'usuario' ";
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$result =null;
			
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
			
			$resultado->free();
		
		return $result;
	}
			
	//Obtiene la conversacion
	function conversacion($mysqli, $yo, $el){
		$query="SELECT * 
				FROM mensajes 
				WHERE emisor = $yo AND receptor = $el OR emisor = $el AND receptor = $yo 
				ORDER BY id ";
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 

			$result = null;
			
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
			
			$resultado->free();
		
		return $result;
	}
	
	//Envia un pensaje 
	function subirMensaje ($mysqli, $emisor, $receptor, $mensaje){
		
		$query="INSERT INTO mensajes (emisor, receptor, texto)
			  VALUES ('".$emisor."', '".$receptor."', '".$mensaje."')";
					
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	
	//Publica algo en el tablon
	function subirPublicacion ($mysqli, $emisor, $mensaje){
		
		$query="INSERT INTO publicaciones (usuario, texto)
			  VALUES ('".$emisor."', '".$mensaje."')";
					
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
			






?>