package umu.tds.gestor.modelo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import umu.tds.gestor.modelo.CuentaGasto;

public class CuentaGastoPorcentual extends CuentaGasto {

	public CuentaGastoPorcentual() {
		
	}
	
	public CuentaGastoPorcentual(Double gastoAsociado, Map<String, Double> distribuciones) {
		if(distribuciones == null || distribuciones.isEmpty()) {
			throw new IllegalArgumentException("Se debe añadir al menos una persona a la cuenta");
		}
		
		this.gastoAsociado = gastoAsociado;
		this.distribuciones = new LinkedHashMap<>(distribuciones);
		this.participantes = new ArrayList<>(distribuciones.keySet());
		//El pagador siempre será el primero
		this.nombrePagador = this.participantes.get(0);
		this.saldos = new LinkedHashMap<String, Double>();
		this.ID = UUID.randomUUID();

		for (String p : this.participantes) {
			this.saldos.put(p, 0.0);
		}
		
		//Inicializamos el saldo del pagador y recalculamos todos los saldos
		this.saldos.put(this.nombrePagador, this.gastoAsociado);
		recalcularSaldos();
	}

	public CuentaGastoPorcentual(Double gastoAsociado, String... participantes) {
		if(Arrays.asList(participantes) == null || Arrays.asList(participantes).isEmpty()) {
			throw new IllegalArgumentException("Se debe añadir al menos una persona a la cuenta");
		}
		
		//El pagador siempre será el primero
		this.nombrePagador = participantes[0];
		this.gastoAsociado = gastoAsociado;
		this.distribuciones = new HashMap<String, Double>();
		this.saldos = new HashMap<String, Double>();
		this.participantes = new ArrayList<>(List.of(participantes));	
		this.ID = UUID.randomUUID();
		
		//TODO: Cambiar esto, que esto lo divide equitativamente
		for (String p : this.participantes) {
			this.distribuciones.put(p, 100.0/this.participantes.size());
			this.saldos.put(p, 0.0);
		}
		
		//Inicializamos el saldo del pagador y recalculamos todos los saldos
		this.saldos.put(this.nombrePagador, this.gastoAsociado);
		recalcularSaldos();
	}

	@Override
	public void setGasto(String pagador, Double gasto) {
		if(gasto == null || gasto < 0) {
			throw new IllegalArgumentException("El gasto debe existir o ser mayor que 0");
		}
		if(pagador == null || pagador.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del pagador no puede ser vacío");
		}
		if(!participantes.contains(pagador)) {
			throw new IllegalArgumentException("El pagador debe ser un miembro de la cuenta");
		}
		this.nombrePagador = pagador;
		this.gastoAsociado = gasto;
		this.saldos.put(pagador, gasto);
		this.participantes.remove(pagador);
		this.participantes.add(0, pagador);
		recalcularSaldos();
	}

	public void setDistribucion(String nombre, double value) {
		if(nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la persona no puede estar vacío.");
		} else if (value < 0 || value > 100) {
			throw new IllegalArgumentException("El valor debe estar entre 0 y 100, ambos inclusive.");
		}
		distribuciones.put(nombre, value);
		recalcularSaldos();
	}

	public void setDistribuciones(Map<String, Double> distribuciones) {
		if(distribuciones == null || distribuciones.isEmpty()) {
			throw new IllegalArgumentException("Debe haber al menos una distribuición.");
		}
		this.distribuciones = distribuciones;
		recalcularSaldos();
	}

	@Override
	public void recalcularSaldos() {
		if (this.participantes == null || this.gastoAsociado == null || this.saldos == null || this.distribuciones == null) {
	        return; 
	    }
		
		for(String p : this.participantes) {
			double nuevoSaldo = saldos.get(p) - this.gastoAsociado * (this.distribuciones.get(p) / 100.0);
			this.saldos.put(p, nuevoSaldo);
		}
	}
}
