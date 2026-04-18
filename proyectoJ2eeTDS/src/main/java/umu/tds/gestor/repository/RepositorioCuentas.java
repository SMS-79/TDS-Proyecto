package umu.tds.gestor.repository;

import java.util.List;
import java.util.UUID;

import umu.tds.gestor.modelo.CuentaGasto;
import umu.tds.gestor.modelo.impl.CuentaGastoImpl;

public interface RepositorioCuentas {
	
	List<? extends CuentaGasto> getCuentas();
	
	CuentaGasto getCuenta(UUID id);

	void añadirCuenta(CuentaGastoImpl cuenta);
	
	List<CuentaGastoImpl> filtrarCuentas(String... nombres);
	
}
