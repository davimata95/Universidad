#include "datosUsuario.h"

/*
int main(){

	tDatosUsuario datosUsuario;
	string usuario = "pepe";
	ifstream archivo;
	archivo.open("usuarios.txt");

	cargarDatosUsuario(datosUsuario, usuario, archivo);

	mostrarDatosUsuarios(datosUsuario);

	inicializarDatosUsuario(datosUsuario);
	mostrarDatosUsuarios(datosUsuario);

	ofstream archivo2;
	archivo2.open("usuarioss.txt");
	guardarDatosUsuarios(datosUsuario, archivo2);


	system("pause");
	return 0;
}
*/

void inicializarDatosUsuario(tDatosUsuario & datosUsuario, string usuario) {
	
	datosUsuario.usuario = usuario;
	inicializarListaMensajes(datosUsuario.buzonMensajes);
}

void cargarDatosUsuario(tDatosUsuario & datosUsuario, ifstream & archivo) {

	archivo >> datosUsuario.usuario;
	cargarListaMensajes(datosUsuario.buzonMensajes, archivo );

}

void guardarDatosUsuarios(const tDatosUsuario & datosUsuario, ofstream & archivo) {

	archivo << datosUsuario.usuario << endl;
	guardarListaMensajes(datosUsuario.buzonMensajes, archivo);
}

void mostrarDatosUsuarios(const tDatosUsuario & datosUsuario) {

	cout << datosUsuario.usuario << endl;
	mostrarListaMensajes(datosUsuario.buzonMensajes);
}

