<?php

require_once 'formlibModMascotas.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModMasEst($id, $estado) {
  formulario('modMasEst', 'generaFormularioModMasEst', 'procesaFormularioModMasEst', $id, $estado);
}

function generaFormularioModMasEst($datos, $id, $estado) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modMasEst"> Modificar estado </button>
					<input type="hidden" name="id" value="$id"/>
					<input type="hidden" name="estado" value="$estado"/>
				</td>
				
				<td>
					<select name="valor" required>
						<option selected disabled value="null">Selecciona el estado</option>
						<option value="subido">Poner en adopción</option>
						<option value="no">No poner en adopción</option>
					</select>
					
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModMasEst($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	$estado = isset($params['estado']) ? $params['estado'] : null ;
	  
	if ( ! $valor ) {
		$result[] = 'Seleccione un estado.';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ninguna mascota seleccionada</h3>";
		}
		else{

			modificaMascota ($mysqli, 'estado', $valor, $id);

			mostrarMascota($mysqli, $id);
			
			echo "¡Estado modificado a: $valor!";
	
		}
	}
  
  return $result;
}

?>