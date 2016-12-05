#include <iostream>
using namespace std;
#include <cmath>
#include <cstdlib>
#include <ctime>
#include <fstream>
#include <string>
#include <windows.h>
#include <conio.h>


int codeBreaker(int intentos, int longitud, int intervalo);
int claveAleatoria(int longitud, int intervalo);
int invertir(int);
int longitudCodigo(int codigo);
bool codigoValido(int codigo, int longitud, int intervalo);
int numeroBips(int claveInvertida, int codigoInvertido, int longitud);
int menu();
int pedirIntentos(int intentos);
int pedirLongitud(int longitud);
int pedirIntervalo(int intervalo);
bool mostrar(string nombArch);
int RompeCodigo( int intentos, int longtud, int intervalo);

int main()
{
	string nombArch;
	int intentos, longitud, intervalo;
	ifstream archivo; 
	archivo.open("configCB.txt"); //abre el archivo
	if (archivo.is_open()) // si existe el archivo toma los valores de intentos, longitud e intervalo
	{
		archivo >> intentos;
		archivo >> longitud;
		archivo >> intervalo;
		archivo.close(); //cierra el archivo
	}
	else // si el archivo no existe define los valores de intentos (3), longitud (4) e intervalo (3)
	{ 
		intentos = 3;
		longitud = 4;
		intervalo = 3;
	}
	ofstream archi; 
	archi.open("automatico.txt"); //apertura del archivo 
	archi << -1;
	archi.close();

	int opcion = 1;
	while (opcion != 0) 
	{ 
		opcion = menu();

		switch (opcion) 
		{
			opcion = (menu());
		
			case 0:
				break;
					
			case 1:
				codeBreaker(intentos, longitud, intervalo);
				break;

			case 2:
				intentos = (pedirIntentos(intentos));
				break;

			case 3:
				longitud = (pedirLongitud(longitud));
				break;

			case 4:
				intervalo = (pedirIntervalo(intervalo));
				break;
			
			case 5:
				nombArch = "versionCB.txt";
				mostrar(nombArch);
				break;

			case 6:
				RompeCodigo(intentos, longitud, intervalo);
				break;

			default:
				cout << "No es una opcion valida" << endl;
				break;

		}
	}

	cout << "GAME OVER";

	return 0;
}

int codeBreaker(int intentos, int longitud, int intervalo){ // inicia el juego
	int claveinvertida, codigoinvertido;
	int nintentos = 0;
	int codigo = 9, clave, longitudCod;
	bool valido;
	int correcto;

	cout << "Tienes " << intentos << " intentos" << endl; //define los intentos de la partida
	cout << "El numero tiene " << longitud << " digitos" << endl; //define la longitud de la clave
	cout << "Los digitos estan comprendidos entre 1 y " << intervalo << endl; // define el intervalo de la clave

	clave = claveAleatoria(longitud, intervalo); 
	claveinvertida = invertir(clave); 

	while ((codigo != clave) && (nintentos < intentos))
	{
		cin >> codigo; // el usuario introduce el codigo

		if (codigo == 0) //si el codigo introducido es 0 finaliza la partida
		{
			return 0;
		}

		longitudCod = longitudCodigo(codigo);

		codigoinvertido = invertir(codigo);

		valido = codigoValido(codigo, longitud, intervalo); 
		
		if (valido == true)
		{
			correcto = numeroBips(claveinvertida, codigoinvertido, longitud);
			nintentos++;
			if (correcto != longitud)
			{
				cout << "Intentalo de nuevo, te quedan " << intentos - nintentos << " intentos: ";
			}
			else
			{
				cout << "Has utilizado " << nintentos << " intentos. " << endl;
			}
		}
	}
	
	ofstream archivo1;
	archivo1.open("configCB.txt"); //apertura del archivo 
	archivo1 << intentos << " " << longitud << " " << intervalo;
	archivo1.close(); //cierra el archivo	
	return 0;
}

int claveAleatoria(int longitud, int intervalo) // genera la clave
{  
	int digitos = 1;
	int clave = 0;
	int a = pow(10, longitud - 1);
	int aleatorio;
	srand(time(NULL)); //anula el tiempo para generar el numero 
	while (digitos <= longitud) // genera digitos hasta que sean 4 
	{ 
		aleatorio = (rand() % intervalo) + 1; // genera un numero entre 1 y 4
		clave = clave + (aleatorio * a); // va creando el numero final 
		a = a / 10;
		digitos++; // contador 
	}
	return clave; // devuelve la clave
}

