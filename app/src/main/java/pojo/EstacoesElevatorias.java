package pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Miqueias on 1/7/17.
 */

public class EstacoesElevatorias {

    private int id;
    private String descricao;
    private int regionalId;
    private ArrayList<ConjuntoMotorBomba> conjuntoMotorBombaArrayList;
    private String created;
    private String numero;
    private String bairro;
    private int status;
    private int cidadeId;
    private Regional regional;
    private String endereco;
    private int ufId;
    private ArrayList<ProblemasCheckList> arrayListCheckListNaoMarcadoUltimaVistoria;
    private ArrayList<ProblemaCMBMarcadoUltimaVistoria> arrayListProblemaCMBMarcadoUltimaVistoria;


    public EstacoesElevatorias() {

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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getUfId() {
        return ufId;
    }

    public void setUfId(int ufId) {
        this.ufId = ufId;
    }

    public ArrayList<ProblemasCheckList> getArrayListCheckListNaoMarcadoUltimaVistoria() {
        return arrayListCheckListNaoMarcadoUltimaVistoria;
    }

    public void setArrayListCheckListNaoMarcadoUltimaVistoria(ArrayList<ProblemasCheckList> arrayListCheckListNaoMarcadoUltimaVistoria) {
        this.arrayListCheckListNaoMarcadoUltimaVistoria = arrayListCheckListNaoMarcadoUltimaVistoria;
    }

    public ArrayList<ProblemaCMBMarcadoUltimaVistoria> getArrayListProblemaCMBMarcadoUltimaVistoria() {
        return arrayListProblemaCMBMarcadoUltimaVistoria;
    }

    public void setArrayListProblemaCMBMarcadoUltimaVistoria(ArrayList<ProblemaCMBMarcadoUltimaVistoria> arrayListProblemaCMBMarcadoUltimaVistoria) {
        this.arrayListProblemaCMBMarcadoUltimaVistoria = arrayListProblemaCMBMarcadoUltimaVistoria;
    }

}
