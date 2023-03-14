package by.it_academy.fitness.config;

import by.it_academy.fitness.dao.api.IAdminDao;
import by.it_academy.fitness.dao.api.IProductDao;
import by.it_academy.fitness.dao.api.IRecipeDao;
import by.it_academy.fitness.dao.api.IUserDao;
import by.it_academy.fitness.service.convertion.products.api.IDtoToProductEntity;
import by.it_academy.fitness.service.convertion.products.api.IProductEntityToDto;
import by.it_academy.fitness.service.convertion.recipe.api.IRecipeEntityToDto;
import by.it_academy.fitness.service.convertion.users.IDtoToUserEntity;
import by.it_academy.fitness.service.convertion.users.IUserEntityToDto;
import by.it_academy.fitness.service.products.ProductService;
import by.it_academy.fitness.service.products.RecipeService;
import by.it_academy.fitness.service.products.api.IProductService;
import by.it_academy.fitness.service.products.api.IRecipeService;
import by.it_academy.fitness.service.users.AdminService;
import by.it_academy.fitness.service.users.UserService;
import by.it_academy.fitness.service.users.api.IAdminService;
import by.it_academy.fitness.service.users.api.IUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfig {

    @Bean
    public IAdminService adminService (IAdminDao dao) {
        return new AdminService(dao);
    }

    @Bean
    public IUserService userService (IUserDao iUserDao,
                                     IUserEntityToDto iUserEntityToDto,
                                     IDtoToUserEntity iDtoToUserEntity) {
        return new UserService(iUserDao, iUserEntityToDto, iDtoToUserEntity);
    }

    @Bean
    public IProductService productService (IProductDao iProductDao,
                                           IDtoToProductEntity iDtoToProductEntity,
                                           IProductEntityToDto iProductEntityToDto) {
        return new ProductService(iProductDao, iDtoToProductEntity, iProductEntityToDto);
    }

    @Bean
    public IRecipeService recipeService (IRecipeDao iRecipeDao,
                                         IProductDao iProductDao,
                                         IRecipeEntityToDto iRecipeEntityToDto){
        return new RecipeService(iRecipeDao, iProductDao, iRecipeEntityToDto);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }




}