int invertir(int n)// invierte el numero
{ 
	int invertido = 0, derecha;
	while (n != 0)
	{
		derecha = n % 10; // aisla el ultimo digito del numero	
		invertido = invertido * 10 + derecha; // calcula el numero invertido
		n = n / 10;
	}
	return invertido; //devuelve el numero invertido
}

int longitudCodigo(int codigo)// calcula la longitud del codigo
{ 
	int longitudCod = 0;
	while (codigo >= 1)
	{ 
		codigo = codigo / 10;
		longitudCod++; // suma 1 al contador
	}
	return longitudCod; //devuelve la longitud del codigo
}

bool codigoValido(int codigo, int longitud, int intervalo)
{
	int codigo1 = codigo;
	int numero = 0;
	int contador = 0;
	int digito = pow(10, longitud - 1);
	bool ret = true;
	if (((codigo / (pow(10, longitud - 1)))<1) || (codigo / (pow(10, longitud - 1))>9)) 
	{ 
		cout << "El numero debe tener " << longitud << " digitos" << endl;
		ret= false; // si el numero no tiene la longitud necesaria devuelve falso
	}
	while (contador != longitud && ret) 
	{
		numero = codigo1 / digito;
		if ((numero < 1) || (numero > intervalo)) 
		{
			cout << "Los digitos del numero deben estar entre 1 y " << intervalo << endl;
			ret = false; // si el numero no tiene el intervalo necesario devuelve falso
		}
		if (ret)
		{
			contador++;
			codigo1 = codigo1 % digito;
			digito = digito / 10;
		}
	}
	return ret;
}

int numeroBips(int claveInvertida, int codigoInvertido, int longitud) // comprueba los codigos y muestra los bip/bop
{ 
	int contador = 0;
	int restoCla = claveInvertida % 10;
	int restoCod = codigoInvertido % 10;
	while ((restoCla == restoCod) && (contador <= longitud - 1)) // muestra un bip si coincide un digito
	{
		claveInvertida = claveInvertida / 10;
		codigoInvertido = codigoInvertido / 10;
		restoCla = claveInvertida % 10;
		restoCod = codigoInvertido % 10;
		contador++; // suma uno al contador
		cout << "bip ";
	}
	if (contador <= longitud - 1)  // si el digito no coincide muestra bop
	{
		cout << "bop -- Acceso denegado! " << endl;
	}
	else  // si el numero coincide muestra "-- OK! "
	{
		cout << "-- OK! " << endl;
	}

	return contador; // devuelve contador
}

int menu()
{
	int opcion;
	cout << endl;
	cout << "1 - Jugar" << endl;
	cout << "2 - Cambiar el maximo de intentos" << endl;
	cout << "3 - Cambiar la longitud de los codigos" << endl;
	cout << "4 - Cambiar el intervalo de digitos" << endl;
	cout << "5 - Acerca de CodeBreaker" << endl;
	cout << "6 - Rompedor Automatico" << endl;
	cout << "0 - Salir" << endl;
	cout << "Opcion: ";
	cin >> opcion;
	return opcion; // devuelve la opcion introducida
}

int pedirIntentos(int intentos) 
{
	cout << "Introduce los intentos deseados: ";
	cin >> intentos;
	return intentos; // devuelve los intentos seleccionados
}

int pedirLongitud(int longitud)
{
	cout << "Introduce la longitud maxima del codigo:  ";
	cin >> longitud;
	return longitud; // devuelve la longitud seleccionada
}

int pedirIntervalo(int intervalo) 
{
	cout << "Introduce el intervalo (1-X): ";
	cin >> intervalo;
	return intervalo; // devuelve el intervalo seleccionado
}

bool mostrar(string nombArch) // muestra el archivo de la opcion 5 (acerca de CodeBreaker)
{ 
	string texto;
	ifstream archivo2;
	bool ret = false;

	archivo2.open(nombArch); //abre el archivo
	if (archivo2.is_open())
	{
		getline(archivo2, texto);
		while (texto != "X")
		{
			cout << texto << endl;
			getline(archivo2, texto);
		}
		archivo2.close();
		ret = true;
	}
	return ret;

}


