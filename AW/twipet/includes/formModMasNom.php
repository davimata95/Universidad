<?php

require_once 'formlibModMascotas.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModMasNom($id, $estado) {
  formulario('modMasNom', 'generaFormularioModMasNom', 'procesaFormularioModMasNom', $id, $estado);
}

function generaFormularioModMasNom($datos, $id, $estado) {
  
  $html = <<<EOF
			<tr>					 
				<td>
					<button type="submit" name="modMasNom"> Modificar nombre </button>
					<input type="hidden" name="id" value="$id"/>
					<input type="hidden" name="estado" value="$estado"/>
				</td>
				
				<td>
					<input type="text" name="valor" maxlength="50" size="30" required/> 										
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModMasNom($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	$estado = isset($params['estado']) ? $params['estado'] : null ;
	  
	if ( ! $valor ) {
		$result[] = 'Rellene el campo nombre.';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ninguna mascota seleccionada</h3>";
		}
		else{

			modificaMascota ($mysqli, 'nombre', $valor, $id);

			mostrarMascota($mysqli, $id);
			
			echo "¡Nombre modificado a: $valor!";
		
		}
	}
  
  return $result;
}

?>