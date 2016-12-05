package tp.pr3.control;

import java.util.Scanner;

import tp.pr3.logica.Ficha;
import tp.pr3.logica.Movimiento;
import tp.pr3.logica.Tablero;

public class JugadorHumanoConecta4 extends java.lang.Object implements Jugador {

	private Scanner sc;
	private FactoriaTipoJuego factoria ;
	
	//Crea el jugador humano para juagr a conecta4
	public JugadorHumanoConecta4 (Scanner sc){
		this.sc=sc;
		factoria= new FactoriaConecta4();
	}
	
	//Crea el jugador humano para juagr a conecta4
	public Movimiento getMovimiento(Tablero tab, Ficha color){
		
		System.out.print("Introduce la columna: ");
		
		//Se obtiene la columna en la que se quiere colocar la ficha
		int columna = sc.nextInt();
		sc.nextLine();
		int fila=0;
		
		//Devuelve la factoria
		return factoria.creaMovimiento (columna, fila, color);
		
	}
}
