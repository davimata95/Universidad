<?php

require_once 'formlibModMascotas.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModMasRaza($id, $estado) {
  formulario('modMasRaza', 'generaFormularioModMasRaza', 'procesaFormularioModMasRaza', $id, $estado);
}

function generaFormularioModMasRaza($datos, $id, $estado) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modMasRaza"> Modificar raza </button>
					 <input type="hidden" name="id" value="$id"/>
					 <input type="hidden" name="estado" value="$estado"/>
				</td>
				
				<td>
					<select name="valor" required>
						<option selected disabled value="null">Selecciona la raza</option>
						<option value="Bulldog">Bulldog</option>
						<option value="Husky">Husky</option>
						<option value="Mestizo">Mestizo</option>
					</select>
					
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModMasRaza($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	$estado = isset($params['estado']) ? $params['estado'] : null ;
	  
	if ( ! $valor ) {
		$result[] = 'Seleccione una raza.';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ninguna mascota seleccionada</h3>";
		}
		else{

			modificaMascota ($mysqli, 'raza', $valor, $id);

			mostrarMascota($mysqli, $id);
			
			echo "¡Raza modificada a: $valor!";
			
		}
	}
  
  return $result;
}

?>