package umu.tds.gestor.vista;


import java.util.ArrayList;
import java.util.List;

import javax.swing.ScrollPaneLayout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.modelo.impl.Categoria;

public class GraficoBarrasGastoViewController {
	
	private static final Logger log = LogManager.getLogger(); 
	
	private ControladorGestion controlador;

	
	@FXML
	private ChoiceBox<String> choiceBox;
	
	@FXML
	private StackedBarChart<String, Number> stackedBarChart;
	
	private List<XYChart.Series<String, Number>> listaNGastos = new ArrayList<XYChart.Series<String,Number>>();
	List<XYChart.Series<String, Number>> listaCantidadGastos= new ArrayList<XYChart.Series<String,Number>>();
	
	@FXML
	public void initialize() {
		controlador = Configuracion.getInstancia().getControladorGestion();
		choiceBox.setItems(FXCollections.observableArrayList(List.of("Nº Gastos", "Cantidad total gastada (€)")));
		stackedBarChart.getXAxis().setLabel("Categorias");
		
		for(Categoria c : controlador.getCategorias()) {
			XYChart.Series<String, Number> serieN = new XYChart.Series<String, Number>();
			XYChart.Series<String, Number> serieC = new XYChart.Series<String, Number>();
			serieN.setName(c.toString());
			serieC.setName(c.toString());
			
			XYChart.Data<String, Number> datos1 = new XYChart.Data<String, Number>(c.toString(), controlador.filtrarGastos(null, null, null, List.of(c)).size());;
			
			
			int sum = 0;
			for(Gasto g : controlador.filtrarGastos(null, null, null, List.of(c))) {
				sum += g.getCantidad();
			}
			XYChart.Data<String, Number> datos2 = new XYChart.Data<String, Number>(c.toString(), sum);

			
			serieN.getData().add(datos1);
			serieC.getData().add(datos2);
			
			listaNGastos.add(serieN);
			listaCantidadGastos.add(serieC);
			
			
		}
		
		
		choiceBox.valueProperty().addListener((obsevable, oldValue, newValue) -> {
			stackedBarChart.getYAxis().setLabel(choiceBox.getValue());
			cargarGrafico();
		});
		
		
			
	}
	
	public void cargarGrafico() {
		
			
		if(choiceBox.getValue().equals("Nº Gastos")) {
			stackedBarChart.setData(FXCollections.observableArrayList(listaNGastos));
		}
		else {
			stackedBarChart.setData(FXCollections.observableArrayList(listaCantidadGastos));
		}
		
		stackedBarChart.setMinWidth(controlador.getCategorias().size() * 20.0 + 10);
		stackedBarChart.setAnimated(false);
	}
	
	

}
