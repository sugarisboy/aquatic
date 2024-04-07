package dev.muskrat.aquatic.spring.controller;

import dev.muskrat.aquatic.lib.AquaticApi;
import dev.muskrat.aquatic.lib.common.declaration.StepDeclaration;
import dev.muskrat.aquatic.lib.common.dto.StepDeclarationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/aquatic")
@RequiredArgsConstructor
public class AquaticController {

    private final AquaticApi aquaticApi;

    @PostMapping("/start")
    public void initSelenide(@RequestParam String code) {
        aquaticApi.addToQueueTestByDeclarationId(code);
    }

    @GetMapping("/list")
    public List<String> list() {
        return aquaticApi.getAllTestDeclarationIds();
    }

    @GetMapping("/allSteps")
    public List<StepDeclarationDto> getAllSteps() {
        return aquaticApi.getAllStepDeclarations();
    }
}
