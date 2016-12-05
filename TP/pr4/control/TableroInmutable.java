package tp.pr4.control;

import tp.pr4.logica.Ficha;

public interface TableroInmutable {

	int getFilas();
	int getColumnas();
	Ficha getCasilla(int col, int fil);
	String toString();

}
