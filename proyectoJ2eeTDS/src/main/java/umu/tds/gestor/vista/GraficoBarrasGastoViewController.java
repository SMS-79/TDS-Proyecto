package umu.tds.gestor.vista;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.impl.Categoria;

public class GraficoBarrasGastoViewController {
	
	private static final Logger log = LogManager.getLogger(); 
	
	private ControladorGestion controlador; 
	
	@FXML
	private StackedBarChart<String, Number> stackedBarChart;
	
	@FXML
	public void initialize() {
		controlador = Configuracion.getInstancia().getControladorGestion();
		stackedBarChart.setCategoryGap(100);
		
		for(Categoria c : controlador.getCategorias()) {
			XYChart.Series<String, Number> serie = new XYChart.Series<String, Number>();
			serie.setName(c.getNombre());
			
			XYChart.Data<String, Number> datos = new XYChart.Data<String, Number>(c.getNombre(), controlador.filtrarGastos(null, null, null, List.of(c)).size());
			
			serie.getData().add(datos);
			stackedBarChart.getData().add(serie);
		}
	}
	
	

}
