#ifndef listaChats_h
#define listaChats_h

#include <string>
#include <fstream>

#include "listaUsuarios.h"
#include "chat.h"

/**********************
		TIPOS
**********************/

const int M = 5; 

typedef tChat tArrayChats[M];

typedef struct {
	tArrayChats arrayChats;
	string emisor; //como el emisor es siempre el mismo es mas comodo colocarlo aqui
	int contador;
}tListaChats;

typedef enum { nombre, fecha } tOrden; // para ver en que formato ordena la lista

/***********************
	  PROTOTIPOS
************************/

void inicializarListaChats(tListaChats & listaChats, string usuario);
void cargarListaChats(tListaChats & listaChats, string usuario, ifstream & archivo);
void guardarListaChats(tListaChats & listaChats);
int buscarListaChats(tListaChats listaChats, string nombre);
void añadirFinalListaChats(tListaChats & listaChats, const tChat & chat);
void eliminarListaChats(tListaChats & listaChats, const int posicion);
void moverFinalListaChats(tListaChats & listaChats, string posicion);
void ordenarListaChats(tListaChats& ListaChats, tOrden orden);
bool compara(tChat Chat1, tChat Chat2, tOrden orden);

void mostrarListaChats(const tListaChats & listaChats);

#endif 
