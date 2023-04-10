package by.it_academy.fitness.service.products;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.audit.AuditData;
import by.it_academy.fitness.core.dto.audit.enums.EssenceType;
import by.it_academy.fitness.core.dto.products.*;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.dao.api.IProductDao;
import by.it_academy.fitness.dao.api.IRecipeDao;
import by.it_academy.fitness.dao.entity.products.CompositionEntity;
import by.it_academy.fitness.dao.entity.products.ProductEntity;
import by.it_academy.fitness.dao.entity.products.RecipeEntity;
import by.it_academy.fitness.service.audit.api.IAuditService;
import by.it_academy.fitness.service.convertion.recipe.api.IRecipeEntityToDto;
import by.it_academy.fitness.service.products.api.IProductService;
import by.it_academy.fitness.service.products.api.IRecipeService;
import by.it_academy.fitness.service.products.dataCalculation.ICalculationRecipe;
import by.it_academy.fitness.web.controllers.filter.JwtFilter;
import by.it_academy.fitness.web.controllers.utils.JwtTokenUtil;
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
    private final JwtFilter jwtFilter;
    private final IAuditService iAuditService;

    private String type = String.valueOf(EssenceType.RECIPE);


    public RecipeService(IRecipeDao iRecipeDao, IProductDao iProductDao, IRecipeEntityToDto iRecipeEntityToDto,
                         ICalculationRecipe iCalculationRecipe, IProductService iProductService,
                         JwtFilter jwtFilter, IAuditService iAuditService) {
        this.iRecipeDao = iRecipeDao;
        this.iProductDao = iProductDao;
        this.iRecipeEntityToDto = iRecipeEntityToDto;
        this.iCalculationRecipe = iCalculationRecipe;
        this.iProductService = iProductService;
        this.jwtFilter = jwtFilter;
        this.iAuditService = iAuditService;
    }

    @Override
    public void create(RecipeCreateDto recipeCreate) throws SingleErrorResponse, MultipleErrorResponse {
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
            String mail = JwtTokenUtil.getUsername(jwtFilter.getToken());
            AuditData auditData = new AuditData(
                    mail,
                    "Создана запись в журнале рецептов",
                    type,
                    null
            );
            iAuditService.createReport(auditData);
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
    public void updateRecipe(UUID uuid, LocalDateTime dt_update, RecipeCreateDto recipeCreateDto)
            throws SingleErrorResponse, MultipleErrorResponse {
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
                String mail = JwtTokenUtil.getUsername(jwtFilter.getToken());
                AuditData auditData = new AuditData(
                        mail,
                        "Обновлена запись в журнале рецептов",
                        type,
                        uuid
                );
                iAuditService.createReport(auditData);
            } else {
                throw new SingleErrorResponse("error", "Введите корректную последнюю дату обновления рецепта");
            }
        } else {
            throw new IllegalArgumentException("Рецепт отсутствует");
        }
    }
}
