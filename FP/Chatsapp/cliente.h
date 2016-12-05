#ifndef cliente_h
#define cliente_h

#include <iostream>
using namespace std;
#include <string>

#include "listaChats.h"
#include "servidor.h"

/**********************
		TIPOS
**********************/

typedef struct{
	string usuario;
	tListaChats ChatsUsuario;
}tCliente;

/***********************
	  PROTOTIPOS
************************/

void inicializarCliente(tCliente & cliente, string nombre);
void colocarMensajesEnChat(tChat & chat, tMensaje mensaje);
char menuUsuario(tCliente & cliente, tServidor & servidor);
void seleccionFuncion(tCliente & cliente, tServidor & servidor, char opcion);
void crearChat(tCliente & cliente, string nombre, tListaUsuarios & listaUsuarios, tServidor & servidor);

void conversarCon(tCliente & cliente, tServidor & servidor, int posicion);
bool cargarChats(tCliente & cliente, string nombre);
void arrancarCliente(tCliente & cliente, tServidor &servidor);
void buzonAChat(tListaMensajes & buzon, tCliente & cliente);

#endif 