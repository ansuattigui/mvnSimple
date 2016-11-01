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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author Ralfh
 */
public class FxmlController implements Initializable {
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public Scene sceneShow(String FxmlPath) throws IOException {
        Parent root = (Parent)FXMLLoader.load(getClass().getResource(FxmlPath));        
        Scene scene = new Scene(root);           
        return scene;
    }
    
    public void sairFired(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }    
    
    
    
    
    
    
}
