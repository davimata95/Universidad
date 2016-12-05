<?php

require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesTienda.php';

?>
<!DOCTYPE html>
<html>
	<head>
		<title>Confirmar producto</title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/formularios.css" />
		
		<meta charset="utf-8"/>
		
	</head>
	<center>

	<body>
		
		<div id="contenedor">
			<?php include RUTA_INCLUDES.'/comun/cabecera.php'; 
			
			$mysqli = Conexion::getConection();
			
			if ((!isset ($_SESSION["login"])) || ($_SESSION["tipoUsuario"]=="usuario")){
				echo"<div id='restringido'> ";
					include '../includes/comun/accesoRestringido.html';
				echo"</div>";
			}
			else{
				if(!isset ($_GET['id'])){
					echo"<div id='restringido'> ";
						echo"<h3>No hay ningun producto seleccionado</h3>";
					echo"</div>";
				}
				else{
					$id=$_GET['id'];
									
					$registro=obtenerProducto($mysqli, $id);
						
					if($registro['propietario']!= $_SESSION['id'] && $_SESSION['tipoUsuario'] != 'admin'){
						echo"<h3> No eres el propietario de este producto </h3>";
					}
					else{			
					?>
						<div id="contenidoMostrar">
							<img src="<?php echo RUTA_IMG ?>/log.png" width="50px" alt="logo"><h3> Está a punto de subir a la tienda: </h3>

							<p><img src= <?php echo RUTA_IMG.'/tienda/' .$registro['imagen'];?> class="imgRedondaPerfil" > </p>
							<p><b>Nombre:</b> <?php echo ($registro['nombre']) ?> </p>
							<p><b>Precio:</b> <?php echo ($registro['precio']) ?> </p>
							<p><b>Tipo producto:</b> <?php echo ($registro['tipo']) ?> </p>
							<p><b>Cantidad:</b> <?php echo ($registro['cantidad']) ?> </p>
							<p><b>Descripción:</b> <?php echo ($registro['descripcion']) ?> </p>
							
							<a href="modificarProducto.php?id=<?php echo $id ?>"> <img src="<?php echo RUTA_IMG ?>/modificar.png" alt="modificar" width="50%"></a>
							
						</div>	
						
						<div id="confirmar">
							<h3> Gracias por utilizar nuestra tienda online </h3>
							<h4> ¿Estás seguro de subir este producto? </h4>
							
							<div id="botonesConfirmacion">
								<a href = <?php echo RUTA_USUARIOS ?>/procesaPendiente.php?tipo=producto&id=<?php echo $id ?> class='botonCabecera' > Subir producto </a>
								<?php include (RUTA_INCLUDES.'/comun/link.php'); ?>
							</div>
						</div>

					<!--Cierra el else, si el usuario esta registrado-->
			<?php	}
				}
			}	?>	
			<?php include RUTA_INCLUDES.'/comun/pie.php'; ?>
		</div>
	</body>	
</html>






