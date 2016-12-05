#include <iostream>
using namespace std;
#include <fstream>
#include <iomanip>
#include <string>
#include <ctime> 
#include <sstream>

#include "mensaje.h"

void crearNuevoMensaje(string emisor, string receptor, tMensaje & mensaje) {

	mensaje.emisor = emisor;

	mensaje.receptor = receptor;

	mensaje.fecha = time(0);

	getline(cin, mensaje.texto);
}

void cargarMensaje(tMensaje & mensaje, ifstream & archivo) {

	archivo >> mensaje.emisor;
	archivo >> mensaje.fecha;
	getline(archivo, mensaje.texto);
}

void guardarMensaje(const tMensaje & mensaje, ofstream & archivo) {

	archivo << mensaje.emisor << " ";
	archivo << mensaje.fecha << " ";
	archivo << mensaje.texto << endl;
}

void mostrarMensaje(const tMensaje mensaje) {
	string fecha;

	cout << mensaje.emisor << " ";

	fecha = mostrarFecha(mensaje.fecha);

	cout << fecha<< " ";

	cout << mensaje.texto;

	cout << endl;
}

string mostrarFecha(tFecha fecha){

	stringstream resultado;
	tm ltm;
	localtime_s(&ltm, &fecha);
	resultado << "<" << 1900 + ltm.tm_year << "/" << 1 + ltm.tm_mon << "/" << ltm.tm_mday;
	resultado << ", " << ltm.tm_hour << ":" << ltm.tm_min << ":" << ltm.tm_sec << ">";

	return resultado.str();
}