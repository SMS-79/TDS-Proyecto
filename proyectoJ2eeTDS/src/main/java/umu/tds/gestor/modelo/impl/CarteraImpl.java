package umu.tds.gestor.modelo.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	private File fichero;
	
	
	private CarteraImpl() {
		this(new ArrayList<GastoImpl>(), new ArrayList<CategoriaImpl>(), new ArrayList<Alerta>(), new ArrayList<CuentaGastoImpl>());
	}
	
	private CarteraImpl(List<GastoImpl> gastos, 
						List<CategoriaImpl> categorias,
						List<Alerta> alertas,
						List<CuentaGastoImpl> cuentas) {
		this.gastos = gastos; 
		this.categorias = categorias;
		this.alertas = alertas;
		this.cuentas = cuentas; 
		
		
	}
	
	
	
	public void cargarFichero(){
		System.out.println("haciendolo");
		try {
			fichero = new File("gastos.json");
	        if (fichero.exists()) {
	            ObjectMapper mapper = new ObjectMapper();
	            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
	            
	            
	            setCartera(mapper.readValue(fichero, CarteraImpl.class));
	        }
	    } catch (Exception e) {
	        System.err.println("Error al cargar el fichero de gastos: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public void guardarFichero(){
		try {
			fichero = new File("gastos.json");
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
	       
	        
	        mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, this);
	        
	    } catch (Exception e) {
	    	System.err.println("Error al guardar el fichero de gastos: " + e.getMessage()); 
	        e.printStackTrace();
	    }
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
