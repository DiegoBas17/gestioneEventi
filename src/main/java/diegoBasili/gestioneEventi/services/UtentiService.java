package diegoBasili.gestioneEventi.services;

import diegoBasili.gestioneEventi.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtentiService {
    @Autowired
    private UtentiRepository utentiRepository;


}
