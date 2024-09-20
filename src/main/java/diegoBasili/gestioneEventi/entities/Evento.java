package diegoBasili.gestioneEventi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Evento {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID eventi_id;
    private String titolo;
    private String descrizione;
    private LocalDate dataEvento;
    private String luogo;
    private int numeroPostiDisponibili;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente organizzatore;

    @OneToMany(mappedBy = "evento")
    private List<Prenotazione> prenotazioni = new ArrayList<>();
}
