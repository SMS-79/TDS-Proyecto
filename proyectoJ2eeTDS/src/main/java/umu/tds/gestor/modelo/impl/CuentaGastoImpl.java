package umu.tds.gestor.modelo.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javafx.util.Pair;
import umu.tds.gestor.modelo.CuentaGasto;
import umu.tds.gestor.modelo.Gasto;

public class CuentaGastoImpl implements CuentaGasto {

	//Tupla que asocia un nombre a un gasto. La persona que mete el gasto principal
	private Pair<String, Gasto> gasto;
	//Distribuiciones es para porcentajes, saldos es para valores enteros como tal
	private Map<String, Double> distribuiciones;
	private Map<String, Double> saldos;
	private List<String> participantes;
	//Identificador único de la cuenta
	private UUID ID;
	//Nombre a quien se le asocia el gasto original
	
	//Constructor vacio para la persistencia
	public CuentaGastoImpl() {
	}
	
	//TODO: Comprobacion de errores y persistencia
	public CuentaGastoImpl(String... participantes) {
		this.gasto = new Pair<String, Gasto>(null, null);
		this.distribuiciones = new HashMap<String, Double>();
		this.saldos = new HashMap<String, Double>();
		this.participantes = Arrays.asList(participantes);
		//Esto podria cambiarse para que haya repeticiones pero por el momento lo dejo así.
		this.ID = UUID.randomUUID();
		
		for (String p : this.participantes) {
			//100.0 entre la longitud de la lista para sacar porcentajes iguales
			this.distribuiciones.put(p, 100.0/this.participantes.size());
			//El saldo incial de cada persona será 0
			this.saldos.put(p, null); //TODO: POner aqui gasto o algo, se verá mejor con gasto implementado
		}
	}
	
	@Override
	public List<String> getParticipantes() {
		return participantes;
	}

	@Override
	public Map<String, Double> getDistribuciones() {
		return distribuiciones;
	}

	@Override
	public Gasto getGasto(String nombre) {
		return this.gasto.getValue();
	}
	
	@Override
	public void setGasto(String pagador, Gasto gasto) {
		if(gasto == null || gasto.getCantidad() < 0) {
			throw new IllegalArgumentException("El gasto debe existir o ser mayor que 0");
		}
		if(pagador == null || pagador.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del pagador no puede ser vacío");
		}
		this.gasto = new Pair<String, Gasto>(pagador, gasto);
		recalcularSaldos();
	}

	@Override
	public void setDistribucion(String nombre, double value) {
		//trim quita los espacios
		if(nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la persona no puede estar vacío.");
		} else if (value < 0 || value > 0) {
			throw new IllegalArgumentException("El valor debe estar entre 0 y 100, ambos inclusive.");
		}
		//Reescribe la asociacion si nombre ya estaba añadido
		distribuiciones.put(nombre, value);
		recalcularSaldos();
	}

	@Override
	public void setDistribuciones(Map<String, Double> distribuciones) {
		if(distribuciones == null || distribuciones.isEmpty()) {
			throw new IllegalArgumentException("Debe haber al menos una distribuición.");
		}
		this.distribuiciones = distribuciones;
		recalcularSaldos();
	}

	@Override
	public void introducirGasto(String nombre, GastoImpl gasto) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void recalcularSaldos() {
		for(String p : this.participantes) {
			//Multiplica el gasto total por el porcentaje que le corresponde a la persona
			
			//Los saldos se asocian a un double no a un gasto porque creo que no es necesario. Se podria cambiar si da problemas.
			double gasto = this.gasto.getValue().getCantidad()*(this.distribuiciones.get(p)/100.0);
			this.saldos.put(p, gasto);
			
			//TODO: HACER QUE SE CALCULEN EN NEGATIVO. ES DECIR, SI METO 30 EUROS QUE MI SALDO SEA +20 Y EL RESTO -10 O EQUIVALENTE.
		}
	}
	
	public UUID getId() {
		return this.ID;
	}

}
