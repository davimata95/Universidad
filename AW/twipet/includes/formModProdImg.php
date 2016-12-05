<?php

require_once 'formlibModProducto.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;
require_once (RUTA_INCLUDES.'/funcionImagen.php') ;

function formularioModProdImg($id, $estado) {
  formulario('modProdImg', 'generaFormularioModProdImg', 'procesaFormularioModProdImg', $id, $estado);
}

function generaFormularioModProdImg($datos, $id, $estado) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modProdImg"> Modificar imagen</button>
					<input type="hidden" name="id" value="$id"/>
					<input type="hidden" name="estado" value="$estado"/>
				</td>
				<td>
					<input type="file" name="valor" required/> 
					
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModProdImg($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	$id = isset($params['id']) ? $params['id'] : null ;
	$estado = isset($params['estado']) ? $params['estado'] : null ;
	  
	  if ( !$valor  || !esImagen($valor)) {
		$result[] = 'Seleccione una foto de producto válida.';
		$ok = FALSE;
	  }

  if ( $ok ) {

		if(!isset ($id)){
			echo"<h3>No hay ningún producto seleccionado</h3>";
		}
		else{

			
			modificaProducto($mysqli, 'imagen', $valor, $id);
				
			header('Location: '.RUTA_TIENDA.'/modificarProducto.php?id='.$id.'');
			
		}
  
  return $result;
  }
}

?>