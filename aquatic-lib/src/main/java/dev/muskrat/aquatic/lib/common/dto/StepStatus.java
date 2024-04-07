package dev.muskrat.aquatic.lib.common.dto;

public enum StepStatus {


    /**
     * В очереди
     */
    IN_QUEUE,
    /**
     * В работе
     */
    IN_PROGRESS,
    /**
     * Провален
     */
    FAILURE,
    /**
     * Успешно
     */
    SUCCESS,
    /**
     * Пропущен
     */
    SKIPPED;
}

