package hu.onlab.bevasarlolista.model;


import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ShopTermek {

    @EmbeddedId
    ShopTermekKey id;

    @ManyToOne
    @MapsId("termekId")
    private Termek termek;

    @ManyToOne
    @MapsId("shopId")
    private Shop shop;

    private double egysegar;

    public ShopTermek(){
        this.id = new ShopTermekKey();
    }
}
