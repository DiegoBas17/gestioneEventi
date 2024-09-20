package diegoBasili.gestioneEventi.controllers;

import diegoBasili.gestioneEventi.entities.Prenotazione;
import diegoBasili.gestioneEventi.exceptions.BadRequestException;
import diegoBasili.gestioneEventi.payloads.PrenotazioneDTO;
import diegoBasili.gestioneEventi.payloads.PrenotazioneRespDTO;
import diegoBasili.gestioneEventi.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
    @Autowired
    private PrenotazioniService prenotazioniService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'ADMIN')")
    public PrenotazioneRespDTO save(@RequestBody @Validated PrenotazioneDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            return new PrenotazioneRespDTO(this.prenotazioniService.savePrenotazione(body).getPrenotazione_id().toString());
        }
    }

    @GetMapping("/{prenotazioneId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Prenotazione findById(@PathVariable UUID prenotazioneId){
        return this.prenotazioniService.findById(prenotazioneId);
    }

    @PutMapping("/{prenotazioneId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Prenotazione findByIdAndUpdate(@PathVariable UUID prenotazioneId, @RequestBody @Validated PrenotazioneDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            return this.prenotazioniService.findByIdAndUpdate(prenotazioneId, body);
        }
    }

    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID prenotazioneId){
        this.prenotazioniService.findByIdAndDelete(prenotazioneId);
    }
}
