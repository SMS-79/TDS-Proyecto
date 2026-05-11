package umu.tds.gestor.vista;

import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.page.MonthPage;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.modelo.impl.Categoria;

public class CalendarioViewController {

    @FXML
    private BorderPane panelPrincipal;
    
    @FXML
    private ComboBox<Integer> comboAnos; 
    
    @FXML
    private DatePicker datePicker; 
    
    @FXML
    private ComboBox<String> comboCategoria; 
    

    private ControladorGestion controlador;
    
    private CalendarSource miFuente; 
    private MonthPage monthPage; 
   

    @FXML
    public void initialize() {
        controlador = Configuracion.getInstancia().getControladorGestion();
        // Inicialización del CalendarFX
        monthPage = new MonthPage();
        monthPage.setDate(LocalDate.now()); 
        miFuente = new CalendarSource("Fuente de Gastos");
        monthPage.getCalendarSources().add(miFuente);
        panelPrincipal.setCenter(monthPage);
        
        // Bucle para que el desplegable contenga los anos del 2010 al 2035
        for (int i = 2010; i <= 2035; i++) {
        	comboAnos.getItems().add(i);
        }
        // Bucle para que desplegable muestre todas las categorías existentes
        comboCategoria.getItems().add("Todas");
        for (Categoria cat : controlador.getCategorias()) {
        	comboCategoria.getItems().add(cat.getNombre()); 
        }
        comboCategoria.setValue("Todas");
        
        // Respuesta al modifcar los filtros
        comboAnos.setOnAction(e ->{
        	if(comboAnos.getValue() != null) {
        		monthPage.setDate(monthPage.getDate().withYear(comboAnos.getValue())); 
        	}
        });
        
        datePicker.valueProperty().addListener((obs, oldV, newV) -> {
        	if (newV != null) {
        		monthPage.setDate(newV);
        		comboAnos.setValue(newV.getYear()); 
        	}
        });
        
        comboCategoria.setOnAction(e -> cargarGastosEnCalendario());
        
        cargarGastosEnCalendario(); 
    }
    
    @FXML
    private void volverAHoy(ActionEvent e) {
    	LocalDate hoy = LocalDate.now();
    	monthPage.setDate(hoy);
    	datePicker.setValue(hoy);
    	comboAnos.setValue(hoy.getYear());
    }
    private void cargarGastosEnCalendario() {
    	miFuente.getCalendars().clear();
        
		Calendar calendarioGastos = new Calendar("Mis Gastos");
        calendarioGastos.setStyle(Style.STYLE1); 
        List<? extends Gasto> misGastos = controlador.getGastos();
        String filtroCategoria = comboCategoria.getValue();
        for (Gasto gasto : misGastos) {
        	if("Todas".equals(filtroCategoria) ||
        	gasto.getCategoria().getNombre().equals(filtroCategoria)) {
        
	            Entry<String> entradaGasto = new Entry<>(gasto.getCategoria().getNombre() + ": " + gasto.getCantidad() + "€");
	            
	            
	            entradaGasto.changeStartDate(gasto.getFecha());
	            entradaGasto.changeEndDate(gasto.getFecha());     
	            entradaGasto.setFullDay(true);
	            calendarioGastos.addEntry(entradaGasto);
        }
    }

    miFuente.getCalendars().add(calendarioGastos); 
    }
}
