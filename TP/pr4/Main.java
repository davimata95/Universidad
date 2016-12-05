package tp.pr4;

import org.apache.commons.cli.*;

import tp.pr4.control.ControlSwing;
import tp.pr4.control.FactoriaComplica;
import tp.pr4.control.FactoriaConecta4;
import tp.pr4.control.FactoriaGravity;
import tp.pr4.control.FactoriaTipoJuego;
import tp.pr4.control.VistaConsola;
import tp.pr4.control.VistaSwing;
import tp.pr4.logica.Partida;

public class Main {
	

	@SuppressWarnings("static-access")
	public static void main(java.lang.String[] args){
		
		CommandLine comando = null;
		Options opcion = new Options();
		CommandLineParser parser = new BasicParser();
		
		//el juego por defecto sera c4
		String tipoJuego = "c4";
		//las dimensiones por defecto para el gravity seran 10x10
		int filas = 10, columnas = 10;
		
		FactoriaTipoJuego factoria= new FactoriaConecta4();
		Partida partida;
		java.util.Scanner in;
		in = new java.util.Scanner(System.in);	
		
		opcion.addOption("h", "help", false, "Muestra esta ayuda.");
		
		opcion.addOption(OptionBuilder	.withArgName("game")
										.withLongOpt("game")
										.hasArg().withDescription("Tipo de juego (c4, co, gr). Por defecto, c4.")
										.create("g"));
		
		opcion.addOption(OptionBuilder	.withArgName("columnNumber")
										.withLongOpt("tamX")
										.hasArg().withDescription("Número de columnas del tablero (sólo para Gravity). Por defecto, 10.")
										.create("x"));
		
		opcion.addOption(OptionBuilder	.withArgName("rowNumber")
										.withLongOpt("tamY")
										.hasArg().withDescription("Número de filas del tablero (sólo para Gravity). Por defecto, 10.")
										.create("y"));
		
		opcion.addOption(OptionBuilder	.withArgName("tipo")
										.withLongOpt("ui")
										.hasArg().withDescription("Tipo de interfaz (console, window). Por defecto, console.")
										.create("u"));
	
		try {
			comando = parser.parse(opcion, args);
		} 
		catch (ParseException error) {
			System.err.println("Uso incorrecto: "+ error.getMessage());
			System.err.println("Use -h|--help para más detalles.");
			System.exit(1);
		}
		
		if(comando.hasOption("-h")){
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("tp.pr4.Main [-g <game>] [-h] [-x <columnNumber>] [-y <rowNumber>]", opcion);
			System.exit(0);
		}

		if(comando.hasOption("-g"))
			tipoJuego = comando.getOptionValue("g");

			String[] numArgs = comando.getArgs();
		
		//Tanto co como c4 solo deben tener 2 argumentos -g y el modo de juego
		if (tipoJuego.equalsIgnoreCase("c4")){
			if (numArgs.length > 0){
				argumentosInvalidos(numArgs);
			}
			else factoria = new FactoriaConecta4();
		}
		
		else if (tipoJuego.equalsIgnoreCase("co")) {
			if (numArgs.length > 0){
				argumentosInvalidos(numArgs);
			}
			else {
				factoria = new FactoriaComplica();
			}
		}

		//Si el tipo de juego es gravity se aceptaran mas argumentos para las dimensiones
		else if(tipoJuego.equalsIgnoreCase("gr")){
						
			if (numArgs.length == 0){	
				try{
					if(comando.hasOption("-x")){
						columnas =Integer.parseInt(comando.getOptionValue("x"));
					}
					if(comando.hasOption("-y")){
						filas=Integer.parseInt (comando.getOptionValue("y"));
					}
					
				}catch(NumberFormatException e){
					System.err.println("Introduce columna y fila correcta.");
					System.err.println("Use -h|--help para más detalles.");
					System.exit(1);
				}
			}
			//Si detecta que hay argumentos no utilizados
			else {
				argumentosInvalidos(args);
			}
			factoria = new FactoriaGravity(columnas, filas);
		}
		
		//Si no se introducen estos tipos de juego, sera error
		else{
			System.err.println("Uso incorrecto: Juego '" + tipoJuego + "' incorrecto.");
			System.err.println("Use -h|--help para más detalles.");
			System.exit(1);
		}
		
		partida =new Partida(factoria.creaReglas());		
		
		String tipoVista="consola";
		VistaConsola VC = new VistaConsola(in, factoria, partida);
		
		if(comando.hasOption("-u")){
		
			if (numArgs.length == 0){	
			
				tipoVista = comando.getOptionValue("u");
				
				if (tipoVista.equalsIgnoreCase("swing")){
					ControlSwing CS = new ControlSwing(partida, factoria);
					VistaSwing VS = new VistaSwing(CS);
				//	VS.run();
				}
				else if (tipoVista.equalsIgnoreCase("consola")) {
					
				}
				else{
					System.err.println("Uso incorrecto: Vista '" + tipoVista + "' incorrecto.");
					System.err.println("Use -h|--help para más detalles.");
					System.exit(1);
				}
			}
				//Si no es consola o swing arg. invalidos
			else{
				argumentosInvalidos(args);
			}
		}
		//Se lanzara si no se lanza primero vistaSwing
		VC.run();
	}
	//se meten más argumentos de la cuenta, sirve para -g c4 Z(args de sobra)
	private static void argumentosInvalidos(java.lang.String[] args) {
		String espacio = " ";
		String sobra = "";
		//escribe en el string los argumentos qeu sobran
		for (int i = 0; i < args.length; i++) {
			sobra += args[i] + espacio;
		}
		System.err.println("Uso incorrecto: Argumentos no entendidos: " + sobra);
		System.err.println("Use -h|--help para más detalles.");
		System.exit(1);
	}
}
