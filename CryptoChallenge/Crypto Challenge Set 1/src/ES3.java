/*
-l'inverso dello XOR è lo XOR stesso
-prendo carattere per carattere
-ogni carattere è 2 bytes quindi trovo il modo di convertirlo in un singolo byte
-metto in hor con ogni byte della stringa HEX
-stampo la stringa
-valuto i caratteri in inglese

 */
import java.io.UnsupportedEncodingException;
import java.util.*;

/*Single-byte XOR cipher*/

public class ES3 {


    public static void main(String[] args) throws UnsupportedEncodingException {

        String hex  = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
        byte[] hex1 = HexFormat.of().parseHex(hex);
        byte[] res = new byte[hex1.length];

        char ch = 'A';
        double bestValue = 0;char bestChar = 'a';
        for (int i=0;i<24;i++) {
            for (int j = 0; j < res.length; j++) {
                res[j] = (byte) (hex1[j] ^ (byte) (ch & 0x00FF));
            }
            double check =stringMetric(res);
            if (check > bestValue) {
                bestValue = check;
                bestChar = ch;
            }
            ch = (char) (ch + 1);
        }
        System.out.println(bestChar);
    }


    public static double stringMetric(byte[] arr)
    {
        int count = 0;
        for (byte b : arr)
        {
            // stuff is weighted how I felt like it
            if ((b >= 'a' && b <= 'z') || b == ' ')
                count += 4;
            if ((b >= 'A' && b <= 'Z') || b == '\'' || b == '.' || b == '!' || b == '?')
                count += 2;
            if ((b >= '0' && b <= '9') || b == '\n' || b == '\t' || b == '\r')
                count++;
        }
        return (double)count / (arr.length * 4);
    }
    private static HashMap findFrequency(String result) {
        result = result.toUpperCase();

        HashMap<Character, Float> map = new HashMap<>();

        for (int i = 0; i < result.length(); i++) {
            char c = result.charAt(i);
            Float val = map.get(c);
            if (val != null) {
                map.put(c, val + (((float)1/(float)result.length())*100));
            }
            else {
                map.put(c, ((float)1/(float)result.length()*100));
            }
        }

        return map;
    }

    private static HashMap initializeVocabulary() {
        HashMap<String,Float> hs = new HashMap();
        hs.put("A", (float) 8.4966);
        hs.put("B", (float) 2.0720);
        hs.put("C", (float) 4.5388);
        hs.put("D", (float) 3.3844);
        hs.put("E", (float) 11.1607);
        hs.put("F", (float) 1.8121);
        hs.put("G", (float) 2.4705);
        hs.put("H", (float) 3.0034);
        hs.put("I", (float) 7.5448);
        hs.put("L", (float) 5.4893);
        hs.put("M", (float) 3.0129);
        hs.put("N", (float) 6.6544);
        hs.put("O", (float) 7.1635);
        hs.put("P", (float) 3.1671);
        hs.put("Q", (float) 0.1962);
        hs.put("R", (float) 7.5809);
        hs.put("S", (float) 5.7351);
        hs.put("T", (float) 6.9509);
        hs.put("U", (float) 3.6308);
        hs.put("V", (float) 1.0074);
        hs.put("Z", (float) 0.2722);
        hs.put("J", (float) 0.1965);
        hs.put("K", (float) 1.1016);
        hs.put("X", (float) 0.2902);
        hs.put("W", (float) 1.2899);
        hs.put("Y", (float) 1.7779);
        hs.put("'", (float) 1.9579);
        hs.put(" ", (float) 15.9579);
        return hs;
    }
}
