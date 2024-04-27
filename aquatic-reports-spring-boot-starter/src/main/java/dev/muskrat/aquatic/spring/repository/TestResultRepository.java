package dev.muskrat.aquatic.spring.repository;

import dev.muskrat.aquatic.spring.model.Test;
import dev.muskrat.aquatic.spring.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TestResultRepository extends JpaRepository<TestResult, UUID> {
}
