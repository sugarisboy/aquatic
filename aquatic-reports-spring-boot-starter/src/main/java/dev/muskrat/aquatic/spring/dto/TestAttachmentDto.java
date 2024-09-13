package dev.muskrat.aquatic.spring.dto;

import dev.muskrat.aquatic.lib.common.dto.StepStatus;
import dev.muskrat.aquatic.spring.model.StepResult;
import dev.muskrat.aquatic.spring.model.TestAttachment;
import dev.muskrat.aquatic.spring.model.TestResult;
import dev.muskrat.aquatic.spring.model.enums.AttachmentHolder;
import dev.muskrat.aquatic.spring.model.enums.AttachmentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestAttachmentDto {

    private UUID id;
    private AttachmentHolder holder;
    private AttachmentType type;
    private String content;
    private UUID stepResultId;
    private UUID testResultId;
}
