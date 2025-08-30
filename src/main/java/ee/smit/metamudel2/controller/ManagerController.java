package ee.smit.metamudel2.controller;

import ee.smit.metamudel2.model.business.Product;
import ee.smit.metamudel2.service.business.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
@Tag(name = "Controller for manager requests")
public class ManagerController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Add new product to the database")
    @ApiResponse(responseCode = "201", description = "Product successfully added to the database")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void save(@RequestBody Product product) {
        productService.save(product);
    }
}
