<?php

require_once 'formlibModProducto.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModProdPrecio($id, $estado) {
  formulario('modProdPrecio', 'generaFormularioModProdPrecio', 'procesaFormularioModProdPrecio', $id, $estado);
}

function generaFormularioModProdPrecio($datos, $id, $estado) {
  
  $html = <<<EOF
			 	<tr>
					<td>
						<button type="submit" name="modProdPrecio"> Modificar precio </button>
						 <input type="hidden" name="id" value="$id"/>
						<input type="hidden" name="estado" value="$estado"/>
					</td>
					
					<td>
						<input type="number" name="valor" min="0" max="10000" step="0.10" required/> €
						
					</td>
				</tr>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModProdPrecio($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	$estado = isset($params['estado']) ? $params['estado'] : null ;
	  
	if ( !$valor || !is_numeric($valor) ) {
		$result[] = 'Rellene correctamente el campo precio.';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ningún producto seleccionado</h3>";
		}
		else{

			
			modificaProducto($mysqli, 'precio', $valor, $id);
				
				
			echo "¡Precio modificado a: $valor €!";
			
		}
  
  return $result;
  }
}

?>