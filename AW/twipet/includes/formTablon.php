<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;
require_once RUTA_INCLUDES.'/funcionesSocial.php'; 

function formularioTablon() {
  formulario('tablon', 'generaFormularioTablon', 'procesaFormularioTablon');
}

function generaFormularioTablon($datos) {
  
  $html = <<<EOF
			<textarea name="mensaje" rows = "4" cols = "83" required></textarea>
			<input type="submit" name="tablon" id="boton-enviar" value="Enviar" />
EOF;
  return $html;
}

function procesaFormularioTablon($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$mensaje = isset($params['mensaje']) ? $params['mensaje'] : null ;
	  
	if ( ! $mensaje ) {
		$result[] = 'Rellene el campo mensaje.';
		$ok = FALSE;
	  }

  if ( $ok ) {
			
	$emisor = $_SESSION['id'];
		

	subirPublicacion ($mysqli, $emisor, $mensaje);
	
	//Necesario para que al actualizar la página no se vuelva a subir el mensaje anterior
	
	header('Location: social_tablon.php');

  }
  
  return $result;
}

?>