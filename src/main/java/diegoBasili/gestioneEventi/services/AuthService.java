package diegoBasili.gestioneEventi.services;

import diegoBasili.gestioneEventi.entities.Utente;
import diegoBasili.gestioneEventi.exceptions.UnauthorizedException;
import diegoBasili.gestioneEventi.payloads.UtenteLoginDTO;
import diegoBasili.gestioneEventi.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(UtenteLoginDTO body) {
        Utente found = this.utentiService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
