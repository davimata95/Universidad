<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModTlf() {
  formulario('modTlf', 'generaFormularioModTlf', 'procesaFormularioModTlf');
}

function generaFormularioModTlf($datos) {
  
  $html = <<<EOF
		<tr>					 
			<td>
				<button type="submit" name="modTlf"> Modificar teléfono </button>
			</td>
			
			<td>
				<input type="text" name="valor" maxlength="9" size="30" required/> 										
			</td>
		</tr>
EOF;
  return $html;
}

function procesaFormularioModTlf($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	  
	if ( ! $valor ||  strlen($valor) != 9 || !ctype_digit($valor) ) {
		$result[] = 'Rellene el campo teléfono correctamente (9 dígitos).';
		$ok = FALSE;
	  }

  if ( $ok ) {
			
	$id = $_SESSION['id'];
		

	if(!isset ($id)){
		echo"<h3>No hay ningun usuario seleccionado</h3>";
	}
	else{

		hacerCambio($mysqli, 'telefono', $valor, $id);
		
		echo "¡Teléfono modificado a: $valor!";
		
		
	}
  }
  
  return $result;
}

?>