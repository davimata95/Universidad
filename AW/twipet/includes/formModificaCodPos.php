<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModCodPos() {
  formulario('modCodPos', 'generaFormularioModCodPos', 'procesaFormularioModCodPos');
}

function generaFormularioModCodPos($datos) {
  
  $html = <<<EOF
		<tr>					 
			<td>
				<button type="submit" name="modCodPos"> Modificar código postal </button>
			</td>
			
			<td>
				<input type="text" name="valor" maxlength="50" size="30" required/> 										
			</td>
		</tr>
EOF;
  return $html;
}

function procesaFormularioModCodPos($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	  
	if ( ! $valor ||  strlen($valor) != 5 || !ctype_digit($valor)) {
		$result[] = 'Rellene el campo código postal correctamente (5 dígitos).';
		$ok = FALSE;
	  }

  if ( $ok ) {
			
	$id = $_SESSION['id'];
		

	if(!isset ($id)){
		echo"<h3>No hay ningun usuario seleccionado</h3>";
	}
	else{

		hacerCambio($mysqli, 'codpostal', $valor, $id);
		
		echo "¡Código postal modificado a: $valor!";
	
		
	}
  }
  
  return $result;
}

?>