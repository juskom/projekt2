public class FileOpenerImpl extends AbstractFileOpener {

    private final String fileName;

    public FileOpenerImpl(String fileName) {
        this.fileName = fileName;
    }

    public void doJob() {
        super.openCompressedFile(this.fileName);
    }
}
