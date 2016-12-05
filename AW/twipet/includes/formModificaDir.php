<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;



function formularioModDir() {
  formulario('modDir', 'generaFormularioModDir', 'procesaFormularioModDir');
}

function generaFormularioModDir($datos) {
  
  $html = <<<EOF
		<tr>					 
			<td>
				<button type="submit" name="modDir"> Modificar dirección </button>
			</td>
			
			<td>
				<input type="text" name="valor" maxlength="50" size="30" required/> 										
			</td>
		</tr>
EOF;
  return $html;
}

function procesaFormularioModDir($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	  
	if ( ! $valor  ) {
		$result[] = 'Rellene el campo dirección.';
		$ok = FALSE;
	  }

  if ( $ok ) {
			
	$id = $_SESSION['id'];
		

	if(!isset ($id)){
		echo"<h3>No hay ningun usuario seleccionado</h3>";
	}
	else{

		hacerCambio($mysqli, 'direccion', $valor, $id);
		
		echo "¡Dirección modificada a: $valor!";
		
	}
  }
  
  return $result;
}


?>