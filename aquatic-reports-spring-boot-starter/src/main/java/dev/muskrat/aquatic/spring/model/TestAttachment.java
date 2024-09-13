package dev.muskrat.aquatic.spring.model;

import dev.muskrat.aquatic.spring.model.enums.AttachmentHolder;
import dev.muskrat.aquatic.spring.model.enums.AttachmentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TestAttachment {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AttachmentHolder holder;

    @ManyToOne
    @ToString.Exclude
    private StepResult stepResult;

    @ManyToOne
    @ToString.Exclude
    private TestResult testResult;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private AttachmentType type;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    public TestAttachment(String content, AttachmentType type) {
        this.content = content;
        this.type = type;
    }
}
