<?php 

	require_once '../includes/config.php';
	require RUTA_INCLUDES.'/funcionesVet.php';
	
	$mysqli = Conexion::getConection();
	
	if ($_SESSION["tipoUsuario"]!="admin"){
		echo"<div id='restringido'> ";
			include RUTA_INCLUDES.'/comun/accesoRestringido.html';
		echo"</div>";
	}
	else{
		if(!isset ($_POST['id'])){
			echo"<h3>No hay ningun veterinario seleccionado</h3>";
		}
		else{
			$id=$_POST['id'];

			deleteVeterinario ($mysqli, $id);
				
			include (RUTA_INCLUDES.'/comun/ruta.php');
		}
	}

?>