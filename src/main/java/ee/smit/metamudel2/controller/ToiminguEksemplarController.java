package ee.smit.metamudel2.controller;

import ee.smit.metamudel2.model.business.Product;
import ee.smit.metamudel2.service.business.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/toimingu-eksemplar")
@RequiredArgsConstructor
@Tag(name = "Toimingu eksemplarid (mingi toimingu tüübi järgi täidetud toimingud)")
public class ToiminguEksemplarController {

    private final ProductService productService;


    @PostMapping
    @Operation(summary = "Accept arbitrary JSON object")
    @ApiResponse(responseCode = "201", description = "JSON object received")
    public  Map<String, Object> acceptJson(@RequestBody Map<String, Object> json) {
        // Handle the received JSON object

        System.out.println("JSON object received: " + json);

        Object sisu = json.get("sisu");
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


                System.out.println("JSON object received: " + sisuMap.get("mootmiseTulemus"));



            }


        }


        return json;


    }



}
