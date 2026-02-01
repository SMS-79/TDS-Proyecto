package umu.tds.gestor.modelo;

public interface Alerta {
	
	Categoria getCategoria();
	
	void setCategoria(Categoria categoria);
	
	Intervalo getIntervalo();
	
	void setIntervalo(Intervalo interv);
	
	double getLimite();
	
	void setLimite(double lim);

}