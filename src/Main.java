import java.math.BigInteger;

public class Main
{
    public static void main(final String[] args)
    {
        final var key = BigInteger.valueOf(Long.parseLong(args[0]));
        var tries = 100;

        try
        {
            tries = Integer.parseInt(args[1]);
        }
        catch (Exception ignored)
        {
        }

        final var fermatRSAAttack = new FermatRSAAttack(key);
        final var primes = fermatRSAAttack.attack(tries);

        System.out.printf("""
                    Key-pair to given MODUL %d: %s
                    Evaluated in %d tries.
                """.formatted(fermatRSAAttack.getMODUL(), primes, fermatRSAAttack.getIterationsCount()));
    }
}
