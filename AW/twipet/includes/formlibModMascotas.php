<?php

require_once ('config.php') ;

//
// Funciones genéricas de gestión de formularios
//

/**
 * Se encarga de orquestar todo el proceso de creación y procesamiento de un formulario web.
 *
 * @param string $formId Cadena utilizada como valor del atributo "id" de la etiqueta &lt;form&gt; asociada al formulario y como parámetro a comprobar para verificar que el usuario ha enviado el formulario.
 *
 * @param string $action URL asociada al atributo "action" de la etiqueta &lt;form&gt; del fomrulario y que procesará el envío del formulario.
 *
 * @param callable $generaCamposFormulario Función que devuelve un <code>string</code> con el HTML necesario para presentar los campos del formulario. Es necesario asegurarse que como parte del envío se envía un parámetro con nombre <code$formId</code> (i.e. utilizado como valor del atributo name del botón de envío del formulario).
 *
 * @param callable $procesaFormulario Función que procesa los datos del formulario.
 *
 * @param string $class (opcional) Valor del atributo "class" de la etiqueta &lt;form&gt; asociada al formulario. Si este parámetro incluye la cadena "nocsrf" no se generá el token CSRF para este formulario.
 */
function formulario($formId, $generaCamposFormulario, $procesaFormulario, $campo1=null, $campo2=null) {

   /* Se valida la existencia de sendas funciones necesarias para generar, validar y procesar el formulario
   */  
  if ( ! is_callable($generaCamposFormulario) ) {
    throw new Exception('Se esperaba una función en $generaCamposFormulario');
  }
  
  if ( ! is_callable($procesaFormulario) ) {
    throw new Exception('Se esperaba una función en $procesaFormulario');
  }

  if ( ! formularioEnviado($_POST, $formId) ) {
    echo generaFormulario($formId, $generaCamposFormulario, $campo1, $campo2);
  } else {
    $result = $procesaFormulario($_POST);
    if ( is_array($result) ) {
      // Error al procesar el formulario, volvemos a mostrarlo
      echo generaFormulario($formId, $generaCamposFormulario, $campo1, $campo2, $result, $_POST);
    } else {

		header('Location: '.$result);
    }
  }
}

/**
 * Función que verifica si el usuario ha enviado el formulario. Comprueba si existe el parámetro <code>$formId</code> en <code>$params</code>.
 *
 * @param array $params Array que contiene los datos recibidos en el envío formulario.
 *
 * @param string $formId Nombre del parámetro a verificar.
 *
 * @return boolean Devuelve <code>TRUE</code> si <code>$formId</code> existe como clave en <code>$params</code>
 */
function formularioEnviado(&$params, $formId) {
  return isset($params[$formId]);
} 

/**
 * Función que genera el HTML necesario para el formulario.
 *
 * @param string $formId Cadena utilizada como valor del atributo "id" de la etiqueta &lt;form&gt; asociada al formulario y como parámetro a comprobar para verificar que el usuario ha enviado el formulario.
 *
 * @param string $class (opcional) Valor del atributo "class" de la etiqueta &lt;form&gt; asociada al formulario. Si este parámetro incluye la cadena "nocsrf" no se generá el token CSRF para este formulario.
 *
 * @param array $errores (opcional) Array con los mensajes de error de validación y/o procesamiento del formulario.
 *
 * @param array $datos (opcional) Array con los valores por defecto de los campos del formulario.
 */
function generaFormulario($formId, $generaCamposFormulario, $campo1=null, $campo2=null, $errores = array(), &$datos = array()) {
  
  $action = RUTA_MASCOTAS."/modificarMascota.php?id=$campo1";
  $html= '';
  $numErrores = count($errores);
  if (  $numErrores == 1 ) {
    $html .= "<ul><li>".$errores[0]."</li></ul>";
  } else if ( $numErrores > 1 ) {
    $html .= "<ul><li>";
    $html .= implode("</li><li>", $errores);
    $html .= "</li></ul>";
  }

  $html .= '<form method="POST" action="'.$action.'" id="'.$formId.'">';
  
  $html .= $generaCamposFormulario($datos, $campo1, $campo2);
  $html .= '</form>';
  return $html;
}

?>