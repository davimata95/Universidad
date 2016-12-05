<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;
require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesTienda.php';
require_once (RUTA_INCLUDES.'/funcionImagen.php') ;

function formularioProducto() {
  formulario('producto', 'generaFormularioProducto', 'procesaFormularioProducto');
}

function generaFormularioProducto($datos) {
  
  $html = <<<EOF
				<table width="450px" height="500px">

						<tr>
							<td>
								<label for="nombre">Nombre:</label>
							</td>
							
							<td>
								<input type="text" name="nombre" maxlength="50" size="30" required />
							</td>
						</tr>
						
						<tr>
							<td>
								<label for="precio">Precio:</label>
							</td>
							
							<td>
								 <input type="number" name="precio" min="0.01" max="10000" step="0.01" value="0" required /> Euros
							</td>
						</tr>
						
						<tr>
							<td>
								<label for="tipo">Tipo:</label>
							</td>
							
							<td>
								<select name="tipo" required >
									<option selected disabled value="null">Selecciona el tipo</option>
									<option value="Comida">Comida</option>
									<option value="Juguete">Juguete</option>
									<option value="Accesorio">Accesorio</option>
								</select>
							</td>
						</tr>
						
						<tr>
							<td>
								<label for="cantidad">Cantidad:</label>
							</td>
							
							<td>
								 <input type="number" name="cantidad" min="1" max="10000" step="1" value="0" required />
							</td>
						</tr>
						
						<tr>
							<td>
								<label for="img">Foto producto:</label>
							</td>
							<td>
								<input type="file" name="img" required />
							</td>
						</tr>

						<tr>
							<td>
								<label for="descripcion">Descripción:</label>
							</td>
							
							<td>
								<textarea name="descripcion" maxlength="1000" cols="30" rows="6" required ></textarea>
							</td>
						</tr>
						
					</table>
						
						<input type="submit" name="producto" class="botonCabecera" style="cursor:pointer" value="Confirmar">
						
						<?php include (RUTA_INCLUDES.'/comun/link.php'); ?>
EOF;
  return $html;
}

function procesaFormularioProducto($params) {
	
	$mysqli = Conexion::getConection();
	
	$result = array();
	$ok = TRUE;
	
  $nom = isset($params['nombre']) ? $params['nombre'] : null ;
  $precio = isset($params['precio']) ? $params['precio'] : null ;
  $tipo = isset($params['tipo']) ? $params['tipo'] : null ;
  $cantidad = isset($params['cantidad']) ? $params['cantidad'] : null ;
  $img = isset($params['img']) ? $params['img'] : null ;
  $desc = isset($params['descripcion']) ? $params['descripcion'] : null ;
  $adop = isset($params['adoptar']) ? $params['adoptar'] : null ;
  
  if ( !$nom  ) {
    $result[] = 'Rellene el campo nombre.';
    $ok = FALSE;
  }
  if ( !$precio || !is_numeric($precio) ) {
    $result[] = 'Rellene correctamente el campo precio.';
    $ok = FALSE;
  }
  if ( !$tipo ) {
    $result[] = 'Seleccione un tipo de producto.';
    $ok = FALSE;
  }
  if ( !$cantidad || !ctype_digit($cantidad)) {
    $result[] = 'Rellene correctamente el campo cantidad.';
    $ok = FALSE;
  }
  if ( !$img  || !esImagen($img)) {
    $result[] = 'Seleccione una foto de producto válida.';
    $ok = FALSE;
  }
  if ( !$desc  ) {
    $result[] = 'Rellene el campo descripción.';
    $ok = FALSE;
  }

  if ( $ok ) {

		if ((!isset ($_SESSION["login"])) || ($_SESSION["tipoUsuario"]=="usuario")){
			echo"<div id='restringido'> ";
				include RUTA_INCLUDES.'/comun/accesoRestringido.html';
			echo"</div>";
		}
		else{
			if(empty ($_POST['nombre'])){
				echo"<div id='restringido'> ";
					echo"<h3>No hay ningun producto seleccionado</h3>";
				echo"</div>";
			}
			else{ 

				subirProducto($mysqli, $_SESSION['id'], $_POST['nombre'], $_POST['descripcion'], $_POST['precio'],
								$_POST['cantidad'], 0, $_POST['img'], $_POST['tipo'], 'pendiente');
					
				$num=ultimoProducto($mysqli);
			}

				header('Location: confirmarProducto.php?id='.$num.'');	
		
		}
	}
	 
  return $result;
}

?>