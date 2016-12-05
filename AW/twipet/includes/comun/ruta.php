<?php

	require_once '../includes/config.php';

	if($_SESSION['tipoUsuario'] == 'admin'){
		header('Location: '.RUTA_USUARIOS.'/inicioAdmin.php');	
	}
	else if($_SESSION['tipoUsuario'] == 'veterinario'){
		header('Location: '.RUTA_USUARIOS.'/inicioVet.php');
	}
	else if($_SESSION['tipoUsuario'] == 'usuario'){
		header('Location: '.RUTA_SOCIAL.'/misMascotas.php');
	}

?>