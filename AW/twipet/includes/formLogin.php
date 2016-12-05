<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/funcionesLogin.php') ;
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioLogin() {
  formulario('login', 'generaFormularioLogin', 'procesaFormularioLogin');
}

function generaFormularioLogin($datos) {
  
  $html = <<<EOF
				<table width="450px" height="250px">
					<tr>
						<td>
							<label for="usuario">Nickname *</label>
						</td>
						
						<td>
							<input type="text" name="usuario" id="usuario">
						 </td>
					</tr>
					
					<tr>
						<td>
							<label for="password">Password *</label>
						</td>
						<td>
							<input type="password" name ="password" id="usuario">
						</td>
					</tr>
					
					<tr>
						<td colspan="2" style="text-align:center">
							<input type="submit" name="login" class="botonCabecera" style="cursor:pointer" value="Log In">
							<a href="registro.php" class="botonCabecera"> Registro </a>
						</td>
					</tr>
				</table>
EOF;
  return $html;
}

function procesaFormularioLogin($params) {
		
  $mysqli = Conexion::getConection();
		
  $result = array();
  $ok = TRUE;
		

  $user = isset($params['usuario']) ? $params['usuario'] : null ;
  $pass = isset($params['password']) ? $params['password'] : null ;
  
  $usuario=comprobarUsuario($mysqli, $user);
  
  if ( !$user || !$usuario["nickname"] ) {
    $result[] = 'El nombre de usuario no es válido.';
    $ok = FALSE;
  }
  
  $pass = md5($pass);
  $registro=obtenUsuario($mysqli, $user, $pass);
  
  if ( ! $pass ||  $pass == !$registro["password"]) {
    $result[] = 'La contraseña no es válida';
    $ok = FALSE;
  }

  if ( $ok ) {

	if (isset($registro)){
		
		// SEGURIDAD: Forzamos que se genere una nueva cookie de sesión por si la han capturado antes de hacer login
		session_regenerate_id(true);
		
		//Establecemos los session 
		$_SESSION["usuario"] = $registro["nickname"];
		$_SESSION["id"] = $registro["id"];
		$_SESSION["tipoUsuario"] = $registro["tipo"];
		$_SESSION["login"] = true;
		$_SESSION["avatar"] = $registro["avatar"];
		$_SESSION["nombre"] = $registro["nombre"];
		$_SESSION["apellidos"] = $registro["apellidos"];
		
		$result = accion();
		
	} 
	}
  return $result;
}

function accion() {
	if($_SESSION['tipoUsuario'] == 'admin'){
			$result=RUTA_USUARIOS.'/inicioAdmin.php';	
		}
		else if($_SESSION['tipoUsuario'] == 'veterinario'){
			$result=RUTA_USUARIOS.'/inicioVet.php';
		}
		else if($_SESSION['tipoUsuario'] == 'usuario'){
			$result = RUTA_SOCIAL.'/social_miPerfil.php';
		}
		return $result;
}

?>