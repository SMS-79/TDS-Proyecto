package umu.tds.gestor.modelo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javafx.util.Pair;
import umu.tds.gestor.modelo.CuentaGasto;
import umu.tds.gestor.modelo.Gasto;


@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id_cuenta"
		)

public class CuentaGastoImpl implements CuentaGasto {

	@JsonProperty("id_cuenta")
	private UUID ID;
	
	//Tupla que asocia un nombre a un gasto. La persona que mete el gasto principal
	private Pair<String, Gasto> gasto;
	//Distribuiciones es para porcentajes, saldos es para valores enteros como tal
	private Map<String, Double> distribuiciones;
	private Map<String, Double> saldos;
	private List<String> participantes;
	
	//Constructor vacio para la persistencia
	public CuentaGastoImpl() {
	}
	
	public CuentaGastoImpl(String... participantes) {
		
		if(Arrays.asList(participantes) == null || Arrays.asList(participantes).isEmpty()) {
			throw new IllegalArgumentException("Se debe añadir al menos una persona a la cuenta");
		}
		
		this.gasto = new Pair<String, Gasto>(null, null);
		this.distribuiciones = new HashMap<String, Double>();
		//El saldo de cada persona es un double y no un gasto, ya que puede ser -20, +10, etc, no necesita mas informacion
		this.saldos = new HashMap<String, Double>();
		//Se hace asi para que sea modificable
		this.participantes = new ArrayList<>(List.of(participantes));	
		//Esto podria cambiarse para que haya repeticiones pero por el momento lo dejo así.
		this.ID = UUID.randomUUID();
		
		for (String p : this.participantes) {
			//100.0 entre la longitud de la lista para sacar porcentajes iguales
			this.distribuiciones.put(p, 100.0/this.participantes.size());
			//El saldo incial de cada persona será 0
			this.saldos.put(p, 0.0);
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
		if(!participantes.contains(pagador)) {
			throw new IllegalArgumentException("El pagador debe ser un miembro de la cuenta");
		}
		this.gasto = new Pair<String, Gasto>(pagador, gasto);
		this.saldos.put(pagador, gasto.getCantidad());
		//Movemos el pagador al incio de la lista de participantes
		this.participantes.remove(pagador);
		this.participantes.add(0, pagador);
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
	public void recalcularSaldos() {
		
		for(String p : this.participantes) {
			//Al saldo actual de la persona se le resta su porcentaje asignado del gasto
			double nuevoSaldo = saldos.get(p) - this.gasto.getValue().getCantidad()*(this.distribuiciones.get(p)/100.0);
			this.saldos.put(p, nuevoSaldo);
		}
	}
	
	public UUID getId() {
		return this.ID;
	}

}
