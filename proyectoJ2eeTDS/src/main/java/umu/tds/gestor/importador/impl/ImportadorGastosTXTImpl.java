package umu.tds.gestor.importador.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.importador.ImportadorGastos;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.modelo.Gasto;

public class ImportadorGastosTXTImpl extends ImportadorGastos {

	@Override
	public void leerFichero(String path) {
		
		String linea = "";
		String separadorColumna = " - ";
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		List<Gasto> gastosValidos = new ArrayList<>();
		List<String> categoriasLeidas = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		
			// Saltamos la cabecera y comprobamos si está vacío
			String cabecera = br.readLine();
			if (cabecera == null) {
				throw new IllegalArgumentException("El fichero TXT está vacío");
			}
			
			int numeroLinea = 2; // Empezamos a contar en 2 porque ya quitamos la cabecera
			
			//Leemos la siguiente linea
			while((linea = br.readLine()) != null) {
				if(linea.trim().isEmpty()) {
					numeroLinea++;
					continue;
				}
				
				String[] datos = linea.split(separadorColumna);
				
				if (datos.length < 4) {
					throw new IllegalArgumentException("La línea " + numeroLinea + " no tiene las 4 columnas esperadas del TXT.");
				}
				
				try {
					this.categoria = getValor(datos[0]);
					this.cuenta = getValor(datos[1]);
					
					String strFecha = getValor(datos[2]);
					this.fecha = LocalDate.parse(strFecha, formatter);
					
					String strCantidad = getValor(datos[3]);
					this.cantidad = Double.parseDouble(strCantidad);
					
					// Dejamos los demas campos vacíos
					this.subcategoria = "";
					this.nota = "";
					this.pagador = "";
					this.divisa = "";
					
					//Guardamos las categorias y gastos temporalmente para crearlas al leer todo el fichero
					categoriasLeidas.add(this.categoria);
					CategoriaImpl cat = new CategoriaImpl(this.categoria);
					Gasto gastoTemporal = new GastoImpl(cat, this.fecha, this.cantidad);
					gastosValidos.add(gastoTemporal);
					
				} catch (DateTimeParseException e) {
					throw new IllegalArgumentException("Error al leer la fecha en la línea " + numeroLinea + ". Se esperaba dd/MM/yyyy. Dato: " + linea, e);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Error al leer la cantidad en la línea " + numeroLinea + " Dato: " + linea, e);
				} catch (Exception e) {
					throw new IllegalArgumentException("Error inesperado procesando la línea " + numeroLinea + " - " + e.getMessage(), e);
				}
				
				numeroLinea++;
			}
			
			ControladorGestion controlador = Configuracion.getInstancia().getControladorGestion();
			for(Gasto g : gastosValidos) {
				controlador.crearGasto(g.getCategoria(), g.getFecha(), g.getCantidad());
			}
			//Creamos las categorias que hemos leido
			for(String cat : categoriasLeidas) {
				controlador.crearCategoria(cat);
			}
			
		} catch (IOException e) {
			throw new RuntimeException("No se ha podido leer o encontrar el archivo: " + path, e);
		}
	}
	
	private String getValor(String dato) {
	    String[] partes = dato.split(":", 2);
	    if (partes.length < 2) {
	    	throw new IllegalArgumentException("Formato clave:valor incorrecto en: " + dato);
	    }
	    return partes[1].trim();
	}

}
