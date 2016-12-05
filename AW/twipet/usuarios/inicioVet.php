<?php

require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesUsuarios.php';

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
			<?php
			
			$mysqli = Conexion::getConection();
			?>
			
			<?php include RUTA_INCLUDES.'/comun/cabecera.php'; 
							
			if ((!isset ($_SESSION["login"])) || ($_SESSION["tipoUsuario"]!="veterinario")){
				echo"<div id='restringido'> ";
					include RUTA_INCLUDES.'/comun/accesoRestringido.html';
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
					<table height="150px" align="center">
					
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
						
					</table>
						
				</div>
				
				<div id ="subido">
					<div id="contenidoElementos">
					<?php
						$mascotas = mostrarMisMascotas($mysqli, $_SESSION['id']);
						if(isset ($mascotas)){ ?>
							<h3>Estas son las mascotas que tienes en adopción:</h3>
						
					<?php	foreach($mascotas as $registro){ ?>

								<div class="box_elementos">
									<img src=<?php echo RUTA_IMG.'/adopta/'.$registro['imagen'] ?> class="imgRedondaElemento" width="100%" alt="conoce2">
									<p><strong> <?php echo $registro['nombre'] ?></strong></p>
									<p>Raza: <?php echo $registro['raza'] ?></p>
									<p><?php echo $registro['edad'] ?> Años </p>
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
									<p><?php echo $registro['tipo'] ?> </p>
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
				</div>
	<?php   }
			
		    include RUTA_INCLUDES.'/comun/pie.php' ?>
			
		</div>
	</body>
</html>