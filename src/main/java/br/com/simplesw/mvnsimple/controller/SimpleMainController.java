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
    
    @Inject
    FXMLLoader loader;

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
    private void btnAgendaFired(ActionEvent event) {
    }
    
    
    
    public Scene sceneShow() throws IOException {
//        FXMLLoader loader;    
        
//        loader = new FXMLLoader(getClass().getResource("/view/simpleMain.fxml"));
        Parent root = (Parent)loader.load(getClass().getResource("/view/simpleMain.fxml"));        
        Scene scene = new Scene(root);        
        return scene;
    }
    
}
