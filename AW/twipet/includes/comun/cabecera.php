<div id="cabecera">
	<nav>
		<ul id="lista">
			<li>
				<?php
				
					if (isset ($_SESSION["tipoUsuario"])){
						if ($_SESSION["tipoUsuario"]=="veterinario"){ ?>
							<a href="<?php echo RUTA_USUARIOS ?>/inicioVet.php" class="botonInicio"><img src="<?php echo RUTA_IMG ?>/logotipo.png" width="110px" alt="logo"> </a>
				<?php	}
						else if ($_SESSION["tipoUsuario"]=="usuario"){ ?>
							<a href="<?php echo RUTA_SOCIAL ?>/social_tablon.php" class="botonInicio"><img src="<?php echo RUTA_IMG ?>/logotipo.png" width="110px" alt="logo"> </a>							
				<?php	}
						else if ($_SESSION["tipoUsuario"]=="admin"){ ?>
							<a href="<?php echo RUTA_USUARIOS ?>/inicioAdmin.php" class="botonInicio"><img src="<?php echo RUTA_IMG ?>/logotipo.png" width="110px" alt="logo"> </a>
				<?php	}
					}					
					else{ ?>
						<a href="<?php echo RUTA_LOGIN ?>/login.php" class="botonInicio"><img src="<?php echo RUTA_IMG ?>/logotipo.png" width="110px" alt="logo"> </a>
			<?php	} ?>
				
			</li>
			<li> <a href="<?php echo RUTA_SOCIAL ?>/social_tablon.php" class="botonCabecera">Social</a> </li>
			<li> <a href="<?php echo RUTA_VETERINARIOS ?>/veterinarios.php" class="botonCabecera">Veterinarios</a> </li>
			<li> <a href="<?php echo RUTA_TIENDA ?>/tienda.php" class="botonCabecera">Tienda</a> </li>
			<li> <a href="<?php echo RUTA_MASCOTAS ?>/adopta.php" class="botonCabecera">Adopta</a> </li>
			<li> <a href="<?php echo RUTA_FORO ?>/foro.php" class="botonCabecera">Foro</a> </li>
									
			<div id=registrado>
			<?php
				if(isset($_SESSION["login"])&&($_SESSION["login"] == true)){ ?>
					<p> Bienvenido <?php echo $_SESSION["usuario"] ?> <a href= "<?php echo RUTA_LOGIN ?>/logout.php" class="botonCabecera">Logout</a> </p>
		<?php	}
				else{ ?>
					<p> ¿No estas registrado? <a href=" <?php echo RUTA_LOGIN ?>/login.php" class="botonCabecera">Login</a></p>
		<?php	} ?>
			</div>
		</ul>
	</nav>
</div>

<nav>
	<ul id="menuSocial">
		<li><a href="<?php echo RUTA_SOCIAL ?>/social_miPerfil.php" >Mi perfil</a></li>
		<li><a href="<?php echo RUTA_SOCIAL ?>/social_tablon.php">Tablón</a></li>
		<li><a href="<?php echo RUTA_SOCIAL ?>/social_conoce.php">Conoce</a></li>
		<li><a href="<?php echo RUTA_SOCIAL ?>/misMascotas.php">Mis mascotas</a></li>
	</ul>
</nav>
