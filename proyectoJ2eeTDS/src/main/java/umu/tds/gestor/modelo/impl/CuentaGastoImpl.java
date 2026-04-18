package umu.tds.gestor.modelo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
	
	@JsonProperty
	private String nombrePagador;
	@JsonProperty
	@JsonDeserialize(as = GastoImpl.class) // Importante para que sepa qué clase concreta de Gasto crear
	private Gasto gastoAsociado;
	
	//Distribuiciones es para porcentajes, saldos es para valores enteros como tal
	private Map<String, Double> distribuciones;
	private Map<String, Double> saldos;
	private List<String> participantes;
	
	//Constructor vacio para la persistencia
	public CuentaGastoImpl() {
	}
	
	public CuentaGastoImpl(String... participantes) {
		
		if(Arrays.asList(participantes) == null || Arrays.asList(participantes).isEmpty()) {
			throw new IllegalArgumentException("Se debe añadir al menos una persona a la cuenta");
		}
		
		this.nombrePagador = null;
		this.gastoAsociado = null;
		this.distribuciones = new HashMap<String, Double>();
		//El saldo de cada persona es un double y no un gasto, ya que puede ser -20, +10, etc, no necesita mas informacion
		this.saldos = new HashMap<String, Double>();
		//Se hace asi para que sea modificable
		this.participantes = new ArrayList<>(List.of(participantes));	
		//Esto podria cambiarse para que haya repeticiones pero por el momento lo dejo así.
		this.ID = UUID.randomUUID();
		
		for (String p : this.participantes) {
			//100.0 entre la longitud de la lista para sacar porcentajes iguales
			this.distribuciones.put(p, 100.0/this.participantes.size());
			//El saldo incial de cada persona será 0
			this.saldos.put(p, 0.0);
		}
	}

	public void setSaldos(Map<String, Double> saldos) {
		this.saldos = saldos;
	}

	public Map<String, Double> getSaldos() {
		return saldos;
	}

	public void setParticipantes(List<String> participantes) {
		this.participantes = participantes;
	}

	@Override
	public List<String> getParticipantes() {
		return participantes;
	}

	@Override
	public Map<String, Double> getDistribuciones() {
		return distribuciones;
	}

	@Override
	public Gasto getGasto(String nombre) {
	    return this.gastoAsociado;
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
		this.nombrePagador = pagador;
		this.gastoAsociado = gasto;
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
		distribuciones.put(nombre, value);
		recalcularSaldos();
	}

	@Override
	public void setDistribuciones(Map<String, Double> distribuciones) {
		if(distribuciones == null || distribuciones.isEmpty()) {
			throw new IllegalArgumentException("Debe haber al menos una distribuición.");
		}
		this.distribuciones = distribuciones;
		recalcularSaldos();
	}

	@Override
	public void recalcularSaldos() {
		
		if (this.participantes == null || this.gastoAsociado == null || this.saldos == null || this.distribuciones == null) {
	        return; 
	    }
		
		for(String p : this.participantes) {
			//Al saldo actual de la persona se le resta su porcentaje asignado del gasto
			double nuevoSaldo = saldos.get(p) - this.gastoAsociado.getCantidad() * (this.distribuciones.get(p) / 100.0);
			this.saldos.put(p, nuevoSaldo);
		}
	}
	
	//TODO: Esta puesto como ignore porque no se si será necesario en algun momento, si no lo es, se puede borrar sin ningun problema
	@JsonIgnore
	public UUID getId() {
		return this.ID;
	}

}
