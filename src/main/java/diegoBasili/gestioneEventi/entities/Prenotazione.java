package diegoBasili.gestioneEventi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Prenotazione {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID prenotazione_id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;
}
