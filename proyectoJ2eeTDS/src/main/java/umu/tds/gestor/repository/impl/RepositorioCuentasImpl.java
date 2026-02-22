package umu.tds.gestor.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import umu.tds.gestor.modelo.CuentaGasto;
import umu.tds.gestor.modelo.impl.CuentaGastoImpl;
import umu.tds.gestor.repository.RepositorioCuentas;

public class RepositorioCuentasImpl implements RepositorioCuentas {

	private static RepositorioCuentasImpl instancia = null;
	
	public static RepositorioCuentasImpl getInstancia() {
		if(instancia == null) {
			instancia = new RepositorioCuentasImpl();
		}
		return instancia;
	}
	
	private List<CuentaGasto> cuentas = new ArrayList<>();
	
	@Override
	public List<? extends CuentaGasto> getCuentas() {
		return Collections.unmodifiableList(cuentas);
	}

	@Override
	public void crearCuenta(String... nombres) {
		CuentaGasto cuenta = new CuentaGastoImpl(nombres);
		cuentas.add(cuenta);
	}

	@Override
	public CuentaGasto getCuenta(UUID id) {
		for(CuentaGasto c : cuentas) {
			if(c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}
	
	//Se le pasa una lista de nombres y solo devuelve las cuentas que contienen TODOS los nombre pasados
	public List<CuentaGasto> filtrarCuentas(String... nombres) {
	    return cuentas.stream()
	            .filter(c -> c.getParticipantes().containsAll(List.of(nombres)))
	            .toList();
	}
}
