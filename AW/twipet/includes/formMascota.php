<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;
require_once (RUTA_INCLUDES.'/funcionImagen.php') ;
require RUTA_INCLUDES.'/funcionesMascotas.php';

function formularioMascota() {
  formulario('mascota', 'generaFormularioMascota', 'procesaFormularioMascota');
}

function generaFormularioMascota($datos) {
  
  $html = <<<EOF
				<table width="470px" height="500px">
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
								<label for="raza">Raza:</label>
							</td>
							
							<td>
								<select name="raza" >
									<option selected disabled value="null">Selecciona la raza</option>
									<option value="Bulldog">Bulldog</option>
									<option value="Husky">Husky</option>
									<option value="Mestizo">Mestizo</option>
								
								</select>
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
								<label for="sexo">Sexo:</label>
							</td>
							
							<td>
								<input type="radio" name="sexo" value="Masculino" required /> Masculino
								</br>
								<input type="radio" name="sexo" value="Femenino" required /> Femenino
							</td>
						</tr>
						
						<tr>
							<td>
								<label for="img">Foto perfil:</label>
							</td>
							<td>
								<input type="file" name="img" required/> 
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
							<td>
								<label for="adoptar">Poner en adopción:</label>
							</td>
							
							<td>
								<select name="adoptar" required>
								  <option selected disabled value="null">--</option>
								  <option value="pendiente">Poner en adopción</option>
								  <option value="no">No poner en adopción</option>				
							</td>
						</tr>
	
					</table>
					
					<input type="submit" name= "mascota" class="botonCabecera" style="cursor:pointer" value="Confirmar">
				
					<?php include (RUTA_INCLUDES.'/comun/link.php'); ?>
EOF;
  return $html;
}

function procesaFormularioMascota($params) {
	
	$mysqli = Conexion::getConection();
	
	$result = array();
	$ok = TRUE;
	
  $nom = isset($params['nombre']) ? $params['nombre'] : null ;
  $raza = isset($params['raza']) ? $params['raza'] : null ;
  $edad = isset($params['edad']) ? $params['edad'] : null ;
  $sexo = isset($params['sexo']) ? $params['sexo'] : null ;
  $img = isset($params['img']) ? $params['img'] : null ;
  $desc = isset($params['descripcion']) ? $params['descripcion'] : null ;
  $adop = isset($params['adoptar']) ? $params['adoptar'] : null ;
  
  if ( !$nom  ) {
    $result[] = 'Rellene el campo nombre.';
    $ok = FALSE;
  }
  if ( !$raza  ) {
    $result[] = 'Seleccione una raza.';
    $ok = FALSE;
  }
  if ( !$edad || !ctype_digit($edad )) {
    $result[] = 'Rellene correctamente la edad.';
    $ok = FALSE;
  }
  if ( !$sexo  ) {
    $result[] = 'Seleccione el sexo de la mascota.';
    $ok = FALSE;
  }
  if ( !$img  || !esImagen($img)) {
    $result[] = 'Seleccione una foto de perfil válida.';
    $ok = FALSE;
  }
  if ( !$desc  ) {
    $result[] = 'Rellene el campo descripción.';
    $ok = FALSE;
  }
  if ( !$adop  ) {
    $result[] = 'Seleccione si quiere poner en adopción a la mascota.';
    $ok = FALSE;
  }

  if ( $ok ) {
	
	subirMascota($mysqli, $_SESSION['id'], $nom, $desc, $_POST['sexo'],
					$_POST['img'], $raza, $_POST['edad'], $_POST['adoptar']);
	
	//Obtiene el perro subido a la base de datos, para mostrarlo correctamente en la siguiente ventana.
	$num = ultimaMascota($mysqli);

	header('Location: confirmarMascota.php?id='.$num.'');	

	}
	 
  return $result;
}

?>