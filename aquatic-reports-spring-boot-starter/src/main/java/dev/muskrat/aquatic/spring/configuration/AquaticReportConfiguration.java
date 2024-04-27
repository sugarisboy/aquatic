package dev.muskrat.aquatic.spring.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "dev.muskrat.aquatic")
@EnableJpaRepositories(basePackages = "dev.muskrat.aquatic")
@ComponentScan(basePackages = "dev.muskrat.aquatic.spring")
public class AquaticReportConfiguration {


}
