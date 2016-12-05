<?php

require_once 'formlibModMascotas.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModMasEdad($id, $estado) {
  formulario('modMasEdad', 'generaFormularioModMasEdad', 'procesaFormularioModMasEdad', $id, $estado);
}

function generaFormularioModMasEdad($datos, $id, $estado) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modMasEdad"> Modificar edad </button>
					<input type="hidden" name="id" value="$id"/>
					<input type="hidden" name="estado" value="$estado"/>
				</td>
				
				<td>
					<input type="number" name="valor" min="0" max="10000" step="1" required /> años
					
				</td>

			</tr>
EOF;
  return $html;
}

function procesaFormularioModMasEdad($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	$estado = isset($params['estado']) ? $params['estado'] : null ;
	  
	if ( ! $valor || !ctype_digit($valor )) {
		$result[] = 'Rellena el campo edad.';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ninguna mascota seleccionada</h3>";
		}
		else{

			modificaMascota ($mysqli, 'edad', $valor, $id);

			mostrarMascota($mysqli, $id);
			
			echo "¡Edad modificada a: $valor años!";
		
		}
	}
  
  return $result;
}

?>