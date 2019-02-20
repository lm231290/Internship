package mycollections;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public class MyList<E> implements Collection {

    public MyList() {
        a = new Object[16];
    }

    private Object[] a;

    // amount of elements in 'a'
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (indexOf(o) == -1)
            return false;
        return true;
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++)
            if (o == a[i])
                return i;
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            int arraySize = size;
            int currPosition = 0;

            @Override
            public boolean hasNext() {
                if (arraySize > currPosition + 1)
                    return true;
                return false;
            }

            @Override
            public E next() {
                currPosition++;
                return (E)a[currPosition - 1];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return a.clone();
    }

    @Override
    public boolean add(Object o) {

        if (a == null)
            a = new Object[16];

        try {
            checkSize(1);
            a[size] = (E)o;
            size++;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1)
            return false;
        moveLeft(index + 1, 1);
        size--;
        return true;
    }

    @Override
    public boolean addAll(Collection c) {
        try {
            addArray((E[]) c.toArray());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void addArray(E[] array) {
        checkSize(array.length);
        for (int i = 0; i < array.length; i++)
            a[size+i] = array[i];
        size += array.length;
    }

    @Override
    public void clear() {
//        a = (E[]) createArray(a.getClass(), 16);
        a = new Object[16];
    }

    @Override
    public boolean retainAll(Collection c) {
        boolean dismatch = false;
        Object[] array = c.toArray();
        for (int i = 0; i < size; i++)
            if (!checkForMatch(a[i], array)) {
                remove(a[i]);
                dismatch = true;
                i--;
            }
        return dismatch;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean atLeastOneRemoved = false;
        Object[] array = c.toArray();
        for (int i = 0; i < size; i++)
            if (checkForMatch(a[i], array)) {
                remove(a[i]);
                atLeastOneRemoved = true;
                i--;
            }
        return atLeastOneRemoved;
    }

    private boolean checkForMatch(Object o, Object[] array){
        for (int j = 0; j < a.length; j++)
            if (o == a[j]) {
                return true;
            }
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        if (c.size() == 0)
            return true;
        Object[] array = c.toArray();
        for (int i = 0; i < array.length; i++)
            if (!checkForMatch(array[i], a))
                return false;
        return true;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return a.clone();
    }
//                         TODO:   rename it
    private void moveLeft(int firstElementToBeMovedIndex, int amountOfMoves ){
        for (int i = firstElementToBeMovedIndex; i <size; i++) {
            a[i - amountOfMoves] = a[i];
            a[i] = null;
        }
    }
//  if there will be option of adding new element not to the end
    private void moveRight(int firstElementToBeMovedIndex, int amountOfMoves ){
        for (int i = firstElementToBeMovedIndex; i <a.length - 1; i++) {
            a[i - amountOfMoves] = a[i];
            a[i] = null;
        }
    }

    private void checkSize(int additionalSize) {
        if (size + additionalSize > 0.75 * a.length){
            resize((int)((size + additionalSize) * 1.5));
        }
    }

    private void resize(int newLength){
        Object[] temp = a.clone();
        a = new Object[newLength];
        for (int i = 0; i < size; i++)
            a[i] = temp[i];
    }
}
