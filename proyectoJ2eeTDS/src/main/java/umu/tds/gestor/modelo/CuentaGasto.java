package umu.tds.gestor.modelo;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import umu.tds.gestor.modelo.impl.CuentaGastoEquitativa;
import umu.tds.gestor.modelo.impl.CuentaGastoPorcentual;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo")
@JsonSubTypes({
    @JsonSubTypes.Type(value = CuentaGastoEquitativa.class, name = "equitativa"),
    @JsonSubTypes.Type(value = CuentaGastoPorcentual.class, name = "porcentual")
})
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id_cuenta"
		)
public abstract class CuentaGasto {

	@JsonProperty("id_cuenta")
	protected UUID ID;
	
	@JsonProperty
	protected String nombrePagador;
	
	//Con deserialize le decimos a Jackson que use una clase en concreto (GastoImpl) en lugar de Gasto, que podria tener varias clases hijas
	@JsonProperty
	protected Double gastoAsociado;
	
	protected Map<String, Double> distribuciones;
	protected Map<String, Double> saldos;
	protected List<String> participantes;

	//Constructor vacio para jackson
	protected CuentaGasto() {
		
	}

	public void setSaldos(Map<String, Double> saldos) {
		this.saldos = saldos;
	}

	public Map<String, Double> getSaldos() {
		return this.saldos;
	}
	
	public Double getSaldoMiembro(String miembro) {
		return this.saldos.get(miembro);
	}

	public void setParticipantes(List<String> participantes) {
		this.participantes = participantes;
	}

	public List<String> getParticipantes() {
		return this.participantes;
	}

	public Map<String, Double> getDistribuciones() {
		return this.distribuciones;
	}

	public Double getGastoAsociado() {
	    return this.gastoAsociado;
	}
	
	public String getNombrePagador() {
	    return this.nombrePagador;
	}
	
	public boolean pagar(String miembro, Double pago) {
		
		//El pagador principal no puede realiar un pago ya que ya ha pagado
		if(!participantes.contains(miembro) || miembro.equals(this.nombrePagador)) { return false; }
		
		Double saldoAux = this.getSaldoMiembro(miembro);
		
		//Solo permitimos pagos si al realizarlo nos quedamos en negativo o a 0
		if((saldoAux + pago) <= 0) {
			//Aumentamos el salddo del miembro
			this.saldos.put(miembro, saldoAux + pago);
			
			//Restamos el pago al saldo del pagador
			Double saldoPagador = this.getSaldoMiembro(this.nombrePagador);
			this.saldos.put(this.nombrePagador, saldoPagador - pago);
			return true;
		}
		return false;
	}
	
	@JsonIgnore
	public UUID getID() {
		return this.ID;
	}

	public abstract void setGasto(String pagador, Double gasto);
	
	public abstract void recalcularSaldos();
}
