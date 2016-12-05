<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;
require RUTA_INCLUDES.'/funcionesVet.php';
require_once (RUTA_INCLUDES.'/funcionImagen.php') ;

function formularioVet() {
  formulario('veterinario', 'generaFormularioVet', 'procesaFormularioVet');
}

function generaFormularioVet($datos) {
  
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
								<label for="horariosemanal">Horario Semanal:</label>
							</td>
							
							<td>
								<input type="text" name="horariosemanal" maxlength="50" size="30" required>
							</td>
						</tr>

						<tr>
							<td>
								<label for="horariofinde">Horario Finde:</label>
							</td>
							
							<td>
								<input type="text" name="horariofinde" maxlength="50" size="30" required>
							</td>

						</tr>
						
						<tr>
							<td>
								<label for="direccion">Dirección:</label>
							</td>
							
							<td>
								<input type="text" name="direccion" maxlength="200" size="50" required>
							</td>
						</tr>
						
						<tr>
							<td>
								<label for="codPostal">Código postal:</label>
							</td>
							
							<td>
								<input type="text" name="codPostal" maxlength="5" size="30" required>
							</td>
						</tr>
						
						<tr>
							<td>
								<label for="img">Foto de perfil:</label>
							</td>
							<td>
								<input type="file" name="img" required/> 
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
						
	
					</table>
					
					<input type="submit" name= "veterinario" class="botonCabecera" style="cursor:pointer" value="Confirmar">
				
					<?php include (RUTA_INCLUDES.'/comun/link.php'); ?>
EOF;
  return $html;
}

function procesaFormularioVet($params) {
	
	$mysqli = Conexion::getConection();
	
	$result = array();
	$ok = TRUE;
	
  $nom = isset($params['nombre']) ? $params['nombre'] : null ;
  $horariosemanal = isset($params['horariosemanal']) ? $params['horariosemanal'] : null ;
  $horariofinde = isset($params['horariofinde']) ? $params['horariofinde'] : null ;
  $direccion = isset($params['direccion']) ? $params['direccion'] : null ;
  $codPostal = isset($params['codPostal']) ? $params['codPostal'] : null ;
  $telefono = isset($params['telefono']) ? $params['telefono'] : null ;
  $img = isset($params['img']) ? $params['img'] : null ;

  if ( !$nom  ) {
    $result[] = 'Rellene el campo nombre.';
    $ok = FALSE;
  }
  if ( !$horariosemanal ) {
    $result[] = 'Rellene el campo Horario Semanal.';
    $ok = FALSE;
  }
  if ( !$horariofinde ) {
    $result[] = 'Rellene el campo Horario Fin de Semana.';
    $ok = FALSE;
  }
  if ( !$direccion  ) {
    $result[] = 'Rellene el campo direccion';
    $ok = FALSE;
  }
   if ( !$codPostal || strlen($codPostal) != 5 || !ctype_digit($codPostal) ) {
    $result[] = 'Rellene el campo código postal';
    $ok = FALSE;
  }
  if ( !$telefono ||  strlen($telefono) != 9 || !ctype_digit($telefono) ) {
    $result[] = 'Rellene el campo teléfono correctamente (9 dígitos).';
    $ok = FALSE;
  }
  if ( !$img || !esImagen($img)) {
    $result[] = 'Seleccione una foto de perfil.';
    $ok = FALSE;
  }


  if ( $ok ) {

		if (!isset ($_SESSION["login"])){
			echo"<div id='restringido'> ";
				include RUTA_INCLUDES.'/comun/accesoRestringido.html';
			echo"</div>";
		}
		else{
			if(empty ($_POST['nombre'])){
				echo"<div id='restringido'> ";
					echo"<h3>No hay ningun veterinario seleccionado</h3>";
				echo"</div>";
			}
			else{ 

				subirVeterinario($mysqli, $_POST['nombre'], $_POST['horariosemanal'], $_POST['horariofinde'],
									$_POST['direccion'], $_POST['telefono'], $_POST['img'], $_POST['codPostal']);
				
				//Obtiene el perro subido a la base de datos, para mostrarlo correctamente en la siguiente ventana.
				$num = ultimoVeterinario($mysqli);

				header('Location: perfilVeterinario.php?id='.$num.'');	

			}
		}
	}
	 
  return $result;
}

?>