package diegoBasili.gestioneEventi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import diegoBasili.gestioneEventi.enums.Ruolo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Utente {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID utente_id;
    private String email;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    @JsonIgnore
    @OneToMany(mappedBy = "utente")
    private List<Prenotazione> prenotazioni = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "organizzatore")
    private  List<Evento> eventi = new ArrayList<>();
}
