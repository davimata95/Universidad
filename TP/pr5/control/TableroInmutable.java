package tp.pr5.control;

import tp.pr5.logica.Ficha;

public interface TableroInmutable {

	int getFilas();
	int getColumnas();
	Ficha getCasilla(int col, int fil);
	String toString();

}
