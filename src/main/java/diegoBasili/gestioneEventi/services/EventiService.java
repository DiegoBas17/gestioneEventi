package diegoBasili.gestioneEventi.services;

import diegoBasili.gestioneEventi.entities.Evento;
import diegoBasili.gestioneEventi.entities.Utente;
import diegoBasili.gestioneEventi.enums.Ruolo;
import diegoBasili.gestioneEventi.exceptions.BadRequestException;
import diegoBasili.gestioneEventi.exceptions.NotFoundException;
import diegoBasili.gestioneEventi.payloads.EventoDTO;
import diegoBasili.gestioneEventi.repositories.EventiRepository;
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
public class EventiService {
    @Autowired
    private EventiRepository eventiRepository;
    @Autowired
    private UtentiService utentiService;

    public Evento saveEvento(EventoDTO body) {
        UUID utenteId;
        try {
            utenteId = UUID.fromString(body.organizzatore());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("ID dipendente non valido: " + body.organizzatore() + ". Deve essere un UUID valido.");
        }
        Utente utente = utentiService.findById(utenteId);
        if (utente.getRuolo() != Ruolo.ORGANIZZATORE && utente.getRuolo() != Ruolo.ADMIN) {
            throw new BadRequestException("Solo gli organizzatori o gli admin possono creare eventi");
        }
        LocalDate dataEvento;
        try {
            dataEvento = LocalDate.parse(body.dataEvento());
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Formato della data non valido: " + body.dataEvento() + ", il formato deve essere yyyy-mm-dd!");
        }
        Evento evento =  new Evento(body.titolo(), body.descrizione(), dataEvento, body.luogo(), body.numeroPostiDisponibili(), utente);
        return this.eventiRepository.save(evento);
    }

    public Evento updateEvento(UUID eventoId, EventoDTO body) {
        UUID utenteId;
        try {
            utenteId = UUID.fromString(body.organizzatore());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("ID dipendente non valido: " + body.organizzatore() + ". Deve essere un UUID valido.");
        }
        Utente organizzatore = utentiService.findById(utenteId);
        LocalDate dataEvento;
        try {
            dataEvento = LocalDate.parse(body.dataEvento());
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Formato della data non valido: " + body.dataEvento() + ", il formato deve essere yyyy-mm-dd!");
        }
        Evento evento = eventiRepository.findById(eventoId).orElseThrow(() -> new NotFoundException("Evento non trovato."));
        evento.setTitolo(body.titolo());
        evento.setDescrizione(body.descrizione());
        evento.setDataEvento(dataEvento);
        evento.setLuogo(body.luogo());
        evento.setNumeroPostiDisponibili(body.numeroPostiDisponibili());
        evento.setOrganizzatore(organizzatore);
        return eventiRepository.save(evento);
    }

    public void deleteEvento(UUID eventoId) {
        Evento evento = eventiRepository.findById(eventoId).orElseThrow(() -> new NotFoundException("Evento non trovato."));
        eventiRepository.delete(evento);
    }

    public Page<Evento> findAll(int page, int size, String sortBy) {
        if (page>20) page=20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventiRepository.findAll(pageable);
    }

    public List<Evento> findByOrganizzatore (Utente organizzatore) {
        return eventiRepository.findByOrganizzatore(organizzatore);
    }
}
