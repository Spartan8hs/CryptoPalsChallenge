
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.*;
/*Detect single-character XOR*/
public class ES4 {
    private static Dictionary dictionary;

    public static void main(String[] args) throws UnsupportedEncodingException{
        ArrayList<String> list = new ArrayList<>();
        try {
            File myObj = new File("C:\\Users\\pmatt\\OneDrive\\Desktop\\Lavoro\\Esercizi\\src\\ES4db.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                list.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        char ch = '1'; int BestRow =0;
        char bestChar = 'a';double bestValue = 0;
        for (int i=0;i<24;i++) {
            for (String st : list) {
                double x = stringMetric(Convert(st, ch));
                if (x > bestValue) {
                    bestValue = x;
                    bestChar = ch;
                    BestRow = i;
                    //System.out.println(toCheck.matches("^[a-zA-Z]*$") + toCheck); non ho capito
                }

            }
            ch = (char) (ch + 1);
        }
        System.out.println("Character in XOR:"+bestChar+" found in line "+BestRow);

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
    private static byte[] Convert(String s, char ch) throws UnsupportedEncodingException {
        byte[] hex1 = HexFormat.of().parseHex(s);
        byte[] res = new byte[hex1.length];
        String result;
        for (int j = 0; j < res.length; j++) {
                res[j] = (byte) (hex1[j] ^ (byte) (ch & 0x00FF));
            }
        return res;

    }


}
