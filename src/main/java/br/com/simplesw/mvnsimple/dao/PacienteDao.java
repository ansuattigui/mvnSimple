/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.simplesw.mvnsimple.dao;

import br.com.simplesw.mvnsimple.modelos.Paciente;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ralfh
 */
public class PacienteDao {
    
    @Inject
    public PacienteDao() {
    }
    
    private final String QUERY_LISTA_WITH_CODANT = "from Paciente as pac where pac.codAntigo like :codAnt order by pac.nome";
    private final String QUERY_PROX_COD_ANT = "select max(codAntigo) from Paciente as ca";

    @Inject 
    private EntityManager manager;
    
    public Boolean criaPaciente(Paciente paciente) {
        Boolean resultado = Boolean.FALSE;
        try {
            manager.persist(paciente); 
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public Boolean atualizaPaciente(Paciente paciente) {
        Boolean resultado = Boolean.FALSE;
        try {
            manager.merge(paciente);  
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;        
    }

    public Boolean excluiPaciente(Paciente paciente) {
        Boolean resultado = Boolean.FALSE;
        try {
            manager.remove(paciente);  
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public ArrayList<Paciente> getLista() {
        
        String jpql = "select p from Paciente p order by p.nome";
        TypedQuery<Paciente> query = manager.createQuery(jpql,Paciente.class);
        ArrayList<Paciente> pacientes = (ArrayList) query.getResultList();
        
        return pacientes;                
    }

    public Paciente getPacienteWithId(Integer id) {
        
        Paciente paciente = manager.find(Paciente.class, id);        
        return paciente;
    }        

/*    
    public static ArrayList<Paciente> getListaWithCodAnt(Integer paciente) {

        String jpql = "select p from Paciente p where p.codAntigo like :pcodant order by p.nome";
        TypedQuery<Paciente> query = manager.createQuery(jpql,Paciente.class);
        query.setParameter("pcodant",paciente);
        ArrayList<Paciente> pacientes = (ArrayList) query.getResultList();
        manager.close();        
        
        return pacientes;
    }        
    
    public static ArrayList<Paciente> getListaWithNome(String paciente) {
        ArrayList<Paciente> pacs;
        String jpql = "select p from Paciente p where p.nome LIKE :pnome order by p.nome";
        TypedQuery<Paciente> query = manager.createQuery(jpql,Paciente.class); 
        query.setParameter("pnome",paciente+'%');
        try {
            pacs = (ArrayList) query.getResultList();
        } catch(EntityNotFoundException enf) {
            pacs = null;
        }
        manager.close();        
        
        return pacs;
    }        
    
    public ObservableList<Paciente> getObsLista() {        
        return FXCollections.observableArrayList(this.getLista());
    }    
    public static ObservableList<Paciente> getObsListaWithNome(String pac) {
        List<Paciente> pacientes = getListaWithNome(pac);
        if (pacientes != null) 
            return FXCollections.observableList(pacientes);
        else
            return null;
    }              
    public static ObservableList<Paciente> getObsListaWithCod(Integer paciente) {        
        return FXCollections.observableArrayList(getPacienteWithId(paciente));
    }              
    public static ObservableList<Paciente> getObsListaWithCodAnt(Integer paciente) {        
        return FXCollections.observableArrayList(getListaWithCodAnt(paciente));
    }  
*/

}
