/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.simplesw.mvnsimple.controller;

import br.com.simplesw.mvnsimple.ApplicationContainer;
import br.com.simplesw.mvnsimple.util.CdiContext;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ralfh
 */

public class SimpleMainController extends FxmlController {
    
    private final String FXMLPath = "/view/SimpleMain.fxml";

    @FXML public Accordion menuPrincipal;
    @FXML public TitledPane tpaneAgenda;
    @FXML public Button btnAgenda;
    
    public Stage primaryStage;
        
    
/*    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //CdiContext context = CdiContext.INSTANCE;
//        SimpleFxLauncher launcher = context.getBean(SimpleFxLauncher.class);                
//        primaryStage = launcher.getStage();
    }
*/
    
    public void start(Stage stage, Application.Parameters parameters) throws Exception {
        
        stage.setTitle("Simple");
        stage.setScene(this.sceneShow(FXMLPath));
        stage.show();        
        
    }    
       
    @FXML
    public void btnAgendaFired(ActionEvent event) throws IOException {        

        ApplicationContainer appContainer = ApplicationContainer.getInstance();        
        AgendaController controller = appContainer.getBean(AgendaController.class);                
        Stage stage = new Stage();
        stage.setTitle("Simple");
        stage.setScene(controller.sceneShow(null));
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }    

    @FXML
    public void btnPacientesFired(ActionEvent event) throws IOException {        
        CdiContext context = CdiContext.INSTANCE;
        PacienteController controller = context.getBean(PacienteController.class);                
        Stage stage = new Stage();
        stage.setTitle("Simple");
        stage.setScene(controller.sceneShow(null));
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }    
    
}
