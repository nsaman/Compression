package util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @ParameterizedTest
    @CsvSource({"1,2,1", "8,3,2", "15,2,3", "16,2,4", "1024,5,4", "10,100,1"})
    public void testNthRoot(String number, int root, int result) {
        assertEquals(result, Util.nthRoot(root, new BigInteger(number)).intValue());
    }

    @Test
    public void testNthRootOfBigNumber() {
        BigInteger randomBigInteger = new BigInteger(1024, new Random());
        BigInteger root = Util.nthRoot(255, randomBigInteger);
        assertTrue(root.pow(255).compareTo(randomBigInteger) < 1);
        assertTrue(root.add(BigInteger.ONE).pow(255).compareTo(randomBigInteger) == 1);
    }

    @ParameterizedTest
    @CsvSource({"8,3,2", "15,2,3", "16,2,4", "1024,5,4"})
    void logBaseOf(String number, int result, int root) {
        assertEquals(result, Util.logBaseOf(root, new BigInteger(number)));
    }

    @ParameterizedTest
    @CsvSource({"1,1,1", "16,2,2", "15,2,1", "17,2,2", "256,3,2"})
    public void testSquaredRoots(String number, int roots, int result) {
        assertEquals(result, Util.squaredRoots(new BigInteger(number), roots).intValue());
    }
}