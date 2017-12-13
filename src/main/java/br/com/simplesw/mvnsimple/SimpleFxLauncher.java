/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.simplesw.mvnsimple;

import br.com.simplesw.mvnsimple.controller.SimpleMainController;
import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.AbstractModule;
import java.io.IOException;
import java.security.Provider.Service;
import java.util.Arrays;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.inject.Inject;

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
        launch(args);
    }    
    
    private GuiceContext context = new GuiceContext(this, () -> Arrays.asList(new GuiceModule()));
    
    @Inject private FXMLLoader fxmlLoader;    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        context.init();
        fxmlLoader.setLocation(getViewLocation());
        Parent view = fxmlLoader.load();
 
        primaryStage.setTitle("Guice Example");
        primaryStage.setScene(new Scene(view));
        primaryStage.show();        
        
        
        
//        this.stage = primaryStage;
        
        
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
    

    class GuiceModule extends AbstractModule {
        @Override protected void configure() {
        bind(Service.class).to(Service.class);
    }
}    
    
}
