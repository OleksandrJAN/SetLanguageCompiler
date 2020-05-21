package domain;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class Set implements Serializable {
    private SortedSet<Element> elements;
    private String view;


    public Set() {
        this.elements = new TreeSet<>();
        this.view = "{}";
    }

    public int size() {
        return elements.size();
    }

    private String elementsToString() {
        StringBuilder builder = new StringBuilder();
        builder.append('{');

        for (Element element : elements) {
            builder.append(element.toString());
            if (!element.equals(elements.last())) {
                builder.append(",");
            }
        }

        builder.append('}');
        return builder.toString();
    }


    public void addElement(Element element) {
        elements.add(element);
        view = elementsToString();
    }

    public void addElement(Set subSet) {
        elements.add(new Element(subSet.view));
        view = elementsToString();
    }


    public Set union(Set another) {
        Set union = SerializationUtils.clone(this);
        union.elements.addAll(another.elements);
        union.view = union.elementsToString();
        return union;
    }


    public Set intersection(Set another) {
        Set intersection = SerializationUtils.clone(this);
        Iterator<Element> iterator = intersection.elements.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            if (!another.elements.contains(element)) {
                iterator.remove();
            }
        }

        intersection.view = intersection.elementsToString();
        return intersection;
    }

    public Set difference(Set another) {
        Set difference = SerializationUtils.clone(this);
        difference.elements.removeAll(another.elements);

        difference.view = difference.elementsToString();
        return difference;
    }


    public String getView() {
        return view;
    }

    @Override
    public String toString() {
        return view;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Set set = (Set) o;
        return elements.equals(set.elements) &&
                view.equals(set.view);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements, view);
    }

}
