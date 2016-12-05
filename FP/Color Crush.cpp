//David Mata Lorenzo 1ºC
//David Laborda Garcioa 1ºC

//En el caso de querer cargar una partida, debera obviamente usar el 'formato' adecuado, asi como la DIMENSION apropiada.
//Adjunto un documento con una partida guardada, un tablero de nombre TABLERO, y uno con unas puntuaciones.

//********************
	//BIBLIOTECAS
//********************

#include <iostream> 
using namespace std;
#include <windows.h>
#include <ctime>
#include <iomanip>
#include <string>
#include <fstream>

//******************
	//CONSTANTES
//******************

const int DIMENSION=8;
const int INTENTOS=15;
const bool debug = false;

//*************************
	//TIPOS/ESTRUCTURAS
//*************************

typedef enum {magenta, amarillo, azul, verde, vacio} tFicha;
typedef tFicha tTablero[DIMENSION][DIMENSION] ;
typedef bool tGelatina[DIMENSION][DIMENSION] ;
typedef enum {
black, dark_blue, dark_green, dark_cyan, dark_red,
dark_magenta, dark_yellow, light_gray, dark_gray,
light_blue, light_green, light_cyan, light_red,
light_magenta, light_yellow, white
} tColor;
typedef struct {
	tTablero tablero ;
	int intentosRestantes ;
	int puntos ;
	tGelatina gelatina ;
} tJuego;
typedef enum {arriba, abajo, izquierda, derecha} tDireccion ;
typedef struct {		
	int fila ;
	int columna;
	tDireccion direccion ;
}tMovimiento ;

//*****************
	//CABECERAS
//*****************

//pequeño menu al iniciar para ver si quiere cargar un tablero/partida
int menu () ;
//genera un tablero aleatorio
void generarTablero (tTablero & tablero) ;
//muestra el tablero, asi como la puntuacion y los intentos restantes
void mostrarTablero (const tJuego juego) ;
//cambia el color de la variable para introducirla en el subprograma setColor
void cambiarColor (const tFicha ficha, tColor & color) ;
//cambia el color a mostrar por pantalla
void setColor (const tColor color) ;
//comprobacion del tablero, puntos e intentos
void procesarTablero (tJuego & juego) ; 
//llena los huecos creados por la formacion de grupos 
void llenarHuecos (tJuego & juego) ;
//pide el movimiento y si desea salir o guardar la partida
void gestionarPartida (tJuego & juego) ;
//cambia las fichas del movimiento introducido
void cambioFichas (tTablero & tablero, const tMovimiento movimiento) ;
//comprueba si hay grupos tras hacer el movimiento
bool comprobacion (const tTablero & tablero, const tMovimiento movimiento) ;
//guarda la puntuacion en el archivo de las puntuaciones
void puntuaciones (const string nombre, const int puntuacion) ;
//en el caso de seleccionar la opcion, carga un tablero de un archivo 
void cargarNuevoTablero (tTablero & tablero) ;
//guarda la partida actual (tablero, intentos y puntuacion)
void guardarPartida (const tJuego juego) ;
//carga una partida anteriormente guardada
void cargarPartidaGuardada (tJuego & juego) ;
//muestra las puntuaciones
void mostrarPuntuaciones () ;

int main () {
	
	cout << "Bienvenido a Color Crush. " << endl << endl;

	tJuego juego ;
	int opcion;
	string nombre ;
	juego.intentosRestantes = INTENTOS ;
	juego.puntos = 0 ;
	juego.gelatina ; 
	for (int i = 0 ; i < DIMENSION ; i++) { // declara la matriz gelatina
		for (int j = 0 ; j < DIMENSION ; j++) {
			juego.gelatina[i][j] = true ;
		}
	}

	cout << "Introduce tu nombre: " ;
	cin >> nombre ;

	do {	
		opcion = menu () ;
		switch (opcion) {
			case 1 :
				cout << "Elimina toda la gelatina en " << INTENTOS << " intentos." << endl << endl;
				
				generarTablero (juego.tablero);
				mostrarTablero (juego) ;
				Sleep (1500) ;
				procesarTablero (juego) ;
				gestionarPartida (juego) ;
				puntuaciones (nombre, juego.puntos) ; 
			break;
			case 2 :
				cargarNuevoTablero (juego.tablero) ;
			break;
			case 3 :
				cargarPartidaGuardada (juego) ;
			break;
			case 4 :
				cout << endl;
				mostrarPuntuaciones () ;
			break;
		} 
	} while (opcion !=1 && opcion !=0) ;

	cout << endl <<  "Gracias por jugar al color crush." << endl << endl;
	
	mostrarPuntuaciones () ;
	cout << endl;

	system ("pause") ;
	return 0 ;

}

