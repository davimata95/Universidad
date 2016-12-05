<?php

require_once 'formlibModProducto.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModProdNom($id, $estado) {
  formulario('modProdNom', 'generaFormularioModProdNom', 'procesaFormularioModProdNom', $id, $estado);
}

function generaFormularioModProdNom($datos, $id, $estado) {
  
  $html = <<<EOF
			<tr>					 
				<td>
					<button type="submit" name="modProdNom"> Modificar nombre </button>
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

function procesaFormularioModProdNom($params) {
		
		
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
			echo"<h3>No hay ningún producto seleccionado</h3>";
		}
		else{

			
			modificaProducto($mysqli, 'nombre', $valor, $id);
				
				
			echo "¡Nombre modificado a: $valor!";
			
		}
  
  return $result;
  }
}

?>