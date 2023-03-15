package by.it_academy.fitness.service.products.dataCalculation;

import by.it_academy.fitness.core.dto.products.CompositionCompoundDto;
import by.it_academy.fitness.core.dto.products.CompositionDto;
import by.it_academy.fitness.core.dto.products.CompoundCPFCDto;
import by.it_academy.fitness.dao.api.IProductDao;
import by.it_academy.fitness.dao.entity.products.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CalculationRecipe implements ICalculationRecipe{
    IProductDao iProductDao;

    public CalculationRecipe(IProductDao iProductDao) {
        this.iProductDao = iProductDao;
    }

    public List<CompositionCompoundDto> countCompositionCompoundCPFC
            (List<CompositionDto> compositionDtoList, UUID recipe) {
        List<CompositionCompoundDto> compoundDtoList = new ArrayList<>();
        for (CompositionDto compositionDto : compositionDtoList) {
            ProductEntity product = iProductDao.findByUuid(compositionDto.getUuidProduct());
            int weight = compositionDto.getWeight();
            double ratio = (double) weight / product.getWeight();
            int calories = (int) (product.getCalories() * ratio);
            double proteins = product.getProteins() * ratio;
            double fats = product.getFats() * ratio;
            double carbohydrates = product.getCarbohydrates() * ratio;
            compoundDtoList.add(new CompositionCompoundDto(recipe, product.getUuid(), weight, calories, proteins, fats, carbohydrates));
        }
        return compoundDtoList;
    }

    public CompoundCPFCDto countCompoundCPFC(List<CompositionCompoundDto> compositionCompoundDtos) {
        int weight = 0;
        int calories = 0;
        double proteins = 0.00;
        double fats = 0.00;
        double carbohydrates = 0.00;
        for (CompositionCompoundDto dto : compositionCompoundDtos) {
            weight = weight + dto.getWeight();
            calories = calories + dto.getCalories();
            proteins = proteins + dto.getProteins();
            fats = fats + dto.getFats();
            carbohydrates = carbohydrates + dto.getCarbohydrates();
        }
        return new CompoundCPFCDto(weight, calories, proteins, fats, carbohydrates);
    }
}
