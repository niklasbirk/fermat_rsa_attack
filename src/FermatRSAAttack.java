import java.math.BigInteger;

public class FermatRSAAttack
{
    private final BigInteger MODUL;

    private int iterationsCount;

    public FermatRSAAttack(final long rsaModul)
    {
        this(BigInteger.valueOf(rsaModul));
    }

    public FermatRSAAttack(final BigInteger rsaModul)
    {
        if (isNegative(rsaModul))
        {
            throw new IllegalArgumentException("The provided RSA-Modul must be non-negative!");
        }

        this.MODUL = rsaModul;
    }

    /**
     * The modul {@code N} evaluates from product of two prime numbers (except 2):
     * {@code N = p1 * p2}
     * <br /><br />
     * For the two prime numbers exists {@code a} and {@code b} with {@code p1 = a-b} and {@code p2 = a+b}.
     * So {@code N} can be evaluated as {@code N = (a-b)(a+b) = a^2 - b^2}.
     * <br /><br />
     * The number {@code a} must lie in between {@code p1} and {@code p2} with equal distance {@code b}.
     * A good guess for such an {@code a} is the square root of {@code N} as a starting point.
     * In each iteration increase {@code a} by 1 until a solution is found or the amount of tries is exhausted.
     * <br /><br />
     * In the iteration itself calculate {@code b^2 = a^2 - N} then check if {@code b} is non-negative and a square number.
     * If so the solution is found. If not then increase {@code a} by 1 and try again.
     * <br /><br />
     * This method to calculate {@code p1} and {@code p2} for RSA-keys is a good method if the two prime numbers
     * are close together. However, if they are not close it takes long.
     * So RSA with good key generators are still strong.
     *
     * @param tries the amount of iterations the method should trey before stop to avoid long duration
     * @return a {@link KeyPair} with both prime numbers which generated the modul.
     */
    public KeyPair attack(final int tries)
    {
        final var possibleA = this.MODUL.sqrt().add(BigInteger.ONE);

        for (var i = 0; i < tries; i++)
        {
            final var refinement = BigInteger.valueOf(i);

            final var aSquared = possibleA.add(refinement).pow(2);
            final var bSquared = aSquared.subtract(this.MODUL);

            if (!isNegative(bSquared) && isSquareNumber(bSquared))
            {
                this.iterationsCount = ++i;

                final var a = aSquared.sqrt();
                final var b = bSquared.sqrt();

                return new KeyPair(a.subtract(b), a.add(b));
            }
        }

        throw new OutOfTriesException();
    }

    public int getIterationsCount()
    {
        return this.iterationsCount;
    }

    public BigInteger getMODUL()
    {
        return MODUL;
    }

    private boolean isSquareNumber(final BigInteger number)
    {
        return number.sqrt().pow(2).equals(number);
    }

    private boolean isNegative(final BigInteger number)
    {
        return number.compareTo(BigInteger.ZERO) < 0;
    }

    public record KeyPair(BigInteger p1, BigInteger p2)
    {
        @Override
        public String toString()
        {
            return "p1 = %d, p2 = %d".formatted(p1, p2);
        }
    }

    public class OutOfTriesException extends IndexOutOfBoundsException
    {
        public OutOfTriesException()
        {
            super("""
                    Your given amount of tries are too few to fully compute the solution.
                    Iterations given and needed: %d"""
                .formatted(getIterationsCount()));
        }
    }
}
