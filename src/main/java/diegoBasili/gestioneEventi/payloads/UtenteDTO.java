package diegoBasili.gestioneEventi.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UtenteDTO (@NotEmpty(message = "L'email è obbligatoria")
                         @Email(message = "email non valida!")
                         String email,
                         @NotEmpty(message = "la password è obbligatoria!")
                         @Size(min = 3, max = 40)
                         String password,
                         @NotEmpty(message = "il ruolo è obbligatorio!")
                         @Size(min = 3, max = 10)
                         String ruolo)  {
}
