package dev.muskrat.aquatic.lib.common.exception;

/**
 * Ошибка инициализации декларации шага
 */
public class AquaticStepInitializingException extends RuntimeException {

    public AquaticStepInitializingException(String message) {
        super(message);
    }

    public AquaticStepInitializingException(String message, Throwable cause) {
        super(message, cause);
    }

    public AquaticStepInitializingException(Throwable cause) {
        super(cause);
    }
}
