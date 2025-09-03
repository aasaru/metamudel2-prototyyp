package ee.smit.metamudel2.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ee.smit.metamudel2.model.db.ToiminguEksemplar;
import ee.smit.metamudel2.model.api.Vastus;
import ee.smit.metamudel2.model.api.Veateade;
import ee.smit.metamudel2.repository.ToiminguEksemplarRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/toimingu-eksemplar")
@RequiredArgsConstructor
@Tag(name = "Toimingu eksemplarid (mingi toimingu tüübi järgi täidetud toimingud)")
public class ToiminguEksemplarRestController {

    private final ToiminguEksemplarRepository repository;

    @GetMapping("/{id}")
    @Operation(summary = "Laadi toimingu eksemplar id järgi")
    @ApiResponse(responseCode = "200", description = "Leitud")
    public JsonNode getToiminguEksemplarById(@PathVariable("id") Long toiminguEksemplarId) {

        Optional<ToiminguEksemplar> byId = repository.findById(toiminguEksemplarId);

        if (byId.isPresent()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readTree(byId.get().getJson());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }

    @PostMapping
    @Operation(summary = "Salvesta toimingu eksemplar")
    @ApiResponse(responseCode = "201", description = "JSON object received")
    public Vastus acceptJson(@RequestParam(value = "toimingu-eksemplar-id", required = false) Long toiminguEksemplariId, @RequestBody JsonNode toiming) {
        System.out.println("Jackson JSON object received: " + toiming);

        ToiminguEksemplar toiminguEksemplar = new ToiminguEksemplar("tyyp1", "Juhan", LocalDateTime.now(), toiming.toString());
        toiminguEksemplar.setId(toiminguEksemplariId);

        repository.save(toiminguEksemplar);

        Veateade veateade = new Veateade("root.yld.koostamiseKuupaev", "format", "teade1");
        return new Vastus(toiming, new Veateade[]{veateade});
    }



    @PostMapping("/teosta-arvutused")
    @Operation(summary = "Teosta arvutused")
    @ApiResponse(responseCode = "201", description = "JSON object received")
    public Vastus teostaArvutused(@RequestParam(value = "toimingu-eksemplar-id", required = false) Long toiminguEksemplariId, @RequestBody JsonNode toiming) {
        System.out.println("Jackson JSON object received: " + toiming);

        int mootmisLugem = Integer.parseInt(toiming.get("sisu").get("mootmisLugem").toString());
        int lubatudKiirus = Integer.parseInt(toiming.get("sisu").get("lubatudKiirus").toString());
        int yletatud = mootmisLugem - lubatudKiirus;

        System.out.println("mootmisLugem:" +mootmisLugem);
        System.out.println("lubatudKiirus:" +lubatudKiirus);

        ObjectNode toimingO = (ObjectNode)toiming;
        ObjectNode sisuO = (ObjectNode)toiming.get("sisu");
        sisuO.put("uletatudKiirus", ""+yletatud);
        toimingO.put("sisu", sisuO);




        return new Vastus(toimingO, new Veateade[]{});
    }




    @PostMapping("vana")
    @Operation(summary = "Accept arbitrary JSON object")
    @ApiResponse(responseCode = "201", description = "JSON object received")
    public  Map<String, Object> acceptJson(@RequestBody Map<String, Object> jsonSisse) {
        // Handle the received JSON object

        System.out.println("JSON object received: " + jsonSisse);

        ToiminguEksemplar toiminguEksemplar = new ToiminguEksemplar("tyyp1", "Juhan", LocalDateTime.now(), null);
        repository.save(toiminguEksemplar);

        Object yld = jsonSisse.get("yld");
        if (yld instanceof Map) {
            Map yldMap = (Map) yld;
            if (yldMap.containsKey("koostamiseKuupaev")) {
                //yldMap.put("koostamiseKuupaev", "2099-01-xx");

            }
        }


        Object sisu = jsonSisse.get("sisu");
        if (sisu instanceof Map) {

            Map sisuMap = (Map) sisu;

            if (sisuMap.containsKey("mootmiseTulemus")) {

                Object mootmiseTulemus = sisuMap.get("mootmiseTulemus");

                if (mootmiseTulemus instanceof List) {

                    List mootmiseTulemusList = (List) mootmiseTulemus;

                    for (int i = 0; i < mootmiseTulemusList.size(); i++) {

                        Map rida = (Map) mootmiseTulemusList.get(i);

                        Object mootmisLugem = rida.get("mootmisLugem");
                        Object laiendmaaramatus = rida.get("laiendmaaramatus");
                        Object lubatudKiirus = rida.get("lubatudKiirus");

                        rida.put("uletatudKiirus", (int) mootmisLugem - (int)laiendmaaramatus - (int)lubatudKiirus);


                    }


                }


            }


        }


        List<Object> veaTeated = new ArrayList<>();
        veaTeated.add( annaVeateade("root.yld.koostamiseKuupaev", "See kuupäev ei lähe mitte") );
        veaTeated.add( annaVeateade("root.yld.koostamiseKoht", "See koht ei lähe mitte") );


        Map<String, Object> jsonValja = new HashMap<>();
        jsonValja.put("toiming", jsonSisse);
        jsonValja.put("veateated", veaTeated);


        return jsonValja;


    }


    private static Map<String, Object> annaVeateade(String path, String message) {
        Map<String, Object> veaTeade = new HashMap<>();
        veaTeade.put("path", path);
        veaTeade.put("property", "format");
        veaTeade.put("message", message);
        return veaTeade;
    }


}
