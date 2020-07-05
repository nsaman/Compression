package compression;


import util.Util;

import java.math.BigInteger;
import java.math.RoundingMode;

import static com.google.common.math.BigIntegerMath.log2;

public class Compressor {
    public static final int MAX_BITS = 8;
    public static final int MAX = (int)Math.pow(2, MAX_BITS);
    public static final BigInteger MAX_BUFFER = BigInteger.valueOf(2).pow(MAX_BITS * 3).subtract(BigInteger.ONE);

    public static CompressionSegment nextCompress(BigInteger bigInteger) {
        return nextCompress(bigInteger, 0);
    }

    public static CompressionSegment nextCompress(BigInteger bigInteger, int lookAhead) {
        if(bigInteger.compareTo(BigInteger.ONE) == 0)
            return new CompressionSegment(1, 1, 1, BigInteger.ZERO, BigInteger.ZERO);

        int minMultiplier = 0;
        int minBase = 0;
        int minExponent = 0;
        BigInteger minDifference = null;
        BigInteger lookAheadDifference = null;

        for (int i = MAX; i >= 2; i--) {
            int currentExponent = Util.logBaseOf(i, bigInteger);
            if (currentExponent == 0)
                continue;

            BigInteger currentBigInteger = BigInteger.valueOf(i).pow(currentExponent);
            BigInteger currentMultiplier = bigInteger.divide(currentBigInteger);
            BigInteger currentDifference = bigInteger.subtract(currentBigInteger.multiply(currentMultiplier));
            BigInteger currentLookAheadDifference = null;

            if(lookAhead == 0 || currentDifference.compareTo(MAX_BUFFER) < 1)
                currentLookAheadDifference = currentDifference;
            else {
                currentLookAheadDifference = nextCompress(currentDifference, lookAhead - 1).getLookahead();
            }

            if(lookAheadDifference == null || currentLookAheadDifference.compareTo(lookAheadDifference) < 0) {
                minDifference = currentDifference;
                minBase = i;
                minExponent = currentExponent;
                minMultiplier = currentMultiplier.intValue();
                lookAheadDifference = currentLookAheadDifference;
            }

            if(currentExponent == MAX)
                break;
        }

        return new CompressionSegment(minMultiplier, minBase, minExponent, minDifference, lookAheadDifference);
    }
}
