package hu.onlab.bevasarlolista.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class TermekListaKey implements Serializable {

    @Column(name = "termek_id")
    private Integer termekId;

    @Column(name = "lista_id")
    private Integer listaId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        TermekListaKey that = (TermekListaKey) o;
        return Objects.equals(termekId, that.termekId) &&
                Objects.equals(listaId, that.listaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(termekId, listaId);
    }

    public  TermekListaKey(){}

    public TermekListaKey(Integer termekId, Integer listaId){
        this.termekId = termekId;
        this.listaId = listaId;
    }

}
