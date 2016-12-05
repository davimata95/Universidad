<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModDesc() {
  formulario('modDesc', 'generaFormularioModDesc', 'procesaFormularioModDesc');
}

function generaFormularioModDesc($datos) {
  
  $html = <<<EOF
		<tr>
			<td>
				<button type="submit" name="modDesc"> Modificar descripción </button>
			</td>
			
			<td>
				<textarea name="valor" maxlength="1000" cols="30" rows="6" required></textarea>
				
			</td>
		</tr>
EOF;
  return $html;
}

function procesaFormularioModDesc($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	  
	if ( ! $valor ) {
		$result[] = 'Rellene el campo descripción.';
		$ok = FALSE;
	  }

  if ( $ok ) {
			
	$id = $_SESSION['id'];
		

	if(!isset ($id)){
		echo"<h3>No hay ningun usuario seleccionado</h3>";
	}
	else{

		hacerCambio($mysqli, 'descripcion', $valor, $id);
		
		echo "¡Descripción modificada!";
				
	}
  }
  
  return $result;
}

?>