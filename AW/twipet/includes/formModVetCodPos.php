<?php

require_once 'formlibModVet.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModVetCodPos($id) {
  formulario('modVetCodPos', 'generaFormularioModVetCodPos', 'procesaFormularioModVetCodPos', $id);
}

function generaFormularioModVetCodPos($datos, $id) {
  
  $html = <<<EOF
		<tr>					 
			<td>
				<button type="submit" name="modVetCodPos"> Modificar código postal </button>
				<input type="hidden" name="id" value="$id"/>
			</td>
			
			<td>
				<input type="number" name="valor" maxlength="5" required>									
			</td>
		</tr>
EOF;
  return $html;
}

function procesaFormularioModVetCodPos($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	
	  
	if ( ! $valor ||  strlen($valor) != 5 || !ctype_digit($valor)) {
		$result[] = 'Rellene el campo código postal correctamente (5 dígitos).';
		$ok = FALSE;
	  }

  if ( $ok ) {
		

	if(!isset ($id)){
			echo"<h3>No hay ningún veterinario seleccionado</h3>";
		}
	else{

		modificaVeterinario ($mysqli, 'codPostal', $valor, $id);
		
		echo "¡Código postal modificado a: $valor!";

	}
  }
  
  return $result;
}

?>