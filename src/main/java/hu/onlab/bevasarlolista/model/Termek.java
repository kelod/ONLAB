package hu.onlab.bevasarlolista.model;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "termek")
public class Termek {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Builder.Default
    @OneToMany(mappedBy = "termek", cascade = CascadeType.ALL)
    private Set<TermekLista> participated_lists = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "termek", cascade = CascadeType.ALL)
    private Set<ShopTermek> present_in_shops = new HashSet<>();

    @Column(unique = true)
    private String name;
    private String mertekegyseg;

    public void addParticipatedList(TermekLista termekLista){
        if(participated_lists == null){
            participated_lists = new HashSet<>();
        }
        participated_lists.add(termekLista);
    }
}
