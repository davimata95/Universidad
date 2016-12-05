<?php

require_once 'formlibModMascotas.php';
require_once (RUTA_INCLUDES.'/funcionImagen.php') ;
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModMasImg($id, $estado) {
  formulario('modMasImg', 'generaFormularioModMasImg', 'procesaFormularioModMasImg', $id, $estado);
}

function generaFormularioModMasImg($datos, $id, $estado) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modMasImg"> Modificar imagen</button>
					<input type="hidden" name="id" value="$id"/>
					<input type="hidden" name="estado" value="$estado"/>
				</td>
				<td>
					<input type="file" name="valor" required /> 
					
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModMasImg($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	$estado = isset($params['estado']) ? $params['estado'] : null ;
	  
	if ( ! $valor || !esImagen($valor) ) {
		$result[] = 'Seleccione una imagen válida.';
		$ok = FALSE;
	  }	

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ninguna mascota seleccionada</h3>";
		}
		else{

			modificaMascota ($mysqli, 'imagen', $valor, $id);

			mostrarMascota($mysqli, $id);
			
			header('Location: '.RUTA_MASCOTAS.'/modificarMascota.php?id='.$id.'');
		}
	}
  
  return $result;
}

?>