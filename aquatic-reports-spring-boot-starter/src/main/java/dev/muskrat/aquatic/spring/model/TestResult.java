package dev.muskrat.aquatic.spring.model;

import dev.muskrat.aquatic.lib.common.dto.TestStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {

    @Id
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TestStatus status;

    @OneToMany
    @SQLRestriction("holder == 'TEST_ATTACHMENT'")
    private List<TestAttachment> attachments;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @ToString.Exclude
    @OneToMany(mappedBy = "testResult", cascade = CascadeType.ALL)
    private List<StepResult> stepResults;
}
