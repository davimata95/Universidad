<?php
	require_once '../includes/config.php';
	require RUTA_INCLUDES.'/funcionesMascotas.php';
	require RUTA_INCLUDES.'/funcionesTienda.php';
	
	$mysqli = Conexion::getConection();
	
	if($_GET['tipo']=='mascota'){

		modificaMascota($mysqli, 'estado', "subido", $_GET['id']);
	}
	
	if($_GET['tipo']=='producto'){
		
		confirmaProducto($mysqli, $_GET['id']);
	}
	
	if($_SESSION['tipoUsuario']=='admin'){
	
		header('Location: '.RUTA_USUARIOS.'/inicioAdmin.php');	
	} 
	if($_SESSION['tipoUsuario']=='usuario'){
	
		header('Location: '.RUTA_SOCIAL.'/misMascotas.php');	
	} 
	else if($_SESSION['tipoUsuario']=='veterinario'){
		
		header('Location: '.RUTA_USUARIOS.'/inicioVet.php');	
	}
	
?>