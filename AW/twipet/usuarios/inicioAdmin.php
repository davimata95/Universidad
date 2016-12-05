<?php

require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesUsuarios.php';
require RUTA_INCLUDES.'/funcionesVet.php';

?>
<!DOCTYPE php>

<html>
	<head>
		<title>Mi Perfil Veterinario</title>
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/social.css" />
		
		<meta charset="utf-8"/>
	</head>
	
	<body>
	
		<div id="contenedor">
			
			<?php include RUTA_INCLUDES.'/comun/cabecera.php'; 
			
			$mysqli = Conexion::getConection();
							
			if ((!isset ($_SESSION["login"])) || ($_SESSION["tipoUsuario"]!="admin")){
				echo"<div id='restringido'> ";
					include '../includes/comun/accesoRestringido.html';
				echo"</div>";
			}
			else{
			?>
				<div id="perfil">
							
					<img src=<?php echo RUTA_IMG.'/usuarios/'.$_SESSION['avatar'] ?> class="imgRedondaPerfil"/>
					<div id="informacion">
						<h2><?php echo $_SESSION['nombre'].' '. $_SESSION['apellidos']?></h2>
					</div>
					<p></p>
					<table height="200px" align="center">

						<tr>
							<td>
								<a href="<?php echo RUTA_MASCOTAS ?>/subirMascota.php" class="botonCabecera">Subir mascota</a>
							</td>
						</tr>
						<tr>
							<td>
								<a href="<?php echo RUTA_TIENDA ?>/subirProducto.php" class="botonCabecera">Subir producto</a>
							</td>
						</tr>
						<tr>
							<td>
								<a href="<?php echo RUTA_VETERINARIOS ?>/subirVeterinario.php" class="botonCabecera">Subir veterinario</a>
							</td>
						</tr>
						
					</table>
						
				</div>
				
				<div id ="subido">
					<div id="contenidoElementos">
					<?php
						$mascotas = obtenerMascotasAdmin($mysqli);
						if(isset ($mascotas)){ ?>
							<h3>Estas son las mascotas de Twipet:</h3>
						
					<?php	foreach($mascotas as $registro){ ?>

								<div class="box_elementos">
									<img src=<?php echo RUTA_IMG.'/adopta/'.$registro['imagen'] ?> class="imgRedondaElemento" width="100%" alt="conoce2">
									<p><strong> <?php echo $registro['nombre'] ?></strong></p>
									<p>Raza: <?php echo $registro['raza'] ?></p>
							<?php 	if($registro['estado']=='pendiente'){ ?>
										<a href="<?php echo RUTA_MASCOTAS ?>/confirmarMascota.php?id=<?php echo $registro['id'] ?>"> <img src="<?php echo RUTA_IMG ?>/pendiente.png" alt="pendiente" width="50%"></a>
							<?php 	}
									else{ ?>
										<a href="<?php echo RUTA_MASCOTAS ?>/modificarMascota.php?id=<?php echo $registro['id'] ?>"> <img src="<?php echo RUTA_IMG ?>/modificar.png" alt="modificar" width="50%"></a>
							<?php	} ?>
									
									<form action="<?php echo RUTA_MASCOTAS ?>/eliminarMascota.php" method = "POST">
										<input type="image" src="<?php echo RUTA_IMG ?>/eliminar.png" width="50%" />
										 <input type="hidden" name="id" value="<?php echo $registro['id']?>"/>
									</form>
								</div>	
						<?php }
						}
						else{
							echo "<h2>En estos momentos no tienes mascotas. </h2>";
						} ?>

					</div>
					
					<div id="contenidoElementos">
					<?php
						$productos = obtenerProductosAdmin($mysqli);
						if(isset ($productos)){ ?>
							<h3>Estos son los productos de la tienda de Twipet:</h3>
						
					<?php	foreach($productos as $registro){ ?>

								<div class="box_elementos">
									<img src=<?php echo RUTA_IMG.'/tienda/'.$registro['imagen'] ?> class="imgRedondaElemento" width="100%" alt="conoce2">
									<p><strong> <?php echo $registro['nombre'] ?></strong></p>
									<p>Precio: <?php echo $registro['precio'] ?> € </p>
							<?php 	if($registro['estado']=='pendiente'){ ?>
										<a href="<?php echo RUTA_TIENDA ?>/confirmarProducto.php?id=<?php echo $registro['id'] ?>"> <img src="<?php echo RUTA_IMG ?>/pendiente.png" alt="pendiente" width="50%"></a>
							<?php 	}
									else{ ?>
										<a href="<?php echo RUTA_TIENDA ?>/modificarProducto.php?id=<?php echo $registro['id'] ?>"> <img src="<?php echo RUTA_IMG ?>/modificar.png" alt="modificar" width="50%"></a>
							<?php	} ?>
									<form action="<?php echo RUTA_TIENDA ?>/eliminarProducto.php" method = "POST">
										<input type="image" src="<?php echo RUTA_IMG ?>/eliminar.png" width="50%" />
										 <input type="hidden" name="id" value="<?php echo $registro['id']?>"/>
									</form>
								</div>
					<?php	}
						}
						else{
							echo "<h2>En estos momentos no tienes productos. </h2>";
						} ?>

					</div>
					
					<div id="contenidoElementos">
					<?php
						$veterinarios = obtenerVeterinarios($mysqli);
						if(isset ($veterinarios)){ ?>
							<h3>Estos son los veterinarios de Twipet:</h3>
						
					<?php	foreach($veterinarios as $registro){ ?>

								<div class="box_elementos">
									<img src=<?php echo RUTA_IMG.'/veterinarios/'.$registro['imagen'] ?> class="imgRedondaElemento" width="100%" alt=<?php echo $registro['nombre'] ?>>
									<p><strong> <?php echo $registro['nombre'] ?></strong></p>
								
							
									<a href="<?php echo RUTA_VETERINARIOS?>/modificarVeterinario.php?id=<?php echo $registro['id'] ?>"> <img src="<?php echo RUTA_IMG ?>/modificar.png" alt="modificar" width="50%"></a>

									
									<form action="<?php echo RUTA_VETERINARIOS ?>/eliminarVeterinario.php" method = "POST">
										<input type="image" src="<?php echo RUTA_IMG ?>/eliminar.png" width="50%" />
										<input type="hidden" name="id" value="<?php echo $registro['id']?>"/>
									</form>
								</div>	
						<?php }
						}
						else{
							echo "<h2>En estos momentos no hay veterinarios. </h2>";
						} ?>

					</div>
				</div>
	<?php   }
			
		    include RUTA_INCLUDES.'/comun/pie.php' ?>
			
		</div>
	</body>
</html>