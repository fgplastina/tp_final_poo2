package sem_estacionamiento;

import java.time.LocalTime;

public class EstacionamientoCompraApp extends Estacionamiento {

	private Boolean estaVigente;
	private int numeroDeCelular;

	public EstacionamientoCompraApp(String patente, int numeroDeCelular, LocalTime horaFinal) {
		super(patente, horaFinal);
		this.numeroDeCelular = numeroDeCelular;
		this.estaVigente = true;
	}
	
	@Override
	public Boolean estacionamientoVigente() {
		return this.estaVigente;
		
	}
	
	public Boolean sonNumerosIguales(int nroCelular) {
		return nroCelular == this.getNumeroDeCelular();
	}

	@Override
	public void finalizar(LocalTime horaFin) {
		this.setEstaVigente(false);
		this.setHoraDeFinalizacion(horaFin);
		
	}
	
	public Boolean getEstaVigente() {
		return this.estaVigente;
	}
	
	public void setEstaVigente(Boolean estaVigente) {
		this.estaVigente = estaVigente;
	}

	public int getNumeroDeCelular() {
		return numeroDeCelular;
	}

	public void setNumeroDeCelular(int numeroDeCelular) {
		this.numeroDeCelular = numeroDeCelular;
	}

}
