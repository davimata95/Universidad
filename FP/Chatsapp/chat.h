#ifndef chat_h
#define chat_h

#include <string>
#include <fstream>

#include "listaUsuarios.h"

/**********************
	  CONSTANTES
las incluyo para pintar las rayas
**********************/

const int continua = 196;
const int discontinua = 240;
const int rara = 175;

/**********************
		TIPOS
**********************/

typedef struct {
	string receptor;
	string emisor;
	tListaMensajes conversacion;
}tChat;

/***********************
	   PROTOTIPOS
************************/

void inicializarChat(tChat & chat, string usuario);
void cargarChat(tChat & chat, string usuario,string emisor, ifstream & archivo);
void guardarChat(tChat & chat,ofstream & archivo);
void mostrarCabecera();
void mostrarChat(const tChat & chat);
//Extra para ahorrar lineas de codigo
void pintarRaya(const int tipo);

#endif