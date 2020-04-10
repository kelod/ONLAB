package hu.onlab.bevasarlolista.repository;

import hu.onlab.bevasarlolista.model.TermekLista;
import hu.onlab.bevasarlolista.model.TermekListaKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermekListaRepository extends JpaRepository<TermekLista, TermekListaKey> {
    TermekLista findByTermekIdAndListaId(Integer termekId, Integer listaId);
}
