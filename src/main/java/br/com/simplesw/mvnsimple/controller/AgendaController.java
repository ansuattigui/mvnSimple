/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.simplesw.mvnsimple.controller;

import static br.com.simplesw.mvnsimple.util.DateUtil.calendarToExtenso;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javax.inject.Inject;
import jfxtras.scene.control.CalendarPicker;

/**
 * FXML Controller class
 *
 * @author ralfh
 */


//@ApplicationScoped
public class AgendaController extends FxmlController {
    
    private final String FXMLPath = "/view/Agenda.fxml";
    
    private SimpleObjectProperty<String> calendarioExtenso;
    
    @FXML public Label lblDataPorExtenso;    
    @FXML public TextField consMarcadas;
    @FXML public TextField consAtendidas;
    @FXML public TextField numEncaixes;
    @FXML public TextField numPVez;
    @FXML public TextField numECG;
    @FXML public VBox vbCalendar;
    @FXML public RadioButton rbTodos;
    @FXML public ToggleGroup tgHorarios;
    @FXML public RadioButton rbOcupados;
    @FXML public RadioButton rbLivres;
    @FXML public Button btnCadastro;
    @FXML public TextField codigo;
    @FXML public TextField codigoant;
    @FXML public TextField evento;
    @FXML public TextField nomeConvenio;
    @FXML public TextField telefoneI;
    @FXML public TextField telefoneII;
    @FXML public TextArea observacoes;
    
    @FXML public CalendarPicker calendario;
    
    @Inject    
    public AgendaController() {        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calendarioExtenso = new SimpleObjectProperty<>();
        initCalendario();     
        calendario.setCalendar(Calendar.getInstance());
        dataPorExtensoBindCalendar();
    }    
    
    @Override
    public Scene sceneShow(String FxmlPath) throws IOException {        
        return super.sceneShow(FXMLPath);
    }
    
    private void dataPorExtensoBindCalendar() {
        lblDataPorExtenso.textProperty().bind(calendarioExtenso.asString());
    }
    
    public void initCalendario() {  
        calendario.calendarProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (calendario.calendarProperty().getValue() != null) {
                calendarioExtenso.set(calendarToExtenso(calendario.calendarProperty().getValue()));
            } 
        }
        });        
    }
    
    
}
