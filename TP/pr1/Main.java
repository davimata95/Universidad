package tp.pr1;

import tp.pr1.control.Controlador;
import tp.pr1.logica.Partida;

public class Main {

	public static void main(java.lang.String[] args){
		
		Controlador controlador;
		Partida partida;
		java.util.Scanner in;
		
		in = new java.util.Scanner(System.in);		
		partida=new Partida();
		
		controlador= new Controlador(partida, in);	
		controlador.run();
	}
}
