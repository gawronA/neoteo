package pl.local.neoteo.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import pl.local.neoteo.helper.*;
import pl.local.neoteo.service.UserRoleService;
import pl.local.neoteo.service.UtilityTypeService;
import pl.local.neoteo.utils.NotificationInterceptor;

import javax.annotation.Resource;
import java.util.Locale;

@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan("pl.local.neoteo")
public class AppConfig implements WebMvcConfigurer {

    @Resource
    private UtilityTypeService utilityTypeService;
    @Resource
    private UserRoleService userRoleService;

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("tilesConfiguration/tiles.xml");
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        TilesViewResolver viewResolver = new TilesViewResolver();
        registry.viewResolver(viewResolver);
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/resources/i18n/site.locales");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("en"));
        resolver.setCookieName("localeCookie");
        resolver.setCookieMaxAge(4800);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        var localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);

        var notificationInterceptor = new NotificationInterceptor();
        registry.addInterceptor(notificationInterceptor);

    }

    @Bean
    @Override
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(getMyUtilityTypeConverter());
        registry.addConverter(getMyUtilityTypeListConverter());
        registry.addConverter(getMyUserRolesConverter());
        registry.addConverter(getMyUserRolesListConverter());

    }

    @Bean
    public UtilityTypeConverter getMyUtilityTypeConverter() {
        return new UtilityTypeConverter(this.utilityTypeService);
    }

    @Bean
    public UtilityTypeListConverter getMyUtilityTypeListConverter() {
        return new UtilityTypeListConverter(this.utilityTypeService);
    }

    @Bean
    public UserRolesConverter getMyUserRolesConverter() {
        return new UserRolesConverter(this.userRoleService);
    }

    @Bean
    public UserRolesListConverter getMyUserRolesListConverter() {
        return new UserRolesListConverter(this.userRoleService);
    }
}
