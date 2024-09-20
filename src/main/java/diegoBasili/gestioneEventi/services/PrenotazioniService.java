package diegoBasili.gestioneEventi.services;

import diegoBasili.gestioneEventi.entities.Evento;
import diegoBasili.gestioneEventi.entities.Prenotazione;
import diegoBasili.gestioneEventi.entities.Utente;
import diegoBasili.gestioneEventi.exceptions.BadRequestException;
import diegoBasili.gestioneEventi.exceptions.NotFoundException;
import diegoBasili.gestioneEventi.payloads.PrenotazioneDTO;
import diegoBasili.gestioneEventi.repositories.EventiRepository;
import diegoBasili.gestioneEventi.repositories.PrenotazioniRepository;
import diegoBasili.gestioneEventi.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioniService {
    @Autowired
    private PrenotazioniRepository prenotazioniRepository;
    @Autowired
    private UtentiRepository utentiRepository;
    @Autowired
    private EventiRepository eventiRepository;

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        if (page>20) page=20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioniRepository.findAll(pageable);
    }

    public Prenotazione savePrenotazione(PrenotazioneDTO body) {
        UUID utenteId;
        UUID eventoId;
        try {
            utenteId = UUID.fromString(body.utenteId());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("ID dipendente non valido: " + body.utenteId() + ". Deve essere un UUID valido.");
        }
        try {
            eventoId = UUID.fromString(body.eventoId());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("ID viaggio non valido: " + body.eventoId() + ". Deve essere un UUID valido.");
        }
        Utente utente = utentiRepository.findById(utenteId).orElseThrow(()-> new NotFoundException(utenteId));
        Evento evento = eventiRepository.findById(eventoId).orElseThrow(()-> new NotFoundException(eventoId));
        if (evento.getNumeroPostiDisponibili() <= 0) {
            throw new BadRequestException("Nessun posto disponibile per l'evento: " + evento.getTitolo());
        }
        evento.setNumeroPostiDisponibili(evento.getNumeroPostiDisponibili() - 1);
        eventiRepository.save(evento);
        Prenotazione prenotazione = new Prenotazione(utente, evento);
        return this.prenotazioniRepository.save(prenotazione);
    }

    public Prenotazione findById(UUID prenotazioneId) {
        return this.prenotazioniRepository.findById(prenotazioneId).orElseThrow(()-> new NotFoundException(prenotazioneId));
    }

    public void findByIdAndDelete(UUID prenotazioneId) {
        Prenotazione found = findById(prenotazioneId);
        this.prenotazioniRepository.delete(found);
    }

    public Prenotazione findByIdAndUpdate(UUID prenotazioneId, PrenotazioneDTO updateBody) {
        UUID utenteId;
        UUID eventoId;
        try {
            utenteId = UUID.fromString(updateBody.utenteId());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("ID dipendente non valido: " + updateBody.utenteId() + ". Deve essere un UUID valido.");
        }
        try {
            eventoId = UUID.fromString(updateBody.eventoId());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("ID viaggio non valido: " + updateBody.eventoId() + ". Deve essere un UUID valido.");
        }
        Utente utente = utentiRepository.findById(utenteId).orElseThrow(()-> new NotFoundException(utenteId));
        Evento evento = eventiRepository.findById(eventoId).orElseThrow(()-> new NotFoundException(eventoId));
        Prenotazione found = findById(prenotazioneId);
        found.setEvento(evento);
        found.setUtente(utente);
        return found;
    }

    public List<Prenotazione> findAllByUtente(Utente utente) {
        return prenotazioniRepository.findAllByUtente(utente);
    }
}
