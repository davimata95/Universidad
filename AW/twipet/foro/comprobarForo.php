<?php

require_once '../includes/config.php';
require_once RUTA_INCLUDES.'/funcionesForo.php'; 
require_once '../includes/formForoResponde.php';

?>
<!DOCTYPE html>

<html>
	<head>
		<title>Foro</title>
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/foro.css" />
		
		<meta charset="utf-8"/>
	</head>
	
	<body>
	
		<div id="contenedor">
			
			<?php include RUTA_INCLUDES.'/comun/cabecera.php'; ?>

			<div id="logoForo">
				
				<a href="foro.php"><img src="<?php echo RUTA_IMG ?>/foro/forotwipet.png" width="30%" alt="logoForo"></a>
				
			</div>
			
			<div id="formularioForo">
			
				<?php
				
					$mysqli = Conexion::getConection();
					
					$id = $_GET["id"];
					
					$registro=obtenerPost($mysqli, $id) ;
					
					if(isset($registro)){ ?>

						<div id="post">
							<tr><td><h1><?php echo $registro['titulo'] ?></h1></td></tr>
							<table>
								<tr><td>Escrito por: <?php echo $registro['autor'] ?></td></tr>
								<tr><td><h3><?php echo $registro['mensaje'] ?></h3></td></tr>
							</table>	
						</div>
	
					
						<h1>Responder en el foro</h1>
						
						<?php
						
						formularioForoRespuesta($id);
						
						?>

					<p></p>
					
				   <?php
						$respuestas=obtenerRespuestas($mysqli, $registro['ID']);
						if(isset ($respuestas)){
							foreach($respuestas as $respuesta){ ?>
							
								<div id="post">
									<tr><td><h2><?php echo $respuesta['titulo'] ?></h2></tr></td>
									<table>
										<tr><td>Escrito por: <?php echo $respuesta['autor'] ?></td></tr>
										<tr><td><h3><?php echo $respuesta['mensaje'] ?></h3></td></tr>
									</table>
								</div>
					<?php 	}
						}	
					} 
					else{ 					
						echo "<h3> El post seleccionado no está disponible. </h3>";										
					}?>
					
				<div id = "botonForo">
					<a href = "foro.php" class="botonCabecera">Volver atrás</a>
				</div>
			
			</div>
			
			<?php include RUTA_INCLUDES.'/comun/pie.php' ?>
			
		</div>
	</body>
</html>