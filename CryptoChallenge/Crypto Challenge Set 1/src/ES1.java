import java.util.Base64;
import java.util.HexFormat;

public class ES1 {
    public static void main(String[] args) {

        String correct = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t";
        String c = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d";
        byte[] res = HexFormat.of().parseHex(c);
        String result = Base64.getEncoder().encodeToString(res);

        System.out.println(result + "CORRECT: "+correct.equals(result));
    }
}
