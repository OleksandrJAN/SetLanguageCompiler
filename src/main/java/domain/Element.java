package domain;

import java.io.Serializable;
import java.util.Objects;

public class Element implements Comparable<Element>, Serializable {
    private String value;

    public int compareTo(Element o) {
        return value.compareTo(o.value);
    }


    public Element(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return value.equals(element.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
