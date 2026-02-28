package umu.tds.gestor.controladores;


import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.AlerNotifGestorImpl;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.modelo.impl.Intervalo;
import umu.tds.gestor.repository.impl.RepositorioAlertasImpl;
import umu.tds.gestor.repository.impl.RepositorioGastosImpl;

public class ControladorGestion {

	private RepositorioGastosImpl repGastos = RepositorioGastosImpl.getInstancia();
	private RepositorioAlertasImpl repAlertas = RepositorioAlertasImpl.getInstancia();
	private AlerNotifGestorImpl gestorAlertas = AlerNotifGestorImpl.getInstancia();
	
	public List<? extends GastoImpl> filtrarGastos(List<Month> meses, LocalDate fechaInicio, LocalDate fechaFin, List<? extends CategoriaImpl> categorias) {
	    return repGastos.filtrarGasto(meses, fechaInicio, fechaFin, categorias);
	}
	
	public void borrarGasto(GastoImpl gasto) {
		repGastos.borrarGasto(gasto); 
	}
	
	public List<? extends GastoImpl> getGastos(){
		return repGastos.getGastos(); 
	}
	
	public void crearGasto(CategoriaImpl categoria, LocalDate fecha, double precio ) {
		GastoImpl newGasto = new GastoImpl(categoria, fecha, precio); 
		
		// String idGasto = UUID.randomUUID().toString(); 
		
		// newGasto.setIdGasto(idGasto); 
		
		repGastos.añadirGasto(newGasto);
		for(Alerta a : repAlertas.getAlertas()) {
			if(a.getCategoria() == null && a.getCategoria() == categoria) {
				try{
					gestorAlertas.gastoAlerta(a, newGasto);
				}catch(LimiteAlertaException e) {
					crearNotificacion(e.getMessage());
				}
			}
			
		}
		
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
		repGastos.cambiarCantidadGasto(gasto, precio); 
	}
	
	public void cambiarFechaGasto(GastoImpl gasto, LocalDate fecha) {
		repGastos.cambiarFechaGasto(gasto, fecha); 
	}
}

