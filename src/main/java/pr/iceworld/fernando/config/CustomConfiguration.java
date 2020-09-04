package pr.iceworld.fernando.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import pr.iceworld.fernando.consts.Const;

/**
 * as long as one of PropertyPlaceholderConfigurer, PropertySourcesPlaceholderConfigurer is implemented is okay for loading property
 **/ 
@Configuration
public class CustomConfiguration {


    @Bean
    public PropertyPlaceholderConfigurer properties() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        Resource[] resources = new FileSystemResource[]{new FileSystemResource(Const.CONFIG_FILE_PATH)};
        ppc.setLocations(resources);
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }

//    @Bean
//    public PropertySourcesPlaceholderConfigurer properties() {
//        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
//        Resource[] resources = new FileSystemResource[]{new FileSystemResource(Const.CONFIG_FILE_PATH)};
//        pspc.setLocations(resources);
//        pspc.setIgnoreUnresolvablePlaceholders(true);
//        return pspc;
//    }
}
