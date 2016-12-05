package tp.pr5.logica;

public enum TipoJuego {

	CONECTA4 (6,7) ,COMPLICA (7,4) ,GRAVITY (10, 10), REVERSI (8,8);
	
	private int alto;
	private int ancho;

	TipoJuego (int f, int c) {
		this.alto = f;
		this.ancho = c;
	}
	
	public int getalto(){
		 if (this==TipoJuego.CONECTA4){
			 alto=6;
		 }
		 if (this==TipoJuego.COMPLICA){
			alto=7; 
		 }
		 if (this==TipoJuego.GRAVITY){
			 
		 }
		if (this==TipoJuego.REVERSI){
			 
		 }
		return alto;
	}
	public int getancho(){
		
		 if (this==TipoJuego.CONECTA4){
			 ancho=7;
		 }
		 if (this==TipoJuego.COMPLICA){
			ancho=4; 
		 }
		 if (this==TipoJuego.GRAVITY){
			
			 
		 }
		if (this==TipoJuego.REVERSI){
			 
		 }
		return ancho;
	}
	
	public void setancho(int x){
		
		if(this==TipoJuego.GRAVITY)
		ancho=x;
	}
	
	public void setalto(int y){
		
		if(this==TipoJuego.GRAVITY)
		alto=y;
	}
	/*
	public String toString(){
	
	String a="";	
		 if (this==TipoJuego.CONECTA4){
			 a="CONECTA4";
		 }
		 if (this==TipoJuego.COMPLICA){
			a="COMPLICA"; 
		 }
		 if (this==TipoJuego.GRAVITY){
			a="GRAVITY";
			 
		 }
		if (this==TipoJuego.REVERSY){
			 a="REVERSY";
		 }
		return a;
		
		
		
	}*/
	
	public boolean getRedimensionable(){
		
		boolean redi=false;
				if(this==TipoJuego.GRAVITY){
					redi=true;
				}
				
				return redi;
		
	}

}