int RompeCodigo( int intentos, int longtud, int intervalo)
{
	int claveinvertida, codigoinvertido;
	int nintentos = 0;
	int codigo = 9, clave, longitudCod;
	int claveRompedora;
	bool valido[10];
	int voyBien=0;
	for (int i = 0; i < 10; i++) //pone el array a true, salvo el 0 que puede estar en el intervalo
		valido[i] = true;
		valido[0] = false;

	cout << "La maquina tiene " << intentos << " intentos" << endl; // define los intentos 
	cout << "El numero tiene " << longtud << " digitos" << endl; // define la longitud del numero
	cout << "Los digitos estan comprendidos entre 1 y " << intervalo << endl; // define el intervalo de los digitos

	clave = claveAleatoria(longtud, intervalo); 
	cout << "La clave que ha de averiguar es: " << clave << endl;
	claveinvertida = invertir(clave); 
	ifstream archivo;
	archivo.open("automatico.txt"); //apertura del archivo 
	archivo >> claveRompedora;
	archivo.close();

	Sleep(1000); //espera para asegurar que la semilla cambia y que no genere la clave correcta a la primera
	while ((claveRompedora != clave) && (nintentos < intentos)){
		if (claveRompedora == -1) //Si no se ha acertado el primer número, o es el primer intento, crea un nuevo codigo para probar.
		{
			
			int a;
			do
			{
				claveRompedora = claveAleatoria(longtud, intervalo);
				a = claveRompedora / pow(10, longtud - 1);
			} while (!valido[a]); //se asegura de que el valor introducido no está marcado como false en el array
		}
		else
		{
			int c = longtud;
			int claveRompedoraaux = claveRompedora;
			while (claveRompedoraaux > 0)
			{
				claveRompedoraaux = claveRompedoraaux / 10;
				c--;
			}
			
			int faltan = c;
			
			claveRompedora = claveRompedora*(pow(10, faltan));


				Sleep(1000);
				int digitos = 1;
				int nuevo = 0;
				int a = pow(10, faltan - 1);
				int aleatorio;
				srand(time(NULL)); //usa el tiempo como semilla para generar el numero 
				while (digitos <= faltan) // genera digitos hasta que sean de la longitud deseada
				{
					do
					{	
						aleatorio = (rand() % intervalo) + 1;
					} 
					while (!valido[aleatorio]); // genera un numero que no se haya usado con anterioridad
					claveRompedora = claveRompedora + (aleatorio * a); // va creando el numero final 
					a = a / 10;
					digitos++; // contador 
				
				}
		}
		cout << claveRompedora << endl;
		longitudCod = longitudCodigo(claveRompedora);

		codigoinvertido = invertir(claveRompedora);

		codigoValido(claveRompedora, longtud, intervalo);

		int aux = numeroBips(claveinvertida, codigoinvertido, longtud);
		if (aux > voyBien)
		{
			voyBien = aux; //reinicia el array si se acierta un nuevo dígito
			for (int i = 0; i < 10; i++)
				valido[i] = true;
			valido[0] = false;
		}
		
			if (aux == 0)
			{
				int help = claveRompedora / pow(10, longtud-1); //apunta un nuevo dígito fallado en el array como false
				int help2 = (pow(10, longtud ));
				help = help % help2;
				valido[help] = false;
			}
			else
			{
				int help = claveRompedora / pow(10, longtud-aux-1);
				help = help % 10;
				valido[help] = false;
			}

		

		int pot = pow(10, longtud - aux);

		claveRompedora = claveRompedora / pot;

		if (claveRompedora != 0) //guarda en un archivo de texto las partes que van siendo correctas de la clave
		{
			ofstream archivo;
			archivo.open("automatico.txt"); //apertura del archivo 
			archivo << claveRompedora;
			archivo.close();
		}
		else claveRompedora = -1; //Si es el primer intento y no se ha acertado el primer dígito, o ya se ha acertado la clave, vuelve a poner el archivo de texto en "-1"


		nintentos++;
	}
	cout << "La maquina ha necesitado: " << nintentos << " intentos. " << endl;

	ofstream archivo1;
	archivo1.open("configCB.txt"); //apertura del archivo 
	archivo1 << intentos << " " << longtud << " " << intervalo;
	archivo1.close(); //cierra el archivo	

	ofstream archi;
	archi.open("automatico.txt"); //apertura del archivo 
	archi << -1;
	archi.close();

	return 0;


}