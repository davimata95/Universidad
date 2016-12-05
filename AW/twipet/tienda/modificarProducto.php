<?php 
	require_once '../includes/config.php';
	require RUTA_INCLUDES.'/funcionesTienda.php';
	require RUTA_INCLUDES.'/formModProdNom.php';
	require RUTA_INCLUDES.'/formModProdPrecio.php';
	require RUTA_INCLUDES.'/formModProdTipo.php';
	require RUTA_INCLUDES.'/formModProdCant.php';
	require RUTA_INCLUDES.'/formModProdImg.php';
	require RUTA_INCLUDES.'/formModProdDesc.php';
?>
<!DOCTYPE html>
<html>
<head>
		<title>Modifica producto</title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/formularios.css" />

		<meta charset="utf-8"/>
		
	</head>
<center>


	<body>
	
		<?php include RUTA_INCLUDES.'/comun/cabecera.php';
		
		$mysqli = Conexion::getConection();
	
		if ((!isset ($_SESSION["login"])) || ($_SESSION["tipoUsuario"]=="usuario")){
			echo"<div id='restringido'> ";
				include RUTA_INCLUDES.'/comun/accesoRestringido.html';
			echo"</div>";
		}
		else{
			if(!isset ($_GET['id'])){
				echo"<div id='restringido'> ";
					echo"<h3>No hay ningun producto seleccionado</h3>";
				echo"</div>";
			}
			else{
				$id = $_GET['id'];
				$registro=obtenerProducto($mysqli, $id);
				$estado = $registro['estado'];
				
				if($registro['propietario']!=$_SESSION['id'] && ($_SESSION["tipoUsuario"]!="admin") ){
					header('Location: tienda.php');	
				}
				else{
		?>
					<div id="contenido">
				
						<img src="<?php echo RUTA_IMG.'/tienda/'.$registro['imagen']?>" width="200px" class="imgRedondaPerfil alt="img"><h3>Modifica los datos del producto:</h3>
						
						<table width="470px" height="500px">
							
							<?php   formularioModProdNom($id, $estado); 
							
									formularioModProdPrecio($id, $estado);
									
									formularioModProdTipo($id, $estado);
									
									formularioModProdCant($id, $estado);
									
									formularioModProdImg($id, $estado);
									
									formularioModProdDesc($id, $estado);
							
							?>
						
						</table>
						
						<?php include (RUTA_INCLUDES.'/comun/link.php'); ?>
					</div>
				
			<?php	}
			} 
		}?>	

		<?php include RUTA_INCLUDES.'/comun/pie.php'; ?>
	</body>
</html>