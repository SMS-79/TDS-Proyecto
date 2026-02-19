package umu.tds.gestor.modelo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import umu.tds.gestor.modelo.CuentaGasto;

public class CuentaGastoImpl implements CuentaGasto {

	private double saldo;
	private Map<String, Double> distribuiciones;
	private List<String> participantes;
	
	//Constructor vacio para la persistencia
	public CuentaGastoImpl() {
	}
	
	public CuentaGastoImpl(double saldo) {
		this.saldo = saldo;
		this.distribuiciones = new HashMap<String, Double>();
		this.participantes = new ArrayList<String>();
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
	public Double getSaldo(String nombre) {
		return this.saldo;
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

}
