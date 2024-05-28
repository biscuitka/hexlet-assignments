package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Product;
import exercise.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "")
    public List<Product> showInDiapason(@RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max) {
        if (min != null){
            if (max != null){
                return productRepository.findAllByPriceBetweenOrderByPriceAsc(min, max);
            } else {
                return productRepository.findAllByPriceGreaterThanOrderByPriceAsc(min);
            }
        } else if (max != null){
            return productRepository.findAllByPriceLessThanOrderByPriceAsc(max);
        } else {
            return productRepository.findAll(Sort.by(Sort.Order.asc("price")));
        }

    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
