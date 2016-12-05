<?php

	//Mostramos el producto seleccionado
	function obtenerProducto($mysqli, $idProducto){
		$query="SELECT * 
				FROM productos 
				WHERE id = $idProducto ";
		$resultado=$mysqli->query($query)
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$registro = $resultado->fetch_assoc();		
			$resultado->free();
			
		return $registro;
	}
	
	//Confirmo el producto, cambio el estado de pendiente a subido
	function confirmaProducto($mysqli, $idProducto){
		$query="UPDATE productos 
				SET estado = 'subido' 
				WHERE id = $idProducto";
        
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	
	//obtengo el ultimo producto ingresado en la bd
	function ultimoProducto($mysqli){

		$query= "select Max(id) as mayor from productos";
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
		if(isset($resultado) && $resultado->num_rows>0){
			$row=$resultado->fetch_assoc();
			$num=$row['mayor'];

			$resultado->free();
		}
		return $num;
	}
	
	//Obtengo todos los productos confirmados de la base de datos
	function obtenerProductos($mysqli){
		$query="SELECT  id, nombre, precio, cantidad, estado, imagen 
				FROM productos 
				WHERE estado LIKE 'subido' 
				ORDER BY cantidad DESC";
		$resultado=$mysqli->query($query)  
				or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$result=NULL;
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
				
			$resultado->free();
		
		return $result;
	}	
	
	//Obtengo los productos filtrados por su tipo
	function obtenerProductosTipo($mysqli, $tipo){
		$query="SELECT  id, nombre, precio, cantidad, estado, imagen 
				FROM productos 
				WHERE estado LIKE 'subido' AND tipo LIKE '$tipo'
				ORDER BY cantidad DESC";
		$resultado=$mysqli->query($query)  
				or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			$result=NULL;
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
				
			$resultado->free();
		
		return $result;
	}	
	
	//Obtengo los productos mas vendidos de la base de datos
	function masVendidos($mysqli){		
		$query="SELECT  *
				FROM productos 
				WHERE estado LIKE 'subido' 
				ORDER BY vendidos DESC LIMIT 10";
		$resultado=$mysqli->query($query)  
				or die ($mysqli->error. " en la línea ".(__LINE__-1)); 
			
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}				
			$resultado->free();
		
		return $result;	
	}
	
	//Obtengo otros productos de la base de datos
	function otrosProductos($mysqli, $idProducto, $tipo){
	
		//Obtengo los producto de la base de datos 
		$query="SELECT  *
				FROM productos 
				WHERE id != $idProducto AND estado LIKE 'subido' AND tipo LIKE '$tipo'
				LIMIT 4";
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 

			$result=NULL;
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
				
			$resultado->free();
		
		return $result;
	}
	
	//Sube un producto a la BD
	function subirProducto($mysqli, $vendedor, $nombre, $descripcion, $precio, $cantidad, $vendidos, $img, $tipo, $estado){
	
		$query="INSERT INTO productos (propietario, nombre, descripcion, precio, cantidad, vendidos, imagen, tipo, estado)
				  VALUES ('".$vendedor."', '".$nombre." ', '".$descripcion."
						', '".$precio."', '".$cantidad."', '".$vendidos."', '".$img."
						', '".$tipo."', '".$estado."')";
			$resultado=$mysqli->query($query)  
				or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	
	//Elimina un producto de la BD
	function eliminaProducto($mysqli, $id){
		
		$query="DELETE FROM productos WHERE id = $id";
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));

	}
	
	//Modifica un producto de la BD
	function modificaProducto($mysqli, $cambio, $valor, $id){
		
		$query="UPDATE productos SET $cambio = '$valor' WHERE id = $id";	
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	
	//Funciones del carrito
			
	//Muestra los productos de mi carrito
	function obtenerCarrito($mysqli, $id){
		$query="SELECT * 
				FROM carrito 
				WHERE propietario = $id AND estado LIKE 'pendiente' ";
		
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1)); 

			$result=NULL;
			while ($fila=$resultado->fetch_assoc()){
				$result[]=$fila;
			}
				
			$resultado->free();
		
		return $result;
	}

	//Añade el producto del carrito
	function anadeProducto($mysqli, $idProducto, $propietario){
		$query="UPDATE productos 
				SET cantidad = cantidad-1 
				WHERE id = $idProducto";
		
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
		
		$query="INSERT INTO carrito (propietario, producto, estado)
					VALUES ($propietario, $idProducto, 'pendiente')";
		
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	
	//Quita el producto del carrito
	function quitaProducto($mysqli, $idCarrito, $idProducto){
		$query="UPDATE productos 
				SET cantidad = cantidad+1 
				WHERE id = $idProducto";
		
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
		
		$query="DELETE FROM carrito 
				WHERE id = $idCarrito";
		
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	
	//Confirmamos la compra poniendo los productos a comprados
	function confirmarCompra($mysqli, $carrito, $idProducto){
		$query="UPDATE carrito 
				SET estado = 'comprado' 
				WHERE id = $carrito";
		
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
			
		$query="UPDATE productos 
				SET vendidos = vendidos+1 
				WHERE id = $idProducto";
		
		$resultado=$mysqli->query($query)  
			or die ($mysqli->error. " en la línea ".(__LINE__-1));
	}
	

	

	
?>