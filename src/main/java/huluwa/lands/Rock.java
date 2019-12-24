package huluwa.lands;

public class Rock implements Land {
    public String landName() {
        return "Rock.png";
    }

    public boolean isBlocking() {
        return true;
    }

    public int attackBuff() {
        return 0;
    }
}
