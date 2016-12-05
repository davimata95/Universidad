<?php

require_once 'formlibModVet.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModVetTlf($id) {
  formulario('modVetTlf', 'generaFormularioModVetTlf', 'procesaFormularioModVetTlf', $id);
}

function generaFormularioModVetTlf($datos, $id) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modVetTlf"> Modificar teléfono </button>
					<input type="hidden" name="id" value="$id"/>
					
				</td>
				
				<td>
					<input type="text" name="valor" maxlength="9" size="30" required/> 										
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModVetTlf($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	  
	if ( ! $valor ||  strlen($valor) != 9 || !ctype_digit($valor) ) {
		$result[] = 'Rellene el campo teléfono correctamente (9 dígitos).';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ningún veterinario seleccionado</h3>";
		}
		else{

			modificaVeterinario ($mysqli, 'telefono', $valor, $id);

			echo "¡Teléfono modificado a: $valor!";

			
			
		}
	}
  
  return $result;
}

?>