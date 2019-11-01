package math.RSA;

public class Constants {

    public static long[] AssignPQ(long number){
        long[] requiredPQ = new long[2];
        for(long i = 2L; i< number; i++) {
            while(number%i == 0L) {
                requiredPQ[0] = i;
                number = number/i;
            }
        }
        if(number >2L)
            requiredPQ[1] = number;
        return requiredPQ;
    }

    public static boolean tryParseLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static long[] generateLongArrayFromString(String[] message){
        long[] encryptedCodes = new long[message.length];
        int index = 0;
        for(String code : message){
            if (tryParseLong(code.trim())){
                encryptedCodes[index] = Long.parseLong(code.trim());
                index++;
            }
            else
                throw new NumberFormatException("Could not parse one of the input codes. Problematic code: " + code);
        }
        return encryptedCodes;
    }
}
