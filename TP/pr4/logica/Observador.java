package tp.pr4.logica;

import tp.pr4.control.TableroInmutable;
import tp.pr4.control.TipoJuego;

public interface Observador {
	
void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador);
void onMovimientoEnd(TableroInmutable tab);
void onUndo(TableroInmutable tab, boolean hayMas);
void onResetPartida(TableroInmutable tabIni, Ficha turno);
void onUndoNotPossible();
void onCambioTurno(Ficha turno);
void onMovimientoIncorrecto(MovimientoInvalido e);
void onCambioJuego(TableroInmutable tab, Ficha turno);
void onCambioDimension(int x, int y);
}
