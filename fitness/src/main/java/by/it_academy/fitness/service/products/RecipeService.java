package by.it_academy.fitness.service.products;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.products.*;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.dao.api.IProductDao;
import by.it_academy.fitness.dao.api.IRecipeDao;
import by.it_academy.fitness.dao.entity.products.ProductEntity;
import by.it_academy.fitness.dao.entity.products.RecipeEntity;
import by.it_academy.fitness.service.convertion.recipe.api.IRecipeEntityToDto;
import by.it_academy.fitness.service.products.api.IRecipeService;
import by.it_academy.fitness.service.products.dataCalculation.ICalculationRecipe;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RecipeService implements IRecipeService {
    private final IRecipeDao iRecipeDao;
    private final IProductDao iProductDao;
    private final IRecipeEntityToDto iRecipeEntityToDto;
    private final ICalculationRecipe iCalculationRecipe;



    public RecipeService(IRecipeDao iRecipeDao, IProductDao iProductDao,
                         IRecipeEntityToDto iRecipeEntityToDto, ICalculationRecipe iCalculationRecipe) {
        this.iRecipeDao = iRecipeDao;
        this.iProductDao = iProductDao;
        this.iRecipeEntityToDto = iRecipeEntityToDto;
        this.iCalculationRecipe = iCalculationRecipe;
    }

    @Override
    public void create(RecipeCreateDto recipeCreate) throws SingleErrorResponse {
        if (!iRecipeDao.existsByTitle(recipeCreate.getTitle())) {
            UUID uuid = UUID.randomUUID();
            LocalDateTime dt_create = LocalDateTime.now();
            List<CompositionCompoundDto> compoundDtos =
                    iCalculationRecipe.countCompositionCompoundCPFC(recipeCreate.getComposition(), uuid);
            CompoundCPFCDto compoundCPFCDto =
                    iCalculationRecipe.countCompoundCPFC(compoundDtos);
            iRecipeDao.save(new RecipeEntity(uuid, dt_create, dt_create,
                    recipeCreate.getTitle(),
                    compoundCPFCDto.getWeight(),
                    compoundCPFCDto.getCalories(),
                    compoundCPFCDto.getProteins(),
                    compoundCPFCDto.getFats(),
                    compoundCPFCDto.getCarbohydrates()));
        } else {
            throw new SingleErrorResponse("error", "Рецепт существует");
        }
    }

    @Override
    public PageDto<RecipeDto> getPage(int number, int size) {
        Pageable page = PageRequest.of(number, size);
        Page<RecipeEntity> recipeEntityPage = iRecipeDao.findAll(page);
        List<RecipeDto> listDto = new ArrayList<>();
        for (RecipeEntity entity : recipeEntityPage) {
            RecipeDto recipeDto = iRecipeEntityToDto.convertRecipeEntityToDto(entity);
            listDto.add(recipeDto);
        }
        return new PageDto<>(recipeEntityPage.getNumber(), recipeEntityPage.getSize(),
                recipeEntityPage.getTotalPages(), recipeEntityPage.getTotalElements(),
                recipeEntityPage.isFirst(), recipeEntityPage.getNumberOfElements(),
                recipeEntityPage.isLast(), listDto);
    }

    @Override
    public void updateRecipe(UUID uuid, LocalDateTime dt_update, RecipeCreateDto recipeCreateDto) throws SingleErrorResponse {
        Optional<RecipeEntity> findEntityUuid = iRecipeDao.findById(uuid);
        RecipeEntity recipeEntity = findEntityUuid.get();
        if (recipeEntity != null) {
            if (recipeEntity.getDt_update().equals(dt_update) && recipeEntity.getUuid().equals(uuid)) {
                CompoundCPFCDto recipeCPFCDto = iCalculationRecipe.countCompoundCPFC(
                        iCalculationRecipe.countCompositionCompoundCPFC(recipeCreateDto.getComposition(), uuid));
                recipeEntity.setTitle(recipeCreateDto.getTitle());
                recipeEntity.setWeight(recipeCPFCDto.getWeight());
                recipeEntity.setCalories(recipeCPFCDto.getCalories());
                recipeEntity.setProteins(recipeCPFCDto.getProteins());
                recipeEntity.setFats(recipeCPFCDto.getFats());
                recipeEntity.setCarbohydrates(recipeCPFCDto.getCarbohydrates());
                recipeEntity.setDt_update(LocalDateTime.now());
                iRecipeDao.save(recipeEntity);
            } else {
                throw new SingleErrorResponse("error", "Введите корректную последнюю дату обновления рецепта");
            }
        } else {
            throw new IllegalArgumentException("Рецепт отсутствует");
        }
    }

//    private List<CompositionCompoundDto> countCompositionCompoundCPFC
//            (List<CompositionDto> compositionDtoList, UUID recipe) {
//        List<CompositionCompoundDto> compoundDtoList = new ArrayList<>();
//        for (CompositionDto compositionDto : compositionDtoList) {
//            ProductEntity product = iProductDao.findByUuid(compositionDto.getUuidProduct());
//            int weight = compositionDto.getWeight();
//            double ratio = (double) weight / product.getWeight();
//            int calories = (int) (product.getCalories() * ratio);
//            double proteins = product.getProteins() * ratio;
//            double fats = product.getFats() * ratio;
//            double carbohydrates = product.getCarbohydrates() * ratio;
//            compoundDtoList.add(new CompositionCompoundDto(recipe, product.getUuid(), weight, calories, proteins, fats, carbohydrates));
//        }
//        return compoundDtoList;
//    }
//
//    private CompoundCPFCDto countCompoundCPFC(List<CompositionCompoundDto> compositionCompoundDtos) {
//        int weight = 0;
//        int calories = 0;
//        double proteins = 0.00;
//        double fats = 0.00;
//        double carbohydrates = 0.00;
//        for (CompositionCompoundDto dto : compositionCompoundDtos) {
//            weight = weight + dto.getWeight();
//            calories = calories + dto.getCalories();
//            proteins = proteins + dto.getProteins();
//            fats = fats + dto.getFats();
//            carbohydrates = carbohydrates + dto.getCarbohydrates();
//        }
//        return new CompoundCPFCDto(weight, calories, proteins, fats, carbohydrates);
//    }

}