//pequeño menu al iniciar para ver si quiere cargar un tablero/partida
int menu () {
	
	int opcion=9;
	string nonum ;
	cout << endl << "Las opciones son: " << endl << endl;
	cout << "1. Jugar. " << endl;
	cout << "2. Cargar tablero a partir de un documento. " << endl;
	cout << "3. Cargar una partida anteriormente guardada. " << endl;
	cout << "4. Mostrar Puntuaciones. " << endl;
	cout << "0. Salir. " << endl;
	cout <<endl <<  "Elige una opcion para comenzar a jugar: " ;
	cin >> opcion ;
	
	while (cin.fail()){ //comprueba que la opcion introducida es valida
		cin.clear();
		cin >> nonum; 
		cout << endl << "Error, introduzca un numero valido: ";
		cin >> opcion; 
		cout << endl;
	}
	
	if (opcion > 4) {
		cout << "Error, introduzca un numero valido: " << endl;
		menu () ;
	}

	return opcion ;
}

//genera un tablero aleatorio
void generarTablero (tTablero & tablero) {
	
	int aleatorio;
	srand(time(NULL)) ;
	tFicha ficha ;
	
	for (int i = 0 ; i<DIMENSION ; i++) {
		for (int j = 0 ; j<DIMENSION ; j++) {
			aleatorio = (rand()%4) ;
			switch (aleatorio) {
			case 0 :	
				ficha = magenta ;
			break ;
			case 1 :
				ficha = amarillo ;
			break ;
			case 2 :
				ficha = azul ;
			break ;
			case 3 :
				ficha = verde ;
			break;
			}
			tablero[i][j] = ficha;
		}
	}
}

//muestra el tablero, asi como la puntuacion y los intentos restantes
void mostrarTablero (const tJuego juego) {
	
	tColor color ;
	cout << endl;
	cout << setw (14) << "N.Intentos " << juego.intentosRestantes ;
	cout << setw (10) << "Puntos " << juego.puntos << endl ;
	
	cout <<setw (3) << char (218) ;
	for (int i = 0 ; i < DIMENSION-1 ; i++) {
		cout << char (196) << char (196) << char (196) << char (194)  ;
	}

	cout << char (196) << char (196) << char (196) << char (191) ;
	cout << endl;
	cout <<setw (2) << DIMENSION ;
	for (int k = 0 ; k < DIMENSION ; k++) {
		cout << char (179) ;

		cambiarColor(juego.tablero[0][k], color) ;
		setColor (color) ;
		cout << char (219);
		if (juego.gelatina[0][k] == true){
			setColor(white) ;
			cout << char (219);
			cambiarColor(juego.tablero[0][k], color) ;
			setColor (color) ;
		}
		else{
			cout << char (219) ;
		}
		cout << char (219);
		setColor(white) ;
		
	}
	for (int k = 1 ; k < DIMENSION ; k++) {
		
		cout << char (179) ;
		cout << endl;
		cout <<setw (3) << char (195);
		for (int j = 1 ; j < DIMENSION ; j++ ){
			cout  << char (196) << char (196) << char (196) << char (197)  ;
		}
		cout  << char (196) << char (196) << char (196) << char (180)  ;
		cout << endl;
		cout <<setw (2) << DIMENSION-k ;
		for (int l = 0 ; l < DIMENSION ; l++) {
			cout << char (179) ;
			cambiarColor(juego.tablero[k][l], color) ;
			setColor (color) ;
			cout << char (219);
			if (juego.gelatina[k][l] == true){
				setColor(white) ;
				cout << char (219) ;
				cambiarColor(juego.tablero[k][l], color) ;
				setColor (color) ;
			}
			else {
				cout << char (219) ;
			}

			cout << char (219) ;
			setColor(white) ;
		}		
	}
	cout << char (179) ;
	cout << endl;
	cout <<setw (3) << char (192) ;
	for (int i = 1 ; i < DIMENSION ; i++) {
		cout << char (196) << char (196) << char (196) << char (193)  ;
	}
	cout << char (196) << char (196) << char (196) << char (217) ;
	cout << endl;
	cout << setw (5) << DIMENSION-DIMENSION+1 ;
		for (int i =2 ; i<DIMENSION+1 ; i++) {
			cout << setw (4) << DIMENSION-DIMENSION+i ;
		}
	cout << endl;
	if (debug) {
		system ("pause") ;
	}

}

