#ifndef datosUsuario_h
#define datosUsuario_h

#include <string>
#include <fstream>

#include "listaMensajes.h"

/**********************
		TIPOS
**********************/

typedef struct {
	string usuario;
	tListaMensajes buzonMensajes;
} tDatosUsuario;

/***********************
	  PROTOTIPOS
************************/

void inicializarDatosUsuario(tDatosUsuario & datosUsuario, string usuario);
void cargarDatosUsuario(tDatosUsuario & datosUsuario, ifstream & archivo);
void guardarDatosUsuarios(const tDatosUsuario & datosUsuario, ofstream & archivo);
void mostrarDatosUsuarios(const tDatosUsuario & datosUsuario);

#endif
