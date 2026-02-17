package umu.tds.gestor.controladores;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.repository.impl.RepositorioGastosImpl;

public class ControladorGestion {

	private RepositorioGastosImpl repGastos = RepositorioGastosImpl.getInstacia();
	
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
}
