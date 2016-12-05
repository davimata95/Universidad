<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;
require_once RUTA_INCLUDES.'/funcionesForo.php'; 

function formularioForo() {
  formulario('foro', 'generaFormularioForo', 'procesaFormularioForo');
}

function generaFormularioForo($datos) {
  
  $html = <<<EOF
				<table>
					
					<tr>
						<td>Título: </td>
						<td><input type="text" name="titulo" required></td>
					</tr>
					<tr>
						<td>Mensaje: </td>
						<td><textarea name="mensaje" cols="90" rows="10" required></textarea></td>
					</tr>
				</table>
							
				<div id = "botonForo">
					<button type="submit" id="foro" name="foro" style="cursor:pointer" class="botonCabecera">Enviar mensaje</a>
				</div>
EOF;
  return $html;
}

function procesaFormularioForo($params) {
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	
  $titulo = isset($params['titulo']) ? $params['titulo'] : null ;
  $mensaje = isset($params['mensaje']) ? $params['mensaje'] : null ;
  
  
  if ( !$titulo ) {
    $result[] = 'Rellene el campo título.';
    $ok = FALSE;
  }
  if ( ! $mensaje ) {
    $result[] = 'Rellene el campo mensaje.';
    $ok = FALSE;
  }

  if ( $ok ) {

	$identificador = 0;
	
	$autor = $_SESSION["nombre"];
	
	$fecha = date("y-m-d");

	//Evitamos que el usuario ingrese HTML
	$mensaje = htmlentities($mensaje);
	
	//Grabamos el mensaje en la base de datos.
	
	$mysqli = Conexion::getConection();
	
	insertaForo ($mysqli, $autor, $titulo, $mensaje, $identificador, $fecha);
	
	Header("Location: foro.php");
	
  }
  return $result;
}

?>