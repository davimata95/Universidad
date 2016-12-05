<?php

require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesTienda.php'; 

?>
<!DOCTYPE html>
<html>
	<head>
		<title>Carrito de la compra</title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/formularios.css"  />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		
		<meta charset="utf-8"/>
	</head>
<center>

	<body>
		
		<?php include RUTA_INCLUDES.'/comun/cabecera.php'; 
		
		$mysqli = Conexion::getConection();
		
		?>
			
			<div id="contenedor_carrito">
				<img src="<?php echo RUTA_IMG ?>/log.png" width="50px" alt="logo"><h3></h3>
				<form action="formularioBanco.php">
					<h3>TU CARRITO</h3>
					<table width="450px" height="100px" class="tabla" rules="cols">
						<tr>
							<th scope="col">Producto</th>
							<th scope="col">Precio (€)</th>
						</tr>
				
				<?php	$carro = obtenerCarrito ($mysqli, $_SESSION['id']);
						if(isset ($carro)){ 
					
							$total=0;
								foreach ($carro as $carrito) {
									$reg=obtenerProducto($mysqli, $carrito['producto']); ?>
									<tr>
										<td align="center"> <?php echo $reg['nombre'] ?> </td>									
										<td align="center"> <?php echo $reg['precio'] ?> € </td> 
								<?php	$total = $total + $reg['precio'] ; ?>											
									</tr>
						<?php	} ?>
					
		
						<td></td>
						<td align="right"><b>Total: </b><?php echo $total ?> €</td>
				<?php	}?>
						<tr>
							<td colspan="2" style="text-align:center">
								<a href='tienda.php' class='botonCabecera'> Cancelar </a>
								<button type="submit" class='botonCabecera' style="cursor:pointer"> Comprar </button>
	
							</td>
						</tr>
					</table>
				</form>
			</div>
		<?php include RUTA_INCLUDES.'/comun/pie.php'; ?>
	</body>
</html>