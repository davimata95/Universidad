<?php

require_once 'formlibModProducto.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModProdCant($id, $estado) {
  formulario('modProdCant', 'generaFormularioModProdCant', 'procesaFormularioModProdCant', $id, $estado);
}

function generaFormularioModProdCant($datos, $id, $estado) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modProdCant"> Modificar cantidad </button>
					<input type="hidden" name="id" value="$id"/>
					<input type="hidden" name="estado" value="$estado"/>
				</td>
				
				<td>
					<input type="number" name="valor" min="0" max="10000" step="1" required/> Uds.
					
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModProdCant($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	$estado = isset($params['estado']) ? $params['estado'] : null ;
	  
	  if ( !$valor || !ctype_digit($valor)) {
		$result[] = 'Rellene correctamente el campo cantidad.';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ningún producto seleccionado</h3>";
		}
		else{

			
			modificaProducto($mysqli, 'cantidad', $valor, $id);
				
				
			echo "¡Cantidad modificada a: $valor Uds.!";
			
		}
  
  return $result;
  }
}

?>