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
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Builder.Default
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private Set<ShopTermek> offered_goods = new HashSet<>();

    private String name;
    private String address;
}
