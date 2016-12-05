#ifndef servidor_h
#define servidor_h

#include "listaUsuarios.h"

/**********************
		TIPOS
**********************/

typedef struct {
	tListaUsuarios mensajesPendientes;
}tServidor;

/**********************
	  PROTOTIPOS
**********************/

void inicializarServidor(tServidor & servidor, string usuario);

void enviarMensajes(tServidor & servidor, const tListaMensajes & mensajes, string persona);

void recuperarBuzon(tServidor & servidor, tListaMensajes & buzon, string usuario);



#endif