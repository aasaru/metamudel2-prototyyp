package ee.smit.metamudel2.model.business;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "toimingu_eksemplar")
public class ToiminguEksemplar {

    protected ToiminguEksemplar() {
    }

    @Id
    @Setter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name="toimingu_tyyp", nullable=false)
    private String toiminguTyyp;
    @Column(name="koostaja", nullable=false)
    private String koostaja;
    @Column(name="koostamise_aeg", nullable=false)
    private LocalDateTime koostamiseAeg;
    @Column(name="json")
    private String json;


    public ToiminguEksemplar(String toiminguTyyp, String koostaja, LocalDateTime koostamiseAeg, String json) {
        this.toiminguTyyp = toiminguTyyp;
        this.koostaja = koostaja;
        this.koostamiseAeg = koostamiseAeg;
        this.json = json;
    }

}
