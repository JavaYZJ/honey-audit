package red.honey.audit.dashboard.surpport;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yangzhijie
 * @date 2021/1/22 17:35
 */
@Configuration
public class SpringMvcWebConfigSupport implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("templates/**").addResourceLocations("classpath:/templates/");
    }
}
