package dev.muskrat.aquatic.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Step {

    @Id
    @GeneratedValue
    private UUID id;

    private String code;

    @Column(nullable = false)
    private Integer hash;

    @Column(nullable = false)
    private Integer version;

    @Column(nullable = false)
    private String name;

    private String preCondition;

    private String postCondition;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;
}
