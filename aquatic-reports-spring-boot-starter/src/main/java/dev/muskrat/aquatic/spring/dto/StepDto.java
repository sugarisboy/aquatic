package dev.muskrat.aquatic.spring.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepDto {

    private UUID id;

    private String code;

    private Integer hash;

    private Integer version;

    private String name;

    private String preCondition;

    private String postCondition;

    private TestDto test;
}
