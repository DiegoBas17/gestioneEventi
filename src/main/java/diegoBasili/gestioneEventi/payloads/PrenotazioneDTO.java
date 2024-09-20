package diegoBasili.gestioneEventi.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PrenotazioneDTO (@NotEmpty(message = "l'id dell'utente è obbligatorio!")
                               @Size(min = 3, max = 40)
                               String utenteId,
                               @NotEmpty(message = "l'id dell'evento è obbligatorio!")
                               @Size(min = 3, max = 40)
                               String eventoId) {
}
