package br.com.simplesw.mvnsimple.modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "pacientes", schema = "simplesw")
public class Paciente implements Serializable {    
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;    
    private String nome;
    private String sexo;    
    @Temporal(TemporalType.DATE) 
    private Date nascimento;    
    private String naturalidade;
    private String nacionalidade;
    private String rg;
    private String cpf;
    private String estadocivil;
    private String etnia;
    private String profissao;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;
    private String pais;
    private String telresidencial;
    private String telcomercial;
    private String telcelular;
    private String email;    
    private String indicacao;
    private String status;
    private String cadastro;
    @Lob private byte[] fotografia;
    @Temporal(TemporalType.DATE)
    private Date datainclusao;
    @Temporal(TemporalType.DATE)
    private Date dataatualizacao;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the nascimento
     */
    public Date getNascimento() {
        return nascimento;
    }

    /**
     * @param nascimento the nascimento to set
     */
    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    /**
     * @return the naturalidade
     */
    public String getNaturalidade() {
        return naturalidade;
    }

    /**
     * @param naturalidade the naturalidade to set
     */
    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    /**
     * @return the nacionalidade
     */
    public String getNacionalidade() {
        return nacionalidade;
    }

    /**
     * @param nacionalidade the nacionalidade to set
     */
    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    /**
     * @return the rg
     */
    public String getRg() {
        return rg;
    }

    /**
     * @param rg the rg to set
     */
    public void setRg(String rg) {
        this.rg = rg;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the estadocivil
     */
    public String getEstadocivil() {
        return estadocivil;
    }

    /**
     * @param estadocivil the estadocivil to set
     */
    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    /**
     * @return the etnia
     */
    public String getEtnia() {
        return etnia;
    }

    /**
     * @param etnia the etnia to set
     */
    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    /**
     * @return the profissao
     */
    public String getProfissao() {
        return profissao;
    }

    /**
     * @param profissao the profissao to set
     */
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the complemento
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * @param complemento the complemento to set
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the uf
     */
    public String getUf() {
        return uf;
    }

    /**
     * @param uf the uf to set
     */
    public void setUf(String uf) {
        this.uf = uf;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the telresidencial
     */
    public String getTelresidencial() {
        return telresidencial;
    }

    /**
     * @param telresidencial the telresidencial to set
     */
    public void setTelresidencial(String telresidencial) {
        this.telresidencial = telresidencial;
    }

    /**
     * @return the telcomercial
     */
    public String getTelcomercial() {
        return telcomercial;
    }

    /**
     * @param telcomercial the telcomercial to set
     */
    public void setTelcomercial(String telcomercial) {
        this.telcomercial = telcomercial;
    }

    /**
     * @return the telcelular
     */
    public String getTelcelular() {
        return telcelular;
    }

    /**
     * @param telcelular the telcelular to set
     */
    public void setTelcelular(String telcelular) {
        this.telcelular = telcelular;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the indicacao
     */
    public String getIndicacao() {
        return indicacao;
    }

    /**
     * @param indicacao the indicacao to set
     */
    public void setIndicacao(String indicacao) {
        this.indicacao = indicacao;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the cadastro
     */
    public String getCadastro() {
        return cadastro;
    }

    /**
     * @param cadastro the cadastro to set
     */
    public void setCadastro(String cadastro) {
        this.cadastro = cadastro;
    }

    /**
     * @return the fotografia
     */
    public byte[] getFotografia() {
        return fotografia;
    }

    /**
     * @param fotografia the fotografia to set
     */
    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    /**
     * @return the datainclusao
     */
    public Date getDatainclusao() {
        return datainclusao;
    }

    /**
     * @param datainclusao the datainclusao to set
     */
    public void setDatainclusao(Date datainclusao) {
        this.datainclusao = datainclusao;
    }

    /**
     * @return the dataatualizacao
     */
    public Date getDataatualizacao() {
        return dataatualizacao;
    }

    /**
     * @param dataatualizacao the dataatualizacao to set
     */
    public void setDataatualizacao(Date dataatualizacao) {
        this.dataatualizacao = dataatualizacao;
    }


}
