package perf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

import static com.google.common.math.BigIntegerMath.sqrt;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PerfTest {

    @Test
    public void testGuavaSquareRoot() {
        BigInteger randomBigInteger = new BigInteger(1024, new Random());
        sqrt(randomBigInteger, RoundingMode.FLOOR);
    }

}
