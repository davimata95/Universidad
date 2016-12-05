<?php

require_once '../includes/config.php';
require_once RUTA_INCLUDES.'/funcionesSocial.php'; 
require_once RUTA_INCLUDES.'/funcionesUsuarios.php'; 

?>
<DOCTYPE! html>

<html>
	<head>
		<title>Mensajes</title>
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/menuSocial.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/mensajes.css"  />
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

			?>
				<!-- contenido -->
				<div class="contenido">
				
					<h2>Zona Mensajeria Personal TWIPET</h2>
				
					<div id="foto-info">
						<img src=<?php echo RUTA_IMG.'/usuarios/'.$_SESSION['avatar'] ?>>
						<p> <?php echo $_SESSION['nombre'].' '. $_SESSION['apellidos'] ?> </p>
					</div>

					<div id="campo-mensajes">
						
						<ul id="texto-enviado"> 
							
						<?php 
						$otro=obtenerDatos($mysqli, $_GET['con']);
						$conversacion = conversacion($mysqli, $_SESSION['id'], $_GET['con']);
						
						if(isset ($conversacion)){
							foreach ($conversacion as $mensaje){
								
								if($mensaje['emisor'] == $_SESSION['id']){ ?>
								
									<li class="mensajeSaliente">
										
										<div class="avatar">
											<img src=<?php echo RUTA_IMG.'/usuarios/'.$_SESSION['avatar'] ?> alt="">
										</div>
										<div class="texto">
											<p><?php echo nl2br($mensaje['texto']) ?></p>
										</div>
									</li>
							<?php }
								if($mensaje['receptor'] == $_SESSION['id']){ ?>
									
									<li class="mensajeEntrante">
										
										<div class="avatar">
											<a href = "perfilVisita.php?id=<?php echo $otro['id'] ?>"><img src="<?php echo RUTA_IMG ?>/usuarios/<?php echo $otro['avatar'] ?>" > </a>
										</div>
										
										<div class="texto">
											<p><?php echo nl2br($mensaje['texto']) ?></p>
										</div>

									</li>
						  <?php }
							}
						} ?>

						</ul>
						
						<script>
						
							var objDiv = document.getElementById("texto-enviado");
							objDiv.scrollTop = objDiv.scrollHeight;
						
						</script>
						
						<form method="POST" action="enviaMensaje.php">
							<textarea name="mensaje" rows="3" cols="74" required></textarea>
							<input type="submit" id="boton-enviar" value="Enviar" />
							<input type="hidden" name="cambio" value="mensajes"/>
							<input type="hidden" name="receptor" value="<?php echo $_GET['con'] ?>"/>
						</form>
						
						
					</div>
						
					<div id="foto-info">
						<img src=<?php echo RUTA_IMG.'/usuarios/'.$otro['avatar'] ?>>
						<p> <?php echo $otro['nombre'].' '. $otro['apellidos'] ?> </p>
					</div>
					
				</div>
			
			<?php 
			}
			include RUTA_INCLUDES.'/comun/pie.php'; ?>

		</div>
	</body>
</html>