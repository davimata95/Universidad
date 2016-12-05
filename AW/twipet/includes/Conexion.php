<?php


require_once ('config.php') ;

/**
 * Clase de  de gestión de formularios.
 *
 * Gestión de token CSRF está basada en: https://www.owasp.org/index.php/PHP_CSRF_Guard
 */
class Conexion {
	
	
	
	
	
	private static $mysqli;
	
	


	
	

  /**
   * Se encarga de orquestar todo el proceso de creación y procesamiento de un formulario web.
   *
   * @param string $formId Cadena utilizada como valor del atributo "id" de la etiqueta &lt;form&gt; asociada al formulario y como parámetro a comprobar para verificar que el usuario ha enviado el formulario.
   *
   * @param string $action (opcional) URL asociada al atributo "action" de la etiqueta &lt;form&gt; del fomrulario y que procesará el envío del formulario. Por defecto la URL es $_SERVER['PHP_SELF']
   *
   * @param string $class (opcional) Valor del atributo "class" de la etiqueta &lt;form&gt; asociada al formulario. Si este parámetro incluye la cadena "nocsrf" no se generá el token CSRF para este formulario.
   *
   * @param string enctype (opcional) Valor del parámetro enctype del formulario.
   */
  public function __construct( $hostname, $usuario, $password, $baseDatos ) {
	  

	
		if ( !self::$mysqli )
	   {
		   
				  
				  self::$mysqli = new mysqli($hostname, $usuario, $password, $baseDatos);
			
			if ( self::$mysqli->connect_errno) {
				echo "Fallo al conectar a MySQL: (" . self::$mysqli->connect_errno . ") " . self::$mysqli->connect_error ;
			}
			
			if(!self::$mysqli->set_charset("utf8"))
			{
				printf("<hr>Error loading character set utf8 (Err. nº %d): %s\n<hr/>",	self::$mysqli->errno, self::$mysqli->error);
				exit();
			}
			
			ini_set('default_charset', 'UTF-8');
				  
		}else
		{

		}
		
		 if ( !self::$mysqli )
	  {
		  
		  
		  echo "fail";
	  }
		  
  
  
	}
  
  public static function getConection()
  {
	  
	  if ( !self::$mysqli )
	  {
		  
		  
		  echo "fail tremendo";
	  }
	  return self::$mysqli;
  }
  
}
?>
