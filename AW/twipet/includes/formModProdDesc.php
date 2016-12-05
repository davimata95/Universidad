<?php

require_once 'formlibModProducto.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModProdDesc($id, $estado) {
  formulario('modProdDesc', 'generaFormularioModProdDesc', 'procesaFormularioModProdDesc', $id, $estado);
}

function generaFormularioModProdDesc($datos, $id, $estado) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modProdDesc"> Modificar descripción </button>
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

function procesaFormularioModProdDesc($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	$estado = isset($params['estado']) ? $params['estado'] : null ;
	  
	  if ( !$valor  ) {
		$result[] = 'Rellene el campo descripción.';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ningún producto seleccionado</h3>";
		}
		else{

			
			modificaProducto($mysqli, 'descripcion', $valor, $id);
				
				
			echo "¡Descripción modificada!";
			
		}
  
  return $result;
  }
}

?>