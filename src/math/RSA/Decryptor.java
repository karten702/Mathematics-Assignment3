package math.RSA;

import java.math.BigInteger;

public class Decryptor {

    private long N;
    private long e;
    private long d = 1L;
    private long p;
    private long q;

    public Decryptor(long n, long e){
        this.N = n;
        this.e = e;
    }

    public long calculateD(){
        long[] pqArray = Constants.AssignPQ(N);
        p = pqArray[0];
        q = pqArray[1];

        long modNumber = (p-1)*(q-1);
        d = BigInteger.valueOf(e).modInverse(BigInteger.valueOf(modNumber)).longValue();
        return d;
    }

    public String DecryptMessage(long[] crypto){
        char[] decryptedChars = new char[crypto.length];
        int pos = 0;

        for(long c : crypto){
            decryptedChars[pos] = decrypt(c);
            pos++;
        }
        return String.valueOf(decryptedChars);
    }

    private char decrypt(long input){
        BigInteger dev = BigInteger.valueOf(input).modPow(BigInteger.valueOf(d), BigInteger.valueOf(N));
        return (char) dev.intValue();
    }

}
