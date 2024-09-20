package diegoBasili.gestioneEventi.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EventoDTO (@NotEmpty(message = "il titolo è obbligatorio!")
                         @Size(min = 3, max = 40)
                         String titolo,
                         @NotEmpty(message = "la descrizione è obbligatorio!")
                         @Size(min = 3, max = 40)
                         String descrizione,
                         @NotEmpty(message = "la data dell'evento è obbligatorio!")
                         @Size(min = 3, max = 40)
                         String dataEvento,
                         @NotEmpty(message = "il luogo è obbligatorio!")
                         @Size(min = 3, max = 40)
                         String luogo,
                         @NotNull(message = "il numero dei posti disponibile è obbligatorio")
                         @Positive
                         int numeroPostiDisponibili,
                         @NotEmpty(message = "l'id dell'organizzatore è obbligatorio!")
                         @Size(min = 3, max = 40)
                         String organizzatore) {
}
