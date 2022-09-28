import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
/*Implement repeating-key XOR*/
public class ES5 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str1 = "Burning 'em, if you ain't quick and nimble\n" +
                "I go crazy when I hear a cymbal";
        String correct = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272" +
                "a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f";


        byte[] cr = sequentialXor(str1.getBytes(StandardCharsets.UTF_8),"ICE");

        String cr22 = HexFormat.of().formatHex(cr);
        System.out.println(cr22);
        System.out.println(correct);


    }
    private static byte doXOR(byte b, byte b1) {
        return (byte) (b^b1);
    }

    private static byte[] sequentialXor(byte[] bt1, String ice) {
        int x = 0;
        byte[] rt = new byte[bt1.length];
        for (int i=0;i< bt1.length;i++){
            rt[i] = doXOR(bt1[i],(byte) (ice.charAt(x) & 0x00FF));
            x++;
            if(x==3)x=0;
        }
        return  rt;
    }
}
