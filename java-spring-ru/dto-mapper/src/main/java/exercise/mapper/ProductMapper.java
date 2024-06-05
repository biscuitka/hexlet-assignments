package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;

// BEGIN
@Mapper(componentModel = "spring",
unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper{
    @Mapping(source = "price", target = "cost")
    @Mapping(source = "title", target = "name")
    @Mapping(source = "vendorCode", target = "barcode")
    Product toProduct(ProductCreateDTO dto);

    @Mapping(source = "price", target = "cost")
    Product update(ProductUpdateDTO dto, @MappingTarget Product product);

    @Mapping(source = "cost", target = "price")
    @Mapping(source = "name", target = "title")
    @Mapping(source = "barcode", target = "vendorCode")
    ProductDTO toDto(Product product);
}
// END
