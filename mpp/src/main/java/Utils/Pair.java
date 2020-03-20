package Utils;

import java.io.Serializable;
import java.util.Objects;

public class Pair<T1, T2> implements Serializable {

    private T1 first;
    private T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(T1 first) {
        this.first = first;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    @Override
    public String toString(){
        return "" + this.first + "," + this.second;
    }

    @Override
    public boolean equals(Object another){
        if(!(another instanceof Pair))
            return false;
        Pair otherPair = (Pair)another;
        return otherPair.getFirst() == this.first && otherPair.getSecond() == this.second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
