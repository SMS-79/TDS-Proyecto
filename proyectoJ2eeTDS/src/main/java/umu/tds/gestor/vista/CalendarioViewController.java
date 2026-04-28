package umu.tds.gestor.vista;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.page.DayPage;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.impl.GastoImpl;

public class CalendarioViewController {

    @FXML
    private BorderPane panelPrincipal;

    private ControladorGestion controlador;

    @FXML
    public void initialize() {
        controlador = Configuracion.getInstancia().getControladorGestion();
        
        DayPage dayPage = new DayPage();
        
        Calendar calendarioGastos = new Calendar("Mis Gastos");
        calendarioGastos.setStyle(Style.STYLE1); 

        
        List<? extends GastoImpl> misGastos = controlador.getGastos();
        
        for (GastoImpl gasto : misGastos) {
           
            Entry<String> entradaGasto = new Entry<>(gasto.getCategoria().getNombre() + ": " + gasto.getCantidad() + "€");
            
        
            entradaGasto.changeStartDate(gasto.getFecha());
            entradaGasto.changeEndDate(gasto.getFecha());
            
         
            entradaGasto.setFullDay(true);
            
         
            calendarioGastos.addEntry(entradaGasto);
        }

        CalendarSource miFuente = new CalendarSource("Fuente de Gastos");
        miFuente.getCalendars().add(calendarioGastos);
        
        dayPage.getCalendarSources().clear(); 
        dayPage.getCalendarSources().add(miFuente);

        panelPrincipal.setCenter(dayPage);
    }
}
