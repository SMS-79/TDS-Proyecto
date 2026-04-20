package umu.tds.gestor.modelo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import umu.tds.gestor.modelo.CuentaGasto;
import umu.tds.gestor.modelo.Gasto;

public class CuentaGastoEquitativa extends CuentaGasto {

	public CuentaGastoEquitativa() {
		
	}
	
	public CuentaGastoEquitativa(String... participantes) {
		if(Arrays.asList(participantes) == null || Arrays.asList(participantes).isEmpty()) {
			throw new IllegalArgumentException("Se debe añadir al menos una persona a la cuenta");
		}
		
		this.nombrePagador = null;
		this.gastoAsociado = null;
		this.distribuciones = new HashMap<String, Double>();
		this.saldos = new HashMap<String, Double>();
		this.participantes = new ArrayList<>(List.of(participantes));	
		this.ID = UUID.randomUUID();
		
		for (String p : this.participantes) {
			this.distribuciones.put(p, 100.0/this.participantes.size());
			this.saldos.put(p, 0.0);
		}
	}

	@Override
	public void setGasto(String pagador, Gasto gasto) {
		if(gasto == null || gasto.getCantidad() < 0) {
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
		this.saldos.put(pagador, gasto.getCantidad());
		this.participantes.remove(pagador);
		this.participantes.add(0, pagador);
		recalcularSaldos();
	}

	@Override
	public void recalcularSaldos() {
		if (this.participantes == null || this.gastoAsociado == null || this.saldos == null || this.distribuciones == null) {
	        return; 
	    }
		
		for(String p : this.participantes) {
			double nuevoSaldo = saldos.get(p) - this.gastoAsociado.getCantidad() * (this.distribuciones.get(p) / 100.0);
			this.saldos.put(p, nuevoSaldo);
		}
	}
}
