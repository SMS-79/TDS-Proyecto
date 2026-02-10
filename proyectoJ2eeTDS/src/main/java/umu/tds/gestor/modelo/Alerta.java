package umu.tds.gestor.modelo;

public interface Alerta {
	
	Categoria getCategoria();
	
	void setCategoria(Categoria categoria);
	
	double getLimite();
	
	void setLimite(double lim);
	
	void reiniciar();
	
	void añadirGastoAlerta(double g);
	
	void generarNotificacion();

}