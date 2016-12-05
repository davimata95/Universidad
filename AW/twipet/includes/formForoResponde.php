<?php

require_once 'formlibRespForo.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;
require_once RUTA_INCLUDES.'/funcionesForo.php'; 


function formularioForoRespuesta($id) {
  formulario('r_foro', 'generaFormularioForoRespuesta', 'procesaFormularioForoRespuesta', $id);
}

function generaFormularioForoRespuesta($datos, $id) {
	
  
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
				
				<input type="hidden" name="id" value="$id"/>
							
				<div id = "botonForo">
					<button type="submit" name="r_foro" style="cursor:pointer" class="botonCabecera">Enviar mensaje</button>
				</div>
EOF;
  return $html;
}

function procesaFormularioForoRespuesta($params) {
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$id = $identificador;
	
   $titulo = isset($params['titulo']) ? $params['titulo'] : null ;
   $mensaje = isset($params['mensaje']) ? $params['mensaje'] : null ;
   $id = isset($params['id']) ? $params['id'] : null ;
  
  
  if ( !$titulo ) {
    $result[] = 'Rellene el campo título.';
    $ok = FALSE;
  }
  if ( ! $mensaje ) {
    $result[] = 'Rellene el campo mensaje.';
    $ok = FALSE;
  }

  if ( $ok ) {
	  
	
	$autor = $_SESSION["nombre"];
	
	$fecha = date("y-m-d");

	//Evitamos que el usuario ingrese HTML
	$mensaje = htmlentities($mensaje);
	
	//Grabamos el mensaje en la base de datos.
	
	$mysqli = Conexion::getConection();
	
	insertaForo ($mysqli, $autor, $titulo, $mensaje, $id, $fecha);
	
	/* si es un mensaje en respuesta a otro actualizamos los datos */
	
	sumaRespuesta($mysqli, $id);

	Header("Location: comprobarForo.php?id=$id");
	
  }
  return $result;
}

?>