//cambia el color de la variable para introducirla en el subprograma setColor
void cambiarColor (const tFicha ficha, tColor &color) {
	
	switch (ficha){
		case magenta:
			color = light_magenta ;
		break;
		case amarillo:
			color = light_yellow ;
		break;
		case azul:
			color = light_blue ;
		break;
		case verde:
			color = light_green ;
		break;
		case vacio:
			color = black ;
		break;
	}
}

//cambia el color a mostrar por pantalla
void setColor(const tColor color) {
	HANDLE handle = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(handle, color);
}

//comprobacion del tablero, puntos e intentos
void procesarTablero  ( tJuego & juego) {
		
	bool auxiliar[DIMENSION][DIMENSION] ;
	for (int i = 0 ; i < DIMENSION ; i++) {
		for (int j = 0 ; j < DIMENSION ; j++) {
			auxiliar[i][j] = false ;
		}
	}

	bool recomprobar = false ;

	for (int i = 0 ; i < DIMENSION ; i++) {	
		for (int j = 0 ; j < DIMENSION ; j++) {
			int contador = 0 ;
			if (juego.tablero[i][j] == juego.tablero[i][j+1] && juego.tablero[i][j] == juego.tablero[i][j+2] && j < DIMENSION-2 ) {
				juego.puntos = juego.puntos + 3;
				auxiliar[i][j+1] = true;
				auxiliar[i][j+2] = true;
				contador = contador+2 ;
				if (juego.tablero[i][j] == juego.tablero[i][j+3] && j < DIMENSION-3){
					juego.puntos = juego.puntos + 5;
					auxiliar[i][j+3] = true;
					contador = contador+1 ;
					if (juego.tablero[i][j] == juego.tablero[i][j+4] && j < DIMENSION-4){
						juego.puntos = juego.puntos + 7;
						auxiliar[i][j+4] = true;
						contador = contador+1 ;
						if (juego.tablero[i][j] == juego.tablero[i][j+5] && j < DIMENSION-5) {
							juego.puntos = juego.puntos + 10;
							auxiliar[i][j+5] = true;
							contador = contador+1 ;
						}
					}
				}
				auxiliar[i][j] = true;
				recomprobar = true ;
			}
			j = j + contador ;
		}
		for (int j = 0 ; j < DIMENSION ; j++) {
			int contador = 0 ;
			if (juego.tablero[j][i] == juego.tablero[j+1][i] && juego.tablero[j][i] == juego.tablero[j+2][i] && j < DIMENSION - 2 ) {
				juego.puntos = juego.puntos + 3;
				auxiliar[j+1][i] = true;
				auxiliar[j+2][i] = true;
				contador = contador+2 ;
				if (juego.tablero[j][i] == juego.tablero[j+3][i] && j < DIMENSION - 3){
					juego.puntos = juego.puntos + 5;
					auxiliar[j+3][i] = true;
					contador = contador+1 ;
					if (juego.tablero[j][i] == juego.tablero[j+4][i] && j < DIMENSION - 4){
						juego.puntos = juego.puntos + 7;
						auxiliar[j+4][i] = true;
						contador = contador+1 ;
						if (juego.tablero[j][i] == juego.tablero[j+5][i] && j < DIMENSION-5) {
							juego.puntos = juego.puntos + 10;
							auxiliar[j+5][i] = true;
							contador = contador+1 ;
						}
					}
				}
				auxiliar[j][i] = true;
				recomprobar=true;
			}
			j = j + contador ;
		}
	}

	cout << endl;
	
	if (recomprobar) {
		for (int i = 0 ; i < DIMENSION ; i++) {
			for (int j = 0 ; j < DIMENSION ; j++) {
				if (auxiliar[i][j] == true ) {
					juego.tablero[i][j]=vacio ;
					juego.gelatina[i][j]=false ;
				}
			}
		}
		Sleep(300) ;
		system ("CLS") ;
		mostrarTablero (juego) ;
		llenarHuecos (juego) ;

		procesarTablero (juego) ;
	}
	else {
		cout << "No hay grupos disponibles" << endl;
	}
} 

