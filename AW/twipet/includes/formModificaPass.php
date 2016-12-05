<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModPass() {
  formulario('modPass', 'generaFormularioModPass', 'procesaFormularioModPass');
}

function generaFormularioModPass($datos) {
  
  $html = <<<EOF
		<tr>
			<td>
				<button type="submit" name="modPass"> Modificar contraseña </button>
			</td>
			
			<td>
				<input type="text" name="valor" maxlength="50" size="30" required/> 										
			</td>
		</tr>
EOF;
  return $html;
}

function procesaFormularioModPass($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	  
	if ( ! $valor ||  strlen($valor) < 4 ) {
		$result[] = 'Rellene el campo contraseña.';
		$ok = FALSE;
	  }

  if ( $ok ) {
			
	$id = $_SESSION['id'];
		

	if(!isset ($id)){
		echo"<h3>No hay ningun usuario seleccionado</h3>";
	}
	else{
		
		$valorMd5 = md5($valor);
		
		hacerCambio($mysqli, 'password', $valorMd5, $id);
		
		echo "¡Contraseña modificada a: $valor!";
		
	}
  }
  
  return $result;
}

?>