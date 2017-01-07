package java;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Miqueias on 1/7/17.
 */

public class Vistorias {

    private int id;
    private String descricao;
    private Date created;
    private String endereco;
    private String numero;
    private String bairro;
    private int status;
    private int cidadeId;
    private int regionalId;
    private Regional regional;
    private ArrayList<ConjuntoMotorBomba> conjuntoMotorBombaArrayList;

    Vistorias() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(int cidadeId) {
        this.cidadeId = cidadeId;
    }

    public int getRegionalId() {
        return regionalId;
    }

    public void setRegionalId(int regionalId) {
        this.regionalId = regionalId;
    }

    public Regional getRegional() {
        return regional;
    }

    public void setRegional(Regional regional) {
        this.regional = regional;
    }

    public ArrayList<ConjuntoMotorBomba> getConjuntoMotorBombaArrayList() {
        return conjuntoMotorBombaArrayList;
    }

    public void setConjuntoMotorBombaArrayList(ArrayList<ConjuntoMotorBomba> conjuntoMotorBombaArrayList) {
        this.conjuntoMotorBombaArrayList = conjuntoMotorBombaArrayList;
    }
}