//llena los huecos creados por la formacion de grupos 
void llenarHuecos (tJuego & juego) {
	
	cout << endl << "Se han generado grupos." << endl;
	
	for (int i = 0 ; i < DIMENSION ; i++) {
		bool cambios = false ;
		for (int j = 0 ; j < DIMENSION ; j++) {
			if (juego.tablero[i][j] == vacio) {
				cambios = true ;
				if (i == 0) {
					juego.tablero[i][j] =tFicha((rand()%4)) ;
				}
				else{	
					for (int k = i ; k > 0 ; k--) {
						juego.tablero[k][j] = juego.tablero[k-1][j] ;
					}
					juego.tablero[0][j] = tFicha((rand()%4)) ;	
				}
			}
		}
		if (cambios) {
			Sleep(300) ;
			system ("CLS") ;
			mostrarTablero (juego) ;
		
		}
	}
}

//pide el movimiento y si desea salir o guardar la partida
void gestionarPartida (tJuego &juego) {
	
	tMovimiento movimiento = {0,0,derecha};
	string direccion = "a" ;
	string nonum = "a";
	bool finalizar = false;
	bool continuar = false ;
	bool movFalso = false ;
	bool finGelatina = false ;
	int fila = -2 ;
	do {	
		do {
			cout << endl << "Selecciona la fila (horizontal) que quieres modificar (numero)" << endl ;
			cout << "(0 Salir, -1 Guardar) :";
			cin >> fila; 
			movimiento.fila = DIMENSION - fila;
			while (cin.fail()){  // comprueba que no se introduzca ningun caracter no valido
				cin.clear();
				cin >> nonum; 
				cout << "Error, introduzca un numero: ";
				cin >> fila; 
				fila = DIMENSION - fila;
				movimiento.fila = fila ;
				cout << endl;
			}	
			switch (fila) {
				case -1 :	
					guardarPartida (juego) ;
					finalizar = true;
					continuar = true;
				break;
				case 0 :				
					finalizar = true ;
				break;
			}
			if (fila < -1 || fila > DIMENSION) {	
				cout << "Introduce una fila que se encuentre entre -1 y " << DIMENSION << "-1 guardar, 0 salir,." << endl;
			}
		}	while (fila < -1 || fila > DIMENSION) ;
			
		if (!finalizar) {

			int columna = -5 ;
			do {
				cout <<"Selecciona la columna (vertical) que quieres modificar (numero) : " ;
				cin >>columna ;	
				movimiento.columna = columna - 1 ;
				while (cin.fail()){ // comprueba que no se introduzca ningun caracter no valido
					cin.clear();
					cin >> nonum; 
					cout << "Error, introduzca un numero: ";
					cin >> columna; 
					columna = columna-1 ;
					movimiento.columna = columna ;
					cout << endl;
				}
				if (columna < 1 || columna > DIMENSION) {
					cout << "Introduce una columna que se encuentre entre 1 y " << DIMENSION << endl;
				}
			}	while (columna < 1 || columna > DIMENSION) ;
			
			do {

				cout << "seleccione la direccion del movimiento (AR, AB, I, D)" ;
			
				cin >> direccion ;
				if (direccion == "AR") {
					movimiento.direccion = arriba ;
					if (movimiento.fila == 0) {
						cout << endl << "No se puede realizar ese movimiento." << endl;
						movFalso = true;
					}

				}	
				else 
				if (direccion == "AB") {
					movimiento.direccion = abajo ;
					if (movimiento.fila == DIMENSION-1) {	
						cout << endl << "No se puede realizar ese movimiento." << endl;
						movFalso = true;
					}
				}
				else
				if (direccion == "I") {
					movimiento.direccion = izquierda ;
					if (movimiento.columna == 0){
						cout << endl << "No se puede realizar ese movimiento." << endl;
						movFalso = true;
					}

				}
				else
				if (direccion == "D") {
					movimiento.direccion = derecha  ;
					if (movimiento.columna == DIMENSION-1){
						cout << endl << "No se puede realizar ese movimiento." << endl;
						movFalso = true;
					}

				}
			
				if (direccion != "AR" && direccion != "AB" && direccion != "D" && direccion != "I") {
					cout << endl << "Introduce una direccion correcta (AR, AB, I, D)" << endl << endl;
				}
			} while (direccion != "AR" && direccion != "AB" && direccion != "D" && direccion != "I") ;	
		
			if (!movFalso) {
				cambioFichas (juego.tablero, movimiento) ;
				mostrarTablero (juego) ;
				continuar = comprobacion (juego.tablero, movimiento) ;
				if (continuar) {
					juego.intentosRestantes-- ;					
					procesarTablero (juego) ;
					Sleep (300) ;
					system ("CLS") ;
					cout << endl << "El movimiento genero algun grupo." << endl;
					mostrarTablero (juego) ;
					cout << endl;
				}	
				else {
					cambioFichas (juego.tablero, movimiento) ;
					Sleep (300) ;
					system ("CLS") ;
					cout << endl << "El movimiento no genera ningun grupo." << endl;
					mostrarTablero (juego) ;
					cout << endl;	
				}
			}
			finGelatina = true;	
			for (int i =0; i < DIMENSION; i++) {
				for (int j = 0 ; j < DIMENSION ; j++) {
					if (juego.gelatina[i][j]) {
						finGelatina = false ;
					}
				}	
			}
		}
	} while (juego.intentosRestantes > 0 && !finGelatina && !finalizar ) ;
	
	if (finGelatina) {
		cout << endl << "Has conseguido " << juego.puntos << " puntos." << endl;
		cout << endl << " ENHORABUENA. Has eliminado toda la gelatina." << endl;
	}
	if (juego.intentosRestantes == 0){
		cout << endl << "Has conseguido " << juego.puntos << " puntos." << endl;
		cout << endl << "GAME OVER. Intentalo de nuevo." << endl;
	}

}

