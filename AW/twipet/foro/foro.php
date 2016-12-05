<?php

require_once '../includes/config.php';
require_once RUTA_INCLUDES.'/funcionesForo.php'; 

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
			
			<?php include RUTA_INCLUDES.'/comun/cabecera.php' ;

			//Si no estas registrado no puedes adoptar mascotas
			if (!isset ($_SESSION["login"])){			
				header('Location: '.RUTA_LOGIN.'/login.php');
			}
			else{ ?>
			
			
				<div id="logoForo">
					
					<img src="<?php echo RUTA_IMG ?>/foro/forotwipet.png" width="30%" alt="logoForo">
					
				</div>
				
				<div id="secciones">
				
					<div id = "botonForo">
							<a href="mensajeForo.php" class="botonCabecera">Crear un nuevo tema</a>
					</div>
				<?php
					
					$mysqli = Conexion::getConection();
					
					$foros=obtenerForos($mysqli);
					if (!isset ($foros)){
				?>
						<div id="noElementos">							
							<h3> En estos momentos no disponemos de foros </h3>
						</div>
				<?php 
					}
					else { ?>
						<table>
							<tr>
								<td width="500px"><h2>Título</h2></td>
								<td width="200px"><h2>Fecha</h2></td>
								<td width="200px"><h2>Respuestas</h2></td>
								<td width="100px"></td>
							</tr>
							<?php 
								foreach ($foros as $post){ ?>
									<tr>
										<td><h3><?php echo $post['titulo'] ?></h3></td>
										<td><h3><?php echo date($post['fecha']) ?></h3></td>
										<td><h3><?php echo $post['respuestas'] ?></h3></td>
										<td><a href='comprobarForo.php?id=<?php echo $post['ID'] ?>'>Ver tema </a></td>
									</tr>
							<?php	
								}
							?>
						</table>
						
						
					<?php } ?>
				</div>
			
		<?php }
			include RUTA_INCLUDES.'/comun/pie.php'; ?>
			
		</div>
	</body>
</html>