package umu.tds.gestor.repository;

import java.util.List;
import java.util.UUID;
import umu.tds.gestor.modelo.CuentaGasto;

public interface RepositorioCuentas {
	
	List<? extends CuentaGasto> getCuentas();
	
	CuentaGasto getCuenta(UUID id);

	void crearCuenta(String... nombres);
	
	List<CuentaGasto> filtrarCuentas(String... nombres);
	
}
