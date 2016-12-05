#include <iostream>
using namespace std;
#include <fstream>
#include <iomanip>
#include <windows.h>

#include "cliente.h"

bool cargarChats(tCliente & cliente, string nombre){

	bool abierto;
	cliente.usuario = nombre;
	ifstream archivoChats;
	archivoChats.open(nombre + "_chats.txt");
	if (archivoChats.is_open()) {
		abierto = true;
		cargarListaChats(cliente.ChatsUsuario, nombre, archivoChats);
	}
	else{
		abierto = false;
	}
	archivoChats.close();

	return abierto;
}

void arrancarCliente(tCliente & cliente, tServidor & servidor){  
	
	tListaMensajes buzon;
	inicializarListaMensajes(buzon);
	cout << "Accediendo a sus mensajes..." << endl;
	Sleep(500);
	recuperarBuzon(servidor, buzon, cliente.usuario);
	Sleep(1000);
	buzonAChat(buzon, cliente);
	ordenarListaChats(cliente.ChatsUsuario, fecha); 
	// una vez que tiene los nuevos mensajes los ordena
}

void inicializarCliente(tCliente & cliente, string nombre){

	cliente.usuario = nombre;
	inicializarListaChats(cliente.ChatsUsuario, nombre);
}

char menuUsuario(tCliente & cliente, tServidor & servidor){ //string emisor, const tListaChats & listaChats){

	pintarRaya(rara);
	cout << setw(48) << "CHATSAPP 1.0: Chats de " << cliente.usuario << endl;
	pintarRaya(rara);
	cout << endl;
	tMensaje ultimoMensaje;
	for (int i = 0; i< cliente.ChatsUsuario.contador ; i++) {
		pintarRaya(continua);
		cout << i << ": " << cliente.ChatsUsuario.arrayChats[i].receptor << endl;
		ultimoMensaje = consultarUltimoMensaje(cliente.ChatsUsuario.arrayChats[i].conversacion);
		cout << setw(15);  mostrarMensaje(ultimoMensaje);
	}
	pintarRaya(continua);
	cout << endl;
	mostrarCabecera();
	char opcion;
	cout << "Selecciona una de las opciones, siguiendo las instrucciones mencionadas.";
	cin >> opcion;
	
	return opcion;
}

void seleccionFuncion(tCliente & cliente, tServidor & servidor, char opcion){

	int aclaracionInt;
	char aclaracionChar;
	string nuevoChat;
	string persona;

	switch (opcion){ //acepta mayusc y minusc
	case 'C':
	case 'c'://opcion de abrir chat
		cin >> aclaracionInt;
		if (aclaracionInt > cliente.ChatsUsuario.contador - 1){
			cout << "No existe ese chat." << endl;
			Sleep(1000);
		}
		else{
			conversarCon(cliente, servidor, aclaracionInt);
		}
		break;

	case 'E':
	case 'e'://opcion de eliminar chat
		cin >> aclaracionInt;
		if (aclaracionInt > cliente.ChatsUsuario.contador - 1){
			cout << "No existe ese chat." << endl;
			Sleep(1000);
		}
		else {
			eliminarListaChats(cliente.ChatsUsuario, aclaracionInt);
			cout << "Eliminando el chat..." << endl;
			Sleep(500);
			cout << "El chat se elimino correctamente." << endl;
			Sleep(500);
		}
		break;

	case 'O':
	case'o':
		cin >> aclaracionChar;
		if (aclaracionChar == 'f'){
			ordenarListaChats(cliente.ChatsUsuario, fecha);
		}
		else
		if (aclaracionChar == 'n'){
			ordenarListaChats(cliente.ChatsUsuario, nombre);
		}
		else{
			cout << "Esa opcion no es valida." << endl;
			Sleep(1000);
		}
		break;

	case 'N':
	case 'n':
		cout << "Introduce el nombre de la persona con la que desea hablar:  ";
		cin >> persona;

		crearChat(cliente, persona, servidor.mensajesPendientes, servidor);
	break;

	case 'S':
	case 's':

		break;

	default:
		cout << "No es una opcion correcta." << endl;
		Sleep(1000);
		break;
	}
}

void crearChat(tCliente & cliente, string persona, tListaUsuarios & listaUsuarios, tServidor & servidor){

	int verificar = buscarListaChats(cliente.ChatsUsuario, persona);
	if (verificar == -1){ //si no hay ningun chat con esa persona
		int existe = buscarListaUsuarios(listaUsuarios, persona); //lo busca en la lista de usuarios registrados
		if (existe != -1){ // si esta en la lista de usuarios
			if (cliente.ChatsUsuario.contador<M){//si hay huecos.

				inicializarChat(cliente.ChatsUsuario.arrayChats[cliente.ChatsUsuario.contador], cliente.usuario);
				cliente.ChatsUsuario.arrayChats[cliente.ChatsUsuario.contador].receptor = persona;

				tMensaje mensajeInicial;
				
				mensajeInicial.texto= "Nuevo chat iniciado por " + cliente.usuario;
				mensajeInicial.emisor = cliente.usuario;
				mensajeInicial.receptor = persona;
				mensajeInicial.fecha = time(0);
				
				añadirMensaje(cliente.ChatsUsuario.arrayChats[cliente.ChatsUsuario.contador].conversacion, mensajeInicial);
				enviarMensajes(servidor, cliente.ChatsUsuario.arrayChats[cliente.ChatsUsuario.contador].conversacion, persona);
				
				conversarCon(cliente, servidor, cliente.ChatsUsuario.contador);
				
				cliente.ChatsUsuario.contador++;
				
				//ordenar lista de chats
			}
			else {
				cout << "No se puede crear el chat, supera el maximo de chats disponibles." << endl;
				Sleep(1000);
			}
		}
		else{
			cout << endl << "Ese usuario no esta registrado en nuestra base de datos." << endl;
			cout << "No se puede crear un chat con alguine no registrado." << endl;
			Sleep(2000);
		}
	} 
	else 
	{
		cout << "Ya existe un chat con esa persona, se abrira ese chat a continuacion." << endl;
		Sleep(1500);
		conversarCon(cliente, servidor, verificar);
	}
}

void conversarCon(tCliente & cliente, tServidor & servidor, int posicion) {

	tListaMensajes listaAux;
	inicializarListaMensajes(listaAux);

	if (posicion > cliente.ChatsUsuario.contador){
		cout << "No existe ese chat." << endl;
	}
	else{
		tMensaje nuevoMensaje;
		crearNuevoMensaje(cliente.usuario, cliente.ChatsUsuario.arrayChats[posicion].receptor, nuevoMensaje);
		system("CLS");
		mostrarChat(cliente.ChatsUsuario.arrayChats[posicion]);
		while (nuevoMensaje.texto != "$salir$") {
			cout << "Escribe aqui ($salir$ para salir):  ";

			crearNuevoMensaje(cliente.usuario, cliente.ChatsUsuario.arrayChats[posicion].receptor, nuevoMensaje);
			
			if (nuevoMensaje.texto != "$salir$"){
				if (cliente.ChatsUsuario.arrayChats[posicion].conversacion.contador < N){
					añadirMensaje(cliente.ChatsUsuario.arrayChats[posicion].conversacion, nuevoMensaje);
					añadirMensaje(listaAux, nuevoMensaje); // crea una lista con los mensajes enviados
					system("CLS");
					mostrarChat(cliente.ChatsUsuario.arrayChats[posicion]);
				}
				else {
					cout << endl << "Has superado el limite de mensajes." << endl;
					cout << "Se eliminara el mensaje mas antiguo del chat. " << endl << endl;
					eliminarMensajeAntiguo(cliente.ChatsUsuario.arrayChats[posicion].conversacion);
					añadirMensaje(cliente.ChatsUsuario.arrayChats[posicion].conversacion, nuevoMensaje);
					añadirMensaje(listaAux, nuevoMensaje);
					Sleep(1000);
					system("CLS");
					mostrarChat(cliente.ChatsUsuario.arrayChats[posicion]);
				}
			}
		}
	}
	enviarMensajes(servidor, listaAux, cliente.ChatsUsuario.arrayChats[posicion].receptor);
	ordenarListaChats(cliente.ChatsUsuario, fecha);
}

void buzonAChat(tListaMensajes & buzon, tCliente & cliente){
	
	for (int i = 0; i < buzon.contador; i++){

		int posicion = buscarListaChats(cliente.ChatsUsuario, buzon.arrayMensajes[i].emisor); //busca el chat de quien me ha mandado el mensaje
		if (posicion == -1){ //si no hay ningun chat de esa persona
			inicializarChat(cliente.ChatsUsuario.arrayChats[cliente.ChatsUsuario.contador], buzon.arrayMensajes[i].emisor);
			cliente.ChatsUsuario.arrayChats[cliente.ChatsUsuario.contador].emisor = cliente.usuario; //creo esto mal
			cliente.ChatsUsuario.arrayChats[cliente.ChatsUsuario.contador].receptor = buzon.arrayMensajes[i].emisor;
			añadirMensaje(cliente.ChatsUsuario.arrayChats[cliente.ChatsUsuario.contador].conversacion, buzon.arrayMensajes[i]);
			cliente.ChatsUsuario.contador++;
		}
		else {
			cliente.ChatsUsuario.arrayChats[cliente.ChatsUsuario.contador].receptor = buzon.arrayMensajes[i].emisor;
			if (cliente.ChatsUsuario.arrayChats[posicion].conversacion.contador < N){
				añadirMensaje(cliente.ChatsUsuario.arrayChats[posicion].conversacion, buzon.arrayMensajes[i]);
			}
			else{
				eliminarMensajeAntiguo(cliente.ChatsUsuario.arrayChats[posicion].conversacion);
				añadirMensaje(cliente.ChatsUsuario.arrayChats[posicion].conversacion, buzon.arrayMensajes[i]);
			}
		}
	}
}

