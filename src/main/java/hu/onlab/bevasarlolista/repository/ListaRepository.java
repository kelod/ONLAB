package hu.onlab.bevasarlolista.repository;

import hu.onlab.bevasarlolista.model.Lista;
import hu.onlab.bevasarlolista.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ListaRepository extends JpaRepository<Lista,Integer> {

    @Query("select u from Lista u where u.name = ?1")
    Lista findByNameIs(String name);
}
