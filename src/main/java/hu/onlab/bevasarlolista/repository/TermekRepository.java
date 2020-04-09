package hu.onlab.bevasarlolista.repository;

import hu.onlab.bevasarlolista.model.Termek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermekRepository extends JpaRepository<Termek,Integer> {

    Termek findByName(String name);

}
