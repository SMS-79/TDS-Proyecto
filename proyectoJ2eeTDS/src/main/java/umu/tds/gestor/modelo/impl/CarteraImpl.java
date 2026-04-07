package umu.tds.gestor.modelo.impl;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.Cartera;

public class CarteraImpl implements Cartera{
	
	private static CarteraImpl cartera = null; 
	
	public static CarteraImpl getCartera() {
		if (cartera == null) cartera = new CarteraImpl(); 
		return cartera; 
	}
	@JsonProperty("gastos")
	private List<GastoImpl> gastos;
	@JsonProperty("categorias")
	private List<CategoriaImpl> categorias; 
	@JsonProperty("alertas")
	private List<Alerta> alertas;
	@JsonProperty("cuentas")
	private List<CuentaGastoImpl> cuentas;
	
	
	private CarteraImpl() {
		this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}
	
	private CarteraImpl(List<GastoImpl> gastos, 
						List<CategoriaImpl> categorias,
						List<Alerta> alertas,
						List<CuentaGastoImpl> cuentas) {
		this.gastos = (List<GastoImpl>) gastos; 
		this.categorias = (List<CategoriaImpl>) categorias;
		this.alertas = (List<Alerta>) alertas;
		this.cuentas = (List<CuentaGastoImpl>) cuentas; 
	}

	public List<GastoImpl> getGastos() {
		return gastos;
	}

	public void setGastos(List<GastoImpl> gastos) {
		this.gastos = gastos;
	}

	public List<CategoriaImpl> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaImpl> categorias) {
		this.categorias = categorias;
	}

	public List<Alerta> getAlertas() {
		return alertas;
	}

	public void setAlertas(List<Alerta> alertas) {
		this.alertas = alertas;
	}

	public List<CuentaGastoImpl> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<CuentaGastoImpl> cuentas) {
		this.cuentas = cuentas;
	}

	public static void setCartera(CarteraImpl cartera) {
		CarteraImpl.cartera = cartera;
	}
	

	
	

}
