package hu.onlab.bevasarlolista.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

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

}
