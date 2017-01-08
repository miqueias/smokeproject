package pojo;

import java.util.ArrayList;

/**
 * Created by Miqueias on 1/7/17.
 */

public class Rota {

    private int id;
    private String descricao;
    private TipoRota tipoRota;
    private ArrayList<EstacoesElevatorias> estacoesElevatoriasArrayList;

    public Rota() {

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

    public TipoRota getTipoRota() {
        return tipoRota;
    }

    public void setTipoRota(TipoRota tipoRota) {
        this.tipoRota = tipoRota;
    }

    public ArrayList<EstacoesElevatorias> getEstacoesElevatoriasArrayList() {
        return estacoesElevatoriasArrayList;
    }

    public void setEstacoesElevatoriasArrayList(ArrayList<EstacoesElevatorias> estacoesElevatoriasArrayList) {
        this.estacoesElevatoriasArrayList = estacoesElevatoriasArrayList;
    }
}
