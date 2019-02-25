package mycollections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class MyCollection<E> implements Collection {
    public MyCollection() {
        firstSegment = new LinkedList<>();
        secondSegment = new LinkedList<>();
    }

    LinkedList<E> firstSegment;
    LinkedList<E> secondSegment;

    public boolean addToMiddle(Object o) {
        if (firstSegment.size() > secondSegment.size())
            secondSegment.addFirst((E) o);
        else
            firstSegment.addLast((E) o);
        return true;
    }

    @Override
    public int size() {
        return firstSegment.size() + secondSegment.size();
    }

    @Override
    public boolean isEmpty() {
        return firstSegment.size() + secondSegment.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return firstSegment.contains(o) || secondSegment.contains(o);
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        return null;
    }

    private void balance() {
        int difference = Math.abs(firstSegment.size() - secondSegment.size());
        if (difference < 2)
            return;
        if (firstSegment.size() > secondSegment.size()) {
            for (int i = 0; i < difference / 2; i++) {
                secondSegment.addFirst(firstSegment.pollLast());
            }
            return;
        }
        for (int i = 0; i < difference / 2; i++) {
            firstSegment.addLast(secondSegment.pollFirst());
        }
    }

    @Override
    public boolean add(Object o) {
        try {
            secondSegment.add((E) o);
        } catch (Exception e) {
            return false;
        }
        balance();
        return true;
    }

    @Override
    public boolean remove(Object o) {
        boolean result =  firstSegment.remove(o) || secondSegment.remove(o);
        balance();
        return result;
    }

    @Override
    public boolean addAll(Collection c) {
        boolean result = secondSegment.addAll(c);
        balance();
        return result;
    }

    @Override
    public void clear() {
        firstSegment.clear();
        secondSegment.clear();
    }

    @Override
    public boolean retainAll(Collection c) {
//        LinkedList<E> allList;
        throw new UnsupportedOperationException();
//        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean result =  firstSegment.remove(c) || secondSegment.remove(c);
        balance();
        return result;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        Object[] array = new Object[firstSegment.size() + secondSegment.size()];
//
//        Object[] segmentArray = firstSegment.toArray();
//        for (int i = 0; i < segmentArray .length; i++) {
//            array[i] = segmentArray [i];
//        }
//        for (int i = 0; i < sec.length; i++) {
//            array[i] = firstSegmentArray[i];
//        }

        return array;
    }

}
