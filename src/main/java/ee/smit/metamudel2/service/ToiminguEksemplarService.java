package ee.smit.metamudel2.service;

import ee.smit.metamudel2.model.db.DbToiminguEksemplar;
import ee.smit.metamudel2.repository.ToiminguEksemplarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToiminguEksemplarService {

    private final ToiminguEksemplarRepository repository;

    public DbToiminguEksemplar save(DbToiminguEksemplar dbToiminguEksemplar) {
        return repository.save(dbToiminguEksemplar);
    }

}
