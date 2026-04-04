package umu.tds.gestor.repository.impl;


import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import umu.tds.gestor.modelo.impl.CarteraImpl;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.repository.RepositorioGastos;

public class RepositorioGastosImpl implements RepositorioGastos{
	
	private static RepositorioGastosImpl instancia = null;
	
	public static RepositorioGastosImpl getInstancia() {
		if (instancia == null) {
			instancia = new RepositorioGastosImpl();
		}
		
		return instancia; 
	}
	
	private void guardarFichero() {
	    try {
	      
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
	       
	        File fichero = new File("gastos.json");
	        
	       
	        CarteraImpl cartera = CarteraImpl.getCartera();  
	        
	        mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, cartera);
	        
	    } catch (Exception e) {
	    	System.err.println("Error al guardar el fichero de gastos: " + e.getMessage()); 
	        e.printStackTrace();
	    }
	}
	
	private void cargarFichero() {
	    try {
	        File fichero = new File("gastos.json");
	        
	        if (fichero.exists()) {
	            ObjectMapper mapper = new ObjectMapper();
	            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
	            
	            CarteraImpl carteraCargada = mapper.readValue(fichero, CarteraImpl.class);
	            
	            CarteraImpl.setCartera(carteraCargada);
	        }
	    } catch (Exception e) {
	        System.err.println("Error al cargar el fichero de gastos: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	private List<GastoImpl> gastos;
	
	private RepositorioGastosImpl() {
		cargarFichero();
		
		if(CarteraImpl.getCartera() != null && CarteraImpl.getCartera().getGastos() != null) {
			this.gastos = (List<GastoImpl>) CarteraImpl.getCartera().getGastos();
		}
		else {
			this.gastos = new ArrayList<GastoImpl>();
		}
	}
	
	
	
	@Override
	public List<? extends GastoImpl> filtrarGasto(List<Month> meses, LocalDate fechaInicio, LocalDate fechaFin, List<? extends CategoriaImpl> categorias) {
		return gastos.stream()
				.filter(f -> meses == null || meses.isEmpty() || meses.contains(f.getFecha().getMonth()))
				.filter(f -> fechaInicio == null || !f.getFecha().isBefore(fechaInicio))
				.filter(f -> fechaFin == null || !f.getFecha().isAfter(fechaFin))
				.filter(f -> categorias == null || categorias.isEmpty() || categorias.contains(f.getCategoria()))
				.toList();
	}
	
	@Override
	public void borrarGasto(GastoImpl gasto) {
		gastos.remove(gasto);
		guardarFichero();
	}
	
	@Override
	public List<? extends GastoImpl> getGastos(){
		return Collections.unmodifiableList(gastos); 
	}
	
	@Override
	public void añadirGasto(GastoImpl gasto) {
		gastos.add(gasto); 
		guardarFichero(); 
	}
	
	@Override
	public void cambiarCantidadGasto(GastoImpl gasto, double precio) {
		gasto.setCantidad(precio);
		guardarFichero();
	}
	
	@Override
	public void cambiarFechaGasto(GastoImpl gasto, LocalDate fecha) {
		gasto.setFecha(fecha);
		guardarFichero();
	}
	
	@Override
	public void cambiarCategoriaGasto(GastoImpl gasto, CategoriaImpl categoriaImpl) {
		gasto.setCategoria(categoriaImpl);
		guardarFichero();
	}
	

}