//cambia las fichas del movimiento introducido
void cambioFichas (tTablero & tablero, tMovimiento movimiento) {
	
	tFicha aux;
	int fila=0;
	int columna=0;
	switch (movimiento.direccion) { 
		case arriba :	
			fila = -1;
			columna = 0 ;
		break ;
		case abajo :
			fila = 1 ;
			columna = 0;
		break;
		case derecha :
			fila = 0 ;
			columna = 1 ;
		break;
		case izquierda :
			fila = 0 ;
			columna = -1 ;
		break;
	}
	aux = tablero[movimiento.fila][movimiento.columna] ;
	tablero[movimiento.fila][movimiento.columna] = tablero[movimiento.fila+fila][movimiento.columna+columna];
	tablero[movimiento.fila+fila][movimiento.columna+columna]=aux;
}

//comprueba si hay grupos tras hacer el movimiento
bool comprobacion (const tTablero & tablero, const tMovimiento movimiento) {

	int contadorHorizontal = 1 ;
	int contadorVertical = 1 ;
	int filaAux = movimiento.fila;
	int columnaAux = movimiento.columna ;
	bool movValido;

	//comprueba si hay movimiento hacia arriba
	while (filaAux < DIMENSION && tablero [filaAux-1][columnaAux] == tablero[movimiento.fila][movimiento.columna]) {
		contadorVertical++ ;
		filaAux-- ;
	}
	filaAux = movimiento.fila ;
	//comprueba si hay movimiento hacia abajo
	while (filaAux >-1  && tablero [filaAux+1][columnaAux] == tablero[movimiento.fila][movimiento.columna]) {
		contadorVertical++ ;
		filaAux++ ;
	}
	filaAux = movimiento.fila;

	//comprueba si hay movimiento hacia la izquierda
	while (columnaAux > -1 && tablero [filaAux][columnaAux-1] == tablero[movimiento.fila][movimiento.columna]) {
		contadorHorizontal++ ;
		columnaAux-- ;
	}
	columnaAux = movimiento.columna ;

	//comprueba si hay movimiento hacia la derecha
	while (columnaAux < DIMENSION && tablero [filaAux][columnaAux+1] == tablero[movimiento.fila][movimiento.columna]) {
		contadorHorizontal++ ;
		columnaAux++ ;
	}
	columnaAux = movimiento.columna ;

	filaAux = movimiento.fila;
	columnaAux = movimiento.columna ;

	if (movimiento.direccion == arriba) {
		filaAux -- ;
	}
	else
	if (movimiento.direccion == abajo) {
		filaAux ++ ;
	}
	else
	if (movimiento.direccion == izquierda) {
		columnaAux -- ;
	}
	else
	if (movimiento.direccion == derecha) {
		columnaAux ++ ;
	}

	int auxF = filaAux ;
	int auxC = columnaAux ;

	if (movimiento.direccion == arriba || movimiento.direccion == abajo) {
			//comprueba si hay movimiento hacia la izquierda
		while (columnaAux > -1 && tablero [filaAux][columnaAux-1] == tablero[filaAux][movimiento.columna]) {
			contadorHorizontal++ ;
			columnaAux-- ;
		}
		columnaAux = auxC ;

		//comprueba si hay movimiento hacia la derecha
		while (columnaAux < DIMENSION && tablero [filaAux][columnaAux+1] == tablero[filaAux][movimiento.columna]) {
			contadorHorizontal++ ;
			columnaAux++ ;
		}
	}
	else {
		//comprueba si hay movimiento hacia arriba
		while (filaAux < DIMENSION && tablero [filaAux-1][columnaAux] == tablero[movimiento.fila][columnaAux]) {
			contadorVertical++ ;
			filaAux-- ;
		}
		filaAux = auxF ;
		//comprueba si hay movimiento hacia abajo
		while (filaAux > -1 && tablero [filaAux+1][columnaAux] == tablero[movimiento.fila][columnaAux]) {
			contadorVertical++ ;
			filaAux++ ;
		}
	}
	
	if (contadorVertical > 2 || contadorHorizontal >2) {
		movValido = true ;
	}
	else {
		movValido = false ;
	}
	return movValido ;
}

