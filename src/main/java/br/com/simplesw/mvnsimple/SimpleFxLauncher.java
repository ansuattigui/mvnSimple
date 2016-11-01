/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.simplesw.mvnsimple;

import br.com.simplesw.mvnsimple.controller.SimpleMainController;
import br.com.simplesw.mvnsimple.util.CdiContext;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Ralfh
 */
public class SimpleFxLauncher extends Application {
    
    private Stage stage;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SimpleFxLauncher.launch(args);
    }    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        CdiContext context = CdiContext.INSTANCE;
        SimpleMainController controller = context.getBean(SimpleMainController.class);
        primaryStage.setTitle("Simple");
        primaryStage.setScene(controller.sceneShow(null));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
     //   weld.shutdown();
        super.stop();
    }    

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }
    
}
