package umu.tds.gestor.controladores;


import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.AlerNotifGestorImpl;
import umu.tds.gestor.modelo.impl.Categoria;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.modelo.impl.Intervalo;
import umu.tds.gestor.repository.impl.RepositorioAlertasImpl;
import umu.tds.gestor.repository.impl.RepositorioCategoriasImpl;
import umu.tds.gestor.repository.impl.RepositorioGastosImpl;

public class ControladorGestion {

	private RepositorioGastosImpl repGastos = RepositorioGastosImpl.getInstancia();
	private RepositorioCategoriasImpl repCategorias = RepositorioCategoriasImpl.getInstancia();
	private RepositorioAlertasImpl repAlertas = RepositorioAlertasImpl.getInstancia();
	private AlerNotifGestorImpl gestorAlertas = AlerNotifGestorImpl.getInstancia();
	
	public ControladorGestion() {
		crearCategoria("Alimentación");
		crearCategoria("Transporte");
		crearCategoria("Entretenimiento");
	}
	
	public List<? extends GastoImpl> filtrarGastos(List<Month> meses, LocalDate fechaInicio, LocalDate fechaFin, List<? extends Categoria> categorias) {
	    return repGastos.filtrarGasto(meses, fechaInicio, fechaFin, categorias);
	}
	
	public List<? extends Categoria> filtrarCategoria(String categoriaId) {
	    return repCategorias.filtrarCategorias(categoriaId);
	}
	
	public void borrarGasto(GastoImpl gasto) {
		repGastos.borrarGasto(gasto); 
	}
	
	public List<? extends GastoImpl> getGastos(){
		return repGastos.getGastos(); 
	}
	
	public List<? extends Categoria> getCategorias(){
		return repCategorias.getCategorias();
	}

	public List<? extends Alerta> getAlertas(){
		return repAlertas.getAlertas(); 
	}
	
	
	
	public void crearGasto(Categoria categoria, LocalDate fecha, double precio ) {
		GastoImpl newGasto = new GastoImpl(categoria, fecha, precio); 
		
		// String idGasto = UUID.randomUUID().toString(); 
		
		// newGasto.setIdGasto(idGasto); 
		
		repGastos.añadirGasto(newGasto);
		
	
		/*
		for(Alerta a : repAlertas.getAlertas()) {
			if(a.getCategoria() == null || a.getCategoria() == categoria) {
				try{
					gestorAlertas.añadirGastoAlerta(a, newGasto);
				}catch(LimiteAlertaException e) {
					crearNotificacion(e.getMessage());
				}
			}
			
		}
		*/
		
	}
	
	public Categoria crearCategoria(String cat) {
		Categoria nuevaCategoria = new Categoria(cat);
		repCategorias.aniadirCategoria(nuevaCategoria);
		return nuevaCategoria;
	}
	
	
	public void crearAlerta(Categoria categoria, double limite, Intervalo intervalo) {
		
		repAlertas.añadirAlerta(gestorAlertas.crearAlerta(limite, intervalo));
		
	}
	
	public void crearAlerta(double limite, Intervalo intervalo) {
		crearAlerta(null, limite, intervalo);
	}
	
	public void crearNotificacion(String mensaje){
		gestorAlertas.crearNotificacion(mensaje);
	}
	
	public void cambiarCantidadGasto(GastoImpl gasto, double precio) {

		for(Alerta a : repAlertas.getAlertas()) {
			gestorAlertas.quitarGastoAlerta(a, gasto);
		}
		
		repGastos.cambiarCantidadGasto(gasto, precio); 
		
		for(Alerta a : repAlertas.getAlertas()) {
			try{
				gestorAlertas.añadirGastoAlerta(a, gasto);
			}catch(LimiteAlertaException e) {
				crearNotificacion(e.getMessage());
			}
		}
	}
	
	public void cambiarFechaGasto(GastoImpl gasto, LocalDate fecha) {
		repGastos.cambiarFechaGasto(gasto, fecha); 
	}
}

