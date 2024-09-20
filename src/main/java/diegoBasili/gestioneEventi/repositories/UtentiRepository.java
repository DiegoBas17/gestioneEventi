package diegoBasili.gestioneEventi.repositories;

import diegoBasili.gestioneEventi.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UtentiRepository extends JpaRepository<Utente, UUID> {
    boolean existsByEmail(String email);
}
