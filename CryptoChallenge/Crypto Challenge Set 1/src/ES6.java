import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
// provare a decodificare il testo in base 64 quando letto
/*Break repeating-key XOR*/
public class ES6 {

    public static void main(String[] args) {
        byte[] ciphertext = readBase64("C:\\Users\\pmatt\\OneDrive\\Desktop\\Lavoro\\Esercizi\\src\\ES6.txt");

        ArrayList<KeyValuePair<Integer, Integer>> keyValue = foundKey(ciphertext);
        Collections.sort(keyValue, new Comparator<KeyValuePair<Integer, Integer>>() {
            @Override
            public int compare(KeyValuePair<Integer, Integer> arg0, KeyValuePair<Integer, Integer> arg1)
            {
                return arg0.getValue().compareTo(arg1.getValue());
            }
        });
        System.out.println("Best key sizes are: " + keyValue.get(0).getKey() + "(" + keyValue.get(0).getValue() + "), " + keyValue.get(1).getKey() + "(" + keyValue.get(1).getValue() + "), " + keyValue.get(2).getKey() + "(" + keyValue.get(2).getValue() + "), " + keyValue.get(3).getKey() + "(" + keyValue.get(3).getValue() + ")");
        int keysize = keyValue.get(0).getKey(); // trying 29
        byte[] key = new byte[keysize]; // this would contain key

        byte[][] data = makeBlocksBytes(ciphertext,keysize);
        byte[][] trData = trasponseBlock(data);

        for (int k = 0; k < keysize; k++)
        {
            for (int j = 0; j < 255; j++)
            {
                byte[] decoded = single(trData[k], (byte) j);
                double score = stringMetric(decoded);
                if (score > 0.85)
                {
                    key[k] = (byte) j;
                }
            }
        }
        String s = new String(key, StandardCharsets.UTF_8);
        System.out.println("Guessed Key: " + s);
        byte[] repetXORtext = repeating(ciphertext, key);
        String text = new String(repetXORtext, StandardCharsets.UTF_8);
        System.out.println(text);


    }
    public static byte[] repeating(byte[] arr, byte[] key)
    {
        byte[] ret = new byte[arr.length];
        for(int i=0; i<arr.length; i++)
        {
            ret[i] = (byte)(arr[i] ^ key[i % key.length]);
        }
        return ret;
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
    public static byte[] single(byte[] arr, byte key)
    {
        byte[] ret = new byte[arr.length];
        for(int i=0; i<arr.length; i++)
        {
            ret[i] = (byte) (arr[i] ^ key);
        }
        return ret;
    }
    private static byte[][] trasponseBlock(byte[][] data)
    {
        byte[][] res = new byte[data[0].length][data.length];
        for (int r = 0; r < data.length; r++)
        {
            for (int c = 0; c < data[0].length; c++)
            {
               res[c][r] = data[r][c];
            }
        }
        return res;
    }
    private static byte[] readBase64(String file)
    {
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
        byte[] decodedString = new byte[0];
        try {
            decodedString = Base64.getDecoder().decode(new String(filetext).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedString;
    }
    private static ArrayList<KeyValuePair<Integer, Integer>> foundKey(byte[] ciphertext)
    {
        ArrayList<KeyValuePair<Integer, Integer>> nrmlEditDistance = new ArrayList<KeyValuePair<Integer, Integer>>();
        for (int KEYSIZE = 2;KEYSIZE < 40;KEYSIZE++){
            byte[] data1 = Arrays.copyOfRange(ciphertext, 0 * KEYSIZE, 1 * KEYSIZE);
            byte[] data2 = Arrays.copyOfRange(ciphertext, 1 * KEYSIZE, 2 * KEYSIZE);
            byte[] data3 = Arrays.copyOfRange(ciphertext, 2 * KEYSIZE, 3 * KEYSIZE);
            byte[] data4 = Arrays.copyOfRange(ciphertext, 3 * KEYSIZE, 4 * KEYSIZE);
            int totaldist = HammingDistance(toBitArray(data1), toBitArray(data2));
            totaldist += HammingDistance(toBitArray(data1), toBitArray(data3));
            totaldist += HammingDistance(toBitArray(data1), toBitArray(data4));
            totaldist += HammingDistance(toBitArray(data2), toBitArray(data3));
            totaldist += HammingDistance(toBitArray(data2), toBitArray(data4));
            totaldist += HammingDistance(toBitArray(data3), toBitArray(data4));
            nrmlEditDistance.add(new KeyValuePair<Integer, Integer>(KEYSIZE, totaldist / (KEYSIZE)));
        }
        return nrmlEditDistance;
    }
    public static String toBitArray(byte[] byteArray)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            sb.append(String.format("%8s", Integer.toBinaryString(byteArray[i] & 0xFF)).replace(' ', '0'));
        }
        return sb.toString();
    }
    static int HammingDistance(String str1, String str2)
    {
        int i = 0, count = 0;
        while(i < str1.length())
        {
            if (str1.charAt(i) != str2.charAt(i))
                count++;
            i++;
        }
        return count;
    }
    private static byte[][] makeBlocksBytes(byte[] ciphertext, int dim)
    {
        int pos = 0;
        byte[][] data = new byte[ciphertext.length / dim][dim];

        for (int j = 0; j < ciphertext.length / dim; j++)
        {
            for (int l = 0; l < dim; l++)
                data[j][l] = ciphertext[pos++];
        }
        return data;
    }

}
