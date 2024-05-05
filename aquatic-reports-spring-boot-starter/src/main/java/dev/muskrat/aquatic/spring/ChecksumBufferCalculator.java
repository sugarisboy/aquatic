package dev.muskrat.aquatic.spring;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.zip.CRC32;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecksumBufferCalculator {

    private final CRC32 crc32 = new CRC32();
    private final StringBuilder builder = new StringBuilder();

    public ChecksumBufferCalculator addInt(Integer integer) {
        if (integer != null) {
            builder.append(integer);
        }

        return this;
    }

    public ChecksumBufferCalculator addString(String string) {
        if (string != null) {
            builder.append(string);
        }

        return this;
    }

    public int calc() {
        crc32.reset();

        byte[] bytes = builder.toString().getBytes();
        crc32.update(bytes);

        int sum = (int) (crc32.getValue() & Integer.MAX_VALUE);
        return sum;
    }

    public static ChecksumBufferCalculator of() {
        return new ChecksumBufferCalculator();
    }
}
