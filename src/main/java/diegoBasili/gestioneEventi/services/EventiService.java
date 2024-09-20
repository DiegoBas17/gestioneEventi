package diegoBasili.gestioneEventi.services;

import diegoBasili.gestioneEventi.repositories.EventiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventiService {
    @Autowired
    private EventiRepository eventiRepository;
}
