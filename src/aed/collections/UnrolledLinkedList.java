
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

        public void setItem(int idx, T item) {

            this.items[idx] = item;
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
        public void initNull()
        {
            for (int i = 0; i < blockSize; i++)
            {
                this.items[i] = null;
            }
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

       /* List.set(10, 12);

        List.remove();
        List.remove();
        List.remove();*/

        List.rightShift(List.first, 0, 0);


       // System.out.println(List.getArrayOfBlocks().toString());

            //Integer[][] result = List.getArrayOfBlocks();

        //System.out.println(List.get(8));

        /*Iterator<Integer>  it = List.iterator();

        while (it.hasNext())
        {
            it.next();
        }*/



        UnrolledLinkedList<Integer> shallow =  (UnrolledLinkedList<Integer>) List.shallowCopy();

        System.out.println(Arrays.deepToString(List.getArrayOfBlocks()));
        System.out.println(Arrays.deepToString(shallow.getArrayOfBlocks()));
        System.out.println(List.size());
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

    void rightShift(Node start, int startIdx, T itemToKeep)
    {
        Node node = start;
        T[] newArr =  (T[])new Object[blockSize];

        int itemsToAdd =  start.size() - startIdx;


            if (startIdx + itemsToAdd > blockSize) // need to create a new node
            {
               /* Node newNode = new Node();
                for(int i = this.node.size() / 2 + 1; i < this.last.size(); i++)
                {
                    T citem = last.getItem(i);
                    newNode.addInNode(citem);
                    last.removeIdx(i);
                }
                newNode.addInNode(item);
                this.last.next = newNode;
                this.last = newNode;
                this.nNodes++;*/
            }
            else {
                for (int i = startIdx; i < start.size(); i++)
                {
                    newArr[i+1] = start.items[i];
                }


                System.out.println(Arrays.deepToString(newArr));
                //start.items = newArr.clone();
                System.arraycopy(newArr, 0, start.items, 0, blockSize);

            }
       // }

    }

    @Override
    public void addAt(int index, T item) {

        int listSize = this.size();

        if (index == listSize)
        {
            add(item);
        }
        else if (index < listSize)
        {
            Node currentnode = this.first;
            int cnt = currentnode.size();
            int idx = index;

            while (currentnode != null)
            {
                boolean isInThisBlock = (idx) <= currentnode.size()-1 && (idx) >= 0;

                if (isInThisBlock)
                {


                }
                else
                {
                    idx -= currentnode.size();
                    currentnode = currentnode.next;
                }
            }
        }
    }
    //TODO: improve remove to work on all cases
    public T remove()
    {
        int counter = 0;

        if (isEmpty())
            return null;
        else {
            if (this.last.size() <= 1)
            {
                T result = this.last.removeLast();
                this.last = null;
                this.nNodes--;
                return result;

            }
            else {
                return this.last.removeLast();
            }

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
                   currentnode.setItem(idx, element);
                   return;
                }
                else
                {
                    idx -= currentnode.size();
                    currentnode = currentnode.next;
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return (first == null) ? true : false;
    }

    public int size() {
        Node currentnode = this.first;
        int counter = 0;
        while (currentnode != null)
        {

            counter += currentnode.size();
            currentnode = currentnode.next;

        }
        return counter;
    }

    @Override
    public IList<T> shallowCopy() {
        UnrolledLinkedList<T>  newList = new UnrolledLinkedList<T>(this.blockSize);
        if (isEmpty())
            return null;
        else
        {
            Node currentnode = this.first;
            Node node = new Node();
            node.initNull();
            newList.nNodes++;
            newList.first = node;

            while (currentnode.next != null)
            {
                node = new Node();
                node.initNull();
                newList.nNodes++;
                newList.first.next = node;
                currentnode = currentnode.next;
            }
        }

        return newList;
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

