<?php

	

	//Comprueba el inicio de sesión
	session_start();
	
	//Definimos unas rutas para facilitar la interacción entre carpetas
	define('RAIZ_APP', __DIR__);
	
	define('RUTA_APP', '/twipet');
	define('RUTA_IMG', RUTA_APP.'/img');
	define('RUTA_CSS', RUTA_APP.'/css');
	define('RUTA_MASCOTAS', RUTA_APP.'/mascotas');
	define('RUTA_TIENDA', RUTA_APP.'/tienda');
	define('RUTA_SOCIAL', RUTA_APP.'/social');
	define('RUTA_USUARIOS', RUTA_APP.'/usuarios');
	define('RUTA_LOGIN', RUTA_APP.'/login');
	define('RUTA_INCLUDES', __DIR__);
	define('RUTA_FORO', RUTA_APP.'/foro');
	define('RUTA_VETERINARIOS', RUTA_APP.'/veterinarios');
	
	// Hay que quitarlo 
	require_once (RUTA_INCLUDES.'/Conexion.php') ;

	//Conexión con la base de datos
	define('hostname','localhost');
	define('usuario', 'twipet');
	define('password', 'admin');
	define('baseDatos', 'twipet');
	
	$conexion = new Conexion( hostname, usuario, password, baseDatos );
?>
