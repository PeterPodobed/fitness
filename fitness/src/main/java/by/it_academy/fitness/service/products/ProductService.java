package by.it_academy.fitness.service.products;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.audit.AuditDto;
import by.it_academy.fitness.core.dto.audit.enums.EssenceType;
import by.it_academy.fitness.core.dto.products.ProductCreateDto;
import by.it_academy.fitness.core.dto.products.ProductDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.UserMessage;
import by.it_academy.fitness.dao.api.IProductDao;
import by.it_academy.fitness.dao.api.IUserDao;
import by.it_academy.fitness.dao.entity.products.ProductEntity;
import by.it_academy.fitness.service.UserHolder;
import by.it_academy.fitness.service.audit.api.IAuditService;
import by.it_academy.fitness.service.convertion.products.api.IDtoToProductEntity;
import by.it_academy.fitness.service.convertion.products.api.IProductEntityToDto;
import by.it_academy.fitness.service.products.api.IProductService;
import by.it_academy.fitness.web.controllers.utils.JwtTokenUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductService implements IProductService {
    private final IProductDao iProductDao;
    private final IDtoToProductEntity iDtoToProductEntity;
    private final IProductEntityToDto iProductEntityToDto;
    private final IAuditService iAuditService;
    private final IUserDao iUserDao;
    private String type = String.valueOf(EssenceType.PRODUCT);


    public ProductService(IProductDao iProductDao, IDtoToProductEntity iDtoToProductEntity,
                          IProductEntityToDto iProductEntityToDto, IAuditService iAuditService,
                          IUserDao iUserDao) {
        this.iProductDao = iProductDao;
        this.iDtoToProductEntity = iDtoToProductEntity;
        this.iProductEntityToDto = iProductEntityToDto;
        this.iAuditService = iAuditService;
        this.iUserDao = iUserDao;
    }

    @Override
    public boolean createProduct(ProductCreateDto productCreateDTO) {
        Optional<ProductEntity> productEntity = iProductDao.findByTitle(productCreateDTO.getTitle());
        if (productEntity.isEmpty()) {
            ProductEntity entity = iDtoToProductEntity.convertDtoToProductEntity(productCreateDTO);
            iProductDao.save(entity);
//            UserHolder userHolder = new UserHolder();
//            UUID uuid = userHolder.getUser().getUuid();
//            String mail = iUserDao.findByMail(JwtTokenUtil.getUsername())
//                    AuditDto auditDto = new AuditDto(
//                    UUID.randomUUID(),
//                    LocalDateTime.now(),
//                    uuid,
//                    "Создан новый продукт",
//                    type,
//                    "id");
//            iAuditService.createReport(auditDto);
            return true;
        } else throw new UserMessage("Продукт с таким названием уже зарегистрирован");
    }

    @Override
    public void updateProduct(UUID uuid, LocalDateTime dt_update, ProductCreateDto productCreateDto) {
        Optional<ProductEntity> findEntityUuid = iProductDao.findById(uuid);
        ProductEntity productEntity = findEntityUuid.get();
        if (productEntity != null) {
            if (productEntity.getDt_update().equals(dt_update) && productEntity.getUuid().equals(uuid)) {
                productEntity.setDt_update(LocalDateTime.now());
                productEntity.setTitle(productCreateDto.getTitle());
                productEntity.setWeight(productCreateDto.getWeight());
                productEntity.setCalories(productCreateDto.getCalories());
                productEntity.setProteins(productCreateDto.getProteins());
                productEntity.setFats(productCreateDto.getFats());
                productEntity.setCarbohydrates(productCreateDto.getCarbohydrates());
                iProductDao.save(productEntity);
            } else {
                throw new IllegalArgumentException("Введите корректную последнюю дату обновления продукта");
            }
        } else {
            throw new IllegalArgumentException("Продукт отсутствует");
        }
    }

    @Override
    public Optional<ProductEntity> findByUUID(UUID uuid) {
        return iProductDao.findById(uuid);
    }

    @Override
    public PageDto<ProductDto> getPage(int number, int size) throws MultipleErrorResponse {
        Pageable page = PageRequest.of(number, size);
        Page<ProductEntity> productEntityPage = iProductDao.findAll(page);
        List<ProductDto> listDto = new ArrayList<>();
        for (ProductEntity entity : productEntityPage) {
            ProductDto productDto = iProductEntityToDto.convertProductEntityToDto(entity);
            listDto.add(productDto);
        }
        return new PageDto<>(productEntityPage.getNumber(), productEntityPage.getSize(),
                productEntityPage.getTotalPages(), productEntityPage.getTotalElements(),
                productEntityPage.isFirst(), productEntityPage.getNumberOfElements(),
                productEntityPage.isLast(), listDto);
    }

}
