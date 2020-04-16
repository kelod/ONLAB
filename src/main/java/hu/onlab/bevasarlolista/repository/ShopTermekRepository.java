package hu.onlab.bevasarlolista.repository;

import hu.onlab.bevasarlolista.model.ShopTermek;
import hu.onlab.bevasarlolista.model.ShopTermekKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ShopTermekRepository extends JpaRepository<ShopTermek, ShopTermekKey> {

    ArrayList<ShopTermek> findByTermekId(Integer productId);

}
