<?php

require_once 'formlibModVet.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModVetDir($id) {
  formulario('modVetDir', 'generaFormularioModVetDir', 'procesaFormularioModVetDir', $id);
}

function generaFormularioModVetDir($datos, $id) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modVetDir"> Modificar dirección </button>
					<input type="hidden" name="id" value="$id"/>
					
				</td>
				
				<td>
					<input type="text" name="valor" maxlength="50" size="30" required/> 										
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModVetDir($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	  
	if ( ! $valor ) {
		$result[] = 'Rellene el campo dirección.';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ningún veterinario seleccionado</h3>";
		}
		else{

			modificaVeterinario ($mysqli, 'direccion', $valor, $id);

			echo "¡Dirección modificada a: $valor!";

		}
	}
  
  return $result;
}

?>