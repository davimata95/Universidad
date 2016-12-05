<?php

require_once 'formlibModMascotas.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModMasSex($id, $estado) {
  formulario('modMasSex', 'generaFormularioModMasSex', 'procesaFormularioModMasSex', $id, $estado);
}

function generaFormularioModMasSex($datos, $id, $estado) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modMasSex"> Modificar sexo </button>
					<input type="hidden" name="id" value="$id"/>
					<input type="hidden" name="estado" value="$estado"/>
				</td>
				
				<td>
					<input type="radio" name="valor" value="Masculino"  required/> Masculino
					<input type="radio" name="valor" value="Femenino" required /> Femenino
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModMasSex($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	$estado = isset($params['estado']) ? $params['estado'] : null ;
	  
	if ( ! $valor ) {
		$result[] = 'Seleccione un sexo.';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ninguna mascota seleccionada</h3>";
		}
		else{

			modificaMascota ($mysqli, 'sexo', $valor, $id);

			mostrarMascota($mysqli, $id);
			
			echo "¡Sexo modificado a: $valor!";
			
		}
	}
  
  return $result;
}

?>