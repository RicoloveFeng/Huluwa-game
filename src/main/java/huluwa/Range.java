package huluwa;

public class Range {
    public final int min;
    public final int max;
    public Range(int range_min, int range_max){
        if (range_max < range_min){
            throw new IllegalArgumentException();
        }
        min = range_min;
        max = range_max;
    }

    public boolean inRange(int distance){
        return min <=distance && distance<=max;
    }
}
