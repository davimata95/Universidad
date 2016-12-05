<?php

require_once 'formlibModVet.php';
require_once (RUTA_INCLUDES.'/funcionImagen.php') ;
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModVetImg($id) {
  formulario('modVetImg', 'generaFormularioModVetImg', 'procesaFormularioModVetImg', $id);
}

function generaFormularioModVetImg($datos, $id) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modVetImg"> Modificar imagen</button>
					<input type="hidden" name="id" value="$id"/>
				</td>
				<td>
					<input type="file" name="valor" required /> 
					
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModVetImg($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	  
	if ( ! $valor || !esImagen($valor) ) {
		$result[] = 'Seleccione una imagen válida.';
		$ok = FALSE;
	  }	

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ningún veterinario seleccionado</h3>";
		}
		else{

			modificaVeterinario ($mysqli, 'imagen', $valor, $id);

			header('Location: '.RUTA_VETERINARIOS.'/modificarVeterinario.php?id='.$id.'');
			
			
		}
	}
  
  return $result;
}

?>