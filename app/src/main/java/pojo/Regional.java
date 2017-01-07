package pojo;

import java.util.Date;

/**
 * Created by Miqueias on 1/7/17.
 */

public class Regional {

    private int id;
    private String nome;
    private Date created;

    Regional() {

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
