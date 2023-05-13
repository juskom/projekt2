public class Header {
    private final String authors;
    private final Byte controlSum;
    private final Byte followingZeros;

    public Header(String authors, Byte controlSum, Byte followingZeros) {
        this.authors = authors;
        this.controlSum = controlSum;
        this.followingZeros = followingZeros;
    }

    public String getAuthors() {
        return authors;
    }

    public Byte getControlSum() {
        return controlSum;
    }

    public Byte getFollowingZeros() {
        return followingZeros;
    }
}
