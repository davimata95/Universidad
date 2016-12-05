<?php

require_once 'formlibModVet.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModVetNom($id) {
  formulario('modVetNom', 'generaFormularioModVetNom', 'procesaFormularioModVetNom', $id);
}

function generaFormularioModVetNom($datos, $id) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modVetNom"> Modificar nombre </button>
					<input type="hidden" name="id" value="$id"/>
					
				</td>
				
				<td>
					<input type="text" name="valor" maxlength="50" size="30" required/> 										
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModVetNom($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	  
	if ( ! $valor ) {
		$result[] = 'Rellene el campo nombre.';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ningún veterinario seleccionado</h3>";
		}
		else{

			modificaVeterinario ($mysqli, 'nombre', $valor, $id);
			
			echo "¡Nombre modificado a: $valor!";

		}
	}
  
  return $result;
}

?>