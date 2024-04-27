package dev.muskrat.aquatic.spring.repository;

import dev.muskrat.aquatic.spring.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface TestRepository extends JpaRepository<Test, UUID> {

    Optional<Test> findFirstByCodeOrderByVersionDesc(String code);
}
