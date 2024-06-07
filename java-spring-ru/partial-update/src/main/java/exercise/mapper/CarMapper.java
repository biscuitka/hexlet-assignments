package exercise.mapper;

import exercise.dto.CarCreateDTO;
import exercise.dto.CarDTO;
import exercise.dto.CarUpdateDTO;
import exercise.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

// BEGIN
@Mapper(uses = {JsonNullableMapper.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CarMapper {
    public abstract Car map(CarCreateDTO dto);

    public abstract CarDTO map(Car car);

    public abstract void update(CarUpdateDTO dto, @MappingTarget Car car);
}
// END
