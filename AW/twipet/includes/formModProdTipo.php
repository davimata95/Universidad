<?php

require_once 'formlibModProducto.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModProdTipo($id, $estado) {
  formulario('modProdTipo', 'generaFormularioModProdTipo', 'procesaFormularioModProdTipo', $id, $estado);
}

function generaFormularioModProdTipo($datos, $id, $estado) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modProdTipo"> Modificar tipo </button>
					<input type="hidden" name="id" value="$id"/>
					<input type="hidden" name="estado" value="$estado"/>
				</td>
				
				<td>
					<select name="valor" required>
						<option selected disabled value="null">Selecciona el tipo</option>
						<option value="Comida">Comida</option>
						<option value="Juguete">Juguete</option>
						<option value="Accesorio">Accesorio</option>
					</select>
				</td>

			</tr>
EOF;
  return $html;
}

function procesaFormularioModProdTipo($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	$estado = isset($params['estado']) ? $params['estado'] : null ;
	  
	  if ( !$valor ) {
		$result[] = 'Seleccione un tipo de producto.';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ningún producto seleccionado</h3>";
		}
		else{

			
			modificaProducto($mysqli, 'tipo', $valor, $id);
				
				
			echo "¡Tipo de producto modificado a: $valor!";
			
		}
  
  return $result;
  }
}

?>