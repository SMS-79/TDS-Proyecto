package umu.tds.gestor.modelo;

import java.time.LocalDate;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.AlertaMensual;
import umu.tds.gestor.modelo.impl.AlertaSemanal;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.modelo.impl.Intervalo;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AlertaMensual.class, name = "mensual"),
    @JsonSubTypes.Type(value = AlertaSemanal.class, name = "semanal")
})

public abstract class Alerta {
	
	@JsonProperty("id_alert")
	protected String id;
	
	protected Optional<CategoriaImpl> categoria;
	protected double limite;
	protected double gastoRealizado;
	protected LocalDate activacion;
	
	// Constructor vacío para la persistencia
	protected Alerta() {}
	
	// Constructor sin categoria
	protected Alerta(double lim) {
		this(null, lim);
	}
	
	// Constructor con categoría
	protected Alerta(CategoriaImpl c, double lim) {
		this.categoria = Optional.ofNullable(c);
		this.limite = lim;
		this.activacion = LocalDate.now();
	}
	

	
	//Getters y setters
	
	public String getId() { return id;	}
	
	public CategoriaImpl getCategoria() { return this.categoria.orElse(null);	}
	
	public void setCategoria(CategoriaImpl categ) {	this.categoria = Optional.of(categ);	}
	
	public double getLimite() { return this.limite;	}

	public void setLimite(double lim) {	this.limite = lim;	}

	public double getGastoRealizado() { return gastoRealizado;	}

	public LocalDate getActivacion() { return activacion;	}
	
	public abstract Intervalo getIntervalo();
	
	
	
	
	// Reinicia el temporizador de la alerta
	public void reiniciar() {
		this.activacion = LocalDate.now();
		this.gastoRealizado = 0;
	}
	
	public abstract void añadirGastoAlerta(GastoImpl g) throws LimiteAlertaException;
	
	public abstract void quitarGastoAlerta(GastoImpl g);

}