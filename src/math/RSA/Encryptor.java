package math.RSA;

import java.math.BigInteger;

public class Encryptor {

    private long p = 0L;
    private long q = 0L;
    private long N;
    private long e = 2L;

    public Encryptor(long n){
        this.N = n;
    }

    public long[] CalculatePQ(){
        long[] values = new long[3];
        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        long[] pqArray = Constants.AssignPQ(N);
        endTime = System.currentTimeMillis();
        p = pqArray[0];
        q = pqArray[1];
        values[0] = p;
        values[1] = q;
        values[2] = (endTime - startTime);

        return values;
    }

    public long CalculateE(){
        long PQPrime = (p-1)*(q-1);

        while(!BigIntegerRelativelyPrime(e, PQPrime)){
            e++;
            if(e == p || e == q)
                e++;
        }
        return e;
    }

    private boolean BigIntegerRelativelyPrime(long a, long b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).equals(BigInteger.ONE);
    }

    public String encryptedMessage(String Message){
        long[] encryptedValues = new long[Message.toCharArray().length];
        int pos = 0;

        for(char c : Message.toCharArray()){
            encryptedValues[pos] = Encrypt(c);
            pos++;
        }
        StringBuilder message = new StringBuilder();
        for(long i : encryptedValues){
            message.append(i);
            message.append(",");
        }
        message.deleteCharAt(message.lastIndexOf(","));
        return message.toString();
    }

    private long Encrypt (long input){
        return BigInteger.valueOf(input).modPow(BigInteger.valueOf(e), BigInteger.valueOf(N)).longValue();
    }
}
