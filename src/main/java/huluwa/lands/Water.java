package huluwa.lands;

public class Water implements Land {
    public String landName() {
        return "Water.png";
    }

    public boolean isBlocking() {
        return false;
    }

    public int attackBuff() {
        return -1;
    }
}
