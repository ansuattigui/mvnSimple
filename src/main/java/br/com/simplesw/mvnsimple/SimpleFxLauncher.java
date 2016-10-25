/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.simplesw.mvnsimple;

import br.com.simplesw.mvnsimple.controller.SimpleMainController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 *
 * @author Ralfh
 */
public class SimpleFxLauncher extends Application {
    
    private Weld weld;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.weld = new Weld();
        WeldContainer weldContainer = weld.initialize();
        
        SimpleMainController controller = weldContainer.instance().select(SimpleMainController.class).get();
        
        primaryStage.setTitle("Simple");
        primaryStage.setScene(controller.sceneShow());
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        weld.shutdown();
        super.stop();
    }    
    
}
