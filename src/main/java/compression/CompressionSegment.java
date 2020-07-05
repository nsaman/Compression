package compression;

import java.math.BigInteger;

public class CompressionSegment {
    private int multiplier;
    private int base;
    private int exponent;
    private BigInteger remainder;
    private BigInteger lookahead;

    public CompressionSegment(int multiplier, int base, int exponent, BigInteger remainder, BigInteger lookahead) {
        this.multiplier = multiplier;
        this.base = base;
        this.exponent = exponent;
        this.remainder = remainder;
        this.lookahead = lookahead;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public int getBase() {
        return base;
    }

    public int getExponent() {
        return exponent;
    }

    public BigInteger getRemainder() {
        return remainder;
    }

    public BigInteger getLookahead() {
        return lookahead;
    }

    public BigInteger getTotal() {
        return BigInteger.valueOf(base).pow(exponent).multiply(BigInteger.valueOf(multiplier));
    }
}
