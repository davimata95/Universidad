#ifndef listaMensaje_h
#define listaMensaje_h

#include "mensaje.h"

/**********************
		 TIPOS
**********************/

const int N = 10;

typedef tMensaje tArrayMensajes[N];

typedef struct {
	tArrayMensajes arrayMensajes;
	int contador;
} tListaMensajes;


/**********************
	  PROTOTIPOS
**********************/

void inicializarListaMensajes(tListaMensajes & listaMensajes);
void añadirMensaje(tListaMensajes & listaMensajes, const tMensaje mensaje);
tMensaje consultarUltimoMensaje(const tListaMensajes & listaMensajes);
void cargarListaMensajes(tListaMensajes & listaMensajes, ifstream & archivo);
void guardarListaMensajes(const tListaMensajes & listaMensajes, ofstream & archivo);
void mostrarListaMensajes(const tListaMensajes & listaMensajes);
void eliminarMensajeAntiguo(tListaMensajes & lista);

#endif