package diegoBasili.gestioneEventi.repositories;

import diegoBasili.gestioneEventi.entities.Prenotazione;
import diegoBasili.gestioneEventi.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {
    List<Prenotazione> findAllByUtente(Utente utente);
}
