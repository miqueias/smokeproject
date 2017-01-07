package pojo;

import java.util.ArrayList;

/**
 * Created by Miqueias on 1/7/17.
 */

public class Auth {

    private Auth instance;
    private String status;
    private String mensagem;
    private String token;
    private Lider lider;
    private ArrayList<Motivo> motivoArrayList;
    private Cargo cargo;
    private Escala escala;
    private Rota rota;
    private ArrayList<Problemas> problemasArrayList;
    private ArrayList<ProblemasCheckList> problemasCheckListArrayList;
    private ArrayList<Vistorias> vistoriasArrayList;

    private Auth() {

    }

    public synchronized Auth getInstance() {
        if (instance == null) {
            instance = new Auth();
        }

        return instance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Lider getLider() {
        return lider;
    }

    public void setLider(Lider lider) {
        this.lider = lider;
    }

    public ArrayList<Motivo> getMotivoArrayList() {
        return motivoArrayList;
    }

    public void setMotivoArrayList(ArrayList<Motivo> motivoArrayList) {
        this.motivoArrayList = motivoArrayList;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Escala getEscala() {
        return escala;
    }

    public void setEscala(Escala escala) {
        this.escala = escala;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    public ArrayList<Problemas> getProblemasArrayList() {
        return problemasArrayList;
    }

    public void setProblemasArrayList(ArrayList<Problemas> problemasArrayList) {
        this.problemasArrayList = problemasArrayList;
    }

    public ArrayList<ProblemasCheckList> getProblemasCheckListArrayList() {
        return problemasCheckListArrayList;
    }

    public void setProblemasCheckListArrayList(ArrayList<ProblemasCheckList> problemasCheckListArrayList) {
        this.problemasCheckListArrayList = problemasCheckListArrayList;
    }

    public ArrayList<Vistorias> getVistoriasArrayList() {
        return vistoriasArrayList;
    }

    public void setVistoriasArrayList(ArrayList<Vistorias> vistoriasArrayList) {
        this.vistoriasArrayList = vistoriasArrayList;
    }
}
