<?php

require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesUsuarios.php';
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
			
			$mysqli = Conexion::getConection();
				
			if (empty ($_SESSION["login"])){
				
				echo"<div id='restringido'> ";
					include RUTA_INCLUDES.'/comun/accesoRestringido.html';
				echo"</div>";
			}
			else{	
				
				//Obtengo el usuario de la base de datos
				$visita=obtenerDatos($mysqli, $_GET['id']);

			?>
			
				<!-- Contenedor Principal -->
				<div id="contenidoVisita">
					
					<!-- Foto perfil e info. -->
					<div id="usuario">	
						<div id="foto-info">
							<img src="<?php echo RUTA_IMG ?>/usuarios/<?php echo $visita['avatar']?>">
							<p><?php echo $visita['nombre'].' '. $visita['apellidos']?></p>
							<p><a href="mensajes.php?con=<?php echo $_GET['id'] ?> " class="boton"> Enviar mensaje </a></p> 					
				
							<p> Ciudad: <?php echo $visita['ciudad']?></p>
							<p> Edad: <?php echo $visita['edad']?> años</p>
							<p><?php echo nl2br($visita['descripcion'])?></p>
						</div>
						<div id="mascotas">
						
						<?php
							$mascotas = mostrarMisMascotas($mysqli, $_GET['id']);
							if(isset ($mascotas)){ ?>
								<h3>Estas son las mascotas que tiene <?php echo $visita['nombre']?>:</h3>
							
						<?php	foreach($mascotas as $registro){ ?>
									<div class="box_elementos">
										<a href="<?php echo RUTA_MASCOTAS ?>/adoptarMascota.php?id= <?php echo $registro['id'] ?> "><img src=<?php echo RUTA_IMG.'/adopta/'.$registro['imagen'] ?> class="imgRedondaElemento" width="100%" alt="conoce2"></a>
										<p><strong> <?php echo $registro['nombre'] ?></strong></p>
										<p>Raza: <?php echo $registro['raza'] ?></p>
										<p><?php echo $registro['edad'] ?> Años </p>	
									</div>
						  <?php } 
							} 
							else{?>
								<h3><?php echo $visita['nombre']?> no tiene mascotas </h3>
					  <?php } ?>
						</div>
					</div>

					<div id="publicaciones">
					
						<div id="mis-publis"> Últimas publicaciones </div> 
						
						<?php
							$publicaciones = obtenerMisComentarios($mysqli, $visita['id']);
						
							if (isset ($publicaciones)){	?>
								<!-- Comentarios -->
								<ul id="listaPublicaciones">
									<li>
								
								<?php	foreach($publicaciones as $registro){ ?>
											<div class="main-level">
												<?php $usuario=obtenerDatos($mysqli, $registro['usuario']); ?>
												<!-- Avatar -->
												<div class="avatar"><img src=<?php echo RUTA_IMG.'/usuarios/'.$usuario['avatar'] ?>></div>

												<!-- Contenedor del Comentario -->
												<div class="publicacion">
													<div class="cabeceraPublicacion">
														<h4> <?php echo $usuario['nombre'] .' ' . $usuario["apellidos"] ?></h4>
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
				<!-- Final de del contenedor-->	
				</div>
			
			<?php }
			include RUTA_INCLUDES.'/comun/pie.php'; ?>
			
		</div>
	</body>

</html>