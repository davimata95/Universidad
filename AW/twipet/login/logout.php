<?php
	require_once ('../includes/config.php') ;
	
	unset($_SESSION["usuario"]);
	unset($_SESSION["login"]);
	unset($_SESSION["id"]);
	unset($_SESSION["tipoUsuario"]);
	unset($_SESSION["nombre"]);
	unset($_SESSION["apellidos"]);
	unset($_SESSION["avatar"]);

	header('Location: '.RUTA_LOGIN.'/login.php');
?>