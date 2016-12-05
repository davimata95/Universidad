<?php

require_once '../includes/config.php';
require_once RUTA_INCLUDES.'/funcionesUsuarios.php'; 
require_once RUTA_INCLUDES.'/funcionesSocial.php'; 
require_once '../includes/formTablon.php';

?>
<!DOCTYPE php>

<html>
	<head>
		<title>tablon</title>
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/menuSocial.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/social.css" />
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
				<div id="perfil">
					<img src= <?php echo RUTA_IMG.'/usuarios/'.$_SESSION['avatar'] ?> class="imgRedondaPerfil" alt=<?php echo $_SESSION['usuario']?>/>
					<div id="informacion">
						<p><b><?php echo $_SESSION['nombre'].' '.$_SESSION['apellidos'] ?> </b></p>
					</div>
				</div>	
							
				<div id="tablon"> 
					
					<?php formularioTablon();
					
					$mysqli = Conexion::getConection();
						
					$publicaciones = obtenerComentarios($mysqli);
					if (isset($publicaciones)){	
						foreach($publicaciones as $registro){ ?>
							<div class="comentario">
								<?php $usuario=obtenerDatos($mysqli, $registro['usuario']); ?>
								<div class="imgTablon">
									<a href="perfilVisita.php?id=<?php echo $usuario['id'] ?>"><img src="<?php echo RUTA_IMG ?>/usuarios/<?php echo $usuario['avatar'] ?>"  class="imgRedondaTablon"/> </a>
								</div>
								
								<div class="textoTablon">
									<b><?php echo $usuario['nombre'] ?> dice:</b>
									<p><?php echo $registro['texto'] ?></p>
								</div>
							</div>
				<?php 	} 
					}
					else{
						echo"<h3>No hay publicaciones </h3>";
					} ?>
	
				</div>
	<?php   } ?>
			
			<?php include RUTA_INCLUDES.'/comun/pie.php'; ?>
			
		</div>
	</body>
</html>