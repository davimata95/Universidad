<?php
	
	function obtenUsuario($mysqli, $usuario, $password){
		

		//Comprueba que haya un usuario con esa contraseña
		$query="SELECT nickname, nombre, apellidos, id, tipo, password, avatar
				FROM usuarios 
				WHERE nickname = '$usuario' AND password = '$password'";
				
		$resultado=$mysqli->query($query)  
				or die ($mysqli->error. " en la línea ".(__LINE__-1)); 

		$usuario=$resultado->fetch_assoc();
		
		$resultado->free();
		
		return $usuario;
	}
	
	function comprobarUsuario($mysqli, $nickname) {
			
			//Comprueba que haya un usuario con esa contraseña
		$query="SELECT nickname
				FROM usuarios 
				WHERE nickname = '$nickname'";
				
		$resultado=$mysqli->query($query)  
				or die ($mysqli->error. " en la línea ".(__LINE__-1)); 

		$usuario=$resultado->fetch_assoc();
		
		$resultado->free();
		
		return $usuario;
			
	}
	

?>