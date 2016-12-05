<?php 
	require_once '../includes/config.php';
	require RUTA_INCLUDES.'/funcionesVet.php';
	require_once '../includes/formModVetNom.php';
	require_once '../includes/formModVetHoSem.php';
	require_once '../includes/formModVetHoFin.php';
	require_once '../includes/formModVetTel.php';
	require_once '../includes/formModVetImg.php';
	require_once '../includes/formModVetDir.php';
	require_once '../includes/formModVetCodPos.php';
?>
<!DOCTYPE html>
<html>
<head>
		<title>Modifica veterinario</title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/formularios.css" />

		<meta charset="utf-8"/>
		
	</head>
<center>


	<body>
	
		<?php include RUTA_INCLUDES.'/comun/cabecera.php';
		
		$mysqli = Conexion::getConection();
		
		if ($_SESSION["tipoUsuario"]!="admin"){
			echo"<div id='restringido'> ";
				include RUTA_INCLUDES.'/comun/accesoRestringido.html';
			echo"</div>";
		}
		else{
		
			if(!isset ($_GET['id'])){
				echo"<div id='restringido'> ";
					echo"<h3>No hay ningún veterinario seleccionado</h3>";
				echo"</div>";
			}
			else{

				$id = $_GET['id'];
				$registro=obtenerVeterinario($mysqli, $id);
			
				?>
				
					<div id="contenido">
						<img src="<?php echo RUTA_IMG.'/veterinarios/'.$registro['imagen']?>" width="200px" class="imgRedondaPerfil alt="img"><h3>Modifica los datos del veterinario:</h3>
						
						<table width="470px" height="500px">
							
							<?php   formularioModVetNom($id); 
									
									formularioModVetHoSem($id); 
									
									formularioModVetHoFin($id); 
																
									formularioModVetDir($id);
									
									formularioModVetTlf($id);
									
									formularioModVetImg($id);

									formularioModVetCodPos($id);
							
							?>

						</table>
					
					<?php
						include (RUTA_INCLUDES.'/comun/link.php');
					
					 } 
				}
			?>	
		</div>
		
		<?php include RUTA_INCLUDES.'/comun/pie.php'; ?>
	</body>
</html>