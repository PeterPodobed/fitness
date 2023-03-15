package by.it_academy.fitness.service.products.dataCalculation;

import by.it_academy.fitness.core.dto.products.CompositionCompoundDto;
import by.it_academy.fitness.core.dto.products.CompositionDto;
import by.it_academy.fitness.core.dto.products.CompoundCPFCDto;

import java.util.List;
import java.util.UUID;

public interface ICalculationRecipe {

    List<CompositionCompoundDto> countCompositionCompoundCPFC (List<CompositionDto> compositionDtoList, UUID recipe);

    CompoundCPFCDto countCompoundCPFC(List<CompositionCompoundDto> compositionCompoundDtos);
}
