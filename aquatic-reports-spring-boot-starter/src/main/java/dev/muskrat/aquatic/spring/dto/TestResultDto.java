package dev.muskrat.aquatic.spring.dto;

import dev.muskrat.aquatic.lib.common.dto.TestStatus;
import dev.muskrat.aquatic.spring.model.TestAttachment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResultDto {

    private UUID id;

    private TestStatus status;

/*    @OneToMany
    @SQLRestriction("holder == 'TEST_ATTACHMENT'")
    private List<TestAttachment> attachments;*/

    private TestDto test;

    private List<StepResultDto> stepResults;
}
