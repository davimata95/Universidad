#include <iostream>
using namespace std;
#include <fstream>
#include <iomanip>

#include "listaMensajes.h" 

void inicializarListaMensajes(tListaMensajes & listaMensajes) {

	listaMensajes.contador = 0;
}

void añadirMensaje(tListaMensajes & listaMensajes, const tMensaje mensaje) {
	
	listaMensajes.arrayMensajes[listaMensajes.contador] = mensaje;
	listaMensajes.contador++;
}

tMensaje consultarUltimoMensaje(const tListaMensajes & listaMensajes){

	return listaMensajes.arrayMensajes[listaMensajes.contador - 1]; //-1 porque el array empieza en 0
}

void cargarListaMensajes(tListaMensajes & listaMensajes, ifstream & archivo) {

	archivo >> listaMensajes.contador;
	for (int i = 0; i < listaMensajes.contador; i++) {
		cargarMensaje(listaMensajes.arrayMensajes[i], archivo);
	}
}

void guardarListaMensajes(const tListaMensajes & listaMensajes, ofstream & archivo) {
	archivo << listaMensajes.contador << endl;
	for (int i = 0; i < listaMensajes.contador; i++) {
		guardarMensaje(listaMensajes.arrayMensajes[i], archivo);
	}
}

void mostrarListaMensajes(const tListaMensajes & listaMensajes) {

	for (int i = 0; i < listaMensajes.contador; i++) {
		cout << setw(15) << right;
		mostrarMensaje(listaMensajes.arrayMensajes[i]);
	}
}

void eliminarMensajeAntiguo(tListaMensajes & lista) {

	for (int i = 0; i < lista.contador-1; i++){

		lista.arrayMensajes[i] = lista.arrayMensajes[i + 1];
	}
	lista.contador--;

}