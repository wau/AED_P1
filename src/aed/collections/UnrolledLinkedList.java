
package aed.collections;

import java.util.Arrays;
import java.util.Iterator;
//teste

public class UnrolledLinkedList<T> implements IList<T> {

    //node = block
    private Node first;
    private Node last;
    private Node next;
    private int nNodes;
    private int blockSize;

    private class Node {
        private T[] items; // elementos do bloco
        private int counter; // n de elementos do array do bloco
        private Node next;


        public Node() {
            this.items =  (T[])new Object[blockSize];
            this.counter = 0;
            this.next = null;
        }

        public int size() {
            return this.counter;
        }
        public void reduce(){
            this.counter--;
        }

        public T getItem(int idx) {

            return this.items[idx];
        }

        public void addInNode(T item) {

            this.items[counter++] = item;
        }
        public T removeLast() {
            T result =  this.items[this.counter-1];
            this.items[this.counter-1] = null; // delete last
            this.counter--; //decrement counter
            return result;
        }
        public T removeIdx(int idx) {
            T result =  this.items[idx];
            this.items[idx] = null; // delete last
            this.counter--; //decrement counter
            return result;
        }
    }


    /////////////////////

    public UnrolledLinkedList() {
        this.first = null;
        this.blockSize = 4;
        this.last = null;
        this.next = null;
        this.nNodes = 0;

       // this.size = 0;
    }

    public UnrolledLinkedList(int blockSize) {
        this.first = null;
        this.blockSize = blockSize;
        this.last = null;
        this.next = null;
        this.nNodes = 0;
      //  this.size = 0;
    }

    public static void main(String[] args) {

        UnrolledLinkedList<Integer> List = new UnrolledLinkedList<Integer>();

        List.add(1);
        List.add(2);
        List.add(3);
        List.add(4);
        List.add(5);
        List.add(6);
        List.add(7);
        List.add(8);
        List.add(9);
        List.add(10);
        List.add(11);

       // System.out.println(List.getArrayOfBlocks().toString());

            //Integer[][] result = List.getArrayOfBlocks();

        //System.out.println(List.get(8));

        Iterator<Integer>  it = List.iterator();

        while (it.hasNext())
        {
            it.next();
        }




        System.out.println(Arrays.deepToString(List.getArrayOfBlocks()));
    }

     T[][] getArrayOfBlocks() {

        T[][] result = (T[][])new Object[this.nNodes][this.blockSize];

         Node currentnode = this.first;
         int counter = 0;
         while (currentnode != null)
         {
             for (int i = 0; i < currentnode.size(); i++)
             {
                 result[counter][i] = currentnode.getItem(i);
             }

             currentnode = currentnode.next;
             counter++;
         }
        return result;
    }
    ////////////////


    public void add(T item) {
        if (isEmpty()) {
            // new node
            Node node = new Node();
            this.nNodes++;
            node.addInNode(item);

            this.first = node;
            this.last = node;
        }
        else
        {
            if (this.last.size() < this.blockSize-1)
            {
                this.last.addInNode(item);

            }
            else {//block will be full

                Node newNode = new Node();
                for(int i = this.last.size() / 2 + 1; i < this.last.size(); i++)
                {
                    T citem = last.getItem(i);
                    newNode.addInNode(citem);
                    last.removeIdx(i);
                }
                newNode.addInNode(item);
                this.last.next = newNode;
                this.last = newNode;
                this.nNodes++;

                /*Node newnode = new Node();
                int half = this.last.size() / 2;

                for (int i = 0; i < half; i++)
                {
                    newnode.addInNode(this.last.getItem(i)); // move half
                }
                newnode.addInNode(item); // add item for last
                this.last.next = newnode;
                this.last = newnode;
                this.nNodes++;*/
            }
        }
    }

    @Override
    public void addAt(int index, T item) {

    }

    public T remove()
    {
        int counter = 0;

        if (isEmpty())
            return null;
        else {
            return this.last.removeLast();
        }
    }

    @Override
    public T remove(int index) {
        return null;
    }

    public T get(int index) {
        if (!isEmpty())
        {
            Node currentnode = this.first;
            int cnt = currentnode.size();
            int idx = index;

            while (currentnode != null)
            {
                boolean isInThisBlock = (idx) <= currentnode.size()-1 && (idx) >= 0;

                if (isInThisBlock)
                {
                    return currentnode.getItem(idx);
                }
                else
                {
                    idx -= currentnode.size();
                    currentnode = currentnode.next;
                }
            }
            return null;
        }
        else {
            return null;
        }
    }

    @Override
    public void set(int index, T element) {

    }

    @Override
    public boolean isEmpty() {
        return (first == null) ? true : false;
    }

    public int size() {
        return 0;
    }

    @Override
    public IList<T> shallowCopy() {
        return null;
    }


    //ITERADOR
    private class UnrolledLinkedListIterator implements Iterator<T>
    {
        Node it;
        public int idx;
        UnrolledLinkedListIterator()
        {
            this.it = first;
            this.idx = 0;
        }

        public boolean hasNext() {

            return this.it != null;
        }
        public T next()
        {
            T result = it.getItem(idx);

            if (this.idx < this.it.size()-1) {
                this.idx++;
            }
            else {
                this.it = this.it.next;
                this.idx = 0;
            }

            System.out.println(result);
            return result;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new UnrolledLinkedListIterator();
    }
}

