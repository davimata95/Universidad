package tp.pr4.logica;

public enum Ficha {
	VACIA, NEGRA,BLANCA;
	
	//Funcion dise�ada para pintar las "Fichas"
	public String dibuja (Ficha color){	
		String dibujo=" ";
		if (color == VACIA){
			dibujo = " ";
		}
		else if (color == BLANCA){
			dibujo = "O";
		}
		else if (color == NEGRA){
			dibujo = "X";
		}
		return dibujo;	
	}
	
	
	public String toString(){
		
		String a="";
		if(this==Ficha.BLANCA){
			a="BLANCA";
			
			
			
		}
		if(this==Ficha.NEGRA){
			a="NEGRA";
			
			
			
		}
		if(this==Ficha.VACIA){
			a="VACIA";
			
			
		}
		
		return a;
	}
}

