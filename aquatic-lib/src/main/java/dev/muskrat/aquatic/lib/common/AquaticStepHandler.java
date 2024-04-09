package dev.muskrat.aquatic.lib.common;

@FunctionalInterface
public interface AquaticStepHandler<T> extends AquaticLambdaSerializable {

    void run(T t);
}
