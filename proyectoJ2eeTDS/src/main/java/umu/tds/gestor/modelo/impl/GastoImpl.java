package umu.tds.gestor.modelo.impl;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import umu.tds.gestor.modelo.Gasto;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id_gastos"
		)


public class GastoImpl implements Gasto{
	@JsonProperty("id_gastos")
	private UUID idGasto; 
	
	private Categoria categoria; 
	private LocalDate fecha; 
	private double cantidad; 
	
	
	
	// constructor por defecto para mantener la persistencia en JSON
	public GastoImpl() {
		
	}
	
	public GastoImpl(Categoria categoria, LocalDate fecha, double cantidad) {
		idGasto = UUID.randomUUID(); 
		if (cantidad < 0) {
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
	
	public UUID getIdGasto() {
		return this.idGasto;
	}
	
	public Categoria getCategoria() {
		return categoria; 
	}
	
	public LocalDate getFecha() {
		return fecha; 
	}
	
	public double getCantidad() {
		return cantidad; 
	}
	
	public void setFecha(LocalDate newFecha) {
		this.fecha = newFecha; 
	}
	
	public void setCantidad(double newCantidad) {
		this.cantidad = newCantidad; 
	}
	

	public void setCategoria(Categoria newCategoria) {
		this.categoria = newCategoria; 
	}
	
	
	
}
