package br.com.simplesw.mvnsimple.controller;

import br.com.simplesw.mvnsimple.dao.PacienteDao;
import br.com.simplesw.mvnsimple.enumerated.Cadastro;
import br.com.simplesw.mvnsimple.enumerated.EstadoCivil;
import br.com.simplesw.mvnsimple.enumerated.Etnia;
import br.com.simplesw.mvnsimple.enumerated.Oper;
import br.com.simplesw.mvnsimple.enumerated.Sexo;
import br.com.simplesw.mvnsimple.enumerated.Status;
import br.com.simplesw.mvnsimple.modelos.Paciente;
import br.com.simplesw.mvnsimple.util.DateUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.inject.Inject;
import jidefx.scene.control.field.DateField;
import jidefx.scene.control.field.FormattedTextField;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class PacienteController extends FxmlController {
        
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
    
    private final String FXMLPath = "/view/Pacientes.fxml";
    private SimpleObjectProperty<Integer> sopidade;
    private Oper oper;       
    private byte[] bFotografia; 
    private Paciente paciente;
    private SimpleObjectProperty<Paciente> sopPaciente;
    
    @Inject 
    private PacienteDao dao;
    
    @Inject
    public PacienteController() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.oper = Oper.IDLE;
        sopidade = new SimpleObjectProperty<>();
        sopPaciente = new SimpleObjectProperty<>();
        listenerIdade();

        setToolTips();
        habilEdicaoFired();
        initCombos();
//        initFmtCep();
        addPacienteListener();
//        addPacientesListener();
        bindDataNascIdade();
//        addListenerUF();
        setButtons();
    } 
    
    @Override
    public Scene sceneShow(String FxmlPath) throws IOException {         
        return super.sceneShow(FXMLPath);
    }

    public void listenerIdade() {
        nascimento.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                if (!Objects.isNull(newVal)) {
                    Integer idad = Period.between(DateUtil.ld(nascimento.valueProperty().get()), LocalDate.now()).getYears();
                    sopidade.set(idad);
                } else {
                    sopidade.set(0);
                }            
            }
        });
    }
    
    private void bindDataNascIdade() {
        idade.textProperty().bind(sopidade.asString());
    }                    

    
    private void addPacienteListener() { 
        sopPaciente.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                setButtons();
                habilEdicaoFired();
                mostraPaciente();
            }
        });                
    }
    
/*        
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
*/
        
    private void initCombos() {
        initComboStatus();
        initComboCadastro();
        initComboSexo();
        initComboEstadoCivil();
        initComboEtnia();

//        initComboUfPaciente();
//        initComboConvenio();
//        initComboNaturalidade();
//        initComboNacionalidade();
    }

    private void initComboStatus() {
        status.getItems().clear();
        ObservableList<Status> options = FXCollections.observableArrayList(Status.values());        
        status.getItems().addAll(options);
        status.getSelectionModel().selectFirst();    //  CRIAR ENTRADAS EM STATUS  datafield em pacientes  mascara dados datafield
    }

    private void initComboCadastro() {
        cadastro.getItems().clear();
        ObservableList<Cadastro> options = FXCollections.observableArrayList(Cadastro.values());        
        cadastro.getItems().addAll(options);
    }

/*    
    private void initComboUfPaciente() {            
        uf = UFs.getObsLista();        
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
*/
    
    private void initComboSexo() {       
        sexo.getItems().clear();
        ObservableList<Sexo> options = FXCollections.observableArrayList(Sexo.values());        
        sexo.getItems().addAll(options);        
    }
    
    private void initComboEstadoCivil() {
        estadocivil.getItems().clear();
        ObservableList<EstadoCivil> options = FXCollections.observableArrayList(EstadoCivil.values());    
        ObservableList<String> lista=FXCollections.observableArrayList();
        for (EstadoCivil item : options) {
            lista.add(item.estadocivil());
        }        
        estadocivil.getItems().addAll(lista);        
    }
                    
    private void initComboEtnia() {
        etnia.getItems().clear();
        ObservableList<Etnia> options = FXCollections.observableArrayList(Etnia.values());    
        etnia.getItems().addAll(options);  
    }

