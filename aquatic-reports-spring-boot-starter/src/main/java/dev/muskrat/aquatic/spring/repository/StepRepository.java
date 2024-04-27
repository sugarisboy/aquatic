package dev.muskrat.aquatic.spring.repository;

import dev.muskrat.aquatic.spring.model.Step;
import dev.muskrat.aquatic.spring.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

public interface StepRepository extends JpaRepository<Step, UUID> {
    Optional<Step> findFirstByCodeOrderByVersionDesc(String code);
}
