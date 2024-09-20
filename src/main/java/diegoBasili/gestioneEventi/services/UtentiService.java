package diegoBasili.gestioneEventi.services;

import diegoBasili.gestioneEventi.entities.Utente;
import diegoBasili.gestioneEventi.enums.Ruolo;
import diegoBasili.gestioneEventi.exceptions.BadRequestException;
import diegoBasili.gestioneEventi.exceptions.NotFoundException;
import diegoBasili.gestioneEventi.payloads.UtenteDTO;
import diegoBasili.gestioneEventi.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtentiService {
    @Autowired
    private UtentiRepository utentiRepository;

    public Utente saveUtente(UtenteDTO body) {
        if (body == null) {
            throw new BadRequestException("L'email deve avere un body!");
        }else if (this.utentiRepository.existsByEmail(body.email())) {
            throw new BadRequestException("L'email " + body.email() + " è già in uso!");
        } else {
            Ruolo ruolo;
            try {
                ruolo = Ruolo.valueOf(body.ruolo().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Ruolo utente non valido: " + body.ruolo() + " i ruoli disponibili sono: NORMALE o ORGANIZZATORE!");
            }
            Utente dipendente = new Utente(body.email(), body.password(), ruolo);
            return this.utentiRepository.save(dipendente);
        }
    }

    public Utente findById(UUID utenteId) {
        return this.utentiRepository.findById(utenteId).orElseThrow(()-> new NotFoundException(utenteId));
    }

    public Utente findByEmail(String email) {
        return utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con l'email " + email + " non è stato trovato!"));
    }

    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (page>20) page=20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utentiRepository.findAll(pageable);
    }

    public void findByIdAndDelete(UUID dipendenteId) {
        Utente found = findById(dipendenteId);
        this.utentiRepository.delete(found);
    }
}
