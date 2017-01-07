package pojo;

import java.util.ArrayList;

/**
 * Created by Miqueias on 1/7/17.
 */

public class EstacoesElevatorias {

    private int id;
    private String descricao;
    private int regionalId;
    private Regional regional;
    private ArrayList<ConjuntoMotorBomba> conjuntoMotorBombaArrayList;


    EstacoesElevatorias() {

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
}
