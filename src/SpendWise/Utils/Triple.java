package SpendWise.Utils;

public class Triple<T1, T2, T3> {
    private T1 first;
    private T2 second;
    private T3 third;

    public Triple(T1 firstItem, T2 secondItem, T3 thirdItem) {
        this.first = firstItem;
        this.second = secondItem;
        this.third = thirdItem;
    }

    public void setFirst(T1 firstItem) {
        this.first = firstItem;
    }

    public void setSecond(T2 secondItem) {
        this.second = secondItem;
    }

    public void setThird(T3 thirdItem) {
        this.third = thirdItem;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    public T3 getThird() {
        return third;
    }
}