//guarda la puntuacion en el archivo de las puntuaciones
void puntuaciones (const string nombre, const int puntuacion )
{
	ifstream archivoOriginal;
	ofstream archivoTemporal;
	ifstream archivoCompleto;
	ofstream archivoNuevo;
	string texto;
	string usuario;
	int puntos=0;
	int contador=0;

	archivoOriginal.open ("puntuaciones.txt") ;
	archivoTemporal.open ("temporal.txt");

	if (archivoOriginal.is_open ()){
		archivoOriginal >> usuario ;
		while (usuario != "XXX")
		{	
				if (usuario != nombre)
				{			
					archivoOriginal >> puntos ;
					archivoTemporal << usuario << " " ;
					archivoTemporal << puntos << endl;
				}
				else 
				{
					archivoOriginal >> puntos ;
					archivoTemporal << usuario << " " ;
					archivoTemporal << puntuacion << endl;
					contador++;
				}
				archivoOriginal >> usuario ;
		}
		if (contador==0){
			archivoTemporal << nombre << " " ;
			archivoTemporal << puntuacion << endl;
		}

			
		archivoTemporal << "XXX" << endl;

		archivoOriginal.close();
		archivoTemporal.close();

		archivoCompleto.open ("temporal.txt") ;
		archivoNuevo.open ("puntuaciones.txt") ;

		while (texto != "XXX")
		{
			getline (archivoCompleto, texto) ;
			archivoNuevo << texto << endl;
		}
	}
	else{
		archivoNuevo.open ("puntuaciones.txt") ;
		archivoNuevo << nombre << " " ;
		archivoNuevo << puntuacion << endl;
		archivoNuevo << "XXX" ;
	}
}

