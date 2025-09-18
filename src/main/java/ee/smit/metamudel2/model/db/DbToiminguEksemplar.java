package ee.smit.metamudel2.model.db;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "menetluse_baaskomponent", name = "toimingueksemplar")
public class DbToiminguEksemplar {

    @Id
    @Setter
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name="toimingueksemplar_id")
    private UUID id;
    @Column(name="toimingutyyp_valjalase", nullable=false)
    private String toimingutyypValjalase;
    @Column(name="koostaja", nullable=false)
    private String koostaja;
    @Column(name="koostamise_aeg", nullable=false)
    private LocalDateTime koostamiseAeg;
    @Column(name="json")
    private String json;


}
