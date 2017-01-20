package pojo;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Miqueias on 1/8/17.
 */

public class Vistoria {

    private int id;
    private String leituraCelpe;
    private String leituraCompesa;
    private int cmbsEncontradas;
    private String descricaoProblemas;
    private int estacaoElevatoriaId;
    private int operadorId;
    private EstacoesElevatorias estacoesElevatorias;
    private int Status;
    private ArrayList<ConjuntoMotorBomba> conjuntoMotorBombaArrayList;
    private String descricao;
    private String created;
    private String endereco;
    private String numero;
    private String bairro;
    private int status;
    private int cidadeId;
    private int regionalId;
    private Regional regional;
    private int situacaoProblema;
    private int osRealizada;
    private String numeroOs;
    private ArrayList<ProblemasCheckList> problemasCheckListArrayList;

    public Vistoria() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeituraCelpe() {
        return leituraCelpe;
    }

    public void setLeituraCelpe(String leituraCelpe) {
        this.leituraCelpe = leituraCelpe;
    }

    public String getLeituraCompesa() {
        return leituraCompesa;
    }

    public void setLeituraCompesa(String leituraCompesa) {
        this.leituraCompesa = leituraCompesa;
    }

    public int getCmbsEncontradas() {
        return cmbsEncontradas;
    }

    public void setCmbsEncontradas(int cmbsEncontradas) {
        this.cmbsEncontradas = cmbsEncontradas;
    }

    public String getDescricaoProblemas() {
        return descricaoProblemas;
    }

    public void setDescricaoProblemas(String descricaoProblemas) {
        this.descricaoProblemas = descricaoProblemas;
    }

    public int getEstacaoElevatoriaId() {
        return estacaoElevatoriaId;
    }

    public void setEstacaoElevatoriaId(int estacaoElevatoriaId) {
        this.estacaoElevatoriaId = estacaoElevatoriaId;
    }

    public int getOperadorId() {
        return operadorId;
    }

    public void setOperadorId(int operadorId) {
        this.operadorId = operadorId;
    }

    public EstacoesElevatorias getEstacoesElevatorias() {
        return estacoesElevatorias;
    }

    public void setEstacoesElevatorias(EstacoesElevatorias estacoesElevatorias) {
        this.estacoesElevatorias = estacoesElevatorias;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public ArrayList<ConjuntoMotorBomba> getConjuntoMotorBombaArrayList() {
        return conjuntoMotorBombaArrayList;
    }

    public void setConjuntoMotorBombaArrayList(ArrayList<ConjuntoMotorBomba> conjuntoMotorBombaArrayList) {
        this.conjuntoMotorBombaArrayList = conjuntoMotorBombaArrayList;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
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

    public int getSituacaoProblema() {
        return situacaoProblema;
    }

    public void setSituacaoProblema(int situacaoProblema) {
        this.situacaoProblema = situacaoProblema;
    }

    public int getOsRealizada() {
        return osRealizada;
    }

    public void setOsRealizada(int osRealizada) {
        this.osRealizada = osRealizada;
    }

    public String getNumeroOs() {
        return numeroOs;
    }

    public void setNumeroOs(String numeroOs) {
        this.numeroOs = numeroOs;
    }

    public ArrayList<ProblemasCheckList> getProblemasCheckListArrayList() {
        return problemasCheckListArrayList;
    }

    public void setProblemasCheckListArrayList(ArrayList<ProblemasCheckList> problemasCheckListArrayList) {
        this.problemasCheckListArrayList = problemasCheckListArrayList;
    }
}
