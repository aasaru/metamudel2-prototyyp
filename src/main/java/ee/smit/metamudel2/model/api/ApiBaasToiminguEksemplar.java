package ee.smit.metamudel2.model.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class ApiBaasToiminguEksemplar {
    
    private ApiToiminguEksemplarMeta meta;
    private JsonNode yld;
    private JsonNode sisu;
    
    public String toJsonString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
    
}
