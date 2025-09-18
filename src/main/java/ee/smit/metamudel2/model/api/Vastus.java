package ee.smit.metamudel2.model.api;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class Vastus {
    private final ApiBaasToiminguEksemplar toiming;
    private final Veateade[] veateated;

}
