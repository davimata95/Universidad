 <?php

require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesVet.php';

?>
<!DOCTYPE html>

<html>
	<head>
		<title>perfilveterinario</title>
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/veterinarios.css" />
		
		<meta charset="utf-8"/>
	</head>
	
	<body>
	
		<div id="contenedor">
			

			<?php include RUTA_INCLUDES.'/comun/cabecera.php';  
				$mysqli = Conexion::getConection();	
				
			?>
			
			<?php
				$id=$_GET["id"];
				$vet = obtenerVeterinario($mysqli, $id);
			?>
		
				
				<div id="infoperfil">
				
					<div id="perfil">
						<p><img src="<?php echo RUTA_IMG .'/veterinarios/' .$vet['imagen'] ?>" class="imgRedondaPerfil" alt=<?php echo $vet['nombre'] ?>/></p>
					</div>
					
					<div id="apartado">
						<div class="textoApartado">
							<p><h1><?php echo $vet['nombre'] ?></h1><p>
							<b>Horario:</b>
							
							<p>De lunes a viernes: <?php echo $vet['horariosemanal'] ?></p>
							<p>Sábados, domingos y festivos: <?php echo $vet['horariofinde'] ?></p>
							
						</div> 
					</div>
					<div id="apartado">
						<div class="textoApartado">
							<b>Dirección:</b>
							<p><?php  echo $vet['direccion'] ?></p>
						</div> 
					</div>
					<div id="apartado">
						<div class="textoApartado">
							<b>Teléfono:</b>
		
							<p><?php echo $vet['telefono'] ?></p>
				
						</div> 
					</div>
				</div>
		
			<?php include RUTA_INCLUDES.'/comun/pie.php' ?>
		</div>
	</body>
</html>