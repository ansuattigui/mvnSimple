/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.simplesw.mvnsimple.producers;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author Ralfh
 */

public class EntityManagerProducer {

    @Inject 
    Instance<Object> instance;
        
    public EntityManagerProducer() {
    }
    
    @Produces   
    public EntityManager createEntityManager() {
       return Persistence.createEntityManagerFactory("br.com.simplesw_mvnSimple").createEntityManager();
    }

    public void closeEM(@Disposes EntityManager manager) {
       manager.close();
    }
    
}
