
	<?php if($_SESSION['tipoUsuario']=='usuario'){ ?>
			<a href="<?php echo RUTA_SOCIAL ?>/social_miPerfil.php" class="botonCabecera"> Finalizar </a>					
	<?php	}
		else if($_SESSION['tipoUsuario']=='admin'){ ?>
			<a href="<?php echo RUTA_USUARIOS ?>/inicioAdmin.php" class="botonCabecera"> Finalizar </a>
	<?php	} 
		else if($_SESSION['tipoUsuario']=='veterinario'){ ?>
			<a href="<?php echo RUTA_USUARIOS ?>/inicioVet.php" class="botonCabecera"> Finalizar </a>
	<?php	} ?>
