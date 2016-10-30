package br.com.simplesw.mvnsimple.controller;

import br.com.simplesw.mvnsimple.enumerated.Cadastro;
import br.com.simplesw.mvnsimple.enumerated.EstadoCivil;
import br.com.simplesw.mvnsimple.enumerated.Etnia;
import br.com.simplesw.mvnsimple.enumerated.Sexo;
import br.com.simplesw.mvnsimple.enumerated.Status;
import br.com.simplesw.mvnsimple.enumerated.Oper;
import br.com.simplesw.mvnsimple.modelos.Paciente;
import com.br.ralfh.medico.exceptions.CampoNuloException;
import com.br.ralfh.medico.modelos.CEPs;
import com.br.ralfh.medico.modelos.Convenios;
import com.br.ralfh.medico.modelos.FichaMedica;
import com.br.ralfh.medico.modelos.HorariosAgenda;
import com.br.ralfh.medico.modelos.Pacientes;
import com.br.ralfh.medico.modelos.UF;
import com.br.ralfh.medico.modelos.UFs;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManager;
import jfxtras.labs.util.Util;
import jidefx.scene.control.field.DateField;
import jidefx.scene.control.field.FormattedTextField;
import jidefx.scene.control.field.verifier.IntegerRangePatternVerifier;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class PacienteController implements Serializable {
        
    @FXML public Button btnCriar;                @FXML public Button btnAtualizar;
    @FXML public Button btnDeletar;              @FXML public Button btnConfirmar;
    @FXML public Button btnCancelar;             @FXML public Button btnSair;
    @FXML public Button btnProcurar;             @FXML public Button btnFotografar;
    
    @FXML public TextField id;                   @FXML public ComboBox naturalidade;
    @FXML public TextField nome;                 @FXML public ComboBox nacionalidade;
    @FXML public ChoiceBox<Sexo> sexo;           @FXML public ChoiceBox<String> estadocivil;
    @FXML public DateField nascimento;
    @FXML public TextField idade;
    @FXML public ChoiceBox<Etnia> etnia; 
    
    @FXML public TextField profissao;            @FXML public ComboBox convenio;
    @FXML public TextField rg;                   @FXML public TextField matricula;
    @FXML public TextField cpf;                  @FXML public ImageView imageFotografia;
    
    @FXML public TextField endereco;             @FXML public TextField numero;
    @FXML public TextField complemento;          @FXML public TextField bairro;    
    @FXML public FormattedTextField cep;         @FXML public TextField cidade;
    @FXML public ComboBox uf;                    @FXML public TextField telresidencial;
    @FXML public TextField telcomercial;         @FXML public TextField telcelular;
    @FXML public TextField email; 
    
    @FXML public TextField indicacao;            @FXML public ChoiceBox<Cadastro> cadastro;
    @FXML public ChoiceBox<Status> status;     
    
    @FXML public Button btnFichaMedica;          @FXML public Button btnAtestados;
    @FXML public Button btnReceitas;             @FXML public Button btnRecibos;
    @FXML public Button btnProcuraId;            @FXML public Button btnProcuraNome;
    @FXML public Button btnProcuraCep;
                  
    private Oper oper;       
    private byte[] bFotografia; 

    public PacienteController() {
        this.oper = Oper.IDLE;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setToolTips();
        habilEdicaoFired();
        initCombos();
        initFmtCep();
        addPacienteListener();
        addPacientesListener();
        bindDataNascIdade();
        addListenerDataNasc();
        addListenerUF();
        setButtons();
    } 
    
    @Override
    public void setPaciente(Integer pac, Integer horario) {
        sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithCodAnt(pac)));
        OrigemExterna = horario;
    }
    
    @Override
    public void setPaciente(Paciente pac, Integer horario) {
        paciente = pac;
        sopPaciente.set(paciente);
        OrigemExterna = horario;
    }

    @Override
    public void addStageCloseListener() {
        getController().getStage().setOnHiding(new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent we) {
              if (OrigemExterna > 0) {
                HorariosAgenda.atualizaPacienteHorario(paciente, OrigemExterna);
              }
          }
    });     
    }
    
    public void addListenerDataNasc() {
        nascPaciente.valueProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (!Objects.isNull(newVal)) {
                Integer Idade = Period.between(Util.ld(nascPaciente.valueProperty().get()), LocalDate.now()).getYears();
                sopIdade.set(Idade);
            } else {
                sopIdade.set(0);
            }
            
        }
    });
    }
    
    private void bindDataNascIdade() {
        idade.textProperty().bind(sopIdade.asString());
    }                    
    
    private void addPacienteListener() { 
        sopPaciente.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (sopPaciente.get() != null) {
                if (sopPaciente.get().getId() == -1) {
                    status = Oper.INSERTING;
                } else {
                    status = Oper.SHOWING;
                }
                setButtons();
                habilEdicaoFired();
                mostraPaciente();
            }
        }
    });                
    }
        
    private void addPacientesListener() { 
        sopPacientes.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c ) {
                apagaPaciente();
                if (sopPacientes.size() > 1) {
                    try {
                        String fxmlGUI = "fxml/SelecPaciente.fxml";
                        String titleGUI = "Selecionar Paciente";
                        StageStyle fxmlStyle = StageStyle.UTILITY;
                        GUIFactory selecPaciente = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,getStage());
                        selecPaciente.getController().getStage().initStyle(StageStyle.UNDECORATED);
                        SelecPacienteController controller = (SelecPacienteController) selecPaciente.getController();
                        controller.setPaciente(sopPacientes);
                        selecPaciente.showAndWait();
                        if (controller.closeModal) {
                            paciente = controller.tabelaPacientes.getSelectionModel().getSelectedItem();
                            sopPaciente.set(paciente);
                        } 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (sopPacientes.size() == 1) {
                    paciente = sopPacientes.iterator().next();
                    sopPaciente.set(paciente);
                }
            }
        });        
    }  
        
    private void initCombos() {
        initComboUfPaciente();
        initComboSexoPaciente();
        initComboEstadoCivil();
        initComboEtnia();
        initComboConvenio();
        initComboStatus();
        initComboSitCadastro();
        initComboNaturalidade();
        initComboNacionalidade();
    }

    private void initComboStatus() {
        statusPac.getItems().clear();
        ObservableList<StatusPaciente> options = 
            FXCollections.observableArrayList(
                StatusPaciente.values()
            );        
        statusPac.getItems().addAll(options);
        statusPac.getSelectionModel().selectFirst();    //  CRIAR ENTRADAS EM STATUS  datafield em pacientes  mascara dados datafield
    }

    private void initComboSitCadastro() {
        sitCadastro.getItems().clear();
        ObservableList<SitCadastro> options = 
            FXCollections.observableArrayList(
                SitCadastro.values()
            );        
        sitCadastro.getItems().addAll(options);
    }
    
    
    private void initComboUfPaciente() {            
        ufs = UFs.getObsLista();        
        for (UF uf:ufs) {
            ufPaciente.getItems().add(uf.getUf());
        }
    }
    public void addListenerUF() {
        ufPaciente.valueProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (!Objects.isNull(newVal)) {
                uf = UFs.getUFPelaSigla((String) newVal);
            } else {
                uf = null;
            }            
        }
    });
    }
    
    private void initComboSexoPaciente() {       
        ObservableList<Sexo> options = 
            FXCollections.observableArrayList(
                Sexo.values()
            );        
        sexoPaciente.getItems().addAll(options);        
    }
    
    private void initComboEstadoCivil() {
        ObservableList<EstadoCivil> options = 
            FXCollections.observableArrayList(
                EstadoCivil.values()
            );    
        ObservableList<String> lista=FXCollections.observableArrayList();
        
        for (EstadoCivil item : options) {
            lista.add(item.estadocivil());
        }
        
        estCivilPaciente.getItems().addAll(lista);        
    }
                    
    private void initComboEtnia() {
        ObservableList<Etnia> options = 
            FXCollections.observableArrayList(
                Etnia.values()
            );    
        etniaPaciente.getItems().addAll(options);  
    }

    private void initComboNaturalidade() {
        ObservableList<String> options = 
            FXCollections.observableArrayList(UFs.getNaturalidades());    
        naturPaciente.getItems().addAll(options);        
    }
    
    private void initComboNacionalidade() {
        ArrayList nacion = new ArrayList();
        nacion.add(0, "BRASILEIRA");
        nacionPaciente.getItems().addAll(nacion);        
    }
    
    
    private void initComboConvenio() {
        ObservableList<String> options = 
            FXCollections.observableArrayList(Convenios.getListaNomes());    
        convPaciente.getItems().addAll(options);        
    }

    /// Melhorar mascara com string 5 pos - 3 pos
    private void initFmtCep() {        
        fmtCEP.getPatternVerifiers().put("h", new IntegerRangePatternVerifier(0,99999));
        fmtCEP.getPatternVerifiers().put("g", new IntegerRangePatternVerifier(0,999));
        fmtCEP.setPattern("h-g"); 
        fmtCEP.setClearButtonVisible(true);
    }
    
    public void criaPacienteFired(ActionEvent event) {
        status = Oper.INSERTING;
        apagaPaciente();
        codAntPaciente.setText(String.valueOf(Pacientes.getProcCodAnt()));
        setButtons();
        habilEdicaoFired();
    }
    
    public void atualizaPacienteFired(ActionEvent event) {
        status = Oper.UPDATING;
        setButtons();
        habilEdicaoFired();
    }
    
    public void btnDelPacienteFired(ActionEvent event) {
        if (ExcluiRegistroDlg("EPAC", "", null,this.getStage())) {
            if (!Pacientes.excluiPaciente(paciente)) {
                ShowDialog("EX", "Não foi possível excluir o paciente", null,this.getStage());
            } else {
                ShowDialog("S", "Paciente excluido com sucesso", null,this.getStage());
                status = Oper.IDLE;
                apagaPaciente();
                setButtons();
            }
        }
    }
            
    public void confPacienteFired(ActionEvent event) {          
        if (status==Oper.INSERTING) {            
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            this.paciente = new Paciente();
            if (preenchePaciente()) {   
                if (Pacientes.novoPaciente(this.paciente,manager)) {
                    manager.getTransaction().commit();
                    manager.close();     
                    ShowDialog("S", "Paciente incluido com sucesso", null,this.getStage());                    
                    status = Oper.SHOWING;
                } else {
                    ShowDialog("EX", "Não foi possível incluir o paciente", null,this.getStage());
                    manager.getTransaction().rollback();
                    manager.close();     
                    //status = Oper.INSERTING;
                }    
            }
        } else {
            if (preenchePaciente()) {
                if (Pacientes.atualizaPaciente(paciente)) {
                    ShowDialog("S", "Paciente atualizado com sucesso", null,this.getStage());                    
                    status = Oper.SHOWING;
                } else {
                    ShowDialog("EX", "Não foi possível atualizar o paciente", null,this.getStage());
                    //status = Oper.INSERTING;
                }
                status = Oper.SHOWING;
            } else return;
        }
        
        setButtons();      
        habilEdicaoFired();
    }
    
    public void cancPacienteFired(ActionEvent event) {
        try {
            if (status==Oper.INSERTING) {
                status = Oper.IDLE;
                apagaPaciente();
            } else {
                status = Oper.SHOWING;
                mostraPaciente();
            }        
        } catch (NullPointerException e) {
            status = Oper.IDLE;
        }        
        setButtons();      
        habilEdicaoFired();
    }    
    
    @FXML
    public void fichaMedicaFired(ActionEvent event){
        String fxmlGUI = "fxml/FichaMedica.fxml";
        String titleGUI = "Ficha médica de " + paciente.getNome() + " / " + paciente.getConvenio().getNome();
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory fichamedica;   
        try {
            fichamedica = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
            FichaMedicaController controller = (FichaMedicaController) fichamedica.getController();
            controller.setPaciente(paciente);
            controller.addStageCloseListener();
            fichamedica.showAndWait(); 
        } catch (IOException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    @FXML
    public void btnAtestadosFired(ActionEvent event){
        String fxmlGUI = "fxml/Atestado.fxml";
        String titleGUI = "Atestados de " + paciente.getNome() + " / " + paciente.getConvenio().getNome();
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory atestados;   
        try {
            atestados = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
            AtestadoController controller = (AtestadoController) atestados.getController();
            controller.setPaciente(paciente);
            atestados.showAndWait(); 
        } catch (IOException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    @FXML
    public void btnReceitasFired(ActionEvent event){
        String fxmlGUI = "fxml/Receita.fxml";
        String titleGUI = "Receitas de " + paciente.getNome() + " / " + paciente.getConvenio().getNome();
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory receitas;   
        try {
            receitas = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
            ReceitaController controller = (ReceitaController) receitas.getController();
            controller.setPaciente(paciente);
            receitas.showAndWait(); 
        } catch (IOException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }

    @FXML
    public void btnRecibosFired(ActionEvent event){
        String fxmlGUI = "fxml/Recibo.fxml";
        String titleGUI = "Recibos de " + paciente.getNome() + " / " + paciente.getConvenio().getNome();
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory recibos;   
        try {
            recibos = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
            ReciboController controller = (ReciboController) recibos.getController();
            controller.setPaciente(paciente);
            recibos.showAndWait(); 
        } catch (IOException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }

    
    public void btnProcurarFired(ActionEvent event) {
        apagaPaciente();
        status = Oper.IDLE;
        setButtons();
        habilEdicaoFired();
    }
    

    public void sairFired(ActionEvent event) {
        this.stage.close();
    }    
    
    public void btnFotografarFired(ActionEvent event) throws Exception {
        CameraController controller;
        GUIFactory camera;
                
        StageStyle fxmlStyle = StageStyle.UTILITY;
        String gui = "fxml/Camera.fxml";
        String titleGUI = "Fotografar visitante";

        try {
            camera = new GUIFactory(gui,titleGUI,fxmlStyle,this.getStage());
//            camera.initialize();
            controller = (CameraController) camera.getController();
            camera.showAndWait();
            if (controller.closeModal) {
                Image foto = new Image(controller.file.toURI().toString());
                imageFotografia.setImage(foto);
                bFotografia = controller.bFile;
            } 
        } catch (Exception ex) {
            Logger.getLogger(CameraController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }   
    
    private void mostraPaciente() {
        try {
            codAntPaciente.setText(String.valueOf(paciente.getCodAntigo()));
            nomePaciente.setText(paciente.getNome()); 
            sexoPaciente.getSelectionModel().clearSelection();
            sexoPaciente.getSelectionModel().select(paciente.getSexo());
            nascPaciente.setValue(paciente.getNascimento());
            naturPaciente.getEditor().setText(paciente.getNaturalidade());
            nacionPaciente.getSelectionModel().select(paciente.getNacionalidade());
            estCivilPaciente.getSelectionModel().select(paciente.getEstadoCivil());
            etniaPaciente.getSelectionModel().select(paciente.getEtnia());
            profPaciente.setText(paciente.getProfissao());
            rgPaciente.setText(paciente.getIdentidade());
            cpfPaciente.setText(paciente.getCpf());
            enderPaciente.setText(paciente.getEndereco());
            numEndPaciente.setText(paciente.getNumero());
            compEndPaciente.setText(paciente.getComplemento());
            bairroPaciente.setText(paciente.getBairro());
            fmtCEP.setText(paciente.getCep());
            cidadePaciente.setText(paciente.getCidade());
            ufPaciente.getSelectionModel().select(paciente.getEstado());
            telResPaciente.setText(paciente.getTelResidencial());
            telComPaciente.setText(paciente.getTelComercial());
            celularPaciente.setText(paciente.getCelular());
            emailPaciente.setText(paciente.getEmail());
            String conv = paciente.getConvenio().getNome();
            convPaciente.getSelectionModel().select(conv);
            matConvPaciente.setText(paciente.getNumConveniado());
            
            indicacao.setText(paciente.getIndicacao());
            //sitCadastro.getSelectionModel().select(SitCadastro.valueOf(paciente.get));
            statusPac.getSelectionModel().select(StatusPaciente.valueOf(paciente.getStatus()));
            dataPrimConsulta.setText(FichaMedica.getDataPrimCons(paciente));
            bFotografia = paciente.getFotografia();
            try {
                ByteArrayInputStream in = new ByteArrayInputStream(bFotografia);
                Image image = new Image(in);            
                this.imageFotografia.setImage(image);
            } catch(NullPointerException e) {
                this.imageFotografia.setImage(null);
            }
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

    private Boolean preenchePaciente() {        
        Boolean resultado = Boolean.FALSE;
        try {
            paciente.setCodAntigo(Integer.parseInt(codAntPaciente.getText()));        
            paciente.setNome(nomePaciente.getText()); 
            paciente.setSexo(sexoPaciente.getSelectionModel().getSelectedItem());
            paciente.setNascimento(nascPaciente.getValue());
            paciente.setEtnia(etniaPaciente.getSelectionModel().getSelectedItem());
            paciente.setConvenio((String) convPaciente.getSelectionModel().getSelectedItem());
            paciente.setNumConveniado(matConvPaciente.getText());
            resultado = Boolean.TRUE;
//        } catch(CampoNuloException | CampoEmBrancoException cne) {
        } catch(CampoNuloException cne) {
            ShowDialog("EX", cne.getMessage(), null,this.getStage());
        }
        paciente.setNaturalidade(naturPaciente.getEditor().getText());
        paciente.setNacionalidade(nacionPaciente.getEditor().getText());
        paciente.setEstadoCivil(estCivilPaciente.getSelectionModel().getSelectedItem());
        paciente.setProfissao(profPaciente.getText());
        paciente.setIdentidade(rgPaciente.getText());
        paciente.setCpf(cpfPaciente.getText());
        paciente.setEndereco(enderPaciente.getText());
        paciente.setNumero(numEndPaciente.getText());
        paciente.setComplemento(compEndPaciente.getText());
        paciente.setBairro(bairroPaciente.getText());
        paciente.setCep(fmtCEP.getText());
        paciente.setCidade(cidadePaciente.getText());
        paciente.setEstado((String) ufPaciente.getSelectionModel().getSelectedItem());
        paciente.setTelComercial(telComPaciente.getText());
        paciente.setTelResidencial(telResPaciente.getText());
        paciente.setCelular(celularPaciente.getText());
        paciente.setEmail(emailPaciente.getText()); 
        
        paciente.setIndicacao(indicacao.getText());
        if (statusPac.getValue()!=null) {
            paciente.setStatus(statusPac.getValue().toString());
        } else {
            paciente.setStatus(statusPac.getItems().get(0).name());
        }
//        paciente.setSitCadastro(sitCadastro.getValue().toString());
        
        paciente.setFotografia(bFotografia);             
        return resultado;
    }
        
    public void btnProcCodFired(ActionEvent event) throws Exception {
//        Pacientes pacientes = new Pacientes();
        sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithCod(Integer.parseInt(codPaciente.getText()))));
    }    
    
    public void btnProcCodAntFired(ActionEvent event) throws Exception {
//        Pacientes pacientes = new Pacientes();
        Integer codant = (codAntPaciente.getText().trim().isEmpty()?-1:Integer.parseInt(codAntPaciente.getText()));
        if (codant>0) {
            sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithCodAnt(codant)));
        }
    }
    
    public void btnProcNomeFired(ActionEvent event) throws Exception {
        String nome = nomePaciente.getText();
        if (!nome.isEmpty()) {
            nome = "%" + nome.replace(" ", "%");            
            sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithNome(nome)));
        }
    } 
    
    public void btnProcCepFired(ActionEvent event) {      
        if (!fmtCEP.getText().isEmpty()) {
            uf = UFs.getUFPeloCep(fmtCEP.getText());
            if (uf != null) {
                preencheCep(uf);
            }
        }
    }
    
    private void preencheCep(UF uf) {
        ufPaciente.getSelectionModel().select(uf.getUf());
        cep = CEPs.getCEPPeloNome(fmtCEP.getText(), uf.getUf());
        if (cep!=null) preencheEndereco();
    }
    
    private void preencheEndereco() {
        if (cep!=null) {
            enderPaciente.setText(cep.getTp_logradouro()+" "+cep.getLogradouro());
            bairroPaciente.setText(cep.getBairro());
            cidadePaciente.setText(cep.getCidade());
        }
    }
    
    
    public void apagaPaciente() {
        this.codAntPaciente.clear();
        this.nomePaciente.clear();
        this.sexoPaciente.getSelectionModel().select(-1);
        this.etniaPaciente.getSelectionModel().select(-1);
        this.nascPaciente.setValue(null);
        this.idade.clear();
        this.naturPaciente.getEditor().clear();
        this.nacionPaciente.getEditor().clear();
        this.estCivilPaciente.getSelectionModel().select(-1);
        this.profPaciente.clear();
        this.rgPaciente.clear();
        this.cpfPaciente.clear();
        this.enderPaciente.clear();
        this.numEndPaciente.clear();
        this.compEndPaciente.clear();
        this.bairroPaciente.clear();
        this.cidadePaciente.clear();
        this.fmtCEP.clear();
        this.ufPaciente.getSelectionModel().select(-1);
        this.telComPaciente.clear();
        this.telResPaciente.clear();
        this.celularPaciente.clear();
        this.emailPaciente.clear();
        this.convPaciente.getSelectionModel().select(-1);
        this.matConvPaciente.clear();  
        this.indicacao.clear();
        this.dataPrimConsulta.clear();
        this.statusPac.getSelectionModel().select(-1);
        this.sitCadastro.getSelectionModel().select(-1);
        this.imageFotografia.setImage(null);
    }
    
    public void habilEdicaoFired() {
        this.codAntPaciente.setEditable(((status==Oper.INSERTING)|(status==Oper.UPDATING))|(status==Oper.IDLE));  //setEditable(Boolean.FALSE);   //(((status==Oper.INSERTING)|(status==Oper.UPDATING))|(status==Oper.IDLE));
        this.nomePaciente.setEditable(((status==Oper.INSERTING)|(status==Oper.UPDATING))|(status==Oper.IDLE));
//        this.sexoPaciente.setDisable((status!=Oper.INSERTING)&(status!=Oper.UPDATING));
//        this.nascPaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
//        this.nascPaciente.setDisable((status!=Oper.INSERTING)&(status!=Oper.UPDATING));
//        this.naturPaciente.setDisable((status!=Oper.INSERTING)&(status!=Oper.UPDATING));
//        this.nacionPaciente.setDisable((status!=Oper.INSERTING)&(status!=Oper.UPDATING));
//        this.estCivilPaciente.setDisable((status!=Oper.INSERTING)&(status!=Oper.UPDATING));
        this.profPaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
        this.rgPaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
        this.cpfPaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
        this.enderPaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
        this.numEndPaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
        this.compEndPaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
        this.bairroPaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
        this.cidadePaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
//        this.fmtCEP.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
//        this.ufPaciente.setDisable((status!=Oper.INSERTING)&(status!=Oper.UPDATING));
        this.telComPaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
        this.telResPaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
        this.celularPaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
        this.emailPaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));
//        this.convPaciente.setDisable((status!=Oper.INSERTING)&(status!=Oper.UPDATING));
        this.matConvPaciente.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));              
        this.indicacao.setEditable((status==Oper.INSERTING)|(status==Oper.UPDATING));              
        
    }
    
    private void setButtons() {
        btnCriarPaciente.setDisable((status==Oper.INSERTING)|(status==Oper.UPDATING));
        btnAtualPaciente.setDisable((status==Oper.INSERTING)|(status==Oper.UPDATING)|(status!=Oper.SHOWING));
        btnDelPaciente.setDisable(status!=Oper.SHOWING);
        btnConfPaciente.setDisable((status!=Oper.INSERTING)&(status!=Oper.UPDATING));
        btnCancPaciente.setDisable((status!=Oper.INSERTING)&(status!=Oper.UPDATING));
        
        btnFichaMedica.setDisable((status!=Oper.SHOWING) | (!perfilUsuario.getTipoUsuario().equals("Medico"))) ;
        btnAtestados.setDisable((status!=Oper.SHOWING) | (!perfilUsuario.getTipoUsuario().equals("Medico")));
        btnReceitas.setDisable((status!=Oper.SHOWING) | (!perfilUsuario.getTipoUsuario().equals("Medico")));
        btnRecibos.setDisable((status!=Oper.SHOWING));        
        
        btnProcurar.setDisable(status!=Oper.SHOWING);
        btnProcCodAnt.setDisable(status!=Oper.IDLE);
        btnProcNome.setDisable(status!=Oper.IDLE);
        btnSair.setDisable((status!=Oper.IDLE)&(status!=Oper.SHOWING));
        btnFotografar.setDisable((status!=Oper.INSERTING)&(status!=Oper.UPDATING));
    }
    
    private void setToolTips() {
        btnCriarPaciente.setTooltip(new Tooltip("Criar novo Paciente"));
        btnAtualPaciente.setTooltip(new Tooltip("Atualizar o Paciente selecionado"));        
        btnConfPaciente.setTooltip(new Tooltip("Gravar Paciente"));
        btnDelPaciente.setTooltip(new Tooltip("Excluir Paciente selecionado"));
        btnCancPaciente.setTooltip(new Tooltip("Cancelar as alterações"));        
        btnFichaMedica.setTooltip(new Tooltip("Abrir a Ficha Médica do Paciente"));
        btnReceitas.setTooltip(new Tooltip("Emitir/Acesssar Receitas do Paciente"));
        btnRecibos.setTooltip(new Tooltip("Emitir/Acesssar Recibos do Paciente"));
        btnAtestados.setTooltip(new Tooltip("Emitir/Acesssar Atestados do Paciente"));
        btnProcCodAnt.setTooltip(new Tooltip("Procurar Paciente pelo codigo"));
        btnProcNome.setTooltip(new Tooltip("Procurar Paciente pelo nome"));
        btnRecibos.setTooltip(new Tooltip("Emitir/Acesssar Recibos do Paciente"));
        btnSair.setTooltip(new Tooltip("Fechar esta janela"));
    }
    
    
}