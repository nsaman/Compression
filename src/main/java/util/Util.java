package util;

import java.math.BigInteger;
import java.math.RoundingMode;

import static com.google.common.math.BigIntegerMath.log2;
import static com.google.common.math.BigIntegerMath.sqrt;

public final class Util {

    public static final BigInteger BI_TWO = BigInteger.valueOf(2);

    public static BigInteger nthRoot(int root, BigInteger number) {
        if(root == 1 || number.compareTo(BigInteger.ONE) == 0)
            return number;

        int log2 = log2(BigInteger.valueOf(root),RoundingMode.DOWN);

        BigInteger upper = squaredRoots(number, log2);
        BigInteger lower = sqrt(upper,RoundingMode.DOWN);

        while (lower.compareTo(upper) < 0) {
            BigInteger pivot = upper.add(lower).divide(BI_TWO);
            BigInteger pivotPlusOne = pivot.add(BigInteger.ONE);

            BigInteger estimateLower = pivot.pow(root);
            BigInteger estimateUpper = pivotPlusOne.pow(root);

            int lowerCompare = estimateLower.compareTo(number);
            int upperCompare = estimateUpper.compareTo(number);

            if(lowerCompare <= 0 && upperCompare == 1)
                return pivot;
            else if (upperCompare == 0)
                return pivotPlusOne;
            else if (lowerCompare == 1)
                upper = pivot;
            else {
                if (lower.compareTo(pivot) == 0)
                    lower = lower.add(BigInteger.ONE);
                else
                    lower = pivot;
            }
        }

        return lower;
    }

    public static int nthRootNewton(int root, BigInteger number) {
        int rootMinusOne = root - 1;
        BigInteger rootMinusOneBI = new BigInteger(Integer.toString(rootMinusOne));
        BigInteger rootBI = new BigInteger(Integer.toString(root));
        BigInteger total = number.add(BigInteger.ONE);
        BigInteger subTotal = number;

        while(subTotal.compareTo(total) < 0) {
            total = subTotal;
            subTotal = (subTotal.multiply(rootMinusOneBI)).add(number.divide(subTotal.pow(rootMinusOne))).divide(rootBI);
        }

        return total.intValue();
    }

    public static int logBaseOf(int base, BigInteger number) {
        int approxLog = (int)((double)log2(number, RoundingMode.FLOOR) *  Math.log(2.0D) / Math.log(base));
        BigInteger baseBI = new BigInteger(Integer.valueOf(base).toString());
        BigInteger approxPow = baseBI.pow(approxLog);
        int approxCmp = approxPow.compareTo(number);
        if (approxCmp > 0) {
            do {
                --approxLog;
                approxPow = approxPow.divide(baseBI);
                approxCmp = approxPow.compareTo(number);
            } while(approxCmp > 0);
        } else {
            BigInteger nextPow = baseBI.multiply(approxPow);

            for(int nextCmp = nextPow.compareTo(number); nextCmp <= 0; nextCmp = nextPow.compareTo(number)) {
                ++approxLog;
                nextPow = baseBI.multiply(nextPow);
            }
        }

        return approxLog;
    }

    public static BigInteger squaredRoots(BigInteger number, int squares) {

        if(number.compareTo(BigInteger.ONE) < 1 || squares < 1)
            return number;

        BigInteger currentResult = number;
        for (int i = 0; i < squares; i++) {
            currentResult = sqrt(currentResult, RoundingMode.DOWN);
        }

        return currentResult;
    }
}
