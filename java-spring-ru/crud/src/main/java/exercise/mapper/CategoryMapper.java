package exercise.mapper;

import exercise.dto.*;
import exercise.model.Category;
import exercise.model.Product;
import org.mapstruct.*;

// BEGIN
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class CategoryMapper{

    //@Mapping(target = "assignee.id", source = "assigneeId")
    public abstract Category map(CategoryCreateDTO dto);

    // @Mapping(source = "assignee.id", target = "assigneeId")
    public abstract CategoryDTO map(Category model);
}
// END
