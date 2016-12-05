<?php

	require_once '../includes/config.php';
	require_once '../includes/formForo.php';
	
?>

<!DOCTYPE html>

<html>
	<head>
		<title>Mensaje Foro</title>
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/foro.css" />
		
		<meta charset="utf-8"/>
	</head>
	
	<body>
	
		<div id="contenedor">
			
			<?php include RUTA_INCLUDES.'/comun/cabecera.php'; 
			
				//Establece si es un tema principal o una contestacion
				if(isset ($_SESSION['login'])){
					if(isset($_POST["respuestas"]))
						$respuestas = $_POST['respuestas'];
					else
						$respuestas = 0;
					if(isset($_POST["identificador"]))
						$identificador = $_POST['identificador'];
					else
						$identificador = 0;
				?>

					<div id="logoForo">
						
						<a href="foro.php"><img src="<?php echo RUTA_IMG ?>/foro/forotwipet.png" width="30%" alt="logoForo"></a>
						
					</div>
					
					<div id="formularioForo">
					
						<h1>Añadir mensaje al foro</h1>
						
						<?php
						
						formularioForo();
						
						?>
					
					</div>
				<?php }
				else{
					echo"<div id='restringido'> ";
						include RUTA_INCLUDES.'/comun/accesoRestringido.html';
					echo"</div>";
				} ?>
			
			<?php include RUTA_INCLUDES.'/comun/pie.php' ?>
			
		</div>
	</body>
</html>