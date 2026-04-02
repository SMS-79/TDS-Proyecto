package umu.tds.gestor;

import umu.tds.gestor.controladores.ControladorGestion;

public class ConfiguracionImpl extends Configuracion{
	
	private ControladorGestion controlador; 
	
	public ConfiguracionImpl() {
		this.controlador = new ControladorGestion();
	}

	@Override
	public ControladorGestion getControladorGestion() {
		return controlador;
	}

	@Override
	public String getRutaAlamacen() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Implementar metodos abstractos
}
