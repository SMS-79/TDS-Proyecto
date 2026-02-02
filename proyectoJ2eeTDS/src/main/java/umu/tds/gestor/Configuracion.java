package umu.tds.gestor;

import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.vista.SceneManager;

public abstract class Configuracion {
	
	private static Configuracion instancia; 
	private final SceneManager	sceneManager = new SceneManager(); 
	
	static Configuracion setInstancia(Configuracion impl) {
		return Configuracion.instancia = impl; 
	}
	
	public static Configuracion getInstancia() {
		return Configuracion.instancia; 
	}
	
	public abstract ControladorGestion getControladorGestion();
	
	public abstract String getRutaAlamacen(); 
	
	public SceneManager getSceneManager() {
		return sceneManager; 
	}
}
