<?php 

	require_once '../includes/config.php';
	require RUTA_INCLUDES.'/funcionesMascotas.php';
	
	$mysqli = Conexion::getConection();
	
	if (!isset ($_SESSION["login"])){
		echo"<div id='restringido'> ";
			include RUTA_INCLUDES.'/comun/accesoRestringido.html';
		echo"</div>";
	}
	else{
		if(!isset ($_POST['id'])){
			echo"<h3>No hay ninguna mascota seleccionada</h3>";
		}
		else{
			$id=$_POST['id'];

			deleteMascota ($mysqli, $id);
				
			include (RUTA_INCLUDES.'/comun/ruta.php');
		}
	}

?>