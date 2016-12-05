<?php

require_once '../includes/config.php';
require_once '../includes/formLogin.php';

?>

<!DOCTYPE html>

<html>
	<head>
		<title>LogIn</title>

		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/formularios.css" />
		
		<meta charset="utf-8"/>
	</head>

	<body>

		<div id="contenedor">
			
			<?php include RUTA_INCLUDES.'/comun/cabecera.php' ?>
			
			<div id="contenido">   
          		  
				<?php
					$user = isset($_SESSION['usuario']) ? $_SESSION['usuario'] : null;
					if ( $user ) : include (RUTA_INCLUDES.'/comun/ruta.php');
				?>
				<?php
					else :?>
						<h1 align="center">¡Bienvenido!</h1>
						<?php
						formularioLogin();
					endif;
				?>
				
			</div>
			
			<?php include RUTA_INCLUDES.'/comun/pie.php' ?>
			
		<!-- div de contenido -->
		</div>

	</body>

</html>