package tp.pr3.control;

import java.util.Scanner;

import tp.pr3.logica.Ficha;
import tp.pr3.logica.Movimiento;
import tp.pr3.logica.Tablero;

public class JugadorHumanoGravity extends java.lang.Object implements Jugador{

	private Scanner sc;
	private FactoriaTipoJuego factoria ;
		
	//Crea el jugador humano para juagr a gravity
	public JugadorHumanoGravity (Scanner sc){
		this.sc=sc;
		
	}
	
	//Crea el jugador humano para juagr a gravity
	public Movimiento getMovimiento(Tablero tab, Ficha color){
		
		factoria= new FactoriaGravity(tab.getAncho(),tab.getAlto());
		
		System.out.print("Introduce la columna: ");
		//Se obtiene la columna en la que se quiere colocar la ficha
		int columna = sc.nextInt();
		sc.nextLine();
		
		System.out.print ("Introduce la fila: ");
		//Se obtiene la fila en la que se quiere colocar la ficha
		int fila = sc.nextInt();
		
		//Se pone para que el nextline() de la proxima ejecucion no coja un hueco vacio tras presionar "enter"
		sc.nextLine();
		
		//Devuelve la factoria
		return factoria.creaMovimiento(columna, fila, color);
	}
}
