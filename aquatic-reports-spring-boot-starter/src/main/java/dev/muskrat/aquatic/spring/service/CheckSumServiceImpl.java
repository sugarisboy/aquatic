package dev.muskrat.aquatic.spring.service;

import dev.muskrat.aquatic.lib.common.dto.StepDeclarationDto;
import dev.muskrat.aquatic.lib.common.dto.StepInstanceDto;
import dev.muskrat.aquatic.lib.common.dto.TestDeclarationDto;
import dev.muskrat.aquatic.spring.ChecksumBufferCalculator;
import org.springframework.stereotype.Service;
import java.nio.ByteBuffer;

@Service
public class CheckSumServiceImpl extends CheckSumService {

    @Override
    public int calculateCheckSum(TestDeclarationDto declaration) {
        return ChecksumBufferCalculator.of()
                .addString(declaration.getName())
                .addString(declaration.getDescription())
                .addString(declaration.getId())
                .calc();
    }

    @Override
    public int calculateCheckSum(StepDeclarationDto declaration) {
        return ChecksumBufferCalculator.of()
                .addString(declaration.getName())
                .addString(declaration.getPreCondition())
                .addString(declaration.getPostCondition())
                .addString(declaration.getId())
                .calc();
    }
}
