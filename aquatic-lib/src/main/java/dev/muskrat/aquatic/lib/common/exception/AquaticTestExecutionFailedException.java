package dev.muskrat.aquatic.lib.common.exception;

/**
 * Ошибка исполнения теста
 */
public class AquaticTestExecutionFailedException extends RuntimeException {

    public AquaticTestExecutionFailedException(String message) {
        super(message);
    }

    public AquaticTestExecutionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AquaticTestExecutionFailedException(Throwable cause) {
        super(cause);
    }
}
