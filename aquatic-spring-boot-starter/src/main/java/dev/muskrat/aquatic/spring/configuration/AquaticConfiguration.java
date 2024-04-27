package dev.muskrat.aquatic.spring.configuration;

import dev.muskrat.aquatic.lib.AquaticApi;
import dev.muskrat.aquatic.lib.common.AquaticApiImpl;
import dev.muskrat.aquatic.lib.common.declaration.logic.DeclarationInitializer;
import dev.muskrat.aquatic.lib.common.declaration.logic.impl.DeclarationInitializerImpl;
import dev.muskrat.aquatic.lib.common.declaration.logic.DeclarationStorage;
import dev.muskrat.aquatic.lib.common.declaration.logic.impl.DeclarationStorageImpl;
import dev.muskrat.aquatic.lib.common.declaration.logic.StepDeclarationReader;
import dev.muskrat.aquatic.lib.common.declaration.logic.impl.StepDeclarationReaderImpl;
import dev.muskrat.aquatic.lib.common.declaration.logic.TestDeclarationReader;
import dev.muskrat.aquatic.lib.common.declaration.logic.impl.TestDeclarationReaderImpl;
import dev.muskrat.aquatic.lib.common.events.AquaticBaseEvent;
import dev.muskrat.aquatic.lib.common.execution.logic.TestInstanceFactory;
import dev.muskrat.aquatic.lib.common.execution.logic.impl.TestInstanceFactoryImpl;
import dev.muskrat.aquatic.lib.common.execution.logic.TestPoolExecutor;
import dev.muskrat.aquatic.lib.common.execution.logic.impl.TestPoolExecutorImpl;
import dev.muskrat.aquatic.lib.common.events.AbstractTestEvent;
import dev.muskrat.aquatic.lib.common.events.logic.EventProducer;
import dev.muskrat.aquatic.lib.common.events.logic.impl.EventProducerImpl;
import dev.muskrat.aquatic.lib.common.events.logic.EventService;
import dev.muskrat.aquatic.lib.common.events.logic.impl.EventServiceImpl;
import dev.muskrat.aquatic.lib.common.mapper.MainMapper;
import dev.muskrat.aquatic.lib.common.mapper.MainMapperImpl;
import dev.muskrat.aquatic.lib.selenide.DriverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfiguration
@ConfigurationPropertiesScan(basePackages = "dev.muskrat.aquatic")
@ComponentScan(basePackages = "dev.muskrat.aquatic")
public class AquaticConfiguration {

    @Value("${selenide.chromedriver}")
    private String chromedriverPath;

    @Bean
    public DriverFactory driverFactory() {
        return new DriverFactory(chromedriverPath);
    }

    @Bean
    public static AquaticBeanPostProcessor aquaticBeanPostProcessor() {
        return new AquaticBeanPostProcessor();
    }

    @Bean
    public static AquaticTestBeanPostProcessor aquaticTestBeanPostProcessor() {
        return new AquaticTestBeanPostProcessor();
    }

    @Bean
    public StepDeclarationReader stepDeclarationReader() {
        return new StepDeclarationReaderImpl();
    }

    @Bean
    public TestDeclarationReader testDeclarationReader() {
        return new TestDeclarationReaderImpl();
    }

    @Bean
    public DeclarationStorage declarationStorage() {
        return new DeclarationStorageImpl();
    }

    @Bean
    public DeclarationInitializer declarationInitializer(
            @Autowired StepDeclarationReader stepDeclarationReader,
            @Autowired TestDeclarationReader testDeclarationReader,
            @Autowired DeclarationStorage declarationStorage
    ) {
        return new DeclarationInitializerImpl(
                stepDeclarationReader,
                testDeclarationReader,
                declarationStorage
        );
    }

    @Bean
    public TestInstanceFactory testInstanceFactory() {
        return new TestInstanceFactoryImpl();
    }

    @Bean
    public EventService eventService(
            @Autowired ApplicationEventPublisher applicationEventPublisher
    ) {
        EventServiceImpl eventService = new EventServiceImpl();
        eventService.registerHandler(AquaticBaseEvent.class, event -> {
            applicationEventPublisher.publishEvent(event);
        });
        return eventService;
    }

    @Bean
    public MainMapper mainMapper() {
        return new MainMapperImpl();
    }

    @Bean
    public EventProducer eventProducer(
            @Autowired EventService eventService,
            @Autowired MainMapper mainMapper
    ) {
        return new EventProducerImpl(eventService, mainMapper);
    }

    @Bean
    public TestPoolExecutor testPoolExecutor(
            @Autowired DriverFactory driverFactory,
            @Autowired TestInstanceFactory testInstanceFactory,
            @Autowired EventProducer eventProducer
    ) {
        return new TestPoolExecutorImpl(driverFactory, testInstanceFactory, eventProducer);
    }

    @Bean
    public AquaticApi aquaticApi(
            @Autowired DeclarationStorage declarationStorage,
            @Autowired TestPoolExecutor testPoolExecutor,
            @Autowired EventService eventService,
            @Autowired MainMapper mainMapper
    ) {
        return new AquaticApiImpl(declarationStorage, testPoolExecutor, eventService, mainMapper);
    }
}
