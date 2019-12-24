package huluwa.lands;

public class Plain implements Land {
    public String landName() {
        return "Plain.png";
    }

    public boolean isBlocking() {
        return false;
    }

    public int attackBuff() {
        return 0;
    }
}
