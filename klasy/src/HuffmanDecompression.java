import java.io.*;
import java.util.*;

public class HuffmanDecompression {
    private static final int MAX = 256;
    private static final int MAXROZMIARDRZEWA = 256;
    private static final int DICT_END = '!';
    private Node root;
    private int przesuniecie;
    private String haslo;
    private Map<Integer, String> codesMap = new HashMap<>();
    private static final Tree tree = new Tree();
    public Map<String, Integer> reverseCodesMap = new HashMap<>();
    public static void main(String[] args) throws IOException {
        HuffmanDecompression decompressor = new HuffmanDecompression();
        decompressor.decompress("E:\\pw\\JIMP2\\mapa.comp", "E:\\pw\\JIMP2\\mapadecomp.png", "", 1);
        BinaryTreeVisualiserImpl binaryTreeBuilder = new BinaryTreeVisualiserImpl();
        binaryTreeBuilder.displayTreeFromMap(decompressor.reverseCodesMap);
    }

    public void decompress(String filePath, String outputFilePath, String password, int compLevel) throws IOException {
        this.haslo = password;
        this.przesuniecie = password.chars().sum();

        if (compLevel == 0) {
            try (FileInputStream fis = new FileInputStream(filePath);
                 FileOutputStream fos = new FileOutputStream(outputFilePath)) {

                int znak;
                while ((znak = fis.read()) != -1) {
                    if (!password.isEmpty()) {
                        znak = (znak + 256 - przesuniecie) % 256;
                    }
                    fos.write(znak);
                }
                return;
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[4];
            fis.read(buffer);
            int compxor = buffer[2] & 0xFF;

            root = new Node(-1);
            readDictionary(fis, root);

            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                decompressData(fis, fos, compxor);
            }
        }
    }

    private void readDictionary(FileInputStream fis, Node node) throws IOException {
        for (int i = 0; i < MAX; i++) {
            addCodeToTree(node, i, new byte[0], 0);
        }

        int byteValue;
        int codeLength;
        byte[] dictEnd = new byte[3];
        String prev = "root";
        tree.addNode("root");
        while (true) {
            fis.read(dictEnd);
            if (dictEnd[0] == DICT_END && dictEnd[1] == DICT_END && dictEnd[2] == DICT_END) {
                break;
            }
            fis.skip(-3); // Powrót do poprzedniej pozycji strumienia

            byteValue = fis.read();
            if (!haslo.isEmpty()) {
                byteValue = (byteValue + 256 - przesuniecie) % 256;
            }
            codeLength = fis.read();
            int bitCount = 0;
            byte bit;
            byte[] codeBuffer = new byte[codeLength];

            while (bitCount < codeLength) {
                bit = (byte)fis.read();
                if (bit == 0) {
                    for (int i = bitCount; i < codeLength; i++) {
                        codeBuffer[i] = 0;
                    }
                    break;
                }
                for (int j = 0; j < 8; j++) {
                    if (bitCount == codeLength) {
                        break;
                    }
                    codeBuffer[bitCount] = (byte) ((bit >> (7 - j)) & 1);
                    bitCount++;
                }
            }

            addCodeToTree(node, byteValue, codeBuffer, codeLength);
            tree.addNode(byteValue+" : "+ Arrays.toString(codeBuffer));
            tree.addEdge(byteValue+" : "+ Arrays.toString(codeBuffer),prev);
            prev = byteValue+" : "+ Arrays.toString(codeBuffer);

            // Dodajemy odczytany kod do mapy
            StringBuilder codeBuilder = new StringBuilder();
            for (int i = 0; i < codeLength; i++) {
                codeBuilder.append(codeBuffer[i]);
            }
            try {
                if (codesMap.containsKey(byteValue)) {
                    throw new IllegalArgumentException("Kod dla danego byteValue jest już zawarty w codesMap");
                }
                codesMap.put(byteValue, codeBuilder.toString());
                reverseCodesMap.put( codeBuilder.toString(),byteValue);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

        // Wypisujemy odczytane kody
        System.out.println("Odczytane kody:");
        for (Map.Entry<Integer, String> entry : codesMap.entrySet()) {
            System.out.printf("Bajt: %s, Kod: %s\n", entry.getKey(), entry.getValue());
        }
    }




    private void addCodeToTree(Node node, int byteValue, byte[] fullCodeBuffer, int codeLength) {
        for (int i = 0; i < codeLength; i++) {
            if (fullCodeBuffer[i] == 0) {
                if (node.left == null) {
                    node.left = new Node(-1);
                }
                node = node.left;
            } else {
                if (node.right == null) {
                    node.right = new Node(-1);
                }
                node = node.right;
            }
        }
        node.data = byteValue;
    }




    private void decompressData(FileInputStream fis, FileOutputStream fos, int compxor) throws IOException {
        int decompxor = 0;
        int bitCount = 0;
        int byteValue = fis.read();
        StringBuilder currentCode = new StringBuilder();
        Node node = root;
        //System.out.println("odczytany compxor: " + compxor);

        while (byteValue != -1) {
            for (int i = 7; i >= 0; i--) {
                int bit = (byteValue >> i) & 1;
                if(node == null) {
                    throw new RuntimeException("Wystąpił problem podczas dekodowania. Sprawdź poprawność pliku wejściowego.");
                }
                currentCode.append(bit);
                node = bit == 0 ? node.left : node.right;
                if (node == null) {
                    throw new RuntimeException("Wystąpił problem podczas dekodowania. Sprawdź poprawność pliku wejściowego.");
                }
                if (node.left == null && node.right == null) {
                    fos.write(node.data);
                    decompxor ^= node.data;
                    //System.out.println("obecna wartosc decompxor: " + decompxor);
                    node = root;
                    currentCode.setLength(0);
                }
                if (++bitCount == 8) {
                    break;
                }
            }
            if (currentCode.length() > MAXROZMIARDRZEWA) { 
                throw new IOException("Błąd dekompresji: znaleziono kod dłuższy niż najdłuższy możliwy kod w słowniku!");
            }
            byteValue = fis.read();
        }

        if(compxor == decompxor) System.out.println("Weryfikacja sum kontrolnych xor zakończona pomyślnie!");
        else System.out.println("Weryfikacja sum kontrolnych xor zakończona niepowodzeniem!");
    }

}