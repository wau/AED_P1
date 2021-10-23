package aed.collections;

import java.util.Iterator;

public class ULLQueue<T> implements IQueue<T>  {

    @Override
    public void enqueue(T item) {

    }

    @Override
    public T dequeue() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public IQueue<T> shallowCopy() {
        return null;
    }



    /////ITERADOR
    private class ULLQueueIterator implements Iterator<T>
    {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ULLQueueIterator();
    }
    ///////

    public static void main(String[] args) {

    }

}
