package tp.pr5.control;

import java.util.InputMismatchException;
import java.util.Scanner;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.Tablero;

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
	public Movimiento getMovimiento(Tablero tab, Ficha color) throws InputMismatchException{
		
		System.out.print("Introduce la columna: ");
		//Se obtiene la columna en la que se quiere colocar la ficha
		int columna = 0;
		int fila=0;
		
		columna = sc.nextInt();
		sc.nextLine();
			
		System.out.print ("Introduce la fila: ");
		//Se obtiene la fila en la que se quiere colocar la ficha
		fila = sc.nextInt();

		//Se pone para que el nextline() de la proxima ejecucion no coja un hueco vacio tras presionar "enter"
		sc.nextLine();
		
		//Devuelve la factoria
		return factoria.creaMovimiento(columna, fila, color);
	}
	
	public Movimiento getMovimiento(Tablero tab, Ficha color,int col,int fila){
		
		//Devuelve la factoria
		return factoria.creaMovimiento (col, fila, color);
		
	}
}
