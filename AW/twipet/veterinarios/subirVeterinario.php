<?php 
	require_once '../includes/config.php';
	require_once '../includes/formVet.php';
	
?>
<!DOCTYPE html>
<html>
<head>
		<title>Subir veterinario</title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/formularios.css" />

		<meta charset="utf-8"/>
		
	</head>
<center>

<body>
	
	<?php include RUTA_INCLUDES.'/comun/cabecera.php';
		
		if (!isset ($_SESSION["login"])){
			echo"<div id='restringido'> ";
				include RUTA_INCLUDES.'/comun/accesoRestringido.html';
			echo"</div>";
		}
		else{
			?>
			<div id="contenido">
				<img src="<?php echo RUTA_IMG ?>/log.png" width="50px" alt="logo"><h3>Rellena el formulario del Veterinario:</h3>
				<?php formularioVet();?>
			<?php } ?>	
		</div>
		
		<?php include RUTA_INCLUDES.'/comun/pie.php'; ?>
</body>
</html>