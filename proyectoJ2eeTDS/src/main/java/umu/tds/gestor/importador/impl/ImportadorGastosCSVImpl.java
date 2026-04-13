package umu.tds.gestor.importador.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.importador.ImportadorGastos;


public class ImportadorGastosCSVImpl extends ImportadorGastos {

	@Override
	public void leerFichero(String path) {	
		String linea = "";
		String separador = ","; 
		
		// Esto formatea las fechas (primera columna): 3/2/2022 10:11
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm");
		
		//Lista temporal de gastos mientras leemos el fichero
		List<Gasto> gastosValidos = new ArrayList<>();
		List<String> categoriasLeidas = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			// Saltamos la cabecera
			String cabecera = br.readLine(); 
			if (cabecera == null) {
				throw new IllegalArgumentException("El archivo CSV está vacío");
			}

			int numeroLinea = 2; // Empezamos a contar en 2 porque ya quitamos la cabecera
			
			
			while ((linea = br.readLine()) != null) {
				//Si la linea esta completamente vacia la saltamos pero se cuenta
				if(linea.trim().isEmpty()) {
					numeroLinea++;
					continue;
				}

				// Separamos la linea en los datos
				String[] datos = linea.split(separador);
				
				// Si falla cualquier columna enviamos mensaje informativo y dejamos de leer
				if (datos.length < 8) {
					throw new IllegalArgumentException("La línea " + numeroLinea + " no tiene las 8 columnas esperadas del CSV.");
				}
				
				try {
					
					// Formateamos la fecha con el formato del texto y leemos
					LocalDateTime dateTime = LocalDateTime.parse(datos[0].trim(), formatter);
					this.fecha = dateTime.toLocalDate(); 
					
					this.cuenta = datos[1].trim(); 
					this.categoria = datos[2].trim(); 
					this.subcategoria = datos[3].trim(); 
					this.nota = datos[4].trim();
					this.pagador = datos[5].trim();
					
					// String a double
					this.cantidad = Double.parseDouble(datos[6].trim());
					this.divisa = datos[7].trim();
					
					//TODO: Crear categoria si no existe. Si existe, no crearla
					//Usamos subcategoria porque creo que tiene mas sentido, pero se puede cambiar
					categoriasLeidas.add(this.subcategoria);
					CategoriaImpl cat = new CategoriaImpl(this.subcategoria);
					Gasto gastoTemporal = new GastoImpl(cat, this.fecha, this.cantidad);
					gastosValidos.add(gastoTemporal);
					
				} catch (DateTimeParseException e) {
					throw new IllegalArgumentException("Error al leer la fecha en la línea " + numeroLinea + ". Se esperaba M/d/yyyy H:mm. Dato: " + datos[0], e);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Error al leer la cantidad en la línea " + numeroLinea + " Dato: " + datos[6], e);
				} catch (Exception e) {
					throw new IllegalArgumentException("Error inesperado procesando la línea " + numeroLinea + " - " + e.getMessage(), e);
				}
				
				numeroLinea++;
			}
			
			ControladorGestion controlador = Configuracion.getInstancia().getControladorGestion();
			for(Gasto g : gastosValidos) {
				controlador.crearGasto(g.getCategoria(), g.getFecha(), g.getCantidad());
			}
			for(String cat : categoriasLeidas) {
				controlador.crearCategoria(cat);
			}

		} catch (IOException e) {
			throw new RuntimeException("No se ha podido leer o encontrar el archivo: " + path, e);
		}
	}

}
