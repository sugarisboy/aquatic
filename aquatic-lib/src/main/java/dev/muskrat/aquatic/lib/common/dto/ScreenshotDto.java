package dev.muskrat.aquatic.lib.common.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Base64;

@RequiredArgsConstructor
public class ScreenshotDto {

    @Getter
    private final byte[] content;

    private String base64() {
        return Base64.getEncoder().encodeToString(content);
    }
}
