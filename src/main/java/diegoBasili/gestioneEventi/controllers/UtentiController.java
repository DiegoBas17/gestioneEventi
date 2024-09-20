package diegoBasili.gestioneEventi.controllers;

import diegoBasili.gestioneEventi.entities.Evento;
import diegoBasili.gestioneEventi.entities.Utente;
import diegoBasili.gestioneEventi.exceptions.NotFoundException;
import diegoBasili.gestioneEventi.payloads.EventoDTO;
import diegoBasili.gestioneEventi.repositories.EventiRepository;
import diegoBasili.gestioneEventi.services.EventiService;
import diegoBasili.gestioneEventi.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("Utenti")
public class UtentiController {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private EventiService eventiService;

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        this.utentiService.findByIdAndDelete(currentAuthenticatedUser.getUtente_id());
    }

    @GetMapping
    //@PreAuthorize("hasAuthority('ADMIN')") // Solo gli admin possono leggere l'elenco degli utenti
    public Page<Utente> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy){
        return this.utentiService.findAll(page, size, sortBy);
    }

    @GetMapping("/{dipendenteId}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Utente findById(@PathVariable UUID dipendenteId){
        return this.utentiService.findById(dipendenteId);
    }

    @PutMapping("/me/eventi/{eventoId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento updateEvento(@PathVariable UUID eventoId, @RequestBody @Validated EventoDTO body, @AuthenticationPrincipal Utente utente) {
        List<Evento> listaEventi = eventiService.findByOrganizzatore(utente);
        Evento evento = listaEventi.stream().filter(evento1 -> evento1.getEventi_id().equals(eventoId)).findFirst().orElseThrow(() -> new NotFoundException(eventoId));
        return eventiService.updateEvento(evento.getEventi_id(), body);
    }

    @DeleteMapping("/me/{eventoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(@PathVariable UUID eventoId, @AuthenticationPrincipal Utente utente) {
        List<Evento> listaEventi = eventiService.findByOrganizzatore(utente);
        Evento evento = listaEventi.stream().filter(evento1 -> evento1.getEventi_id().equals(eventoId)).findFirst().orElseThrow(() -> new NotFoundException(eventoId));
        eventiService.deleteEvento(evento.getEventi_id());
    }
}
