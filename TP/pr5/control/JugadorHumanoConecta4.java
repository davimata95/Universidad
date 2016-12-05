package tp.pr5.control;

import java.util.Scanner;
import java.util.InputMismatchException;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.Tablero;

public class JugadorHumanoConecta4 extends java.lang.Object implements Jugador {

	private Scanner sc;
	private FactoriaTipoJuego factoria ;
	
	//Crea el jugador humano para juagr a conecta4
	public JugadorHumanoConecta4 (Scanner sc,FactoriaTipoJuego f){
		this.sc=sc;
		factoria= f;
	}
	public JugadorHumanoConecta4 (FactoriaTipoJuego f){
		factoria= f;
	}
	
	//Crea el jugador humano para juagr a conecta4
	public Movimiento getMovimiento(Tablero tab, Ficha color) throws InputMismatchException{
		
		//Se obtiene la columna en la que se quiere colocar la ficha
		System.out.print("Introduce la columna: ");

		int columna = 0;
		int fila=0;

		columna = sc.nextInt();

		sc.nextLine();
		//Devuelve la factoria
		return factoria.creaMovimiento (columna, fila, color);
		
	}
	
	
	public Movimiento getMovimiento(Tablero tab, Ficha color,int col,int fila){
		
		//Se obtiene la columna en la que se quiere colocar la ficha
		
		//Devuelve la factoria
		return factoria.creaMovimiento (col, fila, color);
		
	}
}
