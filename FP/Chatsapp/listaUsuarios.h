#ifndef listaUsuarios_h 
#define listaUsuarios_h

#include <iostream>
using namespace std;

#include "datosUsuario.h"

/**********************
		TIPOS
**********************/

typedef tDatosUsuario arrayDatosUsuario[N];

typedef struct {
	arrayDatosUsuario arrayDatosUsuario;
	int contador;
} tListaUsuarios;

/***********************
	   PROTOTIPOS
************************/

void inicializarListaUsuarios(tListaUsuarios & listaUsuarios);
bool cargarListaUsuarios(tListaUsuarios & listaUsuarios);
void guardarListaUsuarios(const tListaUsuarios & listaUsuarios);
int buscarListaUsuarios(const tListaUsuarios & listaUsuarios, string nombre);
void insertarUsuario(tListaUsuarios & listaUsuarios, const tDatosUsuario & datosUsuarios);
void mostrarListaUsuarios(const tListaUsuarios & listaUsuarios);

#endif 