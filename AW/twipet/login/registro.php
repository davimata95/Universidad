<?php

require_once '../includes/config.php';
require_once '../includes/formRegistro.php';

?>
<!DOCTYPE html>
<html>
	<head>
		<title>Registro</title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/formularios.css" />
		
		<meta charset="utf-8"/>
		
	</head>
<center>

<body>
	
	<?php include RUTA_INCLUDES.'/comun/cabecera.php' ?>
		
		<div id="contenido">
		
			<?php
					$user = isset($_SESSION['usuario']) ? $_SESSION['usuario'] : null;
					if ( $user ) : include (RUTA_INCLUDES.'/comun/ruta.php');
				?>
					
				<?php
					else :
						?>
						<img src="<?php echo RUTA_IMG ?>/log.png" width="50px" alt="logo"><h3>Rellena el formulario de registro:</h3>
						<?php
						formularioRegistro();
					endif;
				?>
			
		</div>
		
		<?php include RUTA_INCLUDES.'/comun/pie.php' ?>
</body>
</html>
