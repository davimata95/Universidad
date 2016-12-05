<?php 
	require_once '../includes/config.php';
	require RUTA_INCLUDES.'/funcionesMascotas.php';
	require_once '../includes/formModMasNom.php';
	require_once '../includes/formModMasRaza.php';
	require_once '../includes/formModMasEdad.php';
	require_once '../includes/formModMasSex.php';
	require_once '../includes/formModMasImg.php';
	require_once '../includes/formModMasDesc.php';
	require_once '../includes/formModMasEst.php';
?>
<!DOCTYPE html>
<html>
<head>
		<title>Modifica mascota</title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/formularios.css" />

		<meta charset="utf-8"/>
		
	</head>
<center>


	<body>
	
		<?php include RUTA_INCLUDES.'/comun/cabecera.php';
		
		$mysqli = Conexion::getConection();
		
		if (!isset ($_SESSION["login"])){
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
				$estado = $registro['estado'];
			
				if($registro['propietario']!=$_SESSION['id'] && $_SESSION['tipoUsuario']!='admin' ){
					echo"<div id='restringido'> ";
						echo"<h3> No eres el propietario de esta mascota </h3>";
					echo"</div>";
				}
				else{
					if($_SESSION['tipoUsuario']=='admin' && $registro['propietario']!=$_SESSION['id']){
						echo"<div id='restringido'> ";
							echo"<h3> Ten en cuenta que esta no es tu mascota </h3>";
						echo"</div>";
				}
				?>
				
					<div id="contenido">
						<img src="<?php echo RUTA_IMG.'/adopta/'.$registro['imagen']?>" width="200px" class="imgRedondaPerfil alt="img"><h3>Modifica los datos de tu perro:</h3>
						
						<table width="470px" height="500px">
							
							<?php   formularioModMasNom($id, $estado); 
									
									formularioModMasRaza($id, $estado);
									
									formularioModMasEdad($id, $estado);
									
									formularioModMasSex($id, $estado);
									
									formularioModMasImg($id, $estado);
									
									formularioModMasDesc($id, $estado);
									
									formularioModMasEst($id, $estado);
							
							
							?>

						</table>
					
					<?php
						include (RUTA_INCLUDES.'/comun/link.php');
					
					 } 
				}
			}?>	
		</div>
		
		<?php include RUTA_INCLUDES.'/comun/pie.php'; ?>
	</body>
</html>