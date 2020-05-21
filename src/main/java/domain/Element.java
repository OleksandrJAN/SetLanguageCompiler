package domain;

import java.io.Serializable;

public class Element implements Comparable<Element>, Serializable {
    private String value;

    public int compareTo(Element o) {
        return value.hashCode() - o.value.hashCode();
    }


    public Element(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }

}
