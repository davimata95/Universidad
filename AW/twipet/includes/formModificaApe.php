<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModApe() {
  formulario('modApe', 'generaFormularioModApe', 'procesaFormularioModApe');
}

function generaFormularioModApe($datos) {
  
  $html = <<<EOF
			<tr>					 
			<td>
				<button type="submit" name="modApe"> Modificar apellidos </button>
			</td>
			
			<td>
				<input type="text" name="valor" maxlength="50" size="30" required/> 										
			</td>
		</tr>
EOF;
  return $html;
}

function procesaFormularioModApe($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	  
	if ( ! $valor ) {
		$result[] = 'Rellene el campo apellidos.';
		$ok = FALSE;
	  }

  if ( $ok ) {
			
	$id = $_SESSION['id'];
		

	if(!isset ($id)){
		echo"<h3>No hay ningun usuario seleccionado</h3>";
	}
	else{


		$_SESSION['apellidos']=$valor;

		hacerCambio($mysqli, 'apellidos', $valor, $id);
		
		echo "¡Apellidos modificados a: $valor!";
		
		
	}
  }
  
  return $result;
}

?>