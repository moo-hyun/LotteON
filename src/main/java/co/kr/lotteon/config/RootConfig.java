package co.kr.lotteon.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class RootConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    BuildProperties buildProperties() {
        return new BuildProperties(new Properties());
    }

}
