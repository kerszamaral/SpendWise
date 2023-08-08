package SpendWise.Utils;

public class Pair<T1, T2> {
    private T1 first;
    private T2 second;

    public Pair(T1 firstItem, T2 secondItem) {
        this.first = firstItem;
        this.second = secondItem;
    }

    public void setFirst(T1 firstItem) {
        this.first = firstItem;
    }

    public void setSecond(T2 secondItem) {
        this.second = secondItem;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    public boolean equals(Pair<T1, T2> other) {
        return this.first.equals(other.first) && this.second.equals(other.second);
    }

    public T1 getKey() {
        return first;
    }

    public T2 getValue() {
        return second;
    }
}
