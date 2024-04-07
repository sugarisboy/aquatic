package dev.muskrat.aquatic.lib.common;

import java.io.Serializable;

@FunctionalInterface
public interface AquaticStepHandler<T> extends Serializable {

    void run(T t);
}
