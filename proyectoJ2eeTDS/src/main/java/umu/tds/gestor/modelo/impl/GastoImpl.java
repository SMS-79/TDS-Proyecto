package umu.tds.gestor.modelo.impl;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDate;
import java.util.List;

import umu.tds.gestor.modelo.Gasto;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "lista_gastos"
		)


public class GastoImpl implements Gasto{

	private CategoriaImpl categoria; 
	private LocalDate fecha; 
	private double cantidad; 
	private boolean activo; // para indicar que un gasto está activo
	
	@JsonProperty("lista_gastos")
	private List<GastoImpl> gastos; 
	
	// constructor por defecto para mantener la persistencia en JSON
	public GastoImpl() {
	}
	
	public GastoImpl(CategoriaImpl categoria, LocalDate fecha, double cantidad) {
		if (cantidad <= 0) {
			throw new IllegalArgumentException("La cantidad debe de ser postiva.");
		}
		if(fecha == null) {
			throw new IllegalArgumentException("La fecha no puede ser nula"); 
		}
		if(categoria == null) {
			throw new IllegalArgumentException("La categoría no puede ser nula"); 
		}
		this.cantidad = cantidad; 
		this.categoria = categoria; 
		this.fecha = fecha; 
	}
	
	public CategoriaImpl getCategoria() {
		return categoria; 
	}
	
	public LocalDate getFecha() {
		return fecha; 
	}
	
	public double getCantidad() {
		return cantidad; 
	}
	
	public void setFecha(LocalDate newFecha) {
		if(fecha == newFecha) {
			throw new IllegalArgumentException("La fecha debe ser distinta a la actual."); 
		}
		this.fecha = newFecha; 
	}
	
	public void setCantidad(double newCantidad) {
		this.cantidad = newCantidad; 
	}
	

	public void setCategoria(CategoriaImpl newCategoria) {
		if(this.categoria == newCategoria) {
			throw new IllegalArgumentException("La categoria no puede ser la misma."); 
		}
		this.categoria = newCategoria; 
	}
	
	public void borrar() {
		this.activo = false; 
	}
	
	
}
