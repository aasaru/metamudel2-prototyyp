package ee.smit.metamudel2.model.business;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class Vastus {
    private final JsonNode toiming;
    private final Veateade[] veateated;

}
