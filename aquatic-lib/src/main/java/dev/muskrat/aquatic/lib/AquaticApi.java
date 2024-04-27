package dev.muskrat.aquatic.lib;

import dev.muskrat.aquatic.lib.common.AquaticApiImpl;
import dev.muskrat.aquatic.lib.common.declaration.logic.DeclarationStorage;
import dev.muskrat.aquatic.lib.common.declaration.logic.impl.DeclarationStorageImpl;
import dev.muskrat.aquatic.lib.common.dto.StepDeclarationDto;
import dev.muskrat.aquatic.lib.common.execution.logic.TestInstanceFactory;
import dev.muskrat.aquatic.lib.common.execution.logic.impl.TestInstanceFactoryImpl;
import dev.muskrat.aquatic.lib.common.execution.logic.TestPoolExecutor;
import dev.muskrat.aquatic.lib.common.execution.logic.impl.TestPoolExecutorImpl;
import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import dev.muskrat.aquatic.lib.common.events.AbstractTestEvent;
import dev.muskrat.aquatic.lib.common.events.logic.EventProducer;
import dev.muskrat.aquatic.lib.common.events.logic.impl.EventProducerImpl;
import dev.muskrat.aquatic.lib.common.events.logic.EventService;
import dev.muskrat.aquatic.lib.common.events.logic.impl.EventServiceImpl;
import dev.muskrat.aquatic.lib.common.mapper.MainMapper;
import dev.muskrat.aquatic.lib.common.mapper.MainMapperImpl;
import dev.muskrat.aquatic.lib.selenide.DriverFactory;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public interface AquaticApi {

    static AquaticApi getInstance() {
        return AquaticApiSingleton.INSTANCE.api;
    }

    /**
     * Добавление в очередь теста по его ID декларации,
     * для получения всех возможных значений можно использовать {@link #getAllTestDeclarationIds()}
     *
     * @param testDeclarationId ID теста из {@link dev.muskrat.aquatic.lib.common.declaration.TestDeclaration}
     * @return
     */
    UUID addToQueueTestByDeclarationId(String testDeclarationId);

    /**
     * Возвращает все известные на данный момент для API ID декларации тестов из {@link dev.muskrat.aquatic.lib.common.declaration.TestDeclaration}
     * @return Лист ID декларации тестов
     * @see dev.muskrat.aquatic.lib.common.declaration.TestTemplate
     * @see dev.muskrat.aquatic.lib.common.declaration.TestDeclaration
     */
    List<String> getAllTestDeclarationIds();

    /**
     * Возвращает все известные на данный момент декларации шагов из {@link dev.muskrat.aquatic.lib.common.declaration.StepDeclaration}
     * @return Лист DTO для декларации шагов
     *
     * @see dev.muskrat.aquatic.lib.common.annotations.AquaticStep
     * @see StepDeclaration
     */
    List<StepDeclarationDto> getAllStepDeclarations();

    /**
     * Добавляет обработчик событий в логику исполнения тестов
     * @param eventType ссылка на класс типа события
     * @param handler обработчик события
     * @param <T> тип события
     * @see EventService
     * @see AbstractTestEvent
     */
    <T extends AbstractTestEvent> void registerEventHandler(Class<T> eventType, Consumer<T> handler);

    enum AquaticApiSingleton {
        INSTANCE;

        private final AquaticApi api;

        AquaticApiSingleton() {
            DeclarationStorage declarationStorage = new DeclarationStorageImpl();
            DriverFactory driverFactory = new DriverFactory(null);
            EventService eventService = new EventServiceImpl();
            TestInstanceFactory testInstanceFactory = new TestInstanceFactoryImpl();
            MainMapper mapper = new MainMapperImpl();

            EventProducer eventProducer = new EventProducerImpl(
                    eventService,
                    mapper
            );
            TestPoolExecutor testPoolExecutor = new TestPoolExecutorImpl(
                    driverFactory,
                    testInstanceFactory,
                    eventProducer
            );

            api = new AquaticApiImpl(
                    declarationStorage,
                    testPoolExecutor,
                    eventService,
                    mapper
            );
        }
    }
}
