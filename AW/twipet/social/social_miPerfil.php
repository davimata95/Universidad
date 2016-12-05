<?php

require_once '../includes/config.php'; 
require_once RUTA_INCLUDES.'/funcionesUsuarios.php'; 
require_once RUTA_INCLUDES.'/funcionesSocial.php'; 


?>
<DOCTYPE! html>

<html>
	<head>
		<title>Mi perfil</title>
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/menuSocial.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/social.css"  />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		
		
		<meta charset="utf-8"/>
	</head>

	<body>

		<div id="contenedor">
		
			<?php 
			include RUTA_INCLUDES.'/comun/cabecera.php'; 
				
			if (empty ($_SESSION["login"])){
				header('Location: '.RUTA_LOGIN.'/login.php');
			}
			else{	
			?>

			<!-- Contenedor Principal -->
			<div id="contenidoMiPerfil">
				
				<?php
				$mysqli = Conexion::getConection();
				$usuario=obtenerDatos($mysqli, $_SESSION["id"]); 

				if ((!isset ($_SESSION["login"]))){
					include RUTA_INCLUDES.'/comun/accesoRestringido.html';
				}
				else{ ?>
					

					<!-- Foto perfil e info. -->
					<div id="foto-info">
						<p><img src= "<?php echo RUTA_IMG.'/usuarios/'.$_SESSION['avatar'] ?>"  alt="<?php echo $_SESSION['usuario']?>" /></p>
						
						<div id="info">
							<p> <h3> <?php echo $_SESSION['nombre'] ?> </h3>
							
							<p><?php echo nl2br($usuario['descripcion'])?></p>
						
							<a href="<?php echo RUTA_USUARIOS ?>/modificaUsuario.php" class="botonCabecera">Modificar usuario</a>
						</div>
						
					</div>
					
					<div id="mensajeria">

						<h1> Mis mensajes </h1>

						<div id="mensajesNuevos">
						
						<?php
						
							$ultimosMensajes = obtenerUltimosMensajes($mysqli, $_SESSION['id']);		
									
							if (isset($ultimosMensajes)){	?>

								<ul id="listaNuevos">
								
							<?php foreach($ultimosMensajes as $mensajes){ 
									$usuario=obtenerDatos($mysqli, $mensajes['emisor']); ?>
									<li> 
										<a href="mensajes.php?con=<?php echo $mensajes['emisor'] ?>" class="avatar"><img src="<?php echo RUTA_IMG ?>/usuarios/<?php echo $usuario['avatar'] ?>" alt=""></a>
										<p><?php echo $usuario['nombre'] ?> <?php echo $usuario['apellidos'] ?> </p>
									</li>	
							<?php 	}
								echo "</ul>";
							}
							else{
								echo"<h3>No tienes mensajes </h3>";
							}
							?>
						</div>
					</div>

					<div id="publicaciones">
					
						<div id="mis-publis"> Últimas publicaciones </div> 
						
						<?php
							$misPublicaciones = obtenerMisComentarios($mysqli, $_SESSION['id']);
						
					 if (isset ($misPublicaciones)){	?>
							<!-- Comentarios -->
							<ul id="listaPublicaciones">
								<li>
							
							<?php	foreach($misPublicaciones as $registro){ ?>
										<div class="main-level">
											
											<!-- Avatar -->
											<div class="avatar"><img src=<?php echo RUTA_IMG.'/usuarios/'.$_SESSION['avatar'] ?>></div>

											<!-- Contenedor del Comentario -->
											<div class="publicacion">
												<div class="cabeceraPublicacion">
													<h4> <?php echo $_SESSION['nombre'] .' ' . $_SESSION["apellidos"] ?></h4>
												</div>
												<div class="contenidoPublicacion">
													<p><?php echo $registro['texto'] ?></p>
												</div>
											</div>
										</div>	
							<?php   } ?>
								</li>

							<!-- Fin de la lista de comentarios-->
							</ul>
					<?php } 
							else{
								echo"<h3>No tienes publicaciones </h3>";
							} ?>
					<!-- Final de del contenedor-->
					</div>

		<?php   } 
			}?>		
			</div>
			
			<?php include RUTA_INCLUDES.'/comun/pie.php'; ?>
			
		</div>
	</body>

</html>