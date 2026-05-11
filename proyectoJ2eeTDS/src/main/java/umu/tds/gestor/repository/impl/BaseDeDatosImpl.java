package umu.tds.gestor.repository.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.impl.Categoria;
import umu.tds.gestor.modelo.CuentaGasto;
import umu.tds.gestor.modelo.impl.Notificacion;
import umu.tds.gestor.repository.BaseDeDatos;

public class BaseDeDatosImpl implements BaseDeDatos {
	
private static BaseDeDatosImpl BD = null; 
	
	public static BaseDeDatosImpl getBD() {
		if (BD == null) {
			BD = new BaseDeDatosImpl(); 
			BD.cargarFichero();
		}
		return BD; 
	}
	
	@JsonProperty("gastos")
	private List<Gasto> gastos;
	@JsonProperty("categorias")
	private List<Categoria> categorias; 
	@JsonProperty("alertas")
	private List<Alerta> alertas;
	@JsonProperty("notificaciones")
	private List<Notificacion> notificaciones;
	@JsonProperty("cuentas")
	private List<CuentaGasto> cuentas;
	
	private File fichero;
	
	
	private BaseDeDatosImpl() {
		this(new ArrayList<Gasto>(), new ArrayList<Categoria>(), new ArrayList<Alerta>(), new ArrayList<Notificacion>(), new ArrayList<CuentaGasto>());
	}
	
	private BaseDeDatosImpl(List<Gasto> gastos, 
						List<Categoria> categorias,
						List<Alerta> alertas, List<Notificacion> notificaciones,
						List<CuentaGasto> cuentas) {
		this.gastos = gastos; 
		this.categorias = categorias;
		this.alertas = alertas;
		this.notificaciones = notificaciones;
		this.cuentas = cuentas; 
	}
	
	
	@Override
	public void cargarFichero(){
	
		try {
			fichero = new File("gastos.json");
	        if (fichero.exists()) {
	            ObjectMapper mapper = new ObjectMapper();
	            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
	            
	            
	            setBD(mapper.readValue(fichero, BaseDeDatosImpl.class));
	        }
	    } catch (Exception e) {
	        System.err.println("Error al cargar el fichero de gastos: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	@Override
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

	public List<? extends Gasto> getGastos() {
		return this.gastos;
	}

	public void setGastos(List<Gasto> gastos) {
		this.gastos = gastos;
	}

	public List<Categoria> getCategorias() {
		return this.categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Alerta> getAlertas() {
		return this.alertas;
	}

	public void setAlertas(List<Alerta> alertas) {
		this.alertas = alertas;
	}

	public List<Notificacion> getNotificaciones() {
		return this.notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}
	
	public List<CuentaGasto> getCuentas() {
		return this.cuentas;
	}

	public void setCuentas(List<CuentaGasto> cuentas) {
		this.cuentas = cuentas;
	}
	
	public void setBD(BaseDeDatosImpl bd) {
		BaseDeDatosImpl.BD = bd;
	}
	

	
	

}
