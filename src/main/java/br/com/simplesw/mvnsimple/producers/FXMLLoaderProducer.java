/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.simplesw.mvnsimple.producers;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 *
 * @author ralfh
 */
public class FXMLLoaderProducer {
    
    @Inject 
    Instance<Object> instance;

    @Produces
    public FXMLLoader createLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(new Callback<Class<?>, Object>() {

        @Override
        public Object call(Class<?> param) {
            return instance.select(param).get();
        }
    });

    return loader;
    }
}
