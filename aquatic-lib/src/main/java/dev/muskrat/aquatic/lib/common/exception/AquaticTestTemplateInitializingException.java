package dev.muskrat.aquatic.lib.common.exception;

/**
 * Ошибка инициализации шаблона теста
 */
public class AquaticTestTemplateInitializingException extends RuntimeException {

    public AquaticTestTemplateInitializingException(String message) {
        super(message);
    }

    public AquaticTestTemplateInitializingException(String message, Throwable cause) {
        super(message, cause);
    }

    public AquaticTestTemplateInitializingException(Throwable cause) {
        super(cause);
    }
}
