<?php
	
	function obtenerForos($mysqli){
		//Obtengo el productos de la base de datos
		$query="SELECT  * 
				FROM foro 
				WHERE identificador = 0 
				ORDER BY fecha DESC";
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 

		$result = null;	
			
		while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
				
			$resultado->free();
		
		return $result;
	}

	function insertaForo ($mysqli, $autor, $titulo, $mensaje, $identificador, $fecha){
		$query = "INSERT INTO foro (autor, titulo, mensaje, identificador, fecha, ult_respuesta) 
					VALUES ('$autor', '$titulo', '$mensaje', '$identificador','$fecha','$fecha')";		
		$result = $mysqli->query($query)
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}

	function sumaRespuesta($mysqli, $identificador){
		$query="UPDATE foro 
				SET respuestas=respuestas+1
				WHERE ID = '$identificador'";
		$result = $mysqli->query($query)		
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	
	function obtenerPost($mysqli, $id){

		$query="SELECT * 
				FROM  foro 
				WHERE ID = '$id' 
				ORDER BY fecha DESC";
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
		
			$registro = $resultado->fetch_assoc();
			$resultado->free();
		
		return $registro;
	}
		
	function obtenerRespuestas($mysqli, $id){

		$query="SELECT * 
				FROM  foro 
				WHERE identificador = '$id' 
				ORDER BY fecha DESC";
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 

		$result = null;	
			
		while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
				
			$resultado->free();
		
		return $result;
	}
	

	
	
?>
	
	