/*    
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
*/  

    public void criaPacienteFired(ActionEvent event) {
        oper = Oper.INSERTING;
        apagaPaciente();
        setButtons();
        habilEdicaoFired();
    }
    
    public void atualizaPacienteFired(ActionEvent event) {
        oper = Oper.UPDATING;
        setButtons();
        habilEdicaoFired();
    }

    
    public void delPacienteFired(ActionEvent event) {
/*        
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
*/
    }
            
    public void confPacienteFired(ActionEvent event) {          
/*        
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
*/
    }
    
    public void cancPacienteFired(ActionEvent event) {
        if (oper==Oper.INSERTING) {
            oper = Oper.IDLE;
            apagaPaciente();
        } else {
            oper = Oper.SHOWING;
            mostraPaciente();
        }        
        setButtons();      
        habilEdicaoFired();
    }    
    
    @FXML
    public void fichaMedicaFired(ActionEvent event){
/*                
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
*/
    }
    
    @FXML
    public void btnAtestadosFired(ActionEvent event){
/*        
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
*/
    }
    
    @FXML
    public void btnReceitasFired(ActionEvent event){
/*        
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
*/
    }

    @FXML
    public void btnRecibosFired(ActionEvent event){
/*        
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
*/
    }

    
    public void btnProcurarFired(ActionEvent event) {
        apagaPaciente();
        oper = Oper.IDLE;
        setButtons();
        habilEdicaoFired();
    }
    

    public void btnFotografarFired(ActionEvent event) throws Exception {
/*        
        
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
*/
    }   
    
    private void mostraPaciente() {
        try {
            id.setText(String.valueOf(paciente.getId()));
            nome.setText(paciente.getNome()); 
            sexo.setValue(Sexo.valueOf(paciente.getSexo()));                        
            nascimento.setValue(paciente.getNascimento());
//            naturPaciente.getEditor().setText(paciente.getNaturalidade());
//            nacionPaciente.getSelectionModel().select(paciente.getNacionalidade());
            estadocivil.setValue(paciente.getEstadocivil());
            etnia.setValue(Etnia.valueOf(paciente.getEtnia()));
            profissao.setText(paciente.getProfissao());
            rg.setText(paciente.getRg());
            cpf.setText(paciente.getCpf());
            endereco.setText(paciente.getEndereco());
            numero.setText(paciente.getNumero());
            complemento.setText(paciente.getComplemento());
            bairro.setText(paciente.getBairro());
            cep.setText(paciente.getCep());
            cidade.setText(paciente.getCidade());
            uf.getSelectionModel().select(paciente.getUf());
            telresidencial.setText(paciente.getTelresidencial());
            telcomercial.setText(paciente.getTelcomercial());
            telcelular.setText(paciente.getTelcelular());
            email.setText(paciente.getEmail());
            
//            String conv = paciente.getConvenio().getNome();
//            convPaciente.getSelectionModel().select(conv);
//            matConvPaciente.setText(paciente.getNumConveniado());
            
            indicacao.setText(paciente.getIndicacao());
            //sitCadastro.getSelectionModel().select(SitCadastro.valueOf(paciente.get));
            status.setValue(Status.valueOf(paciente.getStatus()));
            //dataPrimConsulta.setText(FichaMedica.getDataPrimCons(paciente));
            
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
//        try {
            paciente.setNome(nome.getText()); 
            paciente.setSexo(sexo.getValue().toString());
            paciente.setNascimento(nascimento.getValue());
            paciente.setEtnia(etnia.getValue().toString());
//            paciente.setConvenio((String) convPaciente.getSelectionModel().getSelectedItem());
//            paciente.setNumConveniado(matConvPaciente.getText());
            resultado = Boolean.TRUE;
//        } catch(CampoNuloException | CampoEmBrancoException cne) {
//        } catch(CampoNuloException cne) {
//            ShowDialog("EX", cne.getMessage(), null,this.getStage());
//        }
        paciente.setNaturalidade(naturalidade.getEditor().getText());
        paciente.setNacionalidade(nacionalidade.getEditor().getText());
        paciente.setEstadocivil(estadocivil.getValue());
        paciente.setProfissao(profissao.getText());
        paciente.setRg(rg.getText());
        paciente.setCpf(cpf.getText());
        paciente.setEndereco(endereco.getText());
        paciente.setNumero(numero.getText());
        paciente.setComplemento(complemento.getText());
        paciente.setBairro(bairro.getText());
        paciente.setCep(cep.getText());
        paciente.setCidade(cidade.getText());
        paciente.setUf((String) uf.getValue());
        paciente.setTelcomercial(telcomercial.getText());
        paciente.setTelresidencial(telresidencial.getText());
        paciente.setTelcelular(telcelular.getText());
        paciente.setEmail(email.getText()); 
        
        paciente.setIndicacao(indicacao.getText());
        paciente.setStatus(status.getValue().toString());
//        paciente.setSitCadastro(sitCadastro.getValue().toString());
        
        paciente.setFotografia(bFotografia);             
        return resultado;
    }
        
    public void btnProcIdFired(ActionEvent event) throws Exception {
        sopPaciente.set(dao.getPacienteWithId(Integer.parseInt(id.getText())));
    }    
    
    public void btnProcCodAntFired(ActionEvent event) throws Exception {
        
        
//        Pacientes pacientes = new Pacientes();
/*        Integer codant = (codAntPaciente.getText().trim().isEmpty()?-1:Integer.parseInt(codAntPaciente.getText()));
        if (codant>0) {
            sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithCodAnt(codant)));
        }
*/
    }
    
    public void btnProcNomeFired(ActionEvent event) throws Exception {
        String pac = nome.getText();
        if (!pac.isEmpty()) {
            pac = "%" + pac.replace(" ", "%");            
//            sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithNome(nome)));
        }
    } 
    
    public void btnProcCepFired(ActionEvent event) {      
/*        if (!fmtCEP.getText().isEmpty()) {
            uf = UFs.getUFPeloCep(fmtCEP.getText());
            if (uf != null) {
                preencheCep(uf);
            }
        }
*/
    }
/*    
    private void preencheCep(UF uf) {
        ufPaciente.getSelectionModel().select(uf.getUf());
        cep = CEPs.getCEPPeloNome(fmtCEP.getText(), uf.getUf());
        if (cep!=null) preencheEndereco();
    }
*/
    
    private void preencheEndereco() {
/*        
        if (cep!=null) {
            enderPaciente.setText(cep.getTp_logradouro()+" "+cep.getLogradouro());
            bairroPaciente.setText(cep.getBairro());
            cidadePaciente.setText(cep.getCidade());
        }
*/
    }
    
    
    public void apagaPaciente() {
        this.id.clear();
        this.nome.clear();
        this.sexo.getSelectionModel().select(-1);
        this.etnia.getSelectionModel().select(-1);
        this.nascimento.setValue(null);
        this.idade.clear();
        this.naturalidade.getEditor().clear();
        this.nacionalidade.getEditor().clear();
        this.estadocivil.getSelectionModel().select(-1);
        this.profissao.clear();
        this.rg.clear();
        this.cpf.clear();
        this.endereco.clear();
        this.numero.clear();
        this.complemento.clear();
        this.bairro.clear();
        this.cidade.clear();
        this.cep.clear();
        this.uf.getSelectionModel().select(-1);
        this.telcomercial.clear();
        this.telresidencial.clear();
        this.telcelular.clear();
        this.email.clear();
        this.convenio.getSelectionModel().select(-1);
        this.matricula.clear();  
        this.indicacao.clear();
//        this.dataPrimConsulta.clear();
        this.status.getSelectionModel().select(-1);
        this.cadastro.getSelectionModel().select(-1);
        this.imageFotografia.setImage(null);
    }
    
    public void habilEdicaoFired() {
        this.id.setEditable(((oper==Oper.INSERTING)|(oper==Oper.UPDATING))|(oper==Oper.IDLE));  
        this.nome.setEditable(((oper==Oper.INSERTING)|(oper==Oper.UPDATING))|(oper==Oper.IDLE));
//        this.sexoPaciente.setDisable((oper!=Oper.INSERTING)&(oper!=Oper.UPDATING));
//        this.nascPaciente.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
//        this.nascPaciente.setDisable((oper!=Oper.INSERTING)&(oper!=Oper.UPDATING));
//        this.naturPaciente.setDisable((oper!=Oper.INSERTING)&(oper!=Oper.UPDATING));
//        this.nacionPaciente.setDisable((oper!=Oper.INSERTING)&(oper!=Oper.UPDATING));
//        this.estCivilPaciente.setDisable((oper!=Oper.INSERTING)&(oper!=Oper.UPDATING));
        this.profissao.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
        this.rg.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
        this.cpf.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
        this.endereco.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
        this.numero.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
        this.complemento.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
        this.bairro.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
        this.cidade.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
//        this.fmtCEP.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
//        this.ufPaciente.setDisable((oper!=Oper.INSERTING)&(oper!=Oper.UPDATING));
        this.telcomercial.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
        this.telresidencial.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
        this.telcelular.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
        this.email.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
        this.convenio.setDisable((oper!=Oper.INSERTING)&(oper!=Oper.UPDATING));
        this.matricula.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));              
        this.indicacao.setEditable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));              
        
    }
    
    private void setButtons() {
        btnCriar.setDisable((oper==Oper.INSERTING)|(oper==Oper.UPDATING));
        btnAtualizar.setDisable((oper==Oper.INSERTING)|(oper==Oper.UPDATING)|(oper!=Oper.SHOWING));
        btnDeletar.setDisable(oper!=Oper.SHOWING);
        btnConfirmar.setDisable((oper!=Oper.INSERTING)&(oper!=Oper.UPDATING));
        btnCancelar.setDisable((oper!=Oper.INSERTING)&(oper!=Oper.UPDATING));
        
//        btnFichaMedica.setDisable((oper!=Oper.SHOWING) | (!perfilUsuario.getTipoUsuario().equals("Medico"))) ;
//        btnAtestados.setDisable((oper!=Oper.SHOWING) | (!perfilUsuario.getTipoUsuario().equals("Medico")));
//        btnReceitas.setDisable((oper!=Oper.SHOWING) | (!perfilUsuario.getTipoUsuario().equals("Medico")));
//        btnRecibos.setDisable((oper!=Oper.SHOWING));        
        
        btnProcurar.setDisable(oper!=Oper.SHOWING);
        btnProcuraId.setDisable(oper!=Oper.IDLE);
        btnProcuraNome.setDisable(oper!=Oper.IDLE);
        btnSair.setDisable((oper!=Oper.IDLE)&(oper!=Oper.SHOWING));
        btnFotografar.setDisable((oper!=Oper.INSERTING)&(oper!=Oper.UPDATING));
    }
    
    private void setToolTips() {
        btnCriar.setTooltip(new Tooltip("Criar Paciente"));
        btnAtualizar.setTooltip(new Tooltip("Atualizar o Paciente selecionado"));        
        btnConfirmar.setTooltip(new Tooltip("Gravar Paciente"));
        btnDeletar.setTooltip(new Tooltip("Excluir Paciente selecionado"));
        btnCancelar.setTooltip(new Tooltip("Cancelar as alterações"));        
        btnFichaMedica.setTooltip(new Tooltip("Abrir a Ficha Médica do Paciente"));
        btnReceitas.setTooltip(new Tooltip("Emitir/Acesssar Receitas do Paciente"));
        btnRecibos.setTooltip(new Tooltip("Emitir/Acesssar Recibos do Paciente"));
        btnAtestados.setTooltip(new Tooltip("Emitir/Acesssar Atestados do Paciente"));
        btnProcuraId.setTooltip(new Tooltip("Procurar Paciente pelo codigo"));
        btnProcuraNome.setTooltip(new Tooltip("Procurar Paciente pelo nome"));
        btnRecibos.setTooltip(new Tooltip("Emitir/Acesssar Recibos do Paciente"));
        btnSair.setTooltip(new Tooltip("Fechar esta janela"));
    }
    
    
}