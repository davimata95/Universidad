#ifndef mensaje_h
#define mensaje_h

#include <iostream>
using namespace std;
#include <string>

/**********************
		TIPOS
**********************/

typedef time_t tFecha;

typedef struct {
	string emisor;
	string receptor;
	tFecha fecha;
	string texto;
} tMensaje;

/**********************
	   PROTOTIPOS
**********************/

void crearNuevoMensaje(string emisor, string receptor, tMensaje & mensaje);
void cargarMensaje(tMensaje & mensaje, ifstream & archivo);
void guardarMensaje(const tMensaje & mensaje, ofstream & archivo);
void mostrarMensaje(const tMensaje mensaje);
string mostrarFecha(tFecha fecha);

#endif