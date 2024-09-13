package dev.muskrat.aquatic.spring.model;

import dev.muskrat.aquatic.lib.common.dto.StepStatus;
import dev.muskrat.aquatic.spring.model.enums.AttachmentHolder;
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
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class StepResult {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StepStatus status;

    @OneToMany(mappedBy = "stepResult", cascade = CascadeType.ALL)
    //@SQLRestriction("holder == 'STEP_ATTACHMENT'")
    private List<TestAttachment> attachments = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "step_id", nullable = false)
    private Step step;

    @ManyToOne
    @JoinColumn(name = "test_result_id", nullable = false)
    private TestResult testResult;

    public void addAttachment(TestAttachment testAttachment) {
        if (attachments == null) {
            this.attachments = new ArrayList<>();
        }

        testAttachment.setTestResult(testResult);
        testAttachment.setCreatedAt(OffsetDateTime.now());
        testAttachment.setHolder(AttachmentHolder.STEP_ATTACHMENT);
        testAttachment.setStepResult(this);

        attachments.add(testAttachment);
    }
}
