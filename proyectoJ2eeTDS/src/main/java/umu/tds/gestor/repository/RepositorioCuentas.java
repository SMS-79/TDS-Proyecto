package umu.tds.gestor.repository;

import java.util.List;

import umu.tds.gestor.modelo.impl.CuentaGastoImpl;

public interface RepositorioCuentas {
	
	List<? extends CuentaGastoImpl> getCuentas();
	
	CuentaGastoImpl filtrarCuenta(String id);

	void crearCuenta(String... nombres);
	
	
	
}
