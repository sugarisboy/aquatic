package dev.muskrat.aquatic.lib.common.exception;

/**
 * Ошибка объявления
 */
public class AquaticDefinitionException extends RuntimeException {

    public AquaticDefinitionException(String message) {
        super(message);
    }

    public AquaticDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public AquaticDefinitionException(Throwable cause) {
        super(cause);
    }
}
