/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.simplesw.mvnsimple.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
 * @author Ralfh
 */
@ApplicationScoped
public class SimpleMainController implements Initializable {

    @FXML private Accordion menuPrincipal;
    @FXML private TitledPane tpaneAgenda;
    @FXML private Button btnAgenda;

/*    
    @Inject
    FXMLLoader loader;
*/
    
    @Inject
    AgendaController agendaController;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAgendaFired(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Simple");
        stage.setScene(agendaController.sceneShow());
        stage.show();
/*        
        Parent root = null;
        try {        
            root = (Parent)FXMLLoader.load(getClass().getResource("/view/Agenda.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(SimpleMainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        stage.setScene(new Scene(root));
        stage.show();   
*/
    }    
    
    
    public Scene sceneShow() throws IOException {
        Parent root = (Parent)FXMLLoader.load(getClass().getResource("/view/simpleMain.fxml"));        
        Scene scene = new Scene(root);        
        return scene;
    }
    
}
