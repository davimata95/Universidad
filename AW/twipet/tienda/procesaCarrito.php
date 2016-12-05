<?php
	
	require_once ('../includes/config.php') ;
	require RUTA_INCLUDES.'/funcionesTienda.php'; 
	
	$cambio=$_POST['cambio'];
	
	$mysqli = Conexion::getConection();
	
	if ($cambio == "sumaProducto" ){
		$idProducto=$_POST['idProducto'];
	}
	else if ($cambio == "quitaProducto"){
		$idProducto=$_POST['idProducto'];
		$idCarrito = $_POST['idCarrito'];
	}

	if ($cambio == "sumaProducto"){
		//Lo quitamos (momentaneamente) de la BD para actualizar la disponibilidad del producto
		//Lo añadimos en nuestro carro
		anadeProducto ($mysqli, $idProducto, $_SESSION['id']);
	}
	//Quita un solo producto
	else if ($cambio == "quitaProducto"){
		quitaProducto ($mysqli, $idCarrito, $idProducto);
	}
	
	//Borra todo el carrito
	else if ($cambio == "elimina"){
		
		//Para cada elemento de mi carrito le elimino y vuelvo a sumarlo a la tienda
		foreach (obtenerCarrito ($mysqli, $_SESSION['id']) as $carrito) {
			
			quitaProducto ($mysqli, $carrito['id'], $carrito['producto']);
		}	
	}
	else if ($cambio == "compra"){
		
		//Para cada elemento de mi carrito lo hago permanente
		foreach (obtenerCarrito ($mysqli, $_SESSION['id']) as $carrito) {
			confirmarCompra ($mysqli, $carrito['id'], $carrito['producto']);
		}
		
	}
	
	header('Location: tienda.php');
		
?>