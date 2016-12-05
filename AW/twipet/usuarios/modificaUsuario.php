<?php 
	require_once '../includes/config.php';
	require RUTA_INCLUDES.'/funcionesUsuarios.php';
	require_once '../includes/formModificaPass.php';
	require_once '../includes/formModificaNom.php';
	require_once '../includes/formModificaApe.php';
	require_once '../includes/formModificaAvatar.php';
	require_once '../includes/formModificaEdad.php';
	require_once '../includes/formModificaDir.php';
	require_once '../includes/formModificaCiudad.php';
	require_once '../includes/formModificaCodPos.php';
	require_once '../includes/formModificaTlf.php';
	require_once '../includes/formModificaDesc.php';
?>
<!DOCTYPE html>
<html>
<head>
		<title>Modifica usuario</title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/formularios.css" />

		<meta charset="utf-8"/>
		
	</head>
<center>


	<body>
	
		<?php include RUTA_INCLUDES.'/comun/cabecera.php'; ?>
		
		<div id="contenido">
		
		<?php
			if (!isset ($_SESSION["login"])){
				include RUTA_INCLUDES.'/comun/accesoRestringido.html';
			}
			else{
				$id=$_SESSION['id'];
			?>

				<img src="<?php echo RUTA_IMG.'/usuarios/'.$_SESSION['avatar']?>" width="200px" class="imgRedondaPerfil alt="img"><h3>Modifica los datos de usuario:</h3>
				
				
				<table width="470px" height="500px">
					
					<?php   formularioModPass();
				
							formularioModNom();
					
							formularioModApe();
							
							formularioModAvatar();
							
							formularioModEdad();
							
							formularioModDir();
							
							formularioModCiudad();
							
							formularioModCodPos();
							
							formularioModTlf();
							
							formularioModDesc();
							
							?>
				</table>
				
		<?php include (RUTA_INCLUDES.'/comun/link.php');
			}?>
			
		</div>
		
		<?php include RUTA_INCLUDES.'/comun/pie.php'; ?>
	</body>
</html>