<?php

require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesTienda.php'; 

?>
<!DOCTYPE php>

<html>
	<head>
		<title>Twipet</title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/adopta-tienda.css"  />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
				
		<meta charset="utf-8"/>
	</head>
	
	<body>
		<div id="contenedor">
			<?php include RUTA_INCLUDES.'/comun/cabecera.php'; 
			
			$mysqli = Conexion::getConection();
			
			?>

				<form method="GET">
					<select onchange='this.form.submit()' name="tipo" id="buscar">
						<option selected disabled value="null">Filtrar</option>
						<option value="comida">Comida</option>
						<option value="juguete">Juguetes</option>
						<option value="accesorio">Accesorios</option>
					</select>	

					<input type="reset" id="boton-enviar" onClick="document.location.href='tienda.php'" value="Quitar filtrado" /> 
				</form>
			
			<?php
			if(isset ($_GET['tipo'])){
				$productos=obtenerProductosTipo($mysqli, $_GET['tipo']);
			}
			else{
				$productos=obtenerProductos($mysqli);
			}

			if (!isset ($productos)){
		?>
				<div id="noElementos">
				
					<h3> En estos momentos no disponemos de productos para mostrarte </h3>
				
				</div>
		<?php 
			}	
			else { ?>

				<div id="hayProductos">				
					<div id="topVentas">
						<h2> Top Ventas</h2>
						<ol>							
						<?php
							foreach(masVendidos($mysqli) as $registro){ 
								
								if ($registro["cantidad"] != 0){?>
									<a class ="si" href='producto.php?id= <?php echo $registro['id']?>'> <li> <?php echo $registro['nombre'] ?> </li></a>
								<?php }
								else{ ?>
									<a class ="no" href='producto.php?id= <?php echo $registro['id']?>'> <li> <?php echo $registro['nombre'] ?> </li></a>
								<?php } ?>

					<?php	} ?>
						</ol>
					</div>
			
					
					<div id="gridTienda">

				<?php	
				
						foreach($productos as $registro){ ?>
						
							<div class='col'>
						
								<img src= <?php echo RUTA_IMG.'/tienda/' .$registro['imagen'] ?> width='200' height='200' alt=<?php echo $registro['nombre']?>>
								<p><b><?php echo $registro['nombre']?></b></p>
								<p><?php echo $registro['precio']?> €</p>
								
								<!--Si no hay productos cambiaa la imagen del carrito a la de agotado-->
								<?php if($registro["cantidad"]==0){ ?>
									<a href='producto.php?id=<?php echo $registro['id']?>' > <img src="<?php echo RUTA_IMG ?>/tienda/agotado.png" class="comprar"></a>	
						<?php			
								}
								else{ ?>
									<a href='producto.php?id=<?php echo $registro['id']?>'> <img src='<?php echo RUTA_IMG ?>/tienda/comprar.png' class='comprar'></a>
						<?php	}
							echo "</div>";	
						}
				?>
					</div>
						<!-- div de gridTienda -->
					
			<?php	
					if(isset ($_SESSION['id'])){
						$carro = obtenerCarrito ($mysqli, $_SESSION['id']);
						if(isset ($carro)){ ?>
							<div id="carro">
								<div id="micarro_titulo"> MI CARRITO </div>
									<ul>
									<?php 
										$total=0;
										foreach ($carro as $carrito) {

												$reg=obtenerProducto($mysqli, $carrito['producto']); ?>
												<tr>
													<td>
														<li> 
															<div id="eliminar"> 
																
																<form  method="post" action="procesaCarrito.php">
																	<button id="elimina" id="eliminar" type="submit" style="cursor:pointer" > Eliminar </button>
																	<input type="hidden" name="idCarrito" value="<?php echo $carrito['id'] ?>"/>
																	<input type="hidden" name="idProducto" value="<?php echo $carrito['producto'] ?>"/>
																	<input type="hidden" name="cambio" value="quitaProducto"/>
																</form>
															</div> 
															
															<a href='producto.php?id= <?php echo $reg['id']?>'> <?php echo $reg['nombre'] ?> </a>
														</li> </td>
													
													<td> <?php echo $reg['precio'] ?> € </td> 
					
											<?php	$total = $total + $reg['precio'] ; ?>											
												</tr>
									<?php	
										}?>
									</ul>
									
									<form method="post" action="procesaCarrito.php">
										<button type="submit" id='compra' style="cursor:pointer"> Vaciar carrito </button>
											<input type="hidden" name="cambio" value="elimina"/>
									</form>
									
									<p><a href='carrito.php' id='compra'> ¡ Comprar ! </a></p>
									
								<div id="total_precio">
									TOTAL: <?php echo $total ?> €
								</div>
							</div>
			<?php  		}
					} ?>
				</div>
			
	 <?php  } ?>	
							
			<?php include RUTA_INCLUDES.'/comun/pie.php' ?>
		</div>
	</body>
</html>