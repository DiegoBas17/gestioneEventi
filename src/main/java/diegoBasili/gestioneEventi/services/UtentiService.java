package diegoBasili.gestioneEventi.services;

import diegoBasili.gestioneEventi.entities.Utente;
import diegoBasili.gestioneEventi.enums.Ruolo;
import diegoBasili.gestioneEventi.exceptions.BadRequestException;
import diegoBasili.gestioneEventi.payloads.UtenteDTO;
import diegoBasili.gestioneEventi.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
