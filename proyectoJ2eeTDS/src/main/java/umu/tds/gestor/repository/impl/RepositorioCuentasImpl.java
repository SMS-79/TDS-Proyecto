package umu.tds.gestor.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import umu.tds.gestor.modelo.impl.CuentaGastoImpl;
import umu.tds.gestor.repository.RepositorioCuentas;

public class RepositorioCuentasImpl implements RepositorioCuentas {

	private static RepositorioCuentasImpl instancia = null;
	
	private BaseDeDatosImpl BD = BaseDeDatosImpl.getBD();
	
	public static RepositorioCuentasImpl getInstancia() {
		if(instancia == null) {
			instancia = new RepositorioCuentasImpl();
		}
		return instancia;
	}
	
	private List<CuentaGastoImpl> cuentas = new ArrayList<>();
	
	public RepositorioCuentasImpl() {
		
		if(BaseDeDatosImpl.getBD() != null) {
			this.BD = BaseDeDatosImpl.getBD();
		}
		
		if(BD.getCuentas() != null) {
			this.cuentas = (List<CuentaGastoImpl>) BD.getCuentas();
		}
		else {
			this.cuentas = new ArrayList<CuentaGastoImpl>();
		}
		
	}
	
	@Override
	public List<? extends CuentaGastoImpl> getCuentas() {
		return Collections.unmodifiableList(cuentas);
	}

	@Override
	public void añadirCuenta(CuentaGastoImpl cuenta) {
		if(!cuentas.contains(cuenta)) {
			cuentas.add(cuenta);	
			BD.guardarFichero();
		}
	}

	@Override
	public CuentaGastoImpl getCuenta(UUID id) {
		for(CuentaGastoImpl c : cuentas) {
			if(c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}
	
	//Se le pasa una lista de nombres y solo devuelve las cuentas que contienen TODOS los nombre pasados
	public List<CuentaGastoImpl> filtrarCuentas(String... nombres) {
	    return cuentas.stream()
	            .filter(c -> c.getParticipantes().containsAll(List.of(nombres)))
	            .toList();
	}
}
