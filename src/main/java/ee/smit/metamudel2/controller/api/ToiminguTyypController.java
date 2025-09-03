package ee.smit.metamudel2.controller.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/toimingu-tyyp")
@RequiredArgsConstructor
@Tag(name = "Toimingu tüübi definitsioonid (JSON Schema kujul)")
public class ToiminguTyypController {

    @GetMapping
    @Operation(summary = "Väljasta Toimingu tüübi JSON schema")
    @ApiResponse(responseCode = "200", description = "JSON Schema")
    public JsonNode jsonSchema(@RequestParam boolean range) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String s = readFile("static/toiming.json");

            if (!range) {
                s = removeRequiredTags(s);
            }



            return mapper.readTree(s);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read toiming.json", e);
        }

    }

    private static String removeRequiredTags(String s) {
        return s.replaceAll(",\\s*\"required\"\\s*:\\s*true", "");
    }

    private String readFile(String filename) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
        if (is == null) {
            throw new RuntimeException("File toiming.json not found");
        }

        String s = new String(is.readAllBytes());
        return s;
    }


}
