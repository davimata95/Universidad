package tp.pr1.logica;

public enum Ficha {
	VACIA, NEGRA,BLANCA;

	public String dibuja (Ficha color){	//Funcion diseñada para pintar las "Fichas"
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
}

