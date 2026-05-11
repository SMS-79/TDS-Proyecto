package umu.tds.gestor.repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.modelo.impl.Categoria;

public interface RepositorioGastos {

	List<? extends Gasto> getGastos();
	
	List<? extends Gasto> filtrarGasto(List<Month> meses, LocalDate fechaInicio, LocalDate fechaFin, List<? extends Categoria> categorias);
	
	void anadirGasto(Gasto gasto);
	
	void borrarGasto(Gasto gasto);
	
	void cambiarCantidadGasto(Gasto gasto, double precio); // faltan parametros
	
	void cambiarFechaGasto(Gasto gasto, LocalDate fecha);
	
	void cambiarCategoriaGasto(Gasto gasto, Categoria categoria);
	
	Gasto getGastoPorID(UUID id);

}
