package by.it_academy.fitness.web.controllers;


import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.products.ProductCreateDto;
import by.it_academy.fitness.core.dto.products.ProductDto;
import by.it_academy.fitness.service.products.api.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final IProductService service;

    public ProductController(IProductService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ResponseEntity<?> doPost(@RequestBody ProductCreateDto productCreateDTO) {
        if (service.createProduct(productCreateDTO)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping(path = "{uuid}/dt_update/{dt_update}")
    protected ResponseEntity<?> update(@PathVariable("uuid") UUID uuid,
                                       @PathVariable("dt_update") LocalDateTime dt_update,
                                       @RequestBody ProductCreateDto product) {
        service.updateProduct(uuid, dt_update, product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PageDto<ProductDto>> getPage(
            @RequestParam(name = "number", required = false, defaultValue = "0") int number,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size){
        return ResponseEntity.status(HttpStatus.OK).body(service.getPage(number, size));
    }


}
