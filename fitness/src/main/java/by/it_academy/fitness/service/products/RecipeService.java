package by.it_academy.fitness.service.products;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.products.*;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.dao.api.IProductDao;
import by.it_academy.fitness.dao.api.IRecipeDao;
import by.it_academy.fitness.dao.entity.products.CompositionEntity;
import by.it_academy.fitness.dao.entity.products.ProductEntity;
import by.it_academy.fitness.dao.entity.products.RecipeEntity;
import by.it_academy.fitness.service.convertion.recipe.api.IRecipeEntityToDto;
import by.it_academy.fitness.service.products.api.IProductService;
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
    private final IProductService iProductService;


    public RecipeService(IRecipeDao iRecipeDao, IProductDao iProductDao, IRecipeEntityToDto iRecipeEntityToDto,
                         ICalculationRecipe iCalculationRecipe, IProductService iProductService) {
        this.iRecipeDao = iRecipeDao;
        this.iProductDao = iProductDao;
        this.iRecipeEntityToDto = iRecipeEntityToDto;
        this.iCalculationRecipe = iCalculationRecipe;
        this.iProductService = iProductService;
    }

    @Override
    public void create(RecipeCreateDto recipeCreate) throws SingleErrorResponse {
        if (!iRecipeDao.existsByTitle(recipeCreate.getTitle())) {
            UUID uuid = UUID.randomUUID();
            LocalDateTime dt_create = LocalDateTime.now();
            List<CompositionDto> compositionDtoList = recipeCreate.getComposition();
            List<CompositionEntity> compositionEntityList = new ArrayList<>();
            for (CompositionDto compositionDto : compositionDtoList) {
                Optional<ProductEntity> searchProduct = iProductService.findByUUID(compositionDto.getUuidProduct());
                ProductEntity product = searchProduct.get();
                compositionEntityList.add(new CompositionEntity(UUID.randomUUID(), product, compositionDto.getWeight()));
            }
//            iRecipeDao.save(new RecipeEntity(uuid, dt_create, dt_create, recipeCreate.getTitle(), compositionEntityList));
            List<CompositionCompoundDto> compoundDtos =
                    iCalculationRecipe.countCompositionCompoundCPFC(recipeCreate.getComposition(), uuid);
            CompoundCPFCDto compoundCPFCDto =
                    iCalculationRecipe.countCompoundCPFC(compoundDtos);
            iRecipeDao.save(new RecipeEntity(uuid, dt_create, dt_create,
                    recipeCreate.getTitle(),
                    compositionEntityList,
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
    public PageDto<RecipeDto> getPage(int number, int size) throws MultipleErrorResponse {
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
            LocalDateTime dt_create = recipeEntity.getDt_create();
            if (recipeEntity.getDt_update().equals(dt_update) && recipeEntity.getUuid().equals(uuid)) {
                List<CompositionDto> compositionDtoList = recipeCreateDto.getComposition();
                List<CompositionEntity> compositionEntityList = new ArrayList<>();
                for (CompositionDto compositionDto : compositionDtoList) {
                    Optional<ProductEntity> searchProduct = iProductService.findByUUID(compositionDto.getUuidProduct());
                    ProductEntity product = searchProduct.get();
                    compositionEntityList.add(new CompositionEntity(UUID.randomUUID(), product, compositionDto.getWeight()));
                }
//                iRecipeDao.save(new RecipeEntity(uuid, dt_create, LocalDateTime.now(), recipeCreateDto.getTitle(), compositionEntityList));

                CompoundCPFCDto recipeCPFCDto = iCalculationRecipe.countCompoundCPFC(
                        iCalculationRecipe.countCompositionCompoundCPFC(recipeCreateDto.getComposition(), uuid));
                recipeEntity.setTitle(recipeCreateDto.getTitle());
                recipeEntity.setComposition(compositionEntityList);
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


}
