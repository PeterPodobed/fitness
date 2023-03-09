package by.it_academy.fitness.service.products;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.products.ProductCreateDto;
import by.it_academy.fitness.core.dto.products.ProductDto;
import by.it_academy.fitness.dao.api.IProductDao;
import by.it_academy.fitness.dao.entity.products.ProductEntity;
import by.it_academy.fitness.service.convertion.products.api.IDtoToProductEntity;
import by.it_academy.fitness.service.convertion.products.api.IProductEntityToDto;
import by.it_academy.fitness.service.products.api.IProductService;
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

    public ProductService(IProductDao iProductDao, IDtoToProductEntity iDtoToProductEntity, IProductEntityToDto iProductEntityToDto) {
        this.iProductDao = iProductDao;
        this.iDtoToProductEntity = iDtoToProductEntity;
        this.iProductEntityToDto = iProductEntityToDto;
    }

    @Override
    public boolean createProduct(ProductCreateDto productCreateDTO) {
        ProductEntity entity = iDtoToProductEntity.convertDtoToProductEntity(productCreateDTO);
        iProductDao.save(entity);
        return true;
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
            } else { throw new IllegalArgumentException("Введите корректную последнюю дату обновления продукта");
            }
        } else { throw new IllegalArgumentException("Продукт отсутствует");
        }
    }

    @Override
    public Optional<ProductEntity> findByUUID(UUID uuid) {
        return iProductDao.findById(uuid);
    }

    @Override
    public PageDto<ProductDto> getPage(int number, int size) {
        Pageable page = PageRequest.of(number, size);
        Page<ProductEntity> productEntityPage = iProductDao.findAll(page);
        List<ProductDto> listDto = new ArrayList<>();
        for (ProductEntity entity: productEntityPage) {
            ProductDto productDto = iProductEntityToDto.convertProductEntityToDto(entity);
            listDto.add(productDto);
        }
        return new PageDto<>(productEntityPage.getNumber(), productEntityPage.getSize(),
                productEntityPage.getTotalPages(), productEntityPage.getTotalElements(),
                productEntityPage.isFirst(), productEntityPage.getNumberOfElements(),
                productEntityPage.isLast(), listDto );
    }
}
