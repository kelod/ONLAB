package hu.onlab.bevasarlolista.model;


import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TermekLista {

    @EmbeddedId
    TermekListaKey id;

    @ManyToOne
    @MapsId("termekId")
    private Termek termek;

    @ManyToOne
    @MapsId("ListaId")
    private Lista lista;

    private double quantity;

    private boolean is_bought;
}
