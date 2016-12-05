package tp.pr4.control;

import java.util.InputMismatchException;
import java.util.Scanner;

import tp.pr4.logica.Ficha;
import tp.pr4.logica.Movimiento;
import tp.pr4.logica.Tablero;

public class JugadorHumanoGravity extends java.lang.Object implements Jugador{

	private Scanner sc;
	private FactoriaTipoJuego factoria ;
		
	//Crea el jugador humano para juagr a gravity
	public JugadorHumanoGravity (Scanner sc,FactoriaTipoJuego f){
		this.sc=sc;
		factoria=f;
	}
	
	public JugadorHumanoGravity (FactoriaTipoJuego f){
		
		factoria=f;
	}
	//Crea el jugador humano para juagr a gravity
	public Movimiento getMovimiento(Tablero tab, Ficha color){
		
		System.out.print("Introduce la columna: ");
		//Se obtiene la columna en la que se quiere colocar la ficha
		int columna = 0;
		int fila=0;
		
		try{
			columna = sc.nextInt();
			sc.nextLine();
			
			System.out.print ("Introduce la fila: ");
			//Se obtiene la fila en la que se quiere colocar la ficha
			fila = sc.nextInt();
		}
		catch(InputMismatchException e){
			System.err.println("Debes introducir una columna y fila en formato entero");
		}

		//Se pone para que el nextline() de la proxima ejecucion no coja un hueco vacio tras presionar "enter"
		sc.nextLine();
		
		//Devuelve la factoria
		return factoria.creaMovimiento(columna, fila, color);
	}
	
	public Movimiento getMovimiento(Tablero tab, Ficha color,int col,int fila){
		
		//Se obtiene la columna en la que se quiere colocar la ficha
		
		//Devuelve la factoria
		return factoria.creaMovimiento (col, fila, color);
		
	}
}
