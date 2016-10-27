/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.simplesw.mvnsimple.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
 * @author ralfh
 */

@ApplicationScoped
public class AgendaController implements Initializable {

    @FXML
    private TextField consMarcadas;
    @FXML
    private TextField consAtendidas;
    @FXML
    private TextField numEncaixes;
    @FXML
    private TextField numPVez;
    @FXML
    private TextField numECG;
    @FXML
    private VBox vbCalendar;
    @FXML
    private RadioButton rbTodos;
    @FXML
    private ToggleGroup tgHorarios;
    @FXML
    private RadioButton rbOcupados;
    @FXML
    private RadioButton rbLivres;
    @FXML
    private Button btnCadastro;
    @FXML
    private TextField codigo;
    @FXML
    private TextField codigoant;
    @FXML
    private TextField evento;
    @FXML
    private TextField nomeConvenio;
    @FXML
    private TextField telefoneI;
    @FXML
    private TextField telefoneII;
    @FXML
    private TextArea observacoes;
    
    @Inject    
    public AgendaController() {
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    public Scene sceneShow() throws IOException {
        Parent root = (Parent)FXMLLoader.load(getClass().getResource("/view/Agenda.fxml"));        
        Scene scene = new Scene(root);        
        return scene;
    }    
    
    
    
}
