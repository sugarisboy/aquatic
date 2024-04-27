package dev.muskrat.aquatic.lib.common.execution.logic.impl;

import com.google.common.collect.ImmutableList;
import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import dev.muskrat.aquatic.lib.common.events.logic.EventProducer;
import dev.muskrat.aquatic.lib.common.execution.TestInstance;
import dev.muskrat.aquatic.lib.common.execution.logic.TestInstanceFactory;
import dev.muskrat.aquatic.lib.common.execution.logic.TestPoolExecutor;
import dev.muskrat.aquatic.lib.selenide.DriverFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
public class TestPoolExecutorImpl implements TestPoolExecutor {

    private final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(3);
    private final CopyOnWriteArrayList<TestInstance<?>> queue = new CopyOnWriteArrayList<>();

    private final DriverFactory driverFactory;
    private final TestInstanceFactory testInstanceFactory;
    private final EventProducer eventProducer;

    @Override
    public UUID addToQueue(TestDeclaration testDeclaration) {
        if (testDeclaration == null) {
            throw new IllegalArgumentException("Can't be add to pool null value");
        }

        TestInstance<?> instance = testInstanceFactory.createInstance(testDeclaration);
        queue.add(instance);

        log.info("Тест {} добавлен в очередь", testDeclaration.getName());

        eventProducer.addTestInQueue(instance);

        threadPoolExecutor.submit(() -> {
            try {
                pollFirst().ifPresent(test -> {
                    TestExecutorImpl testExecutor = new TestExecutorImpl(driverFactory, eventProducer);
                    testExecutor.execute(test);
                });
            } catch (Exception e) {
                log.warn("Ошибка теста", e);
            }
        });

        return instance.getId();
    }

    @Override
    public ImmutableList<TestInstance<?>> getQueue() {
        return ImmutableList.copyOf(queue);
    }

    @Override
    public int getQueueSize() {
        return queue.size();
    }

    protected Optional<TestInstance<?>> pollFirst() {
        synchronized (queue) {
            if (queue.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(queue.get(0));
        }
    }
}
