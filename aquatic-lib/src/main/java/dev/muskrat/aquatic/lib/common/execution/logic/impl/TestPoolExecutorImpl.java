package dev.muskrat.aquatic.lib.common.execution.logic.impl;

import com.google.common.collect.ImmutableList;
import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import dev.muskrat.aquatic.lib.common.events.logic.EventProducer;
import dev.muskrat.aquatic.lib.common.execution.TestExecutorImpl;
import dev.muskrat.aquatic.lib.common.execution.logic.TestInstanceFactory;
import dev.muskrat.aquatic.lib.common.execution.logic.TestPoolExecutor;
import dev.muskrat.aquatic.lib.selenide.DriverFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
public class TestPoolExecutorImpl implements TestPoolExecutor {

    private final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(3);
    private final CopyOnWriteArrayList<TestDeclaration> queue = new CopyOnWriteArrayList<>();

    private final DriverFactory driverFactory;
    private final TestInstanceFactory testInstanceFactory;
    private final EventProducer eventProducer;

    @Override
    public void addToQueue(TestDeclaration testDeclaration) {
        if (testDeclaration == null) {
            throw new IllegalArgumentException("Can't be add to pool null value");
        }

        queue.add(testDeclaration);
        log.info("Тест {} добавлен в очередь", testDeclaration.getName());

        threadPoolExecutor.submit(() -> {
            try {
                pollFirst().ifPresent(test -> {
                    TestExecutorImpl testExecutor = new TestExecutorImpl(driverFactory, testInstanceFactory, eventProducer);
                    testExecutor.execute(test);
                });
            } catch (Exception e) {
                log.warn("Ошибка теста", e);
            }
        });
    }

    @Override
    public List<TestDeclaration> getQueue() {
        return ImmutableList.copyOf(queue);
    }

    @Override
    public int getQueueSize() {
        return queue.size();
    }

    protected Optional<TestDeclaration> pollFirst() {
        synchronized (queue) {
            if (queue.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(queue.get(0));
        }
    }
}
