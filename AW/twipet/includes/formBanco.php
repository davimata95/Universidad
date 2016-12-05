<?php

require_once 'formlib.php';
require_once (RUTA_INCLUDES.'/Conexion.php') ;

function formularioBanco() {
  formulario('banco', 'generaFormularioBanco', 'procesaFormularioBanco');
}

function generaFormularioBanco($datos) {
  
  $html = <<<EOF
				<table width="450px" height="300px">
				</tr>

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
						<label for="apellidos">Apellidos:</label>
					</td>
					
					<td>
						<input type="text" name="apellidos" maxlength="50" size="30" required/>
					</td>
				</tr>

				<tr>
					<td>
						<label for="dni">NIF:</label>
					</td>
					
					<td>
						<input type="text" name="dni" maxlength="20" size="15" required />
					</td>

				</tr>
				<tr>
					<td>
						<label for="telefono">Teléfono:</label>
					</td>
					
					<td>
						<input type="text" name="telefono" maxlength="20" size="15" required />
					</td>

				</tr>
				
				<tr>
					<td>
						<label for="numero_tarjeta">Numero de tarjeta:</label>
					</td>
					
					<td>
						<input type="text" name="numero_tarjeta" maxlength="50" size="30" required/>
					</td>

				</tr>
				<tr>
					<td>
						<label for="seguridad">Código de seguridad (CVV):</label>
					</td>
					
					<td>
						<input type="text" name="seguridad" maxlength="5" size="5" required />
					</td>

				</tr>
			</table>
			<Button type="submit" name="banco" class="botonCabecera" style="cursor:pointer"> Confirmar </Button>
				<input type="hidden" name="cambio" value="compra"/>
			<a href="tienda.php" class="botonCabecera"> Cancelar </a>
EOF;
  return $html;
}

function procesaFormularioBanco($params) {
	
	$mysqli = Conexion::getConection();
	
	$result = array();
	$ok = TRUE;
	
  $nom = isset($params['nombre']) ? $params['nombre'] : null ;
  $apellidos = isset($params['apellidos']) ? $params['apellidos'] : null ;
  $dni = isset($params['dni']) ? $params['dni'] : null ;
  $tlf = isset($params['telefono']) ? $params['telefono'] : null ;
  $n_tarjeta = isset($params['numero_tarjeta']) ? $params['numero_tarjeta'] : null ;
  $seguridad = isset($params['seguridad']) ? $params['seguridad'] : null ;
  
  if ( !$nom  ) {
    $result[] = 'Rellene el campo nombre.';
    $ok = FALSE;
  }
  if ( !$apellidos  ) {
    $result[] = 'Rellene el campo apellidos.';
    $ok = FALSE;
  }
  if ( !$dni ||  strlen($dni) != 9 ) {
    $result[] = 'Rellene correctamente el campo DNI.';
    $ok = FALSE;
  }
  if ( ! $tlf ||  strlen($tlf) != 9 || !ctype_digit($tlf) ) {
    $result[] = 'Rellene correctamente el campo teléfono.';
    $ok = FALSE;
  }
  if ( !$n_tarjeta || !ctype_digit($tlf) ) {
    $result[] = 'Rellene correctamente el campo tarjeta.';
    $ok = FALSE;
  }
  if ( !$seguridad || strlen($seguridad) != 3 || !ctype_digit($seguridad)  ) {
    $result[] = 'Rellene correctamente el campo seguridad.';
    $ok = FALSE;
  }

    if ( $ok ) {

		foreach (obtenerCarrito ($mysqli, $_SESSION['id']) as $carrito) {
				confirmarCompra ($mysqli, $carrito['id']);
		
		header('Location: tienda.php');
	}
	 
  return $result;
}
}

?>