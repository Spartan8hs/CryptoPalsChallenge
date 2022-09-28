import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HexFormat;
/*
Detect AES in ECB mode
 */
public class ES8 {
    public static void main(String[] args) {
        ArrayList<String> decodedFile = readFile("C:\\Users\\pmatt\\OneDrive\\Desktop\\Lavoro\\Esercizi\\src\\ES8.txt");
        int line = 1;
        // li divide in blocchi da 16 perchè con ECB ogni 16 byte cryptati sono uguali
        for (String cText : decodedFile)
        {
            byte[] cipher = HexFormat.of().parseHex(cText);
            byte[][] data = new byte[cipher.length / 16][16];
            int pos = 0;
            for (int j = 0; j < cipher.length / 16; j++)
            {
                for (int l = 0; l < 16; l++)
                    data[j][l] = cipher[pos++];
            }
            boolean breakout = false;
            for (int i = 0; i < data.length; i++)
            {
                if (breakout)
                    break;
                for (int j = 0; j < data.length; j++)
                {
                    if (i == j)
                        continue;
                    if (Arrays.equals(data[i], data[j]))
                    {
                        System.out.println("Line " + line + " Could be ECB");
                        breakout = true;
                        break;
                    }
                }
            }
            line++;
        }
    }

    private static ArrayList<String> readFile(String file) {
        ArrayList<String> ret = new ArrayList<>();
        String line;
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(new File(file)));
            while ((line = input.readLine()) != null)
            {
                ret.add(line);
            }
            input.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return ret;
    }
}
