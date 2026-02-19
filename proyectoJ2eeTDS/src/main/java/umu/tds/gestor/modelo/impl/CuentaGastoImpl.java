package umu.tds.gestor.modelo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import umu.tds.gestor.modelo.CuentaGasto;



//TODO: EL GASTO AHORA MISMO NO ESTA ASOCIADO A UNA PERSONA Y CALCULAR LOS SALDOS DE CADA UNO NO LO TIENE EN CUENTA



public class CuentaGastoImpl implements CuentaGasto {

	private double gasto;
	//Distribuiciones es para porcentajes, saldos es para valores enteros como tal
	private Map<String, Double> distribuiciones;
	private Map<String, Double> saldos;
	private List<String> participantes;
	//Identificador único de la cuenta
	private UUID ID;
	//Nombre a quien se le asocia el gasto original
	
	//Constructor vacio para la persistencia
	public CuentaGastoImpl() {
	}
	
	//TODO: Comprobacion de errores y persistencia
	public CuentaGastoImpl(String... participantes) {
		this.gasto = 0;
		this.distribuiciones = new HashMap<String, Double>();
		this.saldos = new HashMap<String, Double>();
		this.participantes = Arrays.asList(participantes);
		//Esto podria cambiarse para que haya repeticiones pero por el momento lo dejo así.
		this.ID = UUID.randomUUID();
		
		for (String p : this.participantes) {
			//100.0 entre la longitud de la lista para sacar porcentajes iguales
			this.distribuiciones.put(p, 100.0/this.participantes.size());
			//El saldo incial de cada persona será 0
			this.saldos.put(p,0.0);
		}
	}
	
	@Override
	public List<String> getParticipantes() {
		return participantes;
	}

	@Override
	public Map<String, Double> getDistribuciones() {
		return distribuiciones;
	}

	@Override
	public Double getGasto(String nombre) {
		return this.gasto;
	}
	
	@Override
	public void setGasto(double gasto) {
		this.gasto = gasto;
	}

	@Override
	public void setDistribucion(String nombre, double value) {
		//trim quita los espacios
		if(nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la persona no puede estar vacío.");
		} else if (value < 0 || value > 0) {
			throw new IllegalArgumentException("El valor debe estar entre 0 y 100, ambos inclusive.");
		}
		//Reescribe la asociacion si nombre ya estaba añadido
		distribuiciones.put(nombre, value);
		
	}

	@Override
	public void setDistribuciones(Map<String, Double> distribuciones) {
		if(distribuciones == null || distribuciones.isEmpty()) {
			throw new IllegalArgumentException("Debe haber al menos una distribuición.");
		}
		this.distribuiciones = distribuciones;
	}

	@Override
	public void introducirGasto(String nombre, GastoImpl gasto) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void recalcularSaldos() {
		for(String p : this.participantes) {
			//Multiplica el gasto total por el porcentaje que le corresponde a la persona
			double gasto = this.gasto*(this.distribuiciones.get(p)/100.0);
			this.saldos.put(p, gasto);
		}
	}

}
