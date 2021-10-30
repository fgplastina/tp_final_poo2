package sem_venta;

import java.time.LocalDate;
import java.time.LocalTime;

import sem_PuntoDeVenta.PuntoDeVenta;

public class VentaRecarga extends Venta {
	
	@SuppressWarnings("unused")
	private int nroCelular;
	private double monto;
	
	public VentaRecarga(int celular, double monto, PuntoDeVenta puntoDeVenta, int nroControl) {
		super(puntoDeVenta, nroControl);

		this.nroCelular = celular;
		this.monto = monto;
	}
	
	public double getMonto() {
		return this.monto;
	}
	public double getNroCelular() {
		return this.nroCelular;
	}
	
}
