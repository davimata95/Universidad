<?php

require_once 'formlibModMascotas.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModMasDesc($id, $estado) {
  formulario('modMasDesc', 'generaFormularioModMasDesc', 'procesaFormularioModMasDesc', $id, $estado);
}

function generaFormularioModMasDesc($datos, $id, $estado) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modMasDesc"> Modificar descripción </button>
					<input type="hidden" name="id" value="$id"/>
					<input type="hidden" name="estado" value="$estado"/>
				</td>
				
				<td>
					<textarea name="valor" maxlength="1000" cols="30" rows="6" required></textarea>
					
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModMasDesc($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	$estado = isset($params['estado']) ? $params['estado'] : null ;
	  
	if ( ! $valor ) {
		$result[] = 'Rellene el campo descripción.';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ninguna mascota seleccionada</h3>";
		}
		else{

			modificaMascota ($mysqli, 'descripcion', $valor, $id);

			mostrarMascota($mysqli, $id);
			
			echo "¡Descripción modificada!";

		}
	}
  
  return $result;
}

?>