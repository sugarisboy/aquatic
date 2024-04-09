package dev.muskrat.aquatic.example.service;

import org.springframework.stereotype.Service;

@Service
public class SomeServiceImpl implements SomeService {

    @Override
    public void call() {
        System.out.println("hello world");
        throw new RuntimeException("Custom exception");
    }

    @Override
    public String text() {
        return "test";
    }
}
