<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModEdad() {
  formulario('modEdad', 'generaFormularioModEdad', 'procesaFormularioModEdad');
}

function generaFormularioModEdad($datos) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modEdad"> Modificar edad </button>
				</td>
				
				<td>
					<input type="number" name="valor" min="0" max="500" step="1" required /> años
					
				</td>

			</tr>
EOF;
  return $html;
}

function procesaFormularioModEdad($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	  
	if ( ! $valor || !ctype_digit($valor ) ){
		$result[] = 'Rellene el campo edad.';
		$ok = FALSE;
	  }

  if ( $ok ) {
			
	$id = $_SESSION['id'];
		

	if(!isset ($id)){
		echo"<h3>No hay ningun usuario seleccionado</h3>";
	}
	else{
		
		
		hacerCambio($mysqli, 'edad', $valor, $id);
		
		echo "¡Edad modificada a: $valor años!";
	
	}
  }
  
  return $result;
}

?>