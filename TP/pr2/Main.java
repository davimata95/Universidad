package tp.pr2;

import tp.pr2.control.Controlador;
import tp.pr2.logica.Partida;
import tp.pr2.logica.ReglasConecta4;

public class Main {

	public static void main(java.lang.String[] args){
		
		Controlador controlador;
		Partida partida;
		ReglasConecta4 reglas=new ReglasConecta4();
		java.util.Scanner in;
	
		in = new java.util.Scanner(System.in);		
		partida=new Partida(reglas);
		
		controlador= new Controlador(partida, in);	
		controlador.run();
	}
}
