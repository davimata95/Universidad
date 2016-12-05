<?php

require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesSocial.php';

?>
<!DOCTYPE html>

<html>
	<head>
		<title>Twipet - Social</title>
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/menuSocial.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/social.css"  />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		
		<meta charset="utf-8"/>
	</head>
	<body>
		
		<div id="contenedor">
			
		<?php 
			include RUTA_INCLUDES.'/comun/cabecera.php'; 
			
			$mysqli = Conexion::getConection();
				
			if (empty ($_SESSION["login"])){
				
				header('Location: '.RUTA_LOGIN.'/login.php');
			}
			else{	

			?>
			
			<div id="contenidoConoce">
			
			<?php
				
				$personas = obtenerPersonas($mysqli, $_SESSION['id']);
				if (!isset ($personas)){
			?>
					<div id="noElementos">
					
						<h3> En estos momentos no disponemos de usuarios cercanos a ti </h3>
						
					</div>
			<?php 
				}
				else {
			
					echo "<h3> " .$_SESSION['usuario']. ", estos son los usuarios que están cerca de ti:</h3>";				
				
					foreach($personas as $registro){ ?>
						<div class="box">
							<a href="perfilVisita.php?id=<?php echo $registro['id'] ?>"><img src="<?php echo RUTA_IMG ?>/usuarios/<?php echo $registro['avatar'] ?>" 
								id="imagenRedonda" width="100%" alt="<?php echo $registro['nombre'] ?>"> </a>
							<p><b><?php echo $registro['nombre'] ?></b></p>
						</div>
				<?php }			
				} 
			}?>
			</div>
				
			<?php	include RUTA_INCLUDES.'/comun/pie.php'; ?>
			
		</div>
	</body>
</html>