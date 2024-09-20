package diegoBasili.gestioneEventi.repositories;

import diegoBasili.gestioneEventi.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface EventiRepository extends JpaRepository<Evento, UUID> {
}
