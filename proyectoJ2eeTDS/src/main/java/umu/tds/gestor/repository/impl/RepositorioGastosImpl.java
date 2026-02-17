package umu.tds.gestor.repository.impl;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import umu.tds.gestor.modelo.impl.GastoImpl;

public class RepositorioGastosImpl {
	
	private static RepositorioGastosImpl instancia = null;
	
	public static RepositorioGastosImpl getInstacia() {
		if (instancia == null) {
			instancia = new RepositorioGastosImpl();
		}
		return instancia; 
	}
	
	private List<GastoImpl> gastos; 
	

	public void borrarGasto(GastoImpl gasto) {
		gastos.remove(gasto);
	}
	
	public List<? extends GastoImpl> getGastos(){
		return Collections.unmodifiableList(gastos); 
		
	}
	public void añadirGasto(GastoImpl gasto) {
		gastos.add(gasto); 
	}
}
