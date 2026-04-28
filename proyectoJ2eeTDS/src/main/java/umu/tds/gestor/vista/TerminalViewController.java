package umu.tds.gestor.vista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.modelo.impl.GastoImpl;



public class TerminalViewController {

	private ControladorGestion controlador;
	
	String formatoHelp = "%-45s %-1s %-40s\n";
	
	private final String HELP_TEXT = 
			String.format(formatoHelp, "help", "-", "Muestra esta pagina de ayuda") + 
			String.format(formatoHelp, "lsgastos", "-", "Muestra todos los gastos guardados") +
			String.format(formatoHelp, "addgasto [categoria] [dd/mm/aaaa] [gasto]", "-", "Añadir un nuevo gasto");
			/*"help - Muestra esta pagina de ayuda\n" +
			"lsgastos - Muestra todos los gastos guardados\n" +
			"addgasto [categoria] [dd/mm/aaaa] [gasto] - Añadir un nuevo gasto" + 
			"\n";*/
	
	private final String COMANDO_AYUDA = "help";
	private final String COMANDO_AÑADIR_GASTO = "addgasto";
	private final String COMANDO_MOD_GASTO = "modgasto";
	private final String COMANDO_BORRAR_GASTO = "rmgasto";
	private final String COMANDO_LISTAR_GASTOS = "lsgastos";
	private final String COMANDO_CLEAR = "clear";
	
    @FXML
    private TextField terminalLinea;

    @FXML
    private TextArea terminalPanel;

    @FXML
    public void initialize() {
    	this.controlador = Configuracion.getInstancia().getControladorGestion();
    }
    
    @FXML
    void enterComando(ActionEvent event) {
    	String[] comando = terminalLinea.getText().split(" ");
    	
    	switch(comando[0]) {
    		case COMANDO_AYUDA:
    			terminalPanel.appendText(HELP_TEXT);
    			break;
    		case COMANDO_LISTAR_GASTOS:
    			listarGastos();
    			break;
    		case COMANDO_CLEAR:
    			terminalPanel.clear();
    			break;
    			
    		default:
    			terminalPanel.appendText("Comando o sintaxis no reconocida" + "\n");
    		
    	}
    	terminalLinea.clear();
    }
    
   private void listarGastos() {
	   //Creamos formato de la cadena: "%" indica nueva instsruccion, 
	   //"-" alinea a la izquierda, el numero es el tamaño de la columna y "s" indica un string
       String formato = "%-38s %-20s %-12s %s\n";
       
       terminalPanel.appendText(String.format(formato, "ID", "CATEGORIA", "FECHA", "CANTIDAD"));
       terminalPanel.appendText("----------------------------------------------------------------------------------\n");
       
       for(GastoImpl g : controlador.getGastos()) {
           terminalPanel.appendText(String.format(formato, 
               g.getIdGasto().toString(), 
               g.getCategoria(), 
               g.getFecha().toString(), 
               g.getCantidad()
           ));
       }	
   }
}
