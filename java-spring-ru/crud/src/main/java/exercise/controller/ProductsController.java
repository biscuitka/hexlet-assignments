package exercise.controller;

import java.util.List;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.WrongCategoryException;
import exercise.mapper.ProductMapper;
import exercise.model.Category;
import exercise.model.Product;
import exercise.repository.CategoryRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryRepository categoryRepository;

    // BEGIN
    @GetMapping()
    public List<ProductDTO> getAll(){
        return productRepository.findAll().stream()
                .map(productMapper::map)
                .toList();
    }

    @GetMapping(path = "/{id}")
    public ProductDTO getById(@PathVariable Long id){
        return productMapper.map(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@Valid @RequestBody ProductCreateDTO createDTO){
        categoryRepository.findById(createDTO.getCategoryId())
                .orElseThrow(() -> new WrongCategoryException("Category with id " + createDTO.getCategoryId() + " not found"));
        return productMapper.map(productRepository.save(productMapper.map(createDTO)));
    }

    @PutMapping(path = "/{id}")
    public ProductDTO update(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO updateDTO){
        Category category = categoryRepository.findById(updateDTO.getCategoryId().get())
                .orElseThrow(() -> new WrongCategoryException("Category with id " + updateDTO.getCategoryId().get() + " not found"));
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        product.setCategory(category);
        productMapper.update(updateDTO, product);
        return productMapper.map(productRepository.save(product));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        productRepository.deleteById(id);
    }

}
