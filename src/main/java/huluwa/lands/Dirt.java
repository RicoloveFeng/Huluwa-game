package huluwa.lands;

public class Dirt implements Land {
    public String landName() {
        return "Dirt.png";
    }

    public boolean isBlocking() {
        return false;
    }

    public int attackBuff() {
        return 2;
    }
}
