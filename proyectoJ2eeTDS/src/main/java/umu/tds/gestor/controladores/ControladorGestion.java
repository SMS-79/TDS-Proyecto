package umu.tds.gestor.controladores;


import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import umu.tds.gestor.importador.ImportadorGastos;
import umu.tds.gestor.importador.impl.ImportadorGastosCSVImpl;
import umu.tds.gestor.importador.impl.ImportadorGastosTXTImpl;
import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.AlerNotifGestorImpl;
import umu.tds.gestor.modelo.impl.Categoria;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.modelo.impl.Intervalo;
import umu.tds.gestor.modelo.impl.Notificacion;
import umu.tds.gestor.repository.impl.RepositorioAlertasImpl;
import umu.tds.gestor.repository.impl.RepositorioCategoriasImpl;
import umu.tds.gestor.repository.impl.RepositorioGastosImpl;
import umu.tds.gestor.repository.impl.RepositorioNotificacionesImpl;

public class ControladorGestion {

	private RepositorioGastosImpl repGastos = RepositorioGastosImpl.getInstancia();
	private RepositorioCategoriasImpl repCategorias = RepositorioCategoriasImpl.getInstancia();
	private RepositorioAlertasImpl repAlertas = RepositorioAlertasImpl.getInstancia();
	private RepositorioNotificacionesImpl repNotif = RepositorioNotificacionesImpl.getInstancia();
	private AlerNotifGestorImpl gestorAlertas = AlerNotifGestorImpl.getInstancia();
	
	
	public ControladorGestion() {
		crearCategoria("Alimentación");
		crearCategoria("Transporte");
		crearCategoria("Entretenimiento");
	}
	
	public List<? extends GastoImpl> filtrarGastos(List<Month> meses, LocalDate fechaInicio, LocalDate fechaFin, List<? extends Categoria> categorias) {
	    return repGastos.filtrarGasto(meses, fechaInicio, fechaFin, categorias);
	}
	
	public List<? extends Categoria> filtrarCategoria(String categoriaId) {
	    return repCategorias.filtrarCategorias(categoriaId);
	}
	
	public void borrarGasto(GastoImpl gasto) {
		repGastos.borrarGasto(gasto); 
	}
	
	public List<? extends GastoImpl> getGastos(){
		return repGastos.getGastos(); 
	}
	
	public List<? extends Categoria> getCategorias(){
		return repCategorias.getCategorias();
	}

	public List<? extends Alerta> getAlertas(){
		return repAlertas.getAlertas(); 
	}
	
	public List<? extends Notificacion> getNotifs(){
		return repNotif.getNotificaciones();
	}
	
	
	
	public void crearGasto(Categoria categoria, LocalDate fecha, double precio ) throws LimiteAlertaException {
		GastoImpl newGasto = new GastoImpl(categoria, fecha, precio); 
		
		
		repGastos.añadirGasto(newGasto);
		
		StringBuilder mensajesAlerta = new StringBuilder(); 
		boolean saltaAlerta = false; 
		
		for(Alerta a : repAlertas.getAlertas()) {
				try {
					gestorAlertas.añadirGastoAlerta(a, newGasto);
				} catch (LimiteAlertaException e) {
					crearNotificacion(e.getMessage());
					mensajesAlerta.append(e.getMessage()).append("\n");
					saltaAlerta = true; 
				}
		}
		if (saltaAlerta) {
			throw new LimiteAlertaException(mensajesAlerta.toString()); 
		}
	}
	
	public Categoria crearCategoria(String cat) {
		Categoria nuevaCategoria = new Categoria(cat);
		repCategorias.aniadirCategoria(nuevaCategoria);
		return nuevaCategoria;
	}
	
	
	public void crearAlerta(Categoria categoria, double limite, Intervalo intervalo) {
		repAlertas.añadirAlerta(gestorAlertas.crearAlerta(categoria ,limite, intervalo));
	}
	
	public void crearAlerta(double limite, Intervalo intervalo) {
		crearAlerta(null, limite, intervalo);
	}
	
	public void borrarAlerta(Alerta alerta) {
		repAlertas.borrarAlerta(alerta);
	}
	
	public void crearNotificacion(String mensaje){
		repNotif.añadirNotificacion(gestorAlertas.crearNotificacion(mensaje));
	}
	
	public void cambiarCantidadGasto(GastoImpl gasto, double precio) throws LimiteAlertaException {

		for(Alerta a : repAlertas.getAlertas()) {
			gestorAlertas.quitarGastoAlerta(a, gasto);
		}
		
		repGastos.cambiarCantidadGasto(gasto, precio); 
		
		StringBuilder mensajesAlerta = new StringBuilder(); 
		boolean saltaAlerta = false; 
		
		for(Alerta a : repAlertas.getAlertas()) {
				try {
					gestorAlertas.añadirGastoAlerta(a, gasto);
				} catch (LimiteAlertaException e) {
					crearNotificacion(e.getMessage());
					mensajesAlerta.append(e.getMessage()).append("\n");
					saltaAlerta = true; 
				}
			}
		if (saltaAlerta) {
	        throw new LimiteAlertaException(mensajesAlerta.toString()); 
	    }
	}
	
	public void cambiarFechaGasto(GastoImpl gasto, LocalDate fecha) {
		repGastos.cambiarFechaGasto(gasto, fecha); 
	}
	
	public void importarGastos(String rutaFichero) throws LimiteAlertaException {
		ImportadorGastos importador = null;
		String rutaMin = rutaFichero.toLowerCase();
		//Aqui podria ser mejor usar una factoria, sacar la logica del if a un fichero a parte para crear el importador.
		if (rutaMin.endsWith(".csv")) {
			importador = new ImportadorGastosCSVImpl();
		} else if (rutaMin.endsWith(".txt")) {
			importador = new ImportadorGastosTXTImpl();
		} else {
			throw new IllegalArgumentException("Formato de fichero no soportado.");
		}
		
		importador.leerFichero(rutaFichero);
	}
}

