#include <iostream>
using namespace std;
#include <fstream>
#include <string>

#include "listaUsuarios.h"

/*
int main () {
tListaUsuarios listaUsuarios ;
tDatosUsuario datosUsuario ;
tListaMensajes listaMensajes ;
tMensaje mensaje;

string usuario = "pepe";

cargarListaUsuarios (listaUsuarios, usuario) ;
mostrarListaUsuarios (listaUsuarios) ;
guardarListaUsuarios (listaUsuarios) ;

string nombre;
cout << "Que usuario deseas buscar?";
cin >> nombre;
int posicion = buscarListaUsuarios (listaUsuarios, nombre) ;
mostrarDatosUsuarios(listaUsuarios.arrayDatosUsuario[posicion]);

system ("pause") ;
return 0  ;
}
*/

void inicializarListaUsuarios(tListaUsuarios & listaUsuarios) {
	listaUsuarios.contador = 0;
}

bool cargarListaUsuarios(tListaUsuarios & listaUsuarios) {

	string nombre;
	listaUsuarios.contador = 0;
	bool abierto;

	ifstream archivoUsuarios;
	archivoUsuarios.open("usuarios.txt");
	if (archivoUsuarios.is_open()){
		abierto = true;
		do {
			archivoUsuarios >> nombre;
			listaUsuarios.arrayDatosUsuario[listaUsuarios.contador].usuario = nombre;
			if (nombre != "XXX") {
				cargarListaMensajes(listaUsuarios.arrayDatosUsuario[listaUsuarios.contador].buzonMensajes,archivoUsuarios);
				listaUsuarios.contador++;
			}
		} while (nombre != "XXX");
	}
	else{
		abierto = false;
	}
	archivoUsuarios.close();
	return abierto;
}

void guardarListaUsuarios(const tListaUsuarios & listaUsuarios) {

	ofstream archivoUsuarios2;
	archivoUsuarios2.open("usuarios.txt");

	for (int i = 0; i < listaUsuarios.contador; i++) {
		guardarDatosUsuarios(listaUsuarios.arrayDatosUsuario[i], archivoUsuarios2);
	}
	archivoUsuarios2 << "XXX";
	
	archivoUsuarios2.close();
}

int buscarListaUsuarios(const tListaUsuarios & listaUsuarios, string nombre) {

	int posicion;
	bool encontrado = false;
	int i = 0;
	while (i < listaUsuarios.contador && !encontrado) {
		if (listaUsuarios.arrayDatosUsuario[i].usuario == nombre) {
			encontrado = true;
			posicion = i;
		}
		i++;
	}
	if (!encontrado){
		posicion = -1;
	}
	return posicion;
}

void insertarUsuario(tListaUsuarios & listaUsuarios, const tDatosUsuario & datosUsuario) {

	bool encontrado = false;
	int posicion = listaUsuarios.contador;
	for (int i = 0; i < listaUsuarios.contador && !encontrado; i++) {
		if (listaUsuarios.arrayDatosUsuario[i].usuario > datosUsuario.usuario) {
			posicion = i;
			encontrado = true;
		}
	}
	for (int i = listaUsuarios.contador; i > posicion; i--) {
		listaUsuarios.arrayDatosUsuario[i] = listaUsuarios.arrayDatosUsuario[i - 1];
	}
	listaUsuarios.arrayDatosUsuario[posicion] = datosUsuario;
}

void mostrarListaUsuarios(const tListaUsuarios & listaUsuarios) {

	for (int i = 0; i < listaUsuarios.contador; i++) {
		mostrarDatosUsuarios(listaUsuarios.arrayDatosUsuario[i]);
	}
}
