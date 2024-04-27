package dev.muskrat.aquatic.spring.repository;

import dev.muskrat.aquatic.spring.model.StepResult;
import dev.muskrat.aquatic.spring.model.Test;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface StepResultRepository extends JpaRepository<StepResult, UUID> {

    @Query(value = """
                FROM StepResult res 
                INNER JOIN res.step step
                INNER JOIN res.testResult test
                WHERE step.code = :stepCode  
                  AND test.id = :executionId 
            """)
    Optional<StepResult> findByCodeAndTestExecutionId(
            @Param("stepCode") String stepCode,
            @Param("executionId") UUID executionId
    );

}
