package tp.pr3;

import tp.pr3.control.Controlador;
import tp.pr3.control.FactoriaComplica;
import tp.pr3.control.FactoriaConecta4;
import tp.pr3.control.FactoriaGravity;
import tp.pr3.control.FactoriaTipoJuego;
import tp.pr3.logica.Partida;
import tp.pr3.logica.ReglasComplica;
import tp.pr3.logica.ReglasConecta4;
import tp.pr3.logica.ReglasGravity;
import tp.pr3.logica.ReglasJuego;

public class Main {

	public static void main(java.lang.String[] args){
		
		Controlador controlador;
		Partida partida;
		ReglasJuego reglas = new ReglasConecta4() ;
		FactoriaTipoJuego fac = new FactoriaConecta4() ;
		java.util.Scanner in;
	
		//Si hay argumentos
		if(args.length>0){
			
			//Si es el argumento de jugar
			if(args[0] .equals("-g") || args[0] .equals("--game")){

				//Se selecciona el juego
				switch(args[1]){

					case "c4":

						//Si es c4 no hay mas de 2 argumentos
						if(args.length < 3){
							
							//Establece lo necesario para la partida
							reglas=new ReglasConecta4();
							fac=new FactoriaConecta4();
											
						}
						//Si hay mas de 2 saltara el error
						else {
							System.err.print ("Uso incorrecto: Argumentos no entendidos: ");	
							for (int i = 2; i < args.length -1; i++){
								System.err.print (args[i] + " ");
							}
							System.err.print (args[args.length-1] + "\n");
							System.err.println ("Use -h|--help para más detalles.");
							System.exit(1);
						}
					break;

					case "co":
				
						//Si es co no hay mas de 2 argumentos
						if(args.length < 3){
					
							reglas=new ReglasComplica();			
							fac=new FactoriaComplica();
						}
						//Si hay mas de 2 saltara el error
						else {
							System.err.print ("Uso incorrecto: Argumentos no entendidos: ");	
							for (int i = 2; i < args.length -1; i++){
								System.err.print (args[i] + " ");
							}
							System.err.print (args[args.length-1] + "\n");
							System.err.println ("Use -h|--help para más detalles.");
							System.exit(1);
						}	

					break;
					
					case "gr":
				
						//Por defecto el tablero sera 10x10 si no se especifican las dimensiones
						int col = 10 ;
						int fil = 10 ;
						
						//Si estan los argumentos de las dimensiones
						if(args.length > 2){
					
							//Si el argumento es el de seleccion de X
							if(args[2] .equals("-x") || args[2] .equals ("--tamX")){
								
								try {
									//Comprueba que la columna no es un entero
									col =Integer.parseInt(args[3]);
								}
								//Si no lo es lanza la excepcion
								catch (NumberFormatException e){
									System.err.println ("Uso incorrecto: Argumentos no entendidos: " + args[3]);	
									System.err.println ("Use -h|--help para más detalles.");	
									System.exit(1);
								
								}
							
								//if(args[4].equals("-y") || args[4] .equals ("--tamY")){

									try {
										//Comprueba que la fila no es un entero
										fil =Integer.parseInt(args[5]);
									}
									//Si no lo es lanza la excepcion
									catch (NumberFormatException e){
										System.err.println ("Uso incorrecto: Argumentos no entendidos: " + args[5]);	
										System.err.println ("Use -h|--help para más detalles.");
										System.exit(1);
									}
								//}
							}
				
							//Si introduce primero el -y
							else if(args[2] .equals("-y") || args[2] .equals ("--tamY")){

								try {
									//Comprueba si la fila es entero
									fil =Integer.parseInt(args[3]);
								}
								//Si no lanza la excepcion
								catch (NumberFormatException e){
									System.err.println ("Uso incorrecto: Argumentos no entendidos: " + args[3]);	
									System.err.println ("Use -h|--help para más detalles.");	
									System.exit(1);
								}

								//if(args[4].equals("-x") || args[4] .equals ("--tamX")){

									try {
										//Comprueba si la columna es un entero
										col = Integer.parseInt(args[5]);
									}
									//Si no lanza la excepcion
									catch (NumberFormatException e){
										System.err.println ("Uso incorrecto: Argumentos no entendidos: " + args[5]);	
										System.err.println ("Use -h|--help para más detalles.");
										System.exit(1);									
									}
								//}
							}

							//establece las reglas y la factoria con los parametros deseados
							
							reglas = new ReglasGravity(col,fil);	
							fac = new FactoriaGravity(col,fil);
						
							//fin if de gr con argumentos de dimensiones
						}

					break;
			
					default:
						//Si introduce otros argumentos
						System.err.println ("Uso incorrecto: Juego '" + args[1] + "' incorrecto.");	
						System.err.println ("Use -h|--help para más detalles.");	
						System.exit(1);
						
					break;
			
				}//fin switch
				
				in = new java.util.Scanner(System.in);		
				partida=new Partida(reglas);
				controlador= new Controlador(fac, partida, in);	
				controlador.run();	
			}
			
			//Argumento de ayuda
			else if(args[0] .equals("-h" )|| args[0] .equals("--help")){
							
				System.out.println ("usage: tp.pr3.Main [-g <game>] [-h] [-x <columnNumber>] [-y <rowNumber>]");
				System.out.println (" -g,--game <game>           Tipo de juego (c4, co, gr). Por defecto, c4."); 
				System.out.println (" -h,--help                  Muestra esta ayuda.");
				System.out.println (" -x,--tamX <columnNumber>   Número de columnas del tablero (sólo para");
				System.out.println ("                            Gravity). Por defecto, 10.");
				System.out.println (" -y,--tamY <rowNumber>      Número de filas del tablero (sólo para");
				System.out.println ("                            Gravity). Por defecto, 10.");
				System.exit(0);
			}	
			
			//Si lanza un argumento incorrecyo
			else {
				System.err.println ("Uso incorrecto: Unrecognized option: " + args[0]);	
				System.err.println ("Use -h|--help para más detalles.");
				System.exit(1);
			}
		}
		//Si no hay argumentos
		else{
			//Lanza el juego con normalidad, con el conecta 4 por defecto
			reglas=new ReglasConecta4();
			in = new java.util.Scanner(System.in);		
			partida=new Partida(reglas);
			fac=new FactoriaConecta4();
			controlador= new Controlador(fac, partida, in);	
			controlador.run();
		}
	}
}