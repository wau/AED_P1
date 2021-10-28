package aed.collections;

import java.util.Arrays;
import java.util.Iterator;

public class ULLQueue<T> implements IQueue<T>  {
    public int blockSize = 64;

    Node first;
    Node last;
    int size;
    private int nNodes;

    private class Node {
        private T[] items; // elementos do bloco
        private int counter; // n de elementos do array do bloco
        private Node next;
        private int start;

        @SuppressWarnings("unchecked")
        public Node() {

            this.items = (T[]) new Object[blockSize];
            this.counter = 0;
            this.next = null;
            this.start = 0;
        }

        public void addInNode(T item) {

            this.items[counter] = item;
            this.counter++;
         //   size++;
        }
        public int size()
        {
            return this.counter;
        }
        public Node shallowCopy() {
            Node newNode = new Node();

            for (int i = 0; i < blockSize; i++) {
                newNode.items[i] = this.items[i];
            }
            newNode.counter = this.counter;
            newNode.start = this.start;

            if (this.next != null)
                newNode.next = this.next.shallowCopy();
            return newNode;
        }

        public T remove() {
            T result = this.items[this.start];
            this.items[this.start] = null;
            this.start++;
            this.counter--;
           // size--;
            return result;
        }

        public T get()
        {
            T result = this.items[this.start];
            return  result;
        }
        /*public T removeLast() {
            //System.out.println("counter" + this.counter);
            T result =  this.items[this.counter-1];
            this.items[this.counter-1] = null; // delete last
            this.counter--; //decrement counter
            listSize--;
            return result;
        }*/
    }




    public ULLQueue()
    {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public T[][] getArrayOfBlocks() {

        @SuppressWarnings("unchecked")
        T[][] result = (T[][])new Object[this.nNodes][this.blockSize];

        Node currentnode = this.first;
        int counter = 0;
        while (currentnode != null && counter < this.nNodes)
        {
            // for (int i = 0; i < currentnode.size(); i++)
            for (int i = 0; i < blockSize; i++)
            {
                result[counter][i] = currentnode.items[i];
            }

            currentnode = currentnode.next;
            counter++;
        }
        return result;
    }



    @Override
    public void enqueue(T item) {
        if(isEmpty()) // is empty
        {
            Node n = new Node();
            this.nNodes++;
            n.addInNode(item);
            this.size++;
            this.first = n;
            this.last = n;
        }
        else
        {
            if (this.last.size() == blockSize)
            {
                Node newNode = new Node();
                this.nNodes++;
                newNode.addInNode(item);
                this.size++;
                this.last.next = newNode;
                this.last = newNode;
            }
            else {

                this.last.addInNode(item);
                this.size++;
            }
        }
    }

    @Override
    public T dequeue()
    {

        if (isEmpty())
        {
            return  null;
        }
        else {
            if (this.first != null)
            {
                T result = this.first.remove();
                this.size--;
                if (this.first.size() <= 0 && size > 0)
                {
                    this.first = this.first.next;
                    this.nNodes--;
                }


                return result;
            }
            return null;
        }


    }

    @Override
    public T peek() {
        if (isEmpty())
        {
            return null;
        }
        else {
            T result = this.first.get();
            return result;
        }
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public IQueue<T> shallowCopy() {
        ULLQueue<T>  Q = new ULLQueue<T>();

        Q.first = this.first.shallowCopy();
        Q.nNodes = this.nNodes;

        Q.size = this.size;

        Q.last = this.last.shallowCopy();

        return  Q;
    }



    /////ITERADOR


    public Iterator<T> iterator() {
        return new ULLQueueIterator();
    }

    private class ULLQueueIterator implements Iterator<T>
    {
        Node it;
        int counter;
        int size;
        int idx;
        ULLQueueIterator()
        {
            this.it = first;
            this.counter = 0;
            this.size = size();
            this.idx = first.start;
        }
        public boolean hasNext() {
            return this.counter < this.size;
        }
        /*public T next() {
            T result = this.iterator.item;
            this.iterator = this.iterator.next;
            return result;
        }*/
        public T next()
        {
            T result = it.items[this.idx++];

            if (this.idx >= this.it.size()) {
                this.it = this.it.next;
                if(this.it != null)
                {
                    this.idx = this.it.start;
                }

            }


            this.counter++;
            return result;
        }

    }
    ///////

    public static void main(String[] args) {
        ULLQueue<Integer> Q = new ULLQueue<Integer>();
        Q.enqueue(1);

        Q.enqueue(2);
        Q.enqueue(3);
        Q.enqueue(4);
        Q.enqueue(5);
        Q.enqueue(6);

       /* Q.enqueue(7);
        Q.enqueue(8);
        Q.enqueue(9);
        Q.enqueue(10);
        Q.enqueue(11);
        Q.enqueue(12);

        Q.enqueue(13);
        Q.enqueue(14);
        Q.enqueue(15);
        Q.enqueue(16);
        Q.enqueue(17);
        Q.enqueue(18);


        Q.dequeue();
        Q.dequeue();
        Q.dequeue();
        Q.dequeue();
        Q.dequeue();

        Q.enqueue(19);
        Q.enqueue(20);
        Q.enqueue(20);*/


        System.out.println( "size" + " " + Q.size());
        System.out.println( "peek" + " " + Q.peek());
        System.out.println(Arrays.deepToString(Q.getArrayOfBlocks()));

      //  System.out.println(Q.peek());
          ULLQueue<Integer> shallow =  (ULLQueue<Integer>) Q.shallowCopy();

        /*terator iterator = shallow.iterator();

        while(iterator.hasNext())
        {
            System.out.println(iterator.next());
        }*/

        /*Iterator iterator1 = Q.iterator();
        while(iterator.hasNext())
        {
            System.out.println(iterato1r.next());
        }*/

        //  Q.dequeue();

        //   System.out.println(Arrays.deepToString(Q.getArrayOfBlocks()));
        // System.out.println(Q.equals(shallow));

        //    System.out.println(Arrays.deepToString(shallow.getArrayOfBlocks()));


    }

}
