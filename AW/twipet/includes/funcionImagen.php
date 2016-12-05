<?php

function esImagen($src_file_name) {
		
		$supported_image = array(
		'gif',
		'jpg',
		'jpeg',
		'png'
		);

		$ext = strtolower(pathinfo($src_file_name, PATHINFO_EXTENSION)); // Using strtolower to overcome case sensitive
		if (in_array($ext, $supported_image)) {
			return true;
		} else {
			return false;
		}
	}
?>