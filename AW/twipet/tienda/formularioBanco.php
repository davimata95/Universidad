<?php

require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesTienda.php'; 

?>
<!DOCTYPE html>
<html>
<head>
		<title>Datos bancarios</title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/formularios.css" />
		
		<meta charset="utf-8"/>		
	</head>
	<center>
	

	<body>
		
		<div id="contenedor">
		
			<?php include RUTA_INCLUDES.'/comun/cabecera.php';
			
			$mysqli = Conexion::getConection();
		
			if ((!isset ($_SESSION["login"]))){
				echo"<div id='restringido'> ";
					include RUTA_INCLUDES.'/comun/accesoRestringido.html';
				echo"</div>";
			}
			else{
				$compra = obtenerCarrito ($mysqli, $_SESSION['id']);
				if (!isset ($compra)){
					echo"<div id='restringido'> ";
						echo"<h3> No tienes productos en el carrito </h3>";
					echo"</div>";
				}
				else{					
					?>
					<div id="contenido">
					
						<img src="<?php echo RUTA_IMG ?>/log.png" width="50px" alt="logo"><h3>Finalizar la compra:</h3>
						<form method="post" action="procesaCarrito.php" onsubmit= "return valida(this);">
						
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
							<Button type="submit" class="botonCabecera" style="cursor:pointer"> Confirmar </Button>
								<input type="hidden" name="cambio" value="compra"/>
							<a href="tienda.php" class="botonCabecera"> Cancelar </a>
						</form>
					</div>
				
					<div id="carro">
						<div id="micarro_titulo"> MI CARRITO </div>
							<ul>
							<?php 
								$total=0;
								foreach (obtenerCarrito ($mysqli, $_SESSION['id']) as $carrito) {

										$reg=obtenerProducto($mysqli, $carrito['producto']); ?>
										<tr>
											<td>
												<li> 
													<a href='producto.php?id= <?php echo $reg['id']?>'> <?php echo $reg['nombre'] ?> </a>
												</li> </td>
											
											<td> <?php echo $reg['precio'] ?> € </td> 
			
									<?php	$total = $total + $reg['precio'] ; ?>											
										</tr>
							<?php	
								}?>
							</ul>
		
						<div id="total_precio">
							TOTAL: <?php echo $total ?> €
						</div>
					</div>
		<?php   }
			}
			include RUTA_INCLUDES.'/comun/pie.php'; ?>
		</div>
	</body>
</html>