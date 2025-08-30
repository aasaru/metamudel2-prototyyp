package ee.smit.metamudel2.service.business;

import ee.smit.metamudel2.model.business.ToiminguEksemplar;
import ee.smit.metamudel2.repository.ToiminguEksemplarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToiminguEksemplarService {

    private final ToiminguEksemplarRepository repository;

    public ToiminguEksemplar save(ToiminguEksemplar toiminguEksemplar) {
        return repository.save(toiminguEksemplar);
    }

}
