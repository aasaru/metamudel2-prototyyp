package ee.smit.metamudel2.repository;

import ee.smit.metamudel2.model.db.DbToiminguEksemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ToiminguEksemplarRepository extends JpaRepository<DbToiminguEksemplar, UUID> {


    List<DbToiminguEksemplar> findAllByOrderById();

}
