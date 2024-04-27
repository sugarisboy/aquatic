package dev.muskrat.aquatic.spring.dto;

import dev.muskrat.aquatic.lib.common.dto.StepStatus;
import dev.muskrat.aquatic.spring.model.TestAttachment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepResultDto {

    private UUID id;

    private StepStatus status;

    /*@OneToMany
    @SQLRestriction("holder == 'STEP_ATTACHMENT'")
    private List<TestAttachment> attachments;*/

    private StepDto step;
}
