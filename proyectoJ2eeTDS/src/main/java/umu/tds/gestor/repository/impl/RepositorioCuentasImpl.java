package umu.tds.gestor.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import umu.tds.gestor.modelo.CuentaGasto;
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
	
	private List<CuentaGasto> cuentas = new ArrayList<>();
	
	public RepositorioCuentasImpl() {
		
		if(BaseDeDatosImpl.getBD() != null) {
			this.BD = BaseDeDatosImpl.getBD();
		}
		
		if(BD.getCuentas() != null) {
			this.cuentas = (List<CuentaGasto>) BD.getCuentas();
		}
		else {
			this.cuentas = new ArrayList<CuentaGasto>();
		}
		
	}
	
	@Override
	public List<? extends CuentaGasto> getCuentas() {
		return Collections.unmodifiableList(cuentas);
	}

	@Override
	public void anadirCuenta(CuentaGasto cuenta) {
		if(!cuentas.contains(cuenta)) {
			cuentas.add(cuenta);	
			BD.guardarFichero();
		}
	}
	
	@Override
	public void borrarCuenta(CuentaGasto cuenta) {
		cuentas.remove(cuenta);
		BD.guardarFichero();
	}

	@Override
	public CuentaGasto getCuenta(UUID id) {
		for(CuentaGasto c : cuentas) {
			if(c.getID().equals(id)) {
				return c;
			}
		}
		return null;
	}
	
	//Se le pasa una lista de nombres y solo devuelve las cuentas que contienen TODOS los nombre pasados
	public List<CuentaGasto> filtrarCuentas(String... nombres) {
		
		//Normaliza los nombres pasados como argumento para evitar que el filtro sea case sensitive
	    List<String> nombresNormalizados = Arrays.stream(nombres)
	            .map(String::toLowerCase)
	            .toList();
		
		//Pasa cada participante a minuscula y devuelve los nombres normalizados
	    return cuentas.stream()
	            .filter(c -> c.getParticipantes().stream()
	            		.map(String::toLowerCase)
	            		.toList()
	            		.containsAll(nombresNormalizados))
	            .toList();
	}
	
	@Override
	public boolean realizarPago(CuentaGasto cuenta, String miembro, Double pago) {
		boolean exito = cuenta.pagar(miembro, pago);
		if (exito) {
			BD.guardarFichero();
		}
		return exito;
	}
}
