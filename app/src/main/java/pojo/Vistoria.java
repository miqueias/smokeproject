package pojo;

/**
 * Created by Miqueias on 1/8/17.
 */

public class Vistoria {

    private int id;
    private String leituraCelpe;
    private String leituraCompesa;
    private int cmbsEncontradas;
    private String descricaoProblemas;
    private String created;
    private int estacaoElevatoriaId;
    private int operadorId;
    private EstacoesElevatorias estacoesElevatorias;

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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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
}
