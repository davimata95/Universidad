<?php

	require_once ('includes/config.php') ;

	if(isset($_SESSION["login"])){
		if ($_SESSION["tipoUsuario"]=="admin"){
			header('Location: '.RUTA_USUARIOS.'/inicioAdmin.php');
		}
		else if ($_SESSION["tipoUsuario"]=="usuario"){
			header('Location: '.RUTA_SOCIAL.'/social_miPerfil.php');
		}
		else if ($_SESSION["tipoUsuario"]=="veterinario"){
			header('Location: '.RUTA_USUARIOS.'/inicioVet.php');
		}
	}
	else{
		header('Location: '.RUTA_LOGIN.'/login.php');
	}
	

?>