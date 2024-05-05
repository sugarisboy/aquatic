package dev.muskrat.aquatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.muskrat.aquatic.spring.ChecksumBufferCalculator;
import org.junit.jupiter.api.Test;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;

public class ChecksumBufferCalculatorTest {


    @Test
    public void checksumTest() {
        String first = "First line";
        String second = "Socond line";
        int third = 4;

        long calc1 = ChecksumBufferCalculator.of()
                .addString(first)
                .addString(second)
                .addInt(third)
                .calc();

        long calc2 = ChecksumBufferCalculator.of()
                .addString(first)
                .addString(second)
                .addInt(third)
                .calc();

        long calc3 = ChecksumBufferCalculator.of()
                .addString(second)
                .addString(first)
                .addInt(third)
                .calc();

        assertNotNull(calc1);
        assertNotNull(calc2);
        assertEquals(calc1, calc2);
        assertNotEquals(calc1, calc3);
    }
}
