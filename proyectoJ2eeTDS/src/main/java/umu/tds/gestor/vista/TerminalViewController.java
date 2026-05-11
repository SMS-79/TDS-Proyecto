package umu.tds.gestor.vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.Categoria;

public class TerminalViewController {

	private ControladorGestion controlador;
	DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	String formatoHelp = "%-67s %-1s %-40s\n";
	
	private final String HELP_TEXT = 
			String.format(formatoHelp, "help", "-", "Muestra esta pagina de ayuda") +
			String.format(formatoHelp, "clear", "-", "Limpia todo el texto del terminal") + 
			String.format(formatoHelp, "exit", "-", "Salir del terminal y volver a interfaz grafica") +
			String.format(formatoHelp, "lsgastos", "-", "Muestra todos los gastos guardados") +
			String.format(formatoHelp, "addgasto [categoria] [dd/mm/aaaa] [cantidad]", "-", "Añadir un nuevo gasto") +
			String.format(formatoHelp, "modgasto [ID gasto a modificar] [categoria] [dd/mm/aaaa] [cantidad]", "-", "Modifica un gasto existente") +
			String.format(formatoHelp, "delgasto [ID gasto a borrar]", "-", "Borra el gasto seleccionado por la ID");
	
	private final String COMANDO_AYUDA = "help";
	private final String COMANDO_CREAR_GASTO = "addgasto";
	private final String COMANDO_MOD_GASTO = "modgasto";
	private final String COMANDO_BORRAR_GASTO = "delgasto";
	private final String COMANDO_LISTAR_GASTOS = "lsgastos";
	private final String COMANDO_CLEAR = "clear";
	private final String COMANDO_EXIT = "exit";
	
    @FXML
    private TextField terminalLinea;

    @FXML
    private TextArea terminalPanel;

    @FXML
    public void initialize() {
    	this.controlador = Configuracion.getInstancia().getControladorGestion();
    }
    
    @FXML
    void enterComando(ActionEvent event) throws LimiteAlertaException {
    	String[] comando = terminalLinea.getText().split(" ");
    	
    	terminalPanel.appendText("AppGestion>" + terminalLinea.getText() + "\n");
    	
    	switch(comando[0]) {
    		case COMANDO_AYUDA:
    			terminalPanel.appendText(HELP_TEXT);
    			break;
    			
    		case COMANDO_LISTAR_GASTOS:
    			listarGastos();
    			break;
    		
// ###########################################################################################################################
    			
    		case COMANDO_BORRAR_GASTO:
    		    if (comando.length != 2) {
    		        terminalPanel.appendText("Error: Debes indicar el ID del gasto.\n");
    		        break;
    		    }
    		    
    		    try {
    		        UUID id = UUID.fromString(comando[1]);
    		        Gasto gasto = getGastoPorID(id);
    		        
    		        if (gasto != null) {
    		            controlador.borrarGasto(gasto);
    		            terminalPanel.appendText("Gasto borrado exitosamente.\n");
    		        } else {
    		            terminalPanel.appendText("No existe un gasto con ese ID.\n");
    		        }
    		    } catch (IllegalArgumentException e) {
    		        terminalPanel.appendText("Formato de ID incorrecto.\n");
    		    }
    		    break;
    		    
// ###########################################################################################################################
    		    
    		case COMANDO_CREAR_GASTO:
    			
    		    if (comando.length != 4) {
    		        terminalPanel.appendText("Número incorrecto de argumentos\n");
    		        break;
    		    }
    		    
    		    try {
					Categoria cat = new Categoria(comando[1]);
					LocalDate fecha = LocalDate.parse(comando[2], formateador);
					Double cantidad = Double.parseDouble(comando[3]);
					
					controlador.crearGasto(cat, fecha, cantidad);
					terminalPanel.appendText("Gasto creado exitosamente.\n");
					//Crea la categoria si no existe
					controlador.crearCategoria(comando[1]);
					
				} catch (DateTimeParseException e) {
					terminalPanel.appendText("Formato de fecha incorrecto. Ej: 20/10/2025");
				} catch (NumberFormatException e) {
					terminalPanel.appendText("Formato de número (cantidad) incorrecto. Ej: 80.45");
				} catch (IllegalArgumentException e) {
    		        terminalPanel.appendText("Formato de ID incorrecto.\n");
    		    }
    			
    			break;
    			
// ###########################################################################################################################
    			
    		case COMANDO_MOD_GASTO:
    			
    		    if (comando.length != 5) {
    		        terminalPanel.appendText("Número incorrecto de argumentos\n");
    		        break;
    		    }
    		    
    		    try {
    		    	UUID id = UUID.fromString(comando[1]);
					Categoria cat = new Categoria(comando[2]);
					LocalDate fecha = LocalDate.parse(comando[3], formateador);
					Double cantidad = Double.parseDouble(comando[4]);
					
					if (getGastoPorID(id) != null) {
						controlador.editarGasto(getGastoPorID(id), cantidad, cat, fecha);
						terminalPanel.appendText("Gasto editado exitosamente.\n");
						//Crea la categoria si no existe
						controlador.crearCategoria(comando[2]);
    		        } else {
    		            terminalPanel.appendText("No existe un gasto con esa ID.\n");
    		        }
					
				} catch (DateTimeParseException e) {
					terminalPanel.appendText("Formato de fecha incorrecto. Ej: 20/10/2025");
				} catch (NumberFormatException e) {
					terminalPanel.appendText("Formato de número (cantidad) incorrecto. Ej: 80.45");
				}
    			
    			break;
    			
// ###########################################################################################################################
    			
    		case COMANDO_EXIT:
    			Configuracion.getInstancia().getSceneManager().mostrarTablaGastos();
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
       
       for(Gasto g : controlador.getGastos()) {
           terminalPanel.appendText(String.format(formato, 
               g.getIdGasto().toString(), 
               g.getCategoria(), 
               g.getFecha().toString(), 
               g.getCantidad()
           ));
       }	
   }
    
   private Gasto getGastoPorID(UUID id) {
	   return controlador.getGastoPorID(id);
   }
    
}
