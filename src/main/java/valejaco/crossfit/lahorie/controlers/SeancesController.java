package valejaco.crossfit.lahorie.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import valejaco.crossfit.lahorie.dao.SeancesRepository;
import valejaco.crossfit.lahorie.dao.UsersRepository;
import valejaco.crossfit.lahorie.models.Seance;

import java.util.Optional;

@RestController
public class SeancesController {
    @Autowired
    private SeancesRepository seancesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping("/seances")
    public ResponseEntity<?> getSeancesList() {
        return ResponseEntity.ok(seancesRepository.findAll());
    }

    @RequestMapping("/seances/add")
    public ResponseEntity<?> addSeancesList() {
        Optional<Seance> seance = seancesRepository.findById(1L);
        if( seance.isPresent() ) {
            seance.get().addUser( usersRepository.getById(2L) );
            seancesRepository.save(seance.get());
        }
        return ResponseEntity.ok(seancesRepository.findAll());
    }

}
