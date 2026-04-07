package umu.tds.gestor.modelo;

import java.util.List;

import umu.tds.gestor.modelo.impl.CarteraImpl;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.CuentaGastoImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;

public interface Cartera {
	
	public List<GastoImpl> getGastos();
	
	public void setGastos(List<GastoImpl> gastos);
	
	public List<CategoriaImpl> getCategorias();
	
	public void setCategorias(List<CategoriaImpl> categorias);
	
	public List<Alerta> getAlertas();
	
	public void setAlertas(List<Alerta> alertas);
	
	public List<CuentaGastoImpl> getCuentas();
	
	public void setCuentas(List<CuentaGastoImpl> cuentas);
	
}