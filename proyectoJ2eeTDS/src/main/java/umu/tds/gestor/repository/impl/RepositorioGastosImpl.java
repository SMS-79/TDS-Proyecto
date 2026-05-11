package umu.tds.gestor.repository.impl;


import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.modelo.impl.Categoria;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.repository.RepositorioGastos;

public class RepositorioGastosImpl implements RepositorioGastos{
	
	private static RepositorioGastosImpl instancia = null;
	
	private BaseDeDatosImpl BD = BaseDeDatosImpl.getBD();
	
	public static RepositorioGastosImpl getInstancia() {
		if (instancia == null) {
			instancia = new RepositorioGastosImpl();
		}
		
		return instancia; 
	}
	
	
	private List<Gasto> gastos;
	
	private RepositorioGastosImpl() {
		
		if(BaseDeDatosImpl.getBD() != null) {
			this.BD = BaseDeDatosImpl.getBD();
		}
		
		if(BD.getGastos() != null) {
			this.gastos = (List<Gasto>) BD.getGastos();
		}
		else {
			this.gastos = new ArrayList<Gasto>();
		}
	}
	
	
	
	@Override
	public List<? extends Gasto> filtrarGasto(List<Month> meses, LocalDate fechaInicio, LocalDate fechaFin, List<? extends Categoria> categorias) {
		return gastos.stream()
				.filter(f -> meses == null || meses.isEmpty() || meses.contains(f.getFecha().getMonth()))
				.filter(f -> fechaInicio == null || !f.getFecha().isBefore(fechaInicio))
				.filter(f -> fechaFin == null || !f.getFecha().isAfter(fechaFin))
				.filter(f -> categorias == null || categorias.contains(f.getCategoria()))
				.toList();
	}
	
	@Override
	public void borrarGasto(Gasto gasto) {
		gastos.remove(gasto);
		BD.guardarFichero();
	}
	
	@Override
	public List<? extends Gasto> getGastos(){
		return Collections.unmodifiableList(gastos); 
	}
	
	@Override
	public void anadirGasto(Gasto gasto) {
		gastos.add((GastoImpl) gasto); 
		BD.guardarFichero();
	}
	
	@Override
	public void cambiarCantidadGasto(Gasto gasto, double precio) {
		gasto.setCantidad(precio);
		BD.guardarFichero();
	}
	
	@Override
	public void cambiarFechaGasto(Gasto gasto, LocalDate fecha) {
		gasto.setFecha(fecha);
		BD.guardarFichero();
	}
	
	@Override
	public void cambiarCategoriaGasto(Gasto gasto, Categoria categoria) {
		gasto.setCategoria(categoria);
		BD.guardarFichero();
	}
	
	@Override
	public Gasto getGastoPorID(UUID id) {
		return gastos.stream()
				.filter(g -> g.getIdGasto().equals(id))
				.findFirst()	//Uso findFirst porque filter devuelve otro stream y orElse no acepta stream
				.orElse(null);
	}
	
	

}