//en el caso de seleccionar la opcion, carga un tablero de un archivo
void cargarNuevoTablero (tTablero & tablero) {
	
	string archivo ;
	cout << endl << "Introduce el archivo que deseas cargar, anadiendo la extension .txt " << endl; ; // la ñ no sale por pantalla
	cin >> archivo ;
	ifstream archivoTablero;
	int ficha ;
	archivoTablero.open (archivo) ;
	if(archivoTablero.is_open()) {
		for (int i = 0 ; i<DIMENSION ; i++) {
			for (int j = 0 ; j<DIMENSION ; j++) {
				archivoTablero >> ficha;
				tablero[i][j] = tFicha (ficha) ;
			}
		}
		cout << endl << "El tablero se ha cargado con exito, opcion 1 para jugar." << endl;
	}
	else {
		cout << endl << "No se ha encontrado el archivo." << endl;
		generarTablero (tablero) ;
		Sleep(300) ;
	}
}

//guarda la partida actual (tablero, intentos y puntuacion)
void guardarPartida (const tJuego juego) {
	
	ofstream archivoPartida ;
	archivoPartida.open ("partidaGuardada.txt") ;
	archivoPartida << juego.intentosRestantes << " " ;
	archivoPartida << juego.puntos << endl;

	for (int i = 0 ; i < DIMENSION ; i++) {
		for (int j = 0 ; j < DIMENSION ; j++) {
			archivoPartida << juego.tablero[i][j] << " " ;
		}
		archivoPartida << endl;
	}
	
	for (int i = 0 ; i < DIMENSION ; i++) {
		for (int j = 0 ; j < DIMENSION ; j++) {
			archivoPartida << juego.gelatina[i][j] << " " ;
		}
		archivoPartida << endl;
	}
	cout << endl << "La partida se guarpo con exito." << endl;
}

//carga una partida anteriormente guardada
void cargarPartidaGuardada (tJuego & juego) {
	
	ifstream archivoPartida ;
	int ficha ;
	int gelatina ;
	archivoPartida.open ("partidaGuardada.txt") ;
	if (archivoPartida.is_open ()) {
		archivoPartida >> juego.intentosRestantes ;
		archivoPartida >> juego.puntos ;

		for (int i = 0 ; i < DIMENSION ; i++) {
			for (int j = 0 ; j<DIMENSION ; j++) {
				archivoPartida >> ficha ;
				juego.tablero[i][j] = tFicha (ficha) ;
			}
		}
		for (int i = 0 ; i < DIMENSION ; i++) {
			for (int j = 0 ; j<DIMENSION ; j++) {
				archivoPartida >> gelatina ;
				juego.gelatina[i][j] = bool (gelatina) ;
			}
		}
		cout << endl << "La partida se ha cargado con exito, opcion 1 para jugar." << endl;
	}
	else {
		cout << endl << "No se encuentra la partida." << endl ;
		generarTablero (juego.tablero) ;
	}
}

//muestra las puntuaciones
void mostrarPuntuaciones () {
	
	ifstream archivo;
	string usuario ;
	int puntos;

	archivo.open ("puntuaciones.txt") ;
	if (archivo.is_open ())
	{
		archivo >> usuario ;
		while (usuario != "XXX")
		{
			archivo >> puntos ;
			cout << setw(15) << usuario ; 
			cout << setw (5) << puntos   ;
			cout << endl;
			archivo >> usuario ;
		}
		archivo.close ();
	}
	else
	{
		cout << endl << "No se han encontrado puntuaciones."  << endl;
	}
}