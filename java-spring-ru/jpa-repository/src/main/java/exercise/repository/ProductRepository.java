package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findAllByPriceBetweenOrderByPriceAsc(Integer min, Integer max);

    List<Product> findAllByPriceLessThanOrderByPriceAsc(Integer max);

    List<Product> findAllByPriceGreaterThanOrderByPriceAsc(Integer min);
    // END
}
