import org.junit.jupiter.api.*;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class FermatRSAAttackTest
{
    @Test
    public void testExpectExceptionForNegativeNumber()
    {
        assertThrows(IllegalArgumentException.class, () -> new FermatRSAAttack(BigInteger.valueOf(-1)));
    }

    @Test
    public void testObjectCorrectInitialized()
    {
        final var actual = assertDoesNotThrow(() -> new FermatRSAAttack(BigInteger.valueOf(2)));

        final var expectedModul = BigInteger.valueOf(2);
        final var expectedIterationsCount = 0;

        assertEquals(expectedModul, actual.getMODUL());
        assertEquals(expectedIterationsCount, actual.getIterationsCount());
    }

    @Test
    public void testObjectCorrectInitializedWithLong()
    {
        final var actual = assertDoesNotThrow(() -> new FermatRSAAttack(2));

        final var expectedKey = BigInteger.valueOf(2);
        final var expectedIterationsCount = 0;

        assertEquals(expectedKey, actual.getMODUL());
        assertEquals(expectedIterationsCount, actual.getIterationsCount());
    }

    @Test
    public void testFermatRSAAttack()
    {
        final var expectedP1 = 3;
        final var expectedP2 = 5;
        final var expectedIterationsCount = 1;

        final var modul = expectedP1 * expectedP2;

        final var fermatRSAAttack = new FermatRSAAttack(modul);
        final var actualPrimes = fermatRSAAttack.attack(1_000_000);

        assertEquals(expectedP1, actualPrimes.p1().longValue());
        assertEquals(expectedP2, actualPrimes.p2().longValue());
        assertEquals(expectedIterationsCount, fermatRSAAttack.getIterationsCount());
    }

    @Test
    public void testFermatRSAAttackWithTooFewTries()
    {
        assertThrows(FermatRSAAttack.OutOfTriesException.class, () -> {
            final var fermatRSAAttack = new FermatRSAAttack(10);
            fermatRSAAttack.attack(1);
        });
    }
}