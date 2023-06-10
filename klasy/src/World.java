import java.io.File;
import java.io.IOException;

public class World {
public static File infile;
public static File outfile;
public static int clvl = 1;
    public static void main(String[] args) throws IOException {
        if (args.length == 0 || args[1].equals("-h")){
            System.out.println("Instrukcja wywołania programu wykonującego dekompresję: [nazwa_programu] [plik_wejściowy] [plik_wyjściowy] [ewentualne_argumenty]");
            System.out.println("-h - wyświetlenie instrukcji");
            System.out.println("-c - dekompresja wcześniej zaszyfrowanego pliku (po tym argumencie należy podać hasło)");
            //if(args.length<3) System.exit(1);
        }
        //infile = new File(args[0]);
        //outfile = new File(args[1]);
        FileOpener fileOpener = new FileOpenerImpl("E:\\pw\\JIMP2\\mapa.comp");
        fileOpener.doJob();

        HuffmanDecompression decompressor = new HuffmanDecompression();
        decompressor.decompress("E:\\pw\\JIMP2\\ala.comp", "E:\\pw\\JIMP2\\aladecomp.txt", "", clvl);
        BinaryTreeVisualiserImpl binaryTreeBuilder = new BinaryTreeVisualiserImpl();
        binaryTreeBuilder.displayTreeFromMap(decompressor.reverseCodesMap);
    }
}
