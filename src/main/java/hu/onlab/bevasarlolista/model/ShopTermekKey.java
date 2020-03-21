package hu.onlab.bevasarlolista.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ShopTermekKey implements Serializable {

    @Column(name = "termek_id")
    private Integer termekId;

    @Column(name = "shop_id")
    private Integer shopId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ShopTermekKey that = (ShopTermekKey) o;
        return Objects.equals(termekId, that.termekId) &&
                Objects.equals(shopId, that.shopId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(termekId, shopId);
    }

}
