package tp.pr5.logica;

import tp.pr5.control.TableroInmutable;

public interface Observador {
	
	void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador);
	void onMovimientoEnd(TableroInmutable tab);
	void onUndo(TableroInmutable tab, boolean hayMas ,Ficha turno);
	void onResetPartida(TableroInmutable tabIni, Ficha turno);
	void onUndoNotPossible();
	void onCambioTurno(Ficha turno);
	void onMovimientoIncorrecto(MovimientoInvalido e);
	void onCambioJuego(TableroInmutable tab, Ficha turno);
	void onMostrarTablero(TableroInmutable tab);
	void onInicioPartida(TableroInmutable tab);
	void onMovimientoStart(Ficha turno);
}
