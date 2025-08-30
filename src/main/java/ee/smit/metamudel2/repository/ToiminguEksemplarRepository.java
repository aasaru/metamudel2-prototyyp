package ee.smit.metamudel2.repository;

import ee.smit.metamudel2.model.business.Product;
import ee.smit.metamudel2.model.business.ToiminguEksemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToiminguEksemplarRepository extends JpaRepository<ToiminguEksemplar, Long> {


}
