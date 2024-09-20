package diegoBasili.gestioneEventi.controllers;

import diegoBasili.gestioneEventi.exceptions.BadRequestException;
import diegoBasili.gestioneEventi.payloads.UtenteDTO;
import diegoBasili.gestioneEventi.payloads.UtenteLoginDTO;
import diegoBasili.gestioneEventi.payloads.UtenteLoginRespDTO;
import diegoBasili.gestioneEventi.payloads.UtenteRespDTO;
import diegoBasili.gestioneEventi.services.AuthService;
import diegoBasili.gestioneEventi.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteRespDTO save(@RequestBody @Validated UtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
           return new UtenteRespDTO(this.utentiService.saveUtente(body).getUtente_id());
        }

    }

    @PostMapping("/login")
    public UtenteLoginRespDTO login(@RequestBody UtenteLoginDTO payload) {
        return new UtenteLoginRespDTO(this.authService.checkCredentialsAndGenerateToken(payload));
    }
}
