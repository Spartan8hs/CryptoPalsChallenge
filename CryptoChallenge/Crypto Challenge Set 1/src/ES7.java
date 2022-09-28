

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
/*AES in ECB mode*/
public class ES7 {


    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String file = readBase64("C:\\Users\\pmatt\\OneDrive\\Desktop\\Lavoro\\Esercizi\\src\\ES7.txt");
        String res = decrypt(file,"YELLOW SUBMARINE");
        System.out.println(res);
    }




    public static String decrypt(final String strToDecrypt, final String secret) {
        try {
            SecretKey key = new SecretKeySpec("YELLOW SUBMARINE".getBytes(StandardCharsets.US_ASCII), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    private static String readBase64(String file) {
        String filetext = "";
        String line;
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(new File(file)));
            while ((line = input.readLine()) != null)
            {
                filetext += line;
            }
            input.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return filetext;
    }
}
