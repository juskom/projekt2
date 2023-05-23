import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

public abstract class AbstractFileOpener implements FileOpener {

    @Override
    public void openCompressedFile(String fileName) {

        File file = new File(fileName);
        Header header = null;
        byte[] fileBytes = new byte[0];

        try {
            fileBytes = Files.readAllBytes(file.toPath());
        } catch (IOException ex) {
            System.out.println("Problem z plikiem " + ex.getMessage());
        }
        if (file.length() == 0) {
            System.err.println("Pusty plik wejściowy!");
            System.exit(3);
        }

        header = readHeader(fileBytes);
        boolean isHeaderGood = header.startsWith("PK");
        if (!isHeaderGood) {
            System.out.println("Podano niepoprawny plik do dekompresji!" + fileName);
            System.exit(5);
        } else {
            System.out.println("Nagłówek poprawny");
            System.out.println("Nagłówek: " + header.getAuthors() + ", suma kontrolna sprzed kompresji: " + header.getControlSum() + ", zera dopełnienia: " + header.getFollowingZeros());

//            for (byte b : Arrays.copyOfRange(fileBytes, 4, fileBytes.length)) {
//                System.out.println(b);
//                System.out.println(Integer.toBinaryString(b));
//            }

        }
    }
    public Header readHeader(byte[] file) {
        int counter = 0;

        Byte sum = null, zeros = null;
        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : file) {
            if (counter == 4){
                break;
            }
            if (counter < 2) {
                stringBuilder.append((char) b);
            }
            if (counter == 2){
                sum = b;
            }
            if (counter == 3){
                zeros = b;
            }
            counter++;

//            System.out.println(b);
//            System.out.println(Integer.toBinaryString(b));
        }
       return new Header(stringBuilder.toString(), sum, zeros);
    }

}
