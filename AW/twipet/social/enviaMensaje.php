<?php

	require_once ('../includes/config.php') ;
	require_once RUTA_INCLUDES.'/funcionesSocial.php'; 
	
	$mysqli = Conexion::getConection();
		
	if (empty ($_SESSION["login"])){
		echo"<div id='restringido'> ";
			include RUTA_INCLUDES.'/comun/accesoRestringido.html';
		echo"</div>";
	}	
	else{
		$mensaje = $_POST['mensaje'];	
		$cambio = $_POST['cambio'];
		$emisor = $_SESSION['id'];
			
		$receptor = $_POST['receptor'];
			
		subirMensaje ($mysqli, $_SESSION["id"], $receptor, $mensaje);

		header('Location: mensajes.php?con='.$receptor.'');
	}
?>