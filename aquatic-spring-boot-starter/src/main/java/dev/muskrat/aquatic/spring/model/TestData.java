package dev.muskrat.aquatic.spring.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestData {


    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
