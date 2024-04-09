package dev.muskrat.aquatic.lib.common;

@FunctionalInterface
public interface AquaticParametrizedStepRunnable extends AquaticLambdaSerializable {

    Object run();
}
