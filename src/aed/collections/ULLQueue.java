package aed.collections;

import java.util.Iterator;

public class ULLQueue<T> implements IQueue<T>  {
    public int blockSize = 4;
    public class UnrolledLinkedList<T> {
        private class Node{
            private T[] items; // elementos do bloco
            private int counter; // n de elementos do array do bloco
            private Node next;

            @SuppressWarnings("unchecked")
            public Node() {

                this.items =  (T[])new Object[blockSize];
                this.counter = 0;
                this.next = null;
            }
    }


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
