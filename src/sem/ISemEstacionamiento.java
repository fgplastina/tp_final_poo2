package sem;
import sem_estacionamiento.Estacionamiento;

import java.time.LocalTime;

public interface ISemEstacionamiento {
	public void finalizarTodosLosEstacionamientos(LocalTime horaFinDeJornada);
	public boolean estaDentroDeUnaZonaConLaCoordenada(int coordenada);
	public boolean estaEnElMismoPuntoGeograficoDeInicioEstaciomiento(int coordenada, String patente);
	public void registrarEstacionamiento(Estacionamiento e);
	public Estacionamiento buscarEstacionamientoVigente(int nroCelular) throws Exception;
	public boolean tieneEstacionamientoVigente(String patente);
	public boolean consultarEstacionamiento(String patente, int idInspector);
	
}
