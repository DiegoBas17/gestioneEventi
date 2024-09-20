package diegoBasili.gestioneEventi.repositories;

import diegoBasili.gestioneEventi.entities.Evento;
import diegoBasili.gestioneEventi.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface EventiRepository extends JpaRepository<Evento, UUID> {
    List<Evento> findByOrganizzatore(Utente organizzatore);
}

