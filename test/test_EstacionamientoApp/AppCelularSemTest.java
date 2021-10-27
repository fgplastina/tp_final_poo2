package test_EstacionamientoApp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sem.GestorSem;
import sem_estacionamientoApp.AppCelularSem;
import sem_estacionamientoApp.EstadoApp;
import sem_estacionamientoApp.ModoApp;


class AppCelularSemTest {
	
	AppCelularSem sutApp;
	GestorSem gestorMock;
	EstadoApp estadoAppMock;
	ModoApp modoAppMock;
	
	String patente;
	double saldoDisponible;
	int nroCelular;
	int coordenadaGPS;

	@BeforeEach
	void setUp() throws Exception {
		patente = "VAS930";
		coordenadaGPS = 1822717272;
		saldoDisponible = 200.0;
		nroCelular = 11267364;
		gestorMock = mock(GestorSem.class);
		modoAppMock = mock(ModoApp.class);
		estadoAppMock = mock(EstadoApp.class);
		
		sutApp = new AppCelularSem(nroCelular,patente,gestorMock, estadoAppMock, modoAppMock);
		sutApp.setCoordenadaGPS(coordenadaGPS);
	}
	
	@Test
	void testConstructor() {	
		assertTrue(sutApp.getNroPatente() == patente);
		assertTrue(sutApp.getNroCelular() == nroCelular);
		assertTrue(sutApp.getNroPatente() == patente);
		assertTrue(sutApp.getEstadoMovimiento() == estadoAppMock);
		assertTrue(sutApp.getModo() == modoAppMock);
		assertTrue(sutApp.getGestor() == gestorMock);
	}

	@Test
	void testAlRecibirMensajeDrivingEsteSeDelegaASusEstados() {
		
		sutApp.driving();	
		verify(estadoAppMock).manejando(sutApp);
	}
	
	@Test
	void testAlRecibirMensajeWalkingEsteSeDelegaASusEstados() {
		
		sutApp.walking();	
		verify(estadoAppMock).caminando(sutApp);
	}
	
	@Test
	void testDeIniciarEstacionamientoDeFormaManual() {	
		sutApp.setSaldoDisponible(saldoDisponible);
		sutApp.setNroCelular(nroCelular);
		sutApp.iniciarEstacionamiento(patente);	
		verify(modoAppMock).iniciarEstacionamiento(patente, gestorMock, sutApp,saldoDisponible,nroCelular);
	}
	
	@Test
	void testDeFinalizarEstacionamientoDeFormaManual() throws Exception {	
		sutApp.finalizarEstacionamiento();	
		verify(modoAppMock).finalizarEstacionamiento(gestorMock,nroCelular);
	}
	
	
	// TEST COMENZO A CAMINAR
	
	@Test 
	void testAlComenzarACaminarConEstacionamientoNoVigenteYDentroDeLaZonaConCoordenadaSeAlertaElInicioDelEstacionamiento() {
		
		sutApp.setSaldoDisponible(saldoDisponible);
		sutApp.setNroCelular(nroCelular);
		when(gestorMock.tieneEstacionamientoVigente()).thenReturn(false);
		when(gestorMock.estaDentroDeUnaZonaConLaCoordenada(coordenadaGPS)).thenReturn(true);
				
		sutApp.comenzoACaminar();

		verify(modoAppMock).alertaInicioDeEstacionamiento(gestorMock, sutApp, saldoDisponible, nroCelular);
	}
	
	@Test 
	void testAlComenzarACaminarConEstacionamientoVigenteYDentroDeLaZonaConCoordenadaNoIniciaElEstacionamiento() {
		
		when(gestorMock.tieneEstacionamientoVigente()).thenReturn(true);
		when(gestorMock.estaDentroDeUnaZonaConLaCoordenada(coordenadaGPS)).thenReturn(true);
		
		sutApp.comenzoACaminar();

		verifyNoInteractions(modoAppMock);
	}
	
	@Test 
	void testAlComenzarACaminarConEstacionamientoVigenteYNOEstaDentroDeUnaZonaConCoordenadaNoIniciaElEstacionamiento() {
		
		when(gestorMock.tieneEstacionamientoVigente()).thenReturn(true);
		when(gestorMock.estaDentroDeUnaZonaConLaCoordenada(coordenadaGPS)).thenReturn(false);
		
		sutApp.comenzoACaminar();

		verifyNoInteractions(modoAppMock);
	}

	// TEST COMENZO A CONDUCIR
	
	@Test 
	void testAlComenzarAConducirConEstacionamientoNoVigenteYEstaEnElMismoPuntoGeograficoNoSeAlertaElInicioDelEstacionamiento() {
		
		when(gestorMock.tieneEstacionamientoVigente()).thenReturn(false);
		when(gestorMock.estaEnElMismoPuntoGeograficoDeInicioEstcaiomiento(coordenadaGPS)).thenReturn(true);
		
		sutApp.comenzoAManejar();
		verifyNoInteractions(modoAppMock);
		
	}
	
	@Test 
	void testAlComenzarAConducirConEstacionamientoVigenteYNOEstaEnElMismoPuntoGeograficoNoSeAlertaElInicioDelEstacionamiento()  {
		
		when(gestorMock.tieneEstacionamientoVigente()).thenReturn(true);
		when(gestorMock.estaEnElMismoPuntoGeograficoDeInicioEstcaiomiento(coordenadaGPS)).thenReturn(false);
		
		sutApp.comenzoAManejar();

		verifyNoInteractions(modoAppMock);
	}
	
	@Test 
	void testAlComenzarAConducirConEstacionamientoNoVigenteYEstaEnElMismoPuntoGeograficoSeAlertaElInicioDelEstacionamiento()  {
		
		when(gestorMock.tieneEstacionamientoVigente()).thenReturn(true);
		when(gestorMock.estaEnElMismoPuntoGeograficoDeInicioEstcaiomiento(coordenadaGPS)).thenReturn(true);
		
		sutApp.comenzoAManejar();

		verify(modoAppMock).alertaDeFinDeEstacionamiento(gestorMock,nroCelular);
	}

}