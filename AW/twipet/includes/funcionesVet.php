<?php

	//Obtengo una lista con los vet de la BD
	function obtenerVeterinarios($mysqli){
		$query="SELECT *
				FROM veterinarios ";
		$resultado=$mysqli->query($query)  
				or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$result=NULL;
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
				
			$resultado->free();
		
		return $result;
	}	
	
	//Obtengo una lista con los vet de la BD por su codigo postal
	function obtenerVetCodigo($mysqli, $codigo){
		$query="SELECT *
				FROM veterinarios 
				WHERE codPostal = $codigo";
		$resultado=$mysqli->query($query)  
				or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$result=NULL;
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
				
			$resultado->free();
		
		return $result;
	}	

	//Obtengo el vet de la BD
	function obtenerVeterinario($mysqli, $id){
	$query="SELECT * 
			FROM veterinarios 
			WHERE id = $id ";
	$resultado=$mysqli->query($query)
		or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
		
		$registro = $resultado->fetch_assoc();		
		$resultado->free();
		
	return $registro;
	}
	
	//Sube la mascota a la BD
	function subirVeterinario($mysqli, $nombre, $horariosemanal, $horariofinde, $direccion, $telefono, $img, $cod){
	
		$query="INSERT INTO veterinarios ( nombre, horariosemanal, horariofinde, direccion, telefono, imagen, codPostal)
				  VALUES ('".$nombre."', '".$horariosemanal." ', '".$horariofinde."', 
						'".$direccion."', '".$telefono."', '".$img."', '".$cod."')";
						
			$resultado=$mysqli->query($query)  
				or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	
	function ultimoVeterinario ($mysqli){
		$query="SELECT Max(id) as mayor 
				FROM veterinarios";
				
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

	//Modifica un veterinario
	function modificaVeterinario ($mysqli, $cambio, $valor, $id){
		$query="UPDATE veterinarios 
				SET $cambio = '$valor' 
				WHERE id = $id";
				
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	
	//Elimina un veterinario de la BD
	function deleteVeterinario ($mysqli, $id){
		
		$query="DELETE FROM veterinarios 
				WHERE id = $id";
		
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}

?>