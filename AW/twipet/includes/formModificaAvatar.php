<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/funcionImagen.php') ;
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioModAvatar() {
  formulario('modAvatar', 'generaFormularioModAvatar', 'procesaFormularioModAvatar');
}

function generaFormularioModAvatar($datos) {
  
  $html = <<<EOF
			<tr>
				<td>
					<button type="submit" name="modAvatar"> Modificar foto de perfil</button>
				</td>
				<td>
					<input type="file" name="valor" required /> 
					
				</td>
			</tr>
EOF;
  return $html;
}

function procesaFormularioModAvatar($params) {
		
		
    $mysqli = Conexion::getConection();
		
    $result = array();
    $ok = TRUE;
		
	$valor = isset($params['valor']) ? $params['valor'] : null ;
	  
	if ( ! $valor || !esImagen($valor) ) {
		$result[] = 'Seleccione una foto de perfil válida.';
		$ok = FALSE;
	  }

  if ( $ok ) {
			
	$id = $_SESSION['id'];
		

	if(!isset ($id)){
		echo"<h3>No hay ningun usuario seleccionado</h3>";
	}
	else{

		//Si cambia alguno de estos campos, deberá cambiarse en la session
		
		$_SESSION['avatar']=$valor;
		
		hacerCambio($mysqli, 'avatar', $valor, $id);
		
		header('Location: '.RUTA_USUARIOS.'/modificaUsuario.php');
	}
  }
  
  return $result;
}

?>