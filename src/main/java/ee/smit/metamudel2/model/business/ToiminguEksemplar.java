package ee.smit.metamudel2.model.business;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "toimingu_eksemplar")
public class ToiminguEksemplar {

    protected ToiminguEksemplar() {
    }

    @Id
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


    public ToiminguEksemplar(Long id, String toiminguTyyp, String koostaja, LocalDateTime koostamiseAeg, String json) {
        this.id = id;
        this.toiminguTyyp = toiminguTyyp;
        this.koostaja = koostaja;
        this.koostamiseAeg = koostamiseAeg;
        this.json = json;
    }

    public Long getId() {
        return id;
    }

    public String getToiminguTyyp() {
        return toiminguTyyp;
    }

    public String getKoostaja() {
        return koostaja;
    }

    public LocalDateTime getKoostamiseAeg() {
        return koostamiseAeg;
    }

    public String getJson() {
        return json;
    }
}
