<?php
	require_once '../includes/config.php';
	require RUTA_INCLUDES.'/funcionesMascotas.php';

	$id=$_POST['id'];
	$nuevoP = $_SESSION['id'];
	
    $mysqli = Conexion::getConection();
	
	if (!isset ($_SESSION["login"])){
		echo"<div id='restringido'> ";
			include RUTA_INCLUDES.'/comun/accesoRestringido.html';
		echo"</div>";
	}
	else{
		if(!isset ($_POST['id'])){
			echo"<div id='restringido'> ";
				echo"<h3>No hay ninguna mascota seleccionado</h3>";
			echo"</div>";
		}
		else {		
			adoptaMascota($mysqli, $_POST['id'], $nuevoP);
		}
		
		include (RUTA_INCLUDES.'/comun/ruta.php');
		
	}
?>