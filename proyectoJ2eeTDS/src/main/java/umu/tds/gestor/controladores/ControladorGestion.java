package umu.tds.gestor.controladores;


import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import umu.tds.gestor.importador.ImportadorGastos;
import umu.tds.gestor.importador.impl.ImportadorGastosCSVImpl;
import umu.tds.gestor.importador.impl.ImportadorGastosTXTImpl;
import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.CuentaGasto;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.AlerNotifGestorImpl;
import umu.tds.gestor.modelo.impl.Categoria;
import umu.tds.gestor.modelo.impl.CuentaGastoEquitativa;
import umu.tds.gestor.modelo.impl.CuentaGastoPorcentual;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.modelo.impl.Intervalo;
import umu.tds.gestor.modelo.impl.Notificacion;
import umu.tds.gestor.repository.impl.RepositorioAlertasImpl;
import umu.tds.gestor.repository.impl.RepositorioCategoriasImpl;
import umu.tds.gestor.repository.impl.RepositorioCuentasImpl;
import umu.tds.gestor.repository.impl.RepositorioGastosImpl;
import umu.tds.gestor.repository.impl.RepositorioNotificacionesImpl;

public class ControladorGestion {

	private RepositorioGastosImpl repGastos = RepositorioGastosImpl.getInstancia();
	private RepositorioCategoriasImpl repCategorias = RepositorioCategoriasImpl.getInstancia();
	private RepositorioAlertasImpl repAlertas = RepositorioAlertasImpl.getInstancia();
	private RepositorioNotificacionesImpl repNotif = RepositorioNotificacionesImpl.getInstancia();
	private AlerNotifGestorImpl gestorAlertas = AlerNotifGestorImpl.getInstancia();
	private RepositorioCuentasImpl repCuentas = RepositorioCuentasImpl.getInstancia();
	
	private CuentaGasto cuentaSeleccionada;
	
	
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
	
	public List<? extends CuentaGasto> getCuentas(){
		return repCuentas.getCuentas();
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
	
	public void editarGasto(GastoImpl gasto, double precio, Categoria categoria, LocalDate fecha ) throws LimiteAlertaException {

		for(Alerta a : repAlertas.getAlertas()) {
			gestorAlertas.quitarGastoAlerta(a, gasto);
		}
		
		if(gasto.getCantidad() != precio) {
			repGastos.cambiarCantidadGasto(gasto, precio); 
		}
		if(!gasto.getCategoria().equals(categoria)) {
			repGastos.cambiarCategoriaGasto(gasto, categoria); 
		}
		if(!gasto.getFecha().equals(fecha)) {
			repGastos.cambiarFechaGasto(gasto, fecha);
		}
		
		
		
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
	
	public void crearCuenta(Double gasto, Map<String, Double> distribuciones) {
		// Ponemos String[]::new para que decirle a .roArray que queremos que el array sea de strings
		String[] nombres = distribuciones.keySet().toArray(String[]::new);
		CuentaGasto cuenta;
		
		// Si todos los porcentajes son nulos, creamos una cuenta equitativa
		if (distribuciones.values().stream().allMatch(Objects::isNull)) {
			cuenta = new CuentaGastoEquitativa(gasto, nombres);
		} else {
			cuenta = new CuentaGastoPorcentual(gasto, distribuciones);
		}

		
		repCuentas.añadirCuenta(cuenta);
	}
	
	public void borrarCuenta(CuentaGasto cuenta) {
		repCuentas.borrarCuenta(cuenta);
	}
	
	public List<CuentaGasto> filtrarCuenta(String... nombres) {
		return repCuentas.filtrarCuentas(nombres);
	}

	public CuentaGasto getCuentaSeleccionada() {
		return cuentaSeleccionada;
	}

	public void setCuentaSeleccionada(CuentaGasto cuenta) {
		this.cuentaSeleccionada = cuenta;
	}
	
	public boolean realizarPagoCuenta(CuentaGasto cuenta, String miembro, Double pago) {
		return repCuentas.realizarPago(cuenta, miembro, pago);
	}
	
	public GastoImpl getGastoPorID(UUID id) {
		return repGastos.getGastoPorID(id);
	}

	
}


