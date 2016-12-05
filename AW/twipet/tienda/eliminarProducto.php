<?php 

	require_once '../includes/config.php';
	require RUTA_INCLUDES.'/funcionesTienda.php';
	
	$mysqli = Conexion::getConection();
	
	if ((!isset ($_SESSION["login"])) || $_SESSION["tipoUsuario"]=='usuario'){
		echo"<div id='restringido'> ";
			include RUTA_INCLUDES.'/comun/accesoRestringido.html';
		echo"</div>";
	}
	else{
		if(!isset ($_POST['id'])){
			echo"<h3>No hay ningun producto seleccionado</h3>";
		}
		else{

			eliminaProducto($mysqli, $_POST['id']);
				
			include (RUTA_INCLUDES.'/comun/ruta.php'); 
		}
	}

?>