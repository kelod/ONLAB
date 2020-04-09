package hu.onlab.bevasarlolista.model;


import lombok.*;

import javax.persistence.*;

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
    @JoinColumn(name="termek_id")
    private Termek termek;

    @ManyToOne
    @MapsId("listaId")
    @JoinColumn(name = "lista_id")
    private Lista lista;

    private double quantity;

    private boolean is_bought;

    public TermekLista(Termek termek, Lista lista){
        this.termek = termek;
        this.lista = lista;
        this.id = new TermekListaKey();
    }
}
