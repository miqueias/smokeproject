package pojo;

/**
 * Created by Miqueias on 1/7/17.
 */

public class ProblemasCheckList {

    private int id;
    private String descricao;
    private boolean checked;

    public ProblemasCheckList() {

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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
