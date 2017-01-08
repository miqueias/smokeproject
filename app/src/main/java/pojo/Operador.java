package pojo;

/**
 * Created by Miqueias on 1/7/17.
 */

public class Operador {

    private int id;
    private String nome;
    private String login;
    private String cpf;
    private String matricula;
    private int usuarioId;
    private int cargoId;
    private int escalaId;
    private int rotaId;
    private String validaPlaca;

    public Operador() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getCargoId() {
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public int getEscalaId() {
        return escalaId;
    }

    public void setEscalaId(int escalaId) {
        this.escalaId = escalaId;
    }

    public int getRotaId() {
        return rotaId;
    }

    public void setRotaId(int rotaId) {
        this.rotaId = rotaId;
    }

    public String getValidaPlaca() {
        return validaPlaca;
    }

    public void setValidaPlaca(String validaPlaca) {
        this.validaPlaca = validaPlaca;
    }
}
