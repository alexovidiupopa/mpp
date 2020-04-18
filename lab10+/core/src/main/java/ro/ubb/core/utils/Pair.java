package ro.ubb.core.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Pair<T1 extends Comparable<T1>, T2 extends Comparable<T2>> implements Serializable, Comparable<Pair<T1, T2>> {

    private T1 sid;
    private T2 aid;

    public void setFirst(T1 first) {
        this.sid = first;
    }

    public void setSecond(T2 second) {
        this.aid = second;
    }

    public T1 getFirst() {
        return sid;
    }

    public T2 getSecond() {
        return aid;
    }

    @Override
    public String toString(){
        return "" + this.sid + "," + this.aid;
    }

    @Override
    public boolean equals(Object another){
        if(!(another instanceof Pair))
            return false;
        Pair otherPair = (Pair)another;
        return otherPair.getFirst() == this.sid && otherPair.getSecond() == this.aid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid, aid);
    }

    @Override
    public int compareTo(Pair<T1, T2> other) {
        int resultFirst = this.sid.compareTo(other.getFirst());
        if(resultFirst != 0)
            return resultFirst;
        else
            return this.aid.compareTo(other.getSecond());
    }
}