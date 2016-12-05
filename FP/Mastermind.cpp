// David Mata Lorenzo
// David Laborda Garcia
// 1º C 

#include <iostream>
using namespace std;
#include <fstream>
#include <ctime>
#include <string>
#include <iomanip>

const int FICHAS = 4, INTENTOS = 30, MODO = 0;	// Constantes necesarias para el ejercicio
typedef enum {R,A,V,N,G,M} tcolores ;	// Define el tipo tcolores 
typedef enum {NoRep, Rep} tmodo ; // Define el tipo tmodo (0=NoRep, 1=Rep)
typedef tcolores tjugada[FICHAS];	//Define el array de tipo tjugada, cuyos elementos son los pertenecientes a tcolores


void CodigoAleatorio (tjugada &CodigoSecreto) ;	
bool esta_en_jugada(tcolores color_nuevo,const tjugada jugada, int ult_posic) ; //Complemento de CodigoAleatorio (modo sin repeticion (0))
int convertirALetra (const tjugada CodigoSecreto, const char letra, const int Pistaaleatorio);	
int convertirAEnumerado (const char codigoIntroducido[FICHAS], tjugada codigoJugador);
int jugar (const tjugada CodigoSecreto, const string nombre) ;
void leerCodigo (const char codigoIntroducido[FICHAS], bool &continua, bool &pista) ;
void Pista (int &X, int &Y, const tjugada CodigoSecreto) ;
void comprobacion (const tjugada CodigoSecreto, const tjugada codigoJugador, int &contadoraciertos, int &contadorcasi, int &contadormal, int &movimiento, bool &acierto, int &puntuacion) ;
bool EstaRepetido (const tjugada CodigoSecreto, const tjugada codigoJugador, int n, const int contadoraciertos, bool aciertos[FICHAS]) ;
void mostrarMovimiento (int movimiento, int contadoraciertos, int contadorcasi, int contadormal, int puntuacion);
void ayuda();
void calcularPuntuacion (const int contadoraciertos, const int contadorcasi, const bool acierto, int &puntuacion ) ;
void menu (int &opcion, const string nombre) ;
void puntuaciones () ;
void modificarPuntuaciones (const string &nombre, const int puntuacion, const bool &acierto ) ;


int main () 
{
	tjugada CodigoSecreto ;
	int intentosJugador;
	int opcion=2 ;
	int contador = 0;
	string nombre;
	cout << "Bienvenido a Mastermind! Por favor, introduce tu nombre: " ;
	cin >> nombre ;
	while (opcion != 0 )
	{
		menu (opcion, nombre) ;
		switch (opcion) 
		{
			case 1:
				CodigoAleatorio (CodigoSecreto);
				intentosJugador = jugar (CodigoSecreto, nombre) ;
				cout << endl << "Has usado " << intentosJugador << " intentos." << endl ;
				contador++ ;
			break;

			case 2 :
				cout << endl;
				puntuaciones () ;		
			break;

			case 0:
		
			break;

			default:
				cout << endl << "Por favor "<< nombre << " introduce una opcion valida (0,1,2). " << endl;
			break;
		}
	}
	system ("pause") ;
	return 0 ;
}

void CodigoAleatorio (tjugada &CodigoSecreto) 
{
	int aleatorio=0 ;
	srand(time(NULL)) ;

	if (MODO==NoRep) // modo 0 = no se repiten colores
	{
		for (int n=0; n<FICHAS; n++)
		{
			aleatorio = (rand()%6) ;
				
			while (esta_en_jugada(tcolores(aleatorio), CodigoSecreto,  n-1))
			{
				aleatorio = (rand()%6) ;

					
			}
			CodigoSecreto[n] = tcolores (aleatorio);
		}
	}	
	else // si el modo es 0, es con repetición
	{
		for (int n=0; n<FICHAS; n++)
		{
			aleatorio = (rand()%5)+1 ;
			CodigoSecreto[n] = tcolores (aleatorio) ;
		}
	}
	cout << endl;	
}
bool esta_en_jugada(tcolores color_nuevo, const tjugada jugada, int ult_posic)
{
  bool repetido=false;
  int i=0;
  
  while (!repetido && i<=ult_posic)
	{
		repetido= (jugada[i]==color_nuevo);
		i++;
	}
  return repetido;
 }
int convertirALetra (const tjugada CodigoSecreto, const char letra, const int Pistaaleatorio)
{
	int i=0;
	char m [FICHAS] ;
	for (int i=0; i<FICHAS; i++)
	{
		switch (CodigoSecreto[i])
		{
			case R:
				m[i] = 'R';
			break;

			case A:
				m[i] = 'A';
			break;

			case V:
				m[i] = 'V';
			break;

			case N:
				m[i] = 'N';
			break;

			case G:
				m[i] = 'G';
			break;

			case M:
				m[i] = 'M';
			break;
		}
		cout << m[Pistaaleatorio] ;
	}
	cout << endl;

	return m[Pistaaleatorio];
}
int convertirAEnumerado (char codigoIntroducido[FICHAS], tjugada codigoJugador)
{
	int i=0;
	
	for (int i=0; i<FICHAS; i++)
	{
		switch (codigoIntroducido[i])
		{
			case 'R' :
				codigoJugador[i] = R;
			break;

			case 'A':
				codigoJugador[i] = A;
			break;

			case 'V':
				codigoJugador[i] = V ;
			break;

			case 'N':
				codigoJugador[i] = N ;
			break;

			case 'G':
				codigoJugador[i] = G;
			break;

			case 'M':
				codigoJugador[i] = M ;
			break;
		}
	}
	return codigoJugador[i];
}
int jugar (const tjugada CodigoSecreto, const string nombre)
{
	bool acierto=false;
	int movimiento=0 ;
	int puntuacion = 0 ;
	int intentosJugador =0;
	bool continua = true ;
	int Y = 2;
	int X = 0;
	tjugada codigoJugador ;
	char codigoIntroducido[FICHAS] ;

	cout << "Mastermind!!!  Codigos de " << FICHAS <<" colores (RAVNGM), 30 intentos, sin repeticion." << endl;
	cout << "Las letras introducidas deben estar en MAYUSCULAS." << endl;
	cout << "Cada 5 intentos puedes solicitar una pista aletoria, hasta un maximo de 2 veces. " << endl;
	
	while (intentosJugador < 30 && acierto == false && continua != false )
	{
		cout << "Codigo (? para ayuda, 0 para cancelar): " ;
		cin >> codigoIntroducido ;
		cout << endl;
		cout << "Tu codigo es: " ;
		for (int v=0; v<FICHAS; v++)
		{
			codigoIntroducido[v] = putchar (toupper(codigoIntroducido[v])) ;
		}
		convertirAEnumerado (codigoIntroducido, codigoJugador) ;
		
		bool pista = false ;

		leerCodigo (codigoIntroducido , continua, pista) ;
	
		if (pista == true)
		{
			Pista (X, Y, CodigoSecreto) ;
		}

		else 
		{
			if (continua == true)
			{
					
				X++;

				if ( codigoIntroducido[0] =='?')
				{
					ayuda () ;
				}
				else 
				{
								
					int contadoraciertos=0 ;
					int contadorcasi =0 ;
					int contadormal = 0;
				
					movimiento++ ;

					comprobacion (CodigoSecreto, codigoJugador, contadoraciertos, contadorcasi, contadormal, movimiento, acierto, puntuacion) ;
				
					mostrarMovimiento (movimiento, contadoraciertos, contadorcasi, contadormal, puntuacion ) ;

					intentosJugador ++;
				}
			}
		}
	}
	modificarPuntuaciones (nombre, puntuacion, acierto ) ;
	return intentosJugador ;
}
void leerCodigo (const char codigoIntroducido[FICHAS], bool &continua, bool &pista) 
{	
	if (codigoIntroducido[0] =='0') 
	{
		continua = false ;
	}
	if (codigoIntroducido[0] =='!') 
	{
		pista = true ;
	}
}
void Pista (int &X, int &Y, const tjugada CodigoSecreto)
{
	if ( X>=5 && Y>0)
	{
		Y--;
		int Pistaaleatorio=0 ;
		char Letra=0;
		srand(time(NULL)) ;
		Pistaaleatorio = (rand()%FICHAS) ;
		char letraPista = convertirALetra (CodigoSecreto, Letra, Pistaaleatorio) ;
		cout << "Tu pista es: " << endl;
		cout << "En la posicion " << Pistaaleatorio+1 << " se encuentra " <<  letraPista << endl << endl;
		X--; X--; X--; X--; X--; 	
		Y++;
	}
	else
	{
		if (X<5)
		{
			cout << endl << "Debes haber realizado al menos 5 intentos para solicitar una pista. " << endl << endl;
		}
		if (Y>2)
		{
			cout << endl << "Solo puede pedir un maximo de 2 pistas. " << endl << endl;
		}
	}
}
void comprobacion (const tjugada CodigoSecreto, const tjugada codigoJugador, int &contadoraciertos, int &contadorcasi, int &contadormal, int &movimiento, bool &acierto, int &puntuacion)
{
	int n =0;
	bool aciertos [FICHAS] ;
	bool EstaEnSecuencia = false ;
		
	for (n=0;n<FICHAS;n++)
	{
		if ( CodigoSecreto[n] == codigoJugador[n] )
		{
			 aciertos [n] = 1 ;
			 contadoraciertos ++ ; 
		}
		else 
		{
			aciertos [n] = 0 ;
		}
	
	}
	cout << endl;

	for (n=0;n<FICHAS;n++)
	{
		if (aciertos [n]== 0)
		{
			EstaEnSecuencia = EstaRepetido (CodigoSecreto, codigoJugador, n , contadoraciertos, aciertos) ; 
			if (EstaEnSecuencia == true)
			{
				contadorcasi ++ ;
			}
		}
	}

	contadormal = FICHAS - (contadoraciertos+contadorcasi) ;
	
	if (contadoraciertos == FICHAS)
	{
		acierto = true ;
	}

	calcularPuntuacion (contadoraciertos, contadorcasi, acierto, puntuacion ) ;
}
bool EstaRepetido ( const tjugada CodigoSecreto, const tjugada codigoJugador, int n, const int contadoraciertos, bool aciertos[FICHAS]) //AQUI ESTA LA COSA
{
	bool localizado=false;
	int j=0 ;
	while (!localizado && j<FICHAS)
	{
		if (aciertos [j] == 0)				
		{
			localizado = (CodigoSecreto[n] == codigoJugador[j]) ;
		}
		j++;
	}
	return localizado ;
}
void mostrarMovimiento (int movimiento, int contadoraciertos, int contadorcasi, int contadormal, int puntuacion) 
{
	cout << endl;
	
	cout << "Tienes " << contadoraciertos << " aciertos." << endl;
	
	cout << "Tienes " << contadorcasi << " mal colocados." << endl;

	cout << "Tienes " << contadormal << " que no estan en la secuencia." << endl;

	cout << "Tu puntuacion es: " << puntuacion << " puntos." << endl;

	cout << "Llevas " << movimiento << " movimientos." << endl;
}
void ayuda()
{
	string texto;
	ifstream archivo;
	bool ret=false ;
	archivo.open("ayuda.txt");
	if (archivo.is_open())
	{
		getline (archivo, texto) ;
		while (texto!="XXX")
		{
			cout << texto << endl;
			getline (archivo, texto) ;
		}
		archivo.close();
		ret=true ;
	}
	else
	{
		cout << endl << "No se encuentra el archivo (ayuda.txt) . " << endl << endl;
	}
} 
void calcularPuntuacion (const int contadoraciertos, const int contadorcasi, const bool acierto, int &puntuacion )
{
	if (acierto == true)
	{
		 puntuacion = puntuacion + contadoraciertos*5 + contadorcasi + 100 ;
	}
	else 
	{
		 puntuacion = puntuacion + contadoraciertos*5 + contadorcasi ;
	}
}
void menu (int &opcion, const string nombre) 
{ 
    int contador = 0 ; 
    char nonum; 
    cout << endl << "Hola " << nombre << " Elige una opcion ... " << endl << endl; 
    cout << "0 - Salir" << endl; 
	cout << "1 - Jugar" << endl; 
    cout << "2 - Puntuaciones" << endl; 
    cout << "Opcion: " ; 
    cin >> opcion ; 
    while (cin.fail())//cuando detecta que el valor introducido no corresponde a un entero, que es lo que está definido 
    { 
        cin.clear(); 
        cin >> nonum; //almacenamos el caracter en esta variable y pedmimos de nuevo el valor 
        cout <<  endl << "Por favor "<< nombre << " introduce una opcion valida (un numero). " << endl ; 
		menu (opcion, nombre) ;
    }   
} 
void puntuaciones () 
{
	ifstream archivo;
	string usuario ;
	int juegos;
	int ganados;
	int puntos;

	archivo.open ("usuarios.txt") ;
	if (archivo.is_open ())
	{
		archivo >> usuario ;
		while (usuario != "YYY")
		{
			
			archivo >> juegos ;
			archivo >> ganados ;
			archivo >> puntos ;
			cout << setw(15) << usuario ; 
			cout << setw (5) << juegos   ;
			cout << setw (5) << ganados   ;
			cout << setw (5) << puntos   ;
			cout << endl;
			archivo >> usuario ;
			
		}

		archivo.close ();
	}
	else
	{
		cout << "No se encuentra el archivo (usuarios.txt)" << endl << endl;
	}
}
void modificarPuntuaciones (const string &nombre, const int puntuacion, const bool &acierto )
{
	ifstream archivoOriginal;
	ofstream archivoTemporal;
	ifstream archivoCompleto;
	ofstream archivoNuevo;
	string texto;
	string usuario;
	int repetidor = 0 ;
	int juegos=0;
	int ganados=0;
	int puntos=0;

	archivoOriginal.open ("usuarios.txt") ;
	archivoTemporal.open ("temporal.txt");

	if (archivoOriginal.is_open ())
	{
		archivoOriginal >> usuario ;
		while (usuario != "XXX")
		{
			while (usuario != "YYY")
			{	
				if (usuario == nombre)
				{
					archivoOriginal >> juegos ;
					archivoOriginal >> ganados ;
					archivoOriginal >> puntos ;
				
					archivoTemporal << usuario << " " ;
				
					juegos = juegos + 1 ;
					archivoTemporal << juegos << " " ;
				
					if (acierto)
					{
						ganados = ganados + 1 ;
					}
					archivoTemporal << ganados << " " ;
				
					puntos = puntos + puntuacion ;
					archivoTemporal << puntos << endl;

					repetidor++ ;
				}
				else 
				{
				
					archivoOriginal >> juegos ;
					archivoOriginal >> ganados ;
					archivoOriginal >> puntos ;
						
					archivoTemporal << usuario << " " ;
					archivoTemporal << juegos << " " ;
					archivoTemporal << ganados << " " ;
					archivoTemporal << puntos << endl;
				
				}
				archivoOriginal >> usuario ;
			}
			if (repetidor == 0)
			{
				archivoTemporal << nombre << " " ;
				archivoTemporal << 1 << " " ;
				if (acierto)
				{
					ganados = 1 ;
				}
				else
				{
					ganados = 0 ;
				}

				archivoTemporal << ganados << " " ;

				archivoTemporal << puntuacion << endl;
			}
			
			archivoTemporal << "YYY" << endl << "XXX" << endl;

			archivoOriginal >> usuario ;
		}
		archivoOriginal.close();
		archivoTemporal.close();

		archivoCompleto.open ("temporal.txt") ;
		archivoNuevo.open ("usuarios.txt") ;

		while (texto != "XXX")
		{
			getline (archivoCompleto, texto) ;
			archivoNuevo << texto << endl;
		}
		archivoNuevo << "XXX" << endl;
	}
	else
	{
		archivoNuevo.open ("usuarios.txt") ;

		juegos = juegos + 1 ;
		if (acierto)
		{
			ganados = ganados + 1 ;
		}
		puntos = puntos + puntuacion ;
		archivoNuevo << nombre << " " ;
		archivoNuevo << juegos  << " " ;
		archivoNuevo << ganados  << " " ;
		archivoNuevo << puntos << endl;
		archivoNuevo << "YYY" << endl << "XXX" ;
	}
}

