package by.it_academy.fitness.config;

import by.it_academy.fitness.dao.api.*;
import by.it_academy.fitness.service.audit.AuditService;
import by.it_academy.fitness.service.audit.api.IAuditService;
import by.it_academy.fitness.service.convertion.audit.api.IConverseAudit;
import by.it_academy.fitness.service.convertion.products.api.IDtoToProductEntity;
import by.it_academy.fitness.service.convertion.products.api.IProductEntityToDto;
import by.it_academy.fitness.service.convertion.recipe.api.IRecipeEntityToDto;
import by.it_academy.fitness.service.convertion.users.IDtoToUserEntity;
import by.it_academy.fitness.service.convertion.users.IUserEntityToDto;
import by.it_academy.fitness.service.products.ProductService;
import by.it_academy.fitness.service.products.RecipeService;
import by.it_academy.fitness.service.products.api.IProductService;
import by.it_academy.fitness.service.products.api.IRecipeService;
import by.it_academy.fitness.service.products.dataCalculation.ICalculationRecipe;
import by.it_academy.fitness.service.users.AdminService;
import by.it_academy.fitness.service.users.UserService;
import by.it_academy.fitness.service.users.api.IAdminService;
import by.it_academy.fitness.service.users.api.IUserService;
import by.it_academy.fitness.web.controllers.filter.JwtFilter;
import by.it_academy.fitness.web.controllers.utils.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;


@Configuration
public class ServiceConfig {

    @Bean
    public IAdminService adminService(IAdminDao dao) {
        return new AdminService(dao);
    }

    @Bean
    public IUserService userService(IUserDao iUserDao,
                                    IUserEntityToDto iUserEntityToDto,
                                    PasswordEncoder encoder,
                                    IDtoToUserEntity iDtoToUserEntity,
                                    JwtFilter jwtFilter,
                                    IAuditService iAuditService) {
        return new UserService(iUserDao, iUserEntityToDto, encoder,
                iDtoToUserEntity, jwtFilter, iAuditService);
    }

    @Bean
    public IProductService productService(IProductDao iProductDao,
                                          IDtoToProductEntity iDtoToProductEntity,
                                          IProductEntityToDto iProductEntityToDto,
                                          IAuditService iAuditService,
                                          JwtFilter jwtFilter,
                                          ConversionService conversionService) {
        return new ProductService(iProductDao, iDtoToProductEntity, iProductEntityToDto,
                iAuditService, jwtFilter, conversionService);
    }

    @Bean
    public IRecipeService recipeService(IRecipeDao iRecipeDao,
                                        IProductDao iProductDao,
                                        IRecipeEntityToDto iRecipeEntityToDto,
                                        ICalculationRecipe iCalculationRecipe,
                                        IProductService iProductService,
                                        JwtFilter jwtFilter,
                                        IAuditService iAuditService) {
        return new RecipeService(iRecipeDao, iProductDao, iRecipeEntityToDto, iCalculationRecipe,
                iProductService, jwtFilter, iAuditService);
    }

    @Bean
    public IAuditService auditService(IAuditDao iAuditDao,
                                      IConverseAudit iAuditDtoToAuditEntity,
//                                      IUserService iUserService,
                                      IUserEntityToDto iUserEntityToDto,
                                      IUserDao iUserDao) {
        return new AuditService(iAuditDao, iAuditDtoToAuditEntity,
//                iUserService,
                iUserEntityToDto, iUserDao);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }


}
