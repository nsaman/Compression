package compression;

import com.google.common.math.BigIntegerMath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Util;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CompressorTest {
    @Test
    public void workbench() {
        BigInteger randomBigInteger = new BigInteger(73, new Random());

        BigInteger currentBI = randomBigInteger;
        int count = 0;
        while (currentBI.compareTo(BigInteger.valueOf((long)Math.pow(2, Compressor.MAX_BITS * 2) - 1)) == 1) {
            currentBI = Compressor.nextCompress(currentBI, 1).getRemainder();
            count++;
        }
        System.out.println(count);
    }


    @ParameterizedTest
    @CsvSource({"16,1,16,1,0", "1,1,1,1,0", "30,1,30,1,0", "65027,1,255,2,2"})
    public void testNextCompress(String number, int multiplier, int base, int exponent, String remainder) {
        CompressionSegment compressionSegment = Compressor.nextCompress(new BigInteger(number));
        assertEquals(multiplier, compressionSegment.getMultiplier());
        assertEquals(base, compressionSegment.getBase());
        assertEquals(exponent, compressionSegment.getExponent());
        assertEquals(0, compressionSegment.getRemainder().compareTo(new BigInteger(remainder)));
    }

    @Test
    public void testNextCompressMax() {
        BigInteger maxBigInteger = BigInteger.valueOf(2).pow(11).subtract(BigInteger.ONE);
        CompressionSegment compressionSegment = Compressor.nextCompress(maxBigInteger);
        assertEquals(0, compressionSegment.getRemainder().add(compressionSegment.getTotal()).compareTo(maxBigInteger));
    }

    @Test
    public void testNextCompressVeryBigNumber() {
        BigInteger randomBigInteger = new BigInteger(2048, new Random());
        CompressionSegment compressionSegment = Compressor.nextCompress(randomBigInteger);
        assertEquals(0, compressionSegment.getRemainder().add(compressionSegment.getTotal()).compareTo(randomBigInteger));
    }

}