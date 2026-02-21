package umu.tds.gestor.controladores;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.repository.impl.RepositorioGastosImpl;

public class ControladorGestion {

	private RepositorioGastosImpl repGastos = RepositorioGastosImpl.getInstancia();
	
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
	}
	
	public void cambiarCantidadGasto(GastoImpl gasto, double precio) {
		repGastos.cambiarCantidadGasto(gasto, precio); 
	}
	
	public void cambiarFechaGasto(GastoImpl gasto, LocalDate fecha) {
		repGastos.cambiarFechaGasto(gasto, fecha); 
	}
}
