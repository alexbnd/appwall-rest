package com.radware.appwall.appwallrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.radware.appwall"})
@EnableJpaRepositories("com.radware.appwall.repository")
@EntityScan("com.radware.appwall.domain.entities")
@PropertySource("classpath:cli.properties")
@ImportResource({"classpath*:applicationContext.xml"})
public class AppwallRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppwallRestApplication.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
