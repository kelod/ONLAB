package hu.onlab.bevasarlolista.repository;

import hu.onlab.bevasarlolista.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop,Integer> {
}
