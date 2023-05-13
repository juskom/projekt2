import java.io.File;

public class World {
public static File infile;
public static File outfile;
    public static void main(String[] args) {
        if (args.length == 0 || args[1].equals("-h")){
            System.out.println("Instrukcja wywołania programu wykonującego dekompresję: [nazwa_programu] [plik_wejściowy] [plik_wyjściowy] [ewentualne_argumenty]");
            System.out.println("-h - wyświetlenie instrukcji");
            System.out.println("-c - dekompresja wcześniej zaszyfrowanego pliku (po tym argumencie należy podać hasło)");
        }
        if(args.length < 2) {
            System.err.println("Za mało argumentów!");

        }
        infile = new File(args[0]);
        outfile = new File(args[1]);
        System.out.println("hello world");
        FileOpener fileOpener = new FileOpenerImpl("C:\\Users\\Justyna\\Desktop\\studia\\sem2\\jimp\\projekt2\\klasy\\src\\ala.comp");
        fileOpener.doJob();
    }
}
