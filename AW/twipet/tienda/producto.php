<?php

require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesTienda.php';

?>
<!DOCTYPE html>

<html>
	<head>
		<title> Producto </title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/adoptar-comprar.css"  />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		
		<meta charset="utf-8"/>
	</head>

	<body>

		<div id="contenedor">
			
			<?php include RUTA_INCLUDES.'/comun/cabecera.php'; 
			
			$mysqli = Conexion::getConection();
			
			if (!isset ($_SESSION["login"])){
				header('Location: '.RUTA_LOGIN.'/login.php');
			}
			else{
				if(!isset ($_GET['id'])){
					echo"<div id='restringido'> ";
						echo"<h3>No hay ningun producto seleccionado</h3>";
					echo"</div>";
				}
				else{
				?>
					<div id="contCompra">
						
						<?php
							$idProducto=$_GET["id"];
							$registro=obtenerProducto($mysqli, $idProducto);
						?>
				
						<div id="imagen">
							
							<img src= <?php echo RUTA_IMG.'/tienda/' .$registro['imagen'] ?> width="300px" alt=<?php echo $registro['nombre'] ?>>
							
							<?php if ($registro["cantidad"] !=0){ ?>
								<form id="carrito" method="post" action="procesaCarrito.php">
									<button type="submit" style="cursor:pointer" class="boton"> Añadir al carrito </button>
									<input type="hidden" name="idProducto" value="<?php echo $idProducto ?>"/>
									<input type="hidden" name="cambio" value="sumaProducto"/>
								</form>
							<?php } 
								else{ ?>
									<a href="#" > <img src="<?php echo RUTA_IMG ?>/tienda/agotado.png" class="comprar" width="200%">  </a>	
							<?php	} ?>
							
							
						</div>
						
						<div id="descripcion">
							
							<h2> <?php echo "$registro[nombre]" ?> </h2>
							<h3> Precio: <?php echo "$registro[precio]" ?> € </h3>
							
							<p> <?php echo nl2br("$registro[descripcion]") ?> </p>
						
						</div>
					
						
					</div>

					<?php 
					$otros = otrosProductos($mysqli, $idProducto, $registro['tipo']); ?>
					<div id="otros">
				<?php	if (isset ($otros)){ ?>
	
								<h2> Otros <?php echo $registro['tipo'] ?>s disponibles </h2>
											
								<?php foreach ($otros as $registro){ ?>
									<div class="col">
										<img src= <?php echo RUTA_IMG.'/tienda/' ?>/<?php echo "$registro[imagen]" ?> class="producto" > 
										<p class="producto"> <?php echo "$registro[nombre]" ?> </p> 
										<p class="precio" > Precio: <?php echo "$registro[precio]" ?> € </p>
										<?php if ($registro["cantidad"] !=0){ ?>
											<a href="producto.php?id=<?php echo "$registro[id]" ?>" > <img src="<?php echo RUTA_IMG?>/tienda/comprar.png" class="comprar"></a>
										<?php }
											else{ ?>
											<a href="producto.php?id=<?php echo "$registro[id]" ?>" > <img src="<?php echo RUTA_IMG?>/tienda/agotado.png" class="comprar"></a>
											
										<?php } ?>
									</div>
								<?php } ?>

			<?php   	}
						else{
							echo"<h3>No disponemos de más ".$registro['tipo']."s</h3>";
						}
					echo "</div>";
				}
			}
		
			include RUTA_INCLUDES.'/comun/pie.php'; ?>
			
		</div>
	</body>
</html>