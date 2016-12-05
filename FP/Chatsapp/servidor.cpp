#include <iostream>
using namespace std;
#include <fstream>
#include <windows.h>
#include <iomanip>

#include "servidor.h"
#include "cliente.h"

int main(){

	tServidor servidor;
	tCliente cliente;
	tListaChats listaChats;
	tChat chat;
	tListaUsuarios listaUsuarios;
	tDatosUsuario datosUsuario;
	tListaMensajes listaMensajes;
	tMensaje mensaje;

	inicializarListaUsuarios(servidor.mensajesPendientes);
	inicializarListaUsuarios(listaUsuarios);

	do{	
		bool cargaExito = cargarListaUsuarios(servidor.mensajesPendientes);
		if (cargaExito) {
			
			string nombreCliente;
			system("CLS");
			pintarRaya(rara);
			cout << setw(50) << "BIENVENIDO A CHATSAPP." << endl;
			pintarRaya(rara);
			cout << "Por favor introduce tu nombre (0 para salir): ";
			cin >> nombreCliente;
			inicializarCliente(cliente, nombreCliente);
			int usuarioValido = buscarListaUsuarios(servidor.mensajesPendientes, nombreCliente);
			
			if (usuarioValido != -1) { //si el usuario es valido
				
				bool abierto = cargarChats(cliente, nombreCliente);
			
				if (abierto){
					char opcion='x';
				
					arrancarCliente(cliente, servidor); //pasa los mensajes al buzon
					
					do {
						system("CLS");
						opcion = menuUsuario(cliente, servidor);
						seleccionFuncion(cliente, servidor, opcion);
					} while (opcion != 's');

					guardarListaChats(cliente.ChatsUsuario);	
					guardarListaUsuarios(servidor.mensajesPendientes);
				}
				else{
					cout << "No se pudo cargar el archivo de chats del usuario." << endl;
					Sleep(1500);
				
				}
			}
			else{
				if (nombreCliente != "0"){
					cout << "El usuario introducido no esta en la lista de usuarios." << endl;
					Sleep(1500);
				}
			}
		}
		else{
			cout << "No se encuentra el archivo 'usuarios.txt'. " << endl;
			cout << "El programa se cerrara. " << endl;
			cliente.usuario = "0";
		}
	} while (cliente.usuario != "0");
	

	system("pause");
	return 0;
}

void enviarMensajes(tServidor & servidor, const tListaMensajes & mensajes, string persona){ //aqui va guardando los mensajes que se van a enviar

	int posicion = buscarListaUsuarios(servidor.mensajesPendientes, persona);
	//va a encontrar el usuario 100% seguro, ya que sino no le hubiera dejado crear el chat
	int inicio = 0;
	if (mensajes.contador > N){
		inicio = mensajes.contador - N;
	}
	
	for (int i = inicio; i < mensajes.contador ; i++){ // no se xk pero funciona asi 
		cout << i;
		
		//pasa el mensaje de la lista que se le ha pasado a la lista del servidor
		//para incluir los mensajes que estan por recivir
		//si sale warning es xk no esta en la listaUsuarios
		añadirMensaje(servidor.mensajesPendientes.arrayDatosUsuario[posicion].buzonMensajes,  mensajes.arrayMensajes[i]);
	}
}

void recuperarBuzon(tServidor & servidor, tListaMensajes & buzon, string usuario){ //esta funcion recupera los mensajes de la lista de usuarios y los va introduciendo en el chat 

	int id = buscarListaUsuarios(servidor.mensajesPendientes, usuario);

	if (servidor.mensajesPendientes.arrayDatosUsuario[id].buzonMensajes.contador == 0){
		cout << "No tienes ningun mensaje nuevo." << endl;
	}
	else{
		for (int i = 0; i < servidor.mensajesPendientes.arrayDatosUsuario[id].buzonMensajes.contador; i++){
			buzon.arrayMensajes[i] = servidor.mensajesPendientes.arrayDatosUsuario[id].buzonMensajes.arrayMensajes[i];
			buzon.contador++;
		}
		cout << "Tienes " << buzon.contador << " mensaje(s) nuevo(s)." << endl;
	}
	inicializarDatosUsuario(servidor.mensajesPendientes.arrayDatosUsuario[id], usuario);
}