import java.io.UnsupportedEncodingException;
import java.util.HexFormat;

/*FIXED XOR*/

public class ES2 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str1 = "1c0111001f010100061a024b53535009181c";
        String str2 = "686974207468652062756c6c277320657965";
        String correct = "746865206b696420646f6e277420706c6179";

        byte[] hex1 = HexFormat.of().parseHex(str1);
        byte[] hex2 = HexFormat.of().parseHex(str2);

        byte[] res = new byte[hex1.length];

        for (int i =0;i<res.length;i++){
            res[i] = doXOR(hex1[i],hex2[i]);
        }

        String result= new String(res, "UTF-8");
        System.out.println(result);
        String cr2 = HexFormat.of().formatHex(res);
        System.out.println(cr2);
    }

    private static byte doXOR(byte b, byte b1) {
        return (byte) (b^b1);
    }

}
