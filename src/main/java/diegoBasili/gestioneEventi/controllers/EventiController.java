package diegoBasili.gestioneEventi.controllers;

import diegoBasili.gestioneEventi.entities.Evento;
import diegoBasili.gestioneEventi.entities.Utente;
import diegoBasili.gestioneEventi.payloads.EventoDTO;
import diegoBasili.gestioneEventi.services.EventiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/eventi")
public class EventiController {
    @Autowired
    private EventiService eventiService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Evento createEvento(@RequestBody @Validated EventoDTO body) {
        return eventiService.saveEvento(body);
    }

    @PutMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Evento updateEvento(@PathVariable UUID eventoId, @RequestBody @Validated EventoDTO body) {
        return eventiService.updateEvento(eventoId, body);
    }

    @DeleteMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(@PathVariable UUID eventoId) {
        eventiService.deleteEvento(eventoId);
    }

    @GetMapping
    public Page<Evento> findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy){
        return this.eventiService.findAll(page, size, sortBy);
    }
}
