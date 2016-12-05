<?php 
	require_once '../includes/config.php';
	require RUTA_INCLUDES.'/funcionesMascotas.php';
?>
<!DOCTYPE html>
<html>
	<head>
		<title>Subir perro</title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/formularios.css" />

		<meta charset="utf-8"/>
	</head>
	
	<center>

	<body>
		
		<div id="contenedor">
			
			<?php include RUTA_INCLUDES.'/comun/cabecera.php'; 
			
			$mysqli = Conexion::getConection();

			
			//Si soy el propietario de la mascota
			if ((!isset ($_SESSION["login"])) || ($_SESSION["tipoUsuario"]=="usuario")){
				echo"<div id='restringido'> ";
					include RUTA_INCLUDES.'/comun/accesoRestringido.html';
				echo"</div>";
			}
			else{
				
				if(!isset ($_GET['id'])){
					echo"<div id='restringido'> ";
						echo"<h3>No hay ninguna mascota seleccionada</h3>";
					echo"</div>";
				}
				else{
									
					$id = $_GET['id'];
					$registro=mostrarMascota($mysqli, $id);
						
					if($registro['propietario'] != $_SESSION['id'] && $_SESSION['tipoUsuario'] != 'admin'){
						echo"<h3> No eres el propietario de esta mascota </h3>";
					}
					else{	
					?>
					<div id="contenidoMostrar">
						<img src="<?php echo RUTA_IMG ?>/log.png" width="50px" alt="logo"><h3> Está a punto de poner en adopción a: </h3>

						<p><img src= <?php echo RUTA_IMG.'/adopta/' .$registro['imagen'] ?>  class="imgRedondaPerfil"> </p>
						<p><b>Nombre:</b> <?php echo ($registro['nombre']) ?> </p>
						<p><b>Raza:</b> <?php echo ($registro['raza']) ?> </p>
						<p><b>Edad:</b> <?php echo ($registro['edad']) ?> años</p>
						<p><b>Sexo:</b> <?php echo ($registro['sexo']) ?> </p>
						<p><b>Descripción:</b> <?php echo nl2br($registro['descripcion']) ?> </p>
							
						<p><?php if ($registro['estado']=="pendiente")
							echo ("<b>Adoptar: </b>" . $registro['estado']) ;
						?></p>
						<a href="modificarMascota.php?id=<?php echo $id ?>"> <img src="<?php echo RUTA_IMG ?>/modificar.png" alt="modificar" width="50%"></a>

					</div>
					
					<div id="confirmar">

						<h3> Recuerda que un perro no es un juguete </h3>
						
						<?php 
							if ($registro['estado']=="pendiente")
								echo "<h4> ¿Estás seguro de que deseas ponerlo en adopción? </h4>";
							else
								echo "<h4> Cuida bien de " . $registro['nombre'] . " y disfruta de Twipet </h4>";
						?>
						
						<div id="botonesConfirmacion">
						

						<?php if($_SESSION['tipoUsuario']=='usuario'){
								if ($registro['estado']=="pendiente"){
									$rutaSi=RUTA_USUARIOS.'/procesaPendiente.php?tipo=mascota&id='.$id ;
								}
								else{
									$rutaSi=RUTA_MASCOTAS.'/adoptarMascota.php?id='.$id ;
								}
									$rutaNo=RUTA_SOCIAL.'/social_miPerfil.php';
							}
							else if($_SESSION['tipoUsuario']=='admin'){
								$rutaSi=RUTA_USUARIOS.'/procesaPendiente.php?tipo=mascota&id='.$id ;
								$rutaNo=RUTA_USUARIOS.'/inicioAdmin.php';
							} 
							else if($_SESSION['tipoUsuario']=='veterinario'){
								$rutaSi=RUTA_USUARIOS.'/procesaPendiente.php?tipo=mascota&id='.$id ;
								$rutaNo=RUTA_USUARIOS.'/inicioVet.php';
							} 
											
							?>
							
							<a href = <?php echo $rutaSi ?> class='botonCabecera' > Subir mascota </a>
							<a href = <?php echo $rutaNo ?> class="botonCabecera" > Cancelar proceso </a>

						</div>
					</div>	
			
				<!--Cierra el else, si el usuario esta registrado-->
	 <?php 		}	
			  }
			} ?>		
			
			<?php include RUTA_INCLUDES.'/comun/pie.php'; ?>
		</div>
	</body>	
</html>






