#include <iostream>
using namespace std;
#include <fstream>

#include "listaChats.h"


void inicializarListaChats(tListaChats & listaChats, string usuario){
	
	listaChats.emisor = usuario;
	listaChats.contador = 0;
}

void cargarListaChats(tListaChats & listaChats, string usuario, ifstream & archivo){
	string nombre;
	listaChats.emisor = usuario;
	listaChats.contador = 0;
	
	do{
		archivo >> nombre;
		if (nombre != "XXX" && listaChats.contador < M) {
			listaChats.arrayChats[listaChats.contador].receptor = nombre;
			inicializarChat(listaChats.arrayChats[listaChats.contador], usuario);
			cargarChat(listaChats.arrayChats[listaChats.contador], nombre, usuario, archivo);
			listaChats.contador++;
		}
		
	} while (nombre != "XXX" && listaChats.contador < M);
	if (listaChats.contador >= M){
		cout << "No se han podido cargar todos los chats, no hay suficientes espacios." << endl;
		system("pause");
	}
}

void guardarListaChats(tListaChats & listaChats){
	ofstream archivo2;
	archivo2.open(listaChats.emisor + "_chats.txt");
	for (int i = 0; i < listaChats.contador; i++) {
		guardarChat(listaChats.arrayChats[i], archivo2);
	}
	archivo2 << "XXX";
	archivo2.close();
}

int buscarListaChats(tListaChats listaChats, string nombre){

	bool encontrado = false;
	int i = 0;
	while (i < listaChats.contador && !encontrado) {
		if (listaChats.arrayChats[i].receptor == nombre) {
			encontrado = true;
			i--;
		}
		i++;
	}
	if (!encontrado){
		i = -1;
	}
	return i; //devuelve la posicion en la que se encuentra el chat
}

void añadirFinalListaChats(tListaChats & listaChats,const tChat & chat){
	
	listaChats.arrayChats[listaChats.contador] = chat;
	listaChats.contador++;
}

void eliminarListaChats(tListaChats & listaChats, const int posicion){

	for (int i = posicion; i < listaChats.contador; i++){

		listaChats.arrayChats[i] = listaChats.arrayChats[i + 1];
	}
	listaChats.contador--;
}

void moverFinalListaChats(tListaChats & listaChats, int posicion){

	tChat aux = listaChats.arrayChats[posicion];
	eliminarListaChats(listaChats, posicion);
	añadirFinalListaChats(listaChats, aux);
} 

void ordenarListaChats(tListaChats& ListaChats, tOrden orden){

	for (int i = 0; i < ListaChats.contador - 1; i++){
		int menor = i;
		for (int j = i + 1; j < ListaChats.contador; j++){
			if (compara(ListaChats.arrayChats[j], ListaChats.arrayChats[i], orden)){
				menor = j;
			}
		}
		if (menor > i){
			tChat aux = ListaChats.arrayChats[i];
			ListaChats.arrayChats[i] = ListaChats.arrayChats[menor];
			ListaChats.arrayChats[menor] = aux;
		}
	}
}

bool compara(tChat Chat1, tChat Chat2, tOrden orden){

	bool resultado;
	tMensaje mensaje1;
	tMensaje mensaje2;
	if (orden == fecha){
		resultado = consultarUltimoMensaje(Chat1.conversacion).fecha < consultarUltimoMensaje(Chat2.conversacion).fecha;
	}
	else{
		resultado = Chat1.emisor < Chat2.emisor;
	}
	return resultado;
}

//Extras

void mostrarListaChats(const tListaChats & listaChats) {

	for (int i = 0; i < listaChats.contador; i++) {
		mostrarChat(listaChats.arrayChats[i]);
	}
}