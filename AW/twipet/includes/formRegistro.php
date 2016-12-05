<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/funcionesLogin.php') ;
require_once (RUTA_INCLUDES.'/funcionImagen.php') ;
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioRegistro() {
  formulario('registro', 'generaFormularioRegistro', 'procesaFormularioRegistro');
}

function generaFormularioRegistro($datos) {
  
  $html = <<<EOF
				<table width="470px" height="500px">
					</tr>
					
					<tr>
						<td>
							<label for="rol">Rol:</label>
						</td>
						
						<td>
							<select name="rol" required>
							  <option selected disabled value="null">Seleccione un rol</option>
							  <option value="Usuario">Usuario</option>
							  <option value="Veterinario">Veterinario</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="nickname">Nickname:</label>
						</td>
						<td>
							<input type="text" name="nickname" maxlength="50" size="30" required>
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="password">Contraseña:</label>
						</td>
						<td>
							<input type="password" name="password" maxlength="50" size="30" required>
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="password2">Repite la contraseña:</label>
						</td>
						<td>
							<input type="password" name="password2" maxlength="50" size="30" required>
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="nombre">Nombre:</label>
						</td>
						<td>
							<input type="text" name="nombre" maxlength="50" size="30" required>
						</td>
					</tr>

					<tr>
						<td>
							<label for="apellidos">Apellidos:</label>
						</td>
						<td>
							<input type="text" name="apellidos" maxlength="50" size="30" required>
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="archivo">Foto de perfil:</label>
						</td>
						<td>
							<input type="file" name="archivo" id="archivo" required/>
						</td>
					</tr>

					<tr>
						<td>
							<label for="edad">Edad:</label>
						</td>
						<td>
							<input type="number" name="edad" min="0" max="10000" step="1" value="0" required/> años
						</td>
					</tr>
						
					<tr>
						<td>
							<label for="direccion">Dirección:</label>
						</td>
						<td>
							<input type="text" name="direccion" maxlength="50" size="30"required >
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="ciudad">Ciudad:</label>
						</td>
						<td>
							<input type="text" name="ciudad" maxlength="50" size="30" required>
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="codpostal">Código postal:</label>
						</td>
						<td>
							 <input type="number" name="codpostal" maxlength="5" required>
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="telefono">Teléfono:</label>
						</td>
						<td>
							<input type="text" name="telefono" maxlength="9" size="30" required>
						</td>
					</tr>

					<tr>
						<td>
							<label for="descripcion">Descripción:</label>
						</td>
						<td>
							<textarea name="descripcion" maxlength="1000" cols="30" rows="6" required></textarea>
						</td>
					</tr>

					<tr>
						<td colspan="2" style="text-align:center">
							<input type="submit" name="registro" class="botonCabecera" style="cursor:pointer" value="Confirmar">
							<a href="login.php" class="botonCabecera"> Cancelar </a>
						</td>
					</tr>

				</table>
EOF;
  return $html;
}

function procesaFormularioRegistro($params) {
		
  $mysqli = Conexion::getConection();
		
  $result = array();
  $ok = TRUE;
		
  $rol = isset($params['rol']) ? $params['rol'] : null ;
  $nickname = isset($params['nickname']) ? $params['nickname'] : null ;
  $password = isset($params['password']) ? $params['password'] : null ;
  $password2 = isset($params['password2']) ? $params['password2'] : null ;
  $nombre = isset($params['nombre']) ? $params['nombre'] : null ;
  $apellidos = isset($params['apellidos']) ? $params['apellidos'] : null ;
  $archivo = isset($params['archivo']) ? $params['archivo'] : null ;
  $edad = isset($params['edad']) ? $params['edad'] : null ;
  $direccion = isset($params['direccion']) ? $params['direccion'] : null ;
  $ciudad = isset($params['ciudad']) ? $params['ciudad'] : null ;
  $codPostal = isset($params['codpostal']) ? $params['codpostal'] : null ;
  $telefono = isset($params['telefono']) ? $params['telefono'] : null ;
  $descripcion = isset($params['descripcion']) ? $params['descripcion'] : null ;
  
  
  if ( !$rol  ) {
    $result[] = 'Selecciona un rol de usuario.';
    $ok = FALSE;
  }
  
  $registro=comprobarUsuario($mysqli, $nickname);
  
  if ( !$nickname  || $registro["nickname"]) {
	$result[] = 'El nickname de usuario está vacío o ya está ocupado.';
	$ok = FALSE;
  }

  if ( ! $password ||  strlen($password) < 4 ) {
    $result[] = 'La contraseña no es válida (4 dígitos o más).';
    $ok = FALSE;
  }
  
  if (  $password2 != $password ) {
    $result[] = 'Las contraseñas no coinciden.';
    $ok = FALSE;
  }
  
  if ( !$nombre  ) {
    $result[] = 'Rellene el campo nombre.';
    $ok = FALSE;
  }
  if ( !$apellidos  ) {
    $result[] = 'Rellene el campo apellidos.';
    $ok = FALSE;
  }
  if ( !$archivo || !esImagen($archivo) ) {
    $result[] = 'Seleccione una imagen válida.';
    $ok = FALSE;
  }
  if ( !$edad || !ctype_digit($edad) ) {
    $result[] = 'Rellene el campo edad correctamente.';
    $ok = FALSE;
  }
  if ( !$direccion  ) {
    $result[] = 'Rellene el campo direccion.';
    $ok = FALSE;
  }
  if ( !$ciudad  ) {
    $result[] = 'Rellene el campo ciudad.';
    $ok = FALSE;
  }
  if ( !$codPostal || strlen($codPostal) != 5 || !ctype_digit($codPostal) ) {
    $result[] = 'Rellene el campo código postal correctamente (5 dígitos).';
    $ok = FALSE;
  }
  if ( ! $telefono ||  strlen($telefono) != 9 || !ctype_digit($telefono) ) {
    $result[] = 'Rellene el campo teléfono correctamente (9 dígitos).';
    $ok = FALSE;
  }
  if ( !$descripcion  ) {
    $result[] = 'Rellene el campo descripción.';
    $ok = FALSE;
  }

  if ( $ok ) {

	$estado="pendiente";	

	$query="SELECT nickname
				FROM usuarios 
				WHERE nickname = '$nickname'";
				
	$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
	$usuarioValido=$resultado->num_rows;
	
	$password = md5($password);
	
		
	$query="INSERT INTO usuarios (nickname, password, nombre, apellidos, avatar, codPostal, edad, direccion, ciudad, telefono, descripcion, tipo)
	  VALUES ('".$nickname."', '".$password." ', '".$nombre."
			', '".$apellidos."', '".$archivo."', '".$codPostal."', '".$edad."
			', '".$direccion."', '".$ciudad."', '".$telefono."
			', '".$descripcion."', '".$rol."')";
			
	$resultado=$mysqli->query($query)  
		or die ($mysqli->error. " en la línea ".(__LINE__-1));
	
	header('Location: login.php');

			
	}
	 
  return $result;
}

?>