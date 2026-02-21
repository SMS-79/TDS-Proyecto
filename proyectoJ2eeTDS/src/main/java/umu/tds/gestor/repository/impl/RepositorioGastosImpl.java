package umu.tds.gestor.repository.impl;


import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

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
	
	private List<GastoImpl> gastos; 
	
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
	}
	
	@Override
	public List<? extends GastoImpl> getGastos(){
		return Collections.unmodifiableList(gastos); 
	}
	
	@Override
	public void añadirGasto(GastoImpl gasto) {
		gastos.add(gasto); 
	}
	
	@Override
	public void cambiarCantidadGasto(GastoImpl gasto, double precio) {
		gasto.setCantidad(precio);
	}
	
	@Override
	public void cambiarFechaGasto(GastoImpl gasto, LocalDate fecha) {
		gasto.setFecha(fecha);
	}
	
	@Override
	public void cambiarCategoriaGasto(GastoImpl gasto, CategoriaImpl categoriaImpl) {
		gasto.setCategoria(categoriaImpl);
	}
}
