package dev.muskrat.aquatic.lib.common;

import dev.muskrat.aquatic.lib.AquaticApi;
import dev.muskrat.aquatic.lib.common.declaration.logic.DeclarationStorage;
import dev.muskrat.aquatic.lib.common.dto.StepDeclarationDto;
import dev.muskrat.aquatic.lib.common.execution.logic.TestPoolExecutor;
import dev.muskrat.aquatic.lib.common.declaration.TestDeclaration;
import dev.muskrat.aquatic.lib.common.events.AbstractTestEvent;
import dev.muskrat.aquatic.lib.common.events.logic.EventService;
import dev.muskrat.aquatic.lib.common.mapper.MainMapper;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AquaticApiImpl implements AquaticApi {

    private final DeclarationStorage declarationStorage;
    private final TestPoolExecutor testPoolExecutor;
    private final EventService eventService;
    private final MainMapper mainMapper;

    @Override
    public void addToQueueTestByDeclarationId(String code) {
        TestDeclaration test = declarationStorage.findTestDeclarationById(code)
                .orElseThrow(() -> new IllegalArgumentException("Не найден тест с кодом = " + code));
        testPoolExecutor.addToQueue(test);
    }

    @Override
    public List<String> getAllTestDeclarationIds() {
        return declarationStorage.getTests().stream().map(TestDeclaration::getId).collect(Collectors.toList());
    }

    @Override
    public List<StepDeclarationDto> getAllStepDeclarations() {
        return declarationStorage.getSteps().stream().map(mainMapper::map).toList();
    }

    @Override
    public <T extends AbstractTestEvent> void registerEventHandler(Class<T> eventType, Consumer<T> handler) {
        eventService.registerHandler(eventType, handler);
    }
}
