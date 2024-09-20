package diegoBasili.gestioneEventi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public Evento(String titolo, String descrizione, LocalDate dataEvento, String luogo, int numeroPostiDisponibili, Utente organizzatore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataEvento = dataEvento;
        this.luogo = luogo;
        this.numeroPostiDisponibili = numeroPostiDisponibili;
        this.organizzatore = organizzatore;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente organizzatore;

    @JsonIgnore
    @OneToMany(mappedBy = "evento")
    private List<Prenotazione> prenotazioni = new ArrayList<>();
}
