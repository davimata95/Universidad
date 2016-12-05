<?php

require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesMascotas.php';

?>
<!DOCTYPE html>

<html>
	<head>
		<title> adopta </title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/adopta-tienda.css" />
		
		<meta charset="utf-8"/>
	</head>

	<body>

		<div id="contenedor">
			
			<?php include RUTA_INCLUDES.'/comun/cabecera.php'; 
			
			$mysqli = Conexion::getConection();
			
			?>
			
			<div id="contenido">
			
				<div id="bienvenida" >
					<h3> ¡Bienvenido al catálogo de adopción! </h3>
					<p> Aquí podrás consultar todas las mascotas disponibles para su adopción.</p>
				</div>
									
					<?php
						
						$mascotas=obtenerMascotas($mysqli);
						
						if (!isset($mascotas)){
					?>
							<div id="noElementos">							
								<h3> En estos momentos no disponemos de mascotas para adoptar </h3>
							</div>
					<?php 
						}
						else {
							
							echo "<div id='gridAdopta'>";
							
							foreach($mascotas as $registro){ ?>

								<div class='col'>
							
									<img src=" <?php echo RUTA_IMG.'/adopta/' .$registro['imagen'] ?>" id="perro" alt= "<?php $registro['nombre'] ?>">
									<p><b> Nombre: <?php echo $registro['nombre'] ?> </b></p>
									<p> Raza: <?php echo $registro['raza'] ?> </p>
									<p> Sexo: <?php echo $registro['sexo'] ?> </p>

									<p> <a href="adoptarMascota.php?id= <?php echo $registro['id'] ?> " class="boton" > ¡Adoptar! </a> </p> 
								
								</div>
					<?php 
							}
						} 
					?>

				<!-- div de gridAdopcion -->
				</div>
			</div>
			
			<?php include RUTA_INCLUDES.'/comun/pie.php' ?>
			
		<!-- div de contenido -->
		</div>
	</body>
</html>