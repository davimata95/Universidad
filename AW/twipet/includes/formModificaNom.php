<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModNom() {
  formulario('modNom', 'generaFormularioModNom', 'procesaFormularioModNom');
}

function generaFormularioModNom($datos) {
  
  $html = <<<EOF
			<tr>					 
			<td>
				<button type="submit" name="modNom"> Modificar nombre </button>
			</td>
			
			<td>
				<input type="text" name="valor" maxlength="50" size="30" required/> 										
			</td>
		</tr>
EOF;
  return $html;
}

function procesaFormularioModNom($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	  
	if ( ! $valor ) {
		$result[] = 'Rellene el campo contraseña.';
		$ok = FALSE;
	  }

  if ( $ok ) {
			
	$id = $_SESSION['id'];
		

	if(!isset ($id)){
		echo"<h3>No hay ningun usuario seleccionado</h3>";
	}
	else{

		$_SESSION['nombre']=$valor;

		hacerCambio($mysqli, 'nombre', $valor, $id);
		
		echo "¡Nombre modificado a: $valor!";
		
	}
  }
  
  return $result;
}

?>