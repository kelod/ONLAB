package hu.onlab.bevasarlolista.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lista")
public class Lista {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User creatorUser;

    @ManyToMany(mappedBy = "participated_lists")
    private Set<User> participating_users;

    @Builder.Default
    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL)
    private Set<TermekLista> termekek = new HashSet<>();

    private Date creation_time;
    private String name;
    private boolean actual;
    private double vegosszeg;
}
