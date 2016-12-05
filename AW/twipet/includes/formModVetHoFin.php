<?php

require_once 'formlibModVet.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModVetHoFin($id) {
  formulario('modVetHoFin', 'generaFormularioModVetHoFin', 'procesaFormularioModVetHoFin', $id);
}

function generaFormularioModVetHoFin($datos, $id) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modVetHoFin"> Modificar horario finde </button>
					<input type="hidden" name="id" value="$id"/>
				</td>
				
				<td>
					<input type="text" name="valor" maxlength="50" size="30" required/> 										
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModVetHoFin($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;

	  
	if ( ! $valor ) {
		$result[] = 'Rellene el campo horario fin de semana.';
		$ok = FALSE;
	  }

  if ( $ok ) {

	if(!isset ($id)){
			echo"<h3>No hay ningún veterinario seleccionado</h3>";
		}
	else{

		modificaVeterinario ($mysqli, 'horariofinde', $valor, $id);
		
		echo "¡Horario finde modificado a: $valor!";


	}
	}
  
  return $result;
}

?>