# Fermat Attack on RSA

This is just a little ugly program to 
attack on RSA modul with an approach of Fermat.

Use: `java -jar fermat_rsa_attack.jar [MODUL] [TRIES]`

where MODUL is the RSA-modul (generated from both keys you want to evaluate) 
and TRIES the amount of iterations to avoid long runtime. 
TRIES is optional. If not given the default amount of trie is 100.
If there is no solution found within the tries then give a bigger number up to
approx. 2 billion (integer 32).

---
The modul `N` evaluates from product of two prime numbers (except 2):
`N = p1 * p2`

For the two prime numbers exists `a` and `b` with `p1 = a-b` and `p2 = a+b`.
So `N` can be evaluated as `N = (a-b)(a+b) = a^2 - b^2`.

The number `a` must lie in between `p1` and `p2` with equal distance `b`.
A good guess for such an `a` is the square root of `N` as a starting point.
In each iteration increase `a` by 1 until a solution is found or the amount of tries is exhausted.

In the iteration itself calculate `b^2 = a^2 - N` then check if `b` is non-negative and a square number.
If so the solution is found. If not then increase `a` by 1 and try again.
---
**This method to calculate `p1` and `p2` for RSA-keys is a good method if the two prime numbers
are close together. However, if they are not close it takes long.
So RSA with good key generators are still strong.**