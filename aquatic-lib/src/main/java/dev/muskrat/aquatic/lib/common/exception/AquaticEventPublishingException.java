package dev.muskrat.aquatic.lib.common.exception;

/**
 * Ошибка отправки события обработчикам
 */
public class AquaticEventPublishingException extends RuntimeException {

    public AquaticEventPublishingException(String message) {
        super(message);
    }

    public AquaticEventPublishingException(String message, Throwable cause) {
        super(message, cause);
    }

    public AquaticEventPublishingException(Throwable cause) {
        super(cause);
    }
}
