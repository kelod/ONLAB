package hu.onlab.bevasarlolista.repository;

import hu.onlab.bevasarlolista.model.TermekLista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermekListaRepository extends JpaRepository<TermekLista,Integer> {
}
