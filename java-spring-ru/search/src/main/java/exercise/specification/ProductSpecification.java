package exercise.specification;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

// BEGIN
@Component
public class ProductSpecification {
    public Specification<Product> build(ProductParamsDTO paramsDTO) {
        return withTitleCont(paramsDTO.getTitleCont())
                .and(withCategoryId(paramsDTO.getCategoryId()))
                .and(withPriceLt(paramsDTO.getPriceLt()))
                .and(withPriceGt(paramsDTO.getPriceGt()))
                .and(withRatingGt(paramsDTO.getRatingGt()));
    }

    private Specification<Product> withTitleCont(String titleCont) {
        return ((root, query, criteriaBuilder) -> titleCont == null ?
                criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("title"), titleCont.toLowerCase()));
    }

    private Specification<Product> withCategoryId(Long categoryId) {
        return ((root, query, criteriaBuilder) -> categoryId == null ?
                criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("category").get("id"), categoryId));
    }

    private Specification<Product> withPriceLt(Integer priceLt) {
        return ((root, query, criteriaBuilder) -> priceLt == null ?
                criteriaBuilder.conjunction() : criteriaBuilder.lessThan(root.get("price"), priceLt));
    }

    private Specification<Product> withPriceGt(Integer priceGt) {
        return ((root, query, criteriaBuilder) -> priceGt == null ?
                criteriaBuilder.conjunction() : criteriaBuilder.greaterThan(root.get("price"), priceGt));
    }

    private Specification<Product> withRatingGt(Double ratingGt) {
        return ((root, query, criteriaBuilder) -> ratingGt == null ?
                criteriaBuilder.conjunction() : criteriaBuilder.greaterThan(root.get("rating"), ratingGt));
    }


}
// END
