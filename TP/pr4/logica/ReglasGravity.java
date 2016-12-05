package tp.pr4.logica;

public class ReglasGravity extends ReglasBasicas{


	private int ancho;
	private int alto;

	
	public ReglasGravity(int numCols, int numFilas){
		super.ancho=numCols;
		super.alto=numFilas;
		this.ancho=numCols;
		this.alto=numFilas;
		
	}
	

	
	
	@Override
	//Inicia el tablero con las dimensiones
	public Tablero iniciaTablero() {
		return new Tablero(ancho,alto);
	}



	
	//Comprueba si hay tablas
	public boolean tablas(Ficha ultimoEnPoner, Tablero t) {
		Ficha casilla;
		ancho=t.getAncho();
		alto=t.getAlto();
		boolean tablas=true;
          
        for(int i=alto;i>0;i--){
        	for(int j=ancho;j>0;j--){
        		casilla=t.getCasilla(j,i);
        		if(casilla==Ficha.VACIA){
      	   			tablas=false;
      	   			
      	   			j=-1;
      	   			i=-1;
      	   		}
      	   	}
         }
		
        return tablas;
	}
	

}
