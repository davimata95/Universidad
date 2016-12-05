#include <iostream>
using namespace std;
#include <iomanip>
#include <fstream>

#include "chat.h"

/*
int main() {

tChat chat;
tListaUsuarios listaUsuarios;
tDatosUsuarios datosUsuarios;
tListaMensajes listaMensajes;
tMensaje mensaje;

chat.emisor = "pepe";

ifstream archivo;
archivo.open("pepe_chats.txt");
cargarChat(archivo, chat);
archivo.close();
mostrarChat (chat);

ofstream archivo2;
archivo2.open("pepe_chats.txt");
guardarChat(archivo2, chat);
archivo.close();

ifstream archivo3;
archivo3.open("usuarios.txt");
cargarListaUsuarios(archivo3, listaUsuarios);
archivo3.close();
mostrarCabecera(chat.emisor, listaUsuarios);

inicializarChat(chat.conversacion.contador);
mostrarChat(chat);

system("pause");
return 0;
}
*/

void inicializarChat(tChat & chat, string usuario) {
	chat.emisor = usuario;
	inicializarListaMensajes(chat.conversacion);
}

void cargarChat(tChat & chat, string usuario, string emisor, ifstream & archivo) {
	chat.receptor = usuario;
	chat.emisor = emisor;
	inicializarListaMensajes(chat.conversacion);
	cargarListaMensajes(chat.conversacion, archivo);
}

void guardarChat(tChat & chat, ofstream & archivo) {

	archivo << chat.receptor << endl;
	guardarListaMensajes(chat.conversacion, archivo);
}

void mostrarCabecera(){

	pintarRaya(rara);
	cout << endl;
	cout << "   Entrar al chat N: c N <intro>                Crear nuevo chat: n <intro>" << endl;
	cout << "   Eliminar chat de N: e N <intro>    Ordenar chats por nombre: o n <intro>" << endl;
	cout << "   Ordenar chats por fecha: o f <intro>                    salir: s <intro>" << endl;
	cout << endl;
	pintarRaya(rara);
}

void mostrarChat(const tChat & chat) {
	cout << endl;
	pintarRaya(rara);
	cout << setw(35) << right << "Chat de " << chat.emisor << " con " << chat.receptor << endl;
	pintarRaya(rara);
	cout << endl;
	for (int i = 0; i < chat.conversacion.contador; i++) {
		if (chat.conversacion.arrayMensajes[i].emisor != chat.receptor) {
			cout << setw(20) << right;
		}
		else {
			cout << left;

		}
		mostrarMensaje(chat.conversacion.arrayMensajes[i]);
		pintarRaya(discontinua);
	}
}

void pintarRaya(const int tipo) {

	for (int i = 0; i < 80; i++) {
		cout << char(tipo);
	}
}
