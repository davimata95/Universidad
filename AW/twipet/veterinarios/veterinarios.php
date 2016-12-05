<?php

require_once '../includes/config.php';
require RUTA_INCLUDES.'/funcionesVet.php'; 
?>
<!DOCTYPE html>
<html>
	<head>
	 
		<title> Veterinarios </title>
		
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/navegacion.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo RUTA_CSS ?>/veterinarios.css" />
		
		<meta charset="utf-8"/>
	</head>
  
	<body>
	
		<div id="contenedor">
			
			<?php include RUTA_INCLUDES.'/comun/cabecera.php';  
				$mysqli = Conexion::getConection();
			?>
			
			<div id="contenido">

				<form method="GET">
					<label onchange='this.form.submit()' name="cod" id="buscar">

						Filtrar por Código postal --> <input type="text"  name="cod" maxlength="5" size="30" required />
						
					</label>	

					<input type="reset" id="boton-enviar" onClick="document.location.href='veterinarios.php'" value="Quitar filtrado" /> 
				</form>
			
				<?php
			
				if(isset ($_GET['cod'])){
					$veterinarios=obtenerVetCodigo($mysqli, $_GET['cod']);
				}
				else{
					$veterinarios=obtenerVeterinarios($mysqli);
				}
				
				if (isset ($veterinarios)){
					
					foreach($veterinarios as $registro){ ?>
					
						<div class='col'>
					
							<img src= <?php echo RUTA_IMG.'/veterinarios/' .$registro['imagen'] ?> width='200' height='200' alt=<?php echo $registro['nombre']?>>
							<p><b><?php echo $registro['nombre']?></b></p>
							<p><?php echo $registro['direccion']?> </p>
							<p><?php echo $registro['codPostal']?> </p>
							
							<a href='perfilVeterinario.php?id=<?php echo $registro['id']?>' class='boton'> Ver veterinario</a>
					<?php	
						echo "</div>";	
					}
				}
				else{ ?>
					
					<div id="noElementos">
				
						<h3> En estos momentos no disponemos de veterinarios para mostrarte </h3>
				
					</div>
			<?php	}
		?>
			</div>

			<?php include RUTA_INCLUDES.'/comun/pie.php' ?>
			
		</div>
	</body>
</html>