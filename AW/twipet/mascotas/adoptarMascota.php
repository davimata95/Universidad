<?php

require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesMascotas.php';

?>
<!DOCTYPE html>

<html>
	<head>
		<title> adopta </title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/adoptar-comprar.css" />

		<meta charset="utf-8"/>
	</head>

	<body>

		<div id="contenedor">
			
			<?php include RUTA_INCLUDES.'/comun/cabecera.php';
			
			$mysqli = Conexion::getConection();
			
			//Si no estas registrado no puedes adoptar mascotas
			if (!isset ($_SESSION["login"])){			
				header('Location: '.RUTA_LOGIN.'/login.php');
			}
			else{
				if(!isset ($_GET['id'])){
					echo"<div id='restringido'> ";
						echo"<h3>No hay ninguna mascota seleccionada</h3>";
					echo"</div>";
				}
				else{
					?>			
					<div id="contAdopta">
						<?php
							//Obtengo el perro de la base de datos
							$registro=mostrarMascota($mysqli, $_GET['id']);
						?>
				
						<form id="imagen" method="POST" action="procesaAdopcion.php">
						
							<img src= <?php echo RUTA_IMG.'/adopta/' .$registro['imagen'] ?> width="300px" alt=<?php echo $registro['nombre'] ?>>
							<?php if($registro['estado']=='subido'){ ?>
								<div id="adoptar">
									<button type="submit" style="cursor:pointer" class="boton"> ¡Adoptar! </button>
									<input type="hidden" name="id" value="<?php echo $_GET['id']?>"/>
								</div>
						<?php } ?>
						</form>
						
						<div id="descripcion">
							
							<h2> <?php echo nl2br($registro['nombre']) ?> </h2>
							<h3> <?php echo nl2br($registro['raza']) ?> </h3>
							<h4> <?php echo nl2br($registro['edad']) ?> años </h3>
							<h4> Sexo: <?php echo nl2br($registro['sexo']) ?> </h4>
							
							<p> <?php echo nl2br($registro['descripcion']) ?> </p>
						
						</div>
							
					</div>
		<?php   }
			}
			
			include RUTA_INCLUDES.'/comun/pie.php' ?>
			
		</div>
	</body>
</html>