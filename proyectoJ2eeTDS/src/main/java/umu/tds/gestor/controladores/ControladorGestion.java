package umu.tds.gestor.controladores;


import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.AlerNotifGestorImpl;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.modelo.impl.Intervalo;
import umu.tds.gestor.repository.RepositorioCategorias;
import umu.tds.gestor.repository.impl.RepositorioAlertasImpl;
import umu.tds.gestor.repository.impl.RepositorioCategoriasImpl;
import umu.tds.gestor.repository.impl.RepositorioGastosImpl;
import umu.tds.gestor.vista.AddGastoViewPopUpController;
import umu.tds.gestor.vista.VentanaPrincipalControlador;

public class ControladorGestion {

	private RepositorioGastosImpl repGastos = RepositorioGastosImpl.getInstancia();
	private RepositorioCategoriasImpl repCategorias = RepositorioCategoriasImpl.getInstancia();
	private RepositorioAlertasImpl repAlertas = RepositorioAlertasImpl.getInstancia();
	private AlerNotifGestorImpl gestorAlertas = AlerNotifGestorImpl.getInstancia();
	
	public List<? extends GastoImpl> filtrarGastos(List<Month> meses, LocalDate fechaInicio, LocalDate fechaFin, List<? extends CategoriaImpl> categorias) {
	    return repGastos.filtrarGasto(meses, fechaInicio, fechaFin, categorias);
	}
	
	public List<? extends CategoriaImpl> filtrarCategoria(String categoriaId) {
	    return repCategorias.filtrarCategorias(categoriaId);
	}
	
	public void borrarGasto(GastoImpl gasto) {
		repGastos.borrarGasto(gasto); 
	}
	
	public List<? extends GastoImpl> getGastos(){
		return repGastos.getGastos(); 
	}
	
	public List<? extends CategoriaImpl> getCategorias(){
		return repCategorias.getCategorias();
	}
	
	
	
	public void crearGasto(CategoriaImpl categoria, LocalDate fecha, double precio ) {
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
	
	public CategoriaImpl crearCategoria(String cat) {
		CategoriaImpl nuevaCategoria = new CategoriaImpl(cat);
		repCategorias.aniadirCategoria(nuevaCategoria);
		return nuevaCategoria;
	}
	
	
	public void crearAlerta(CategoriaImpl categoria, double limite, Intervalo intervalo) {
		
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

