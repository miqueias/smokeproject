package pojo;

import java.util.ArrayList;

/**
 * Created by Miqueias on 1/7/17.
 */

public class ConjuntoMotorBomba{

    private int id;
    private String numero;
    private int estacaoElevatoriaId;
    private ArrayList<Problemas> problemasArrayList;

    public ConjuntoMotorBomba() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getEstacaoElevatoriaId() {
        return estacaoElevatoriaId;
    }

    public void setEstacaoElevatoriaId(int estacaoElevatoriaId) {
        this.estacaoElevatoriaId = estacaoElevatoriaId;
    }

    public ArrayList<Problemas> getProblemasArrayList() {
        return problemasArrayList;
    }

    public void setProblemasArrayList(ArrayList<Problemas> problemasArrayList) {
        this.problemasArrayList = problemasArrayList;
    }
}
