package diegoBasili.gestioneEventi.controllers;

import diegoBasili.gestioneEventi.entities.Utente;
import diegoBasili.gestioneEventi.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("Utenti")
public class UtentiController {
    @Autowired
    private UtentiService utentiService;

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
}
