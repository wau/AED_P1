
package aed.collections;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
//teste



public class UnrolledLinkedList<T> implements IList<T> {

    //node = block
    private Node first;
    private Node last;
    private Node next;
    private int nNodes;
    private int blockSize;

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

        public void CopyNode(Node n) {

            this = n;

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

            this.items[counter] = item;
            this.counter++;
        }
        public T removeLast() {
            //System.out.println("counter" + this.counter);
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

        public Node shallowCopy() {
            Node newNode = new Node();

            for (int i = 0; i < blockSize; i++) {
                newNode.items[i] = this.items[i];
            }
            newNode.counter = this.counter;
            newNode.next = this.next.shallowCopy();
            return newNode;
        }

        public void leftShift(int idx)
        {
            @SuppressWarnings("unchecked")
            T[] newArr =  (T[])new Object[blockSize];
            for (int i = 0; i < this.size(); i++)
            {
                if (i < idx)
                    newArr[i] = this.items[i];
                else
                    newArr[i] = this.items[i+1];
            }
            //System.out.println(Arrays.deepToString(newArr));
            //start.items = newArr.clone();
            System.arraycopy(newArr, 0, this.items, 0, blockSize);

        }

        public void removeSince(int idx, int startSize)
        {
            for  (int i = idx; i < startSize; i++)
            {
                this.items[i] = null;
                this.counter--;

            }
        }
    }
    /////////////////////
    public UnrolledLinkedList() {
        this.first = null;
        this.blockSize = 300;
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

    public static double calculateAverageExecutionTime(int n, int blockSize)
    {
        UnrolledLinkedList<Integer> List = new UnrolledLinkedList<Integer>(blockSize);

        int trials = 45;
        double totalTime = 0;
        Random r = new Random();

        for (int i2 = 0; i2 < n; i2++)
        {
            List.add(r.nextInt());
        }
        for(int i = 0; i < trials; i++)
        {
            long time = System.currentTimeMillis();

            List.get(n/2);

            totalTime += System.currentTimeMillis() - time;
        }
        return totalTime/trials;

        /*int trials = 45;
        double totalTime = 0;
        Random r = new Random();
        for(int i = 0; i < trials; i++)
        {
            int example = r.nextInt();
            long time = System.currentTimeMillis();
            for (int i2 = 0; i2 < n; i2++)
            {
                List.add(example);
            }

            totalTime += System.currentTimeMillis() - time;
        }
        return totalTime/trials;*/
    }

    public static void testeRazao()
    {
        int start = 1000;
        int maxx = 100000000;
        int counter = 0;

        int blocksizes[] = {16, 32, 256, 512};
        double times[] = {0.0, 0.0, 0.0, 0.0};

        for (int i = start; i < maxx; i*=2)
        {
            for (int j = 0; j < blocksizes.length;  j++)
            {
                times[j] = calculateAverageExecutionTime(i, blocksizes[j]);
            }
            System.out.println("n =" + i + ' ' + times[0] + ' '   + times[1] + ' ' + times[2] +  ' ' + times[3]);
        }
    }


    public static void main(String[] args)  {

        UnrolledLinkedList<Integer> List = new UnrolledLinkedList<Integer>(4);

        List.add(1);
        List.add(2);
        List.add(3);

        List.addAt(0, 0);
        
        List.add(77);
        List.addAt(0, 0);
        List.addAt(0, 0);

        List.addAt(3, 10);
       // List.addAt(2, 7);
    //    List.addAt(2, 77);



       /* List.add(5);
        List.add(6);


        List.addAt(0, 25);
        List.addAt(0, 25);
        List.addAt(1, 30);
        List.addAt(3, 68);
        List.addAt(3, 69);*/



         UnrolledLinkedList<Integer> shallow =  (UnrolledLinkedList<Integer>) List.shallowCopy();
  //      System.out.println(shallow.nNodes);
        // System.out.println(List.size());

      //  List.set(0, 3);

        System.out.println(Arrays.deepToString(List.getArrayOfBlocks()));
          System.out.println(Arrays.deepToString(shallow.getArrayOfBlocks()));

        // ENSAIOS DE RAZÃO DOBRADA

       /* int n = 500000*2;
        double previousTime = calculateAverageExecutionTime(n, 16);
        System.out.println(previousTime);*/

        //bls = blocksize

        //(TEMPO EM MILISEGUNDOS)
        // TESTE DO METODO ADD
        // N / bls = 2 / bls = 4 / bls = 8 / bls = 16 / bls = 32 / bls = 64 / bls = 256
        //n = 500 / 0.06667 / 0.06667 /  0.06667  /0.06667  / 0.06667  / 0.06667 / 0.04446
        //  n = 1000 / 0.08889 / 0.08889/  0.08889 / 0.08889/ 0.06667 /  0.08889 / 0.08889
        //  n = 2000 / 0.17778/ 0.15556/  0.17778 / 0.15556/ 0.13333 /  0.15556 / 0.15556
        //  n = 4000 / 0.24444/ 0.24444/  0.24444 / 0.24444/ 0.2222 / 0.2222 / 0.2
        //  n = 8000 / 4.1777/ 0.42222/ 0.355555 / 0.355555/ 0.355555 /  0.355555 / 0.3333
        //  n = 16000 / 8.08889/ 4.06666/  3.31111 / 2.4222/ 2.4222 /  0.55555 / 0.5111
        //  n = 32000 / 13.0666/ 9.7555/  5.9111 / 4.2888/ 1.9333 /  1.5111 / 1.17777
        //  n = 64000 / 23.26666/ 14.57777/  10.4222 / 7.2888/ 4.17777 /  3.13333 / 2.1111

        //Portanto, r ~ 2 (razão aproximadamente 2)
        //Logo T(n) ~ n,  o metodo add tem complexidade n

        /////////////////////////////////////////////////////////////////////////////////////

        //TESTE DO METODO GET
        //USANDO A FUNÇÃO //testeRazao();
        //(TEMPO EM MILISEGUNDOS)

        // N /   bls = 16 / bls = 32   /   bls = 256   /   bls = 512

        // n =256000 0.06666666666666667     0.022222222222222223        0.0                             0.0
        // n =512000 0.08888888888888889     0.13333333333333333         0.022222222222222223            0.0
        // n =1024000 0.17777777777777778    0.08888888888888889         0.022222222222222223            0.0
        //n =2048000 1.2666666666666666     0.2                         0.13333333333333333             0.022222222222222223
        //n =4096000 2.7555555555555555     0.8888888888888888          0.08888888888888889             0.044444444444444446
        // n =8192000 5.977777777777778      15.777777777777779           0.5555555555555556             0.37777777777777777
        // n =16384000 24.666666666666668    38.91111111111111            1.6222222222222222             0.8666666666666667
        //n =32768000 51.77777777777778     65.42222222222222            11.777777777777779             1.4666666666666666
        //n =65536000 95.86666666666666     124.15555555555555            27.266666666666666            14.466666666666667

        //Portanto, r ~ 2 (razão aproximadamente 2)
        //Logo T(n) ~ n,  o metodo get tem complexidade n,  no entanto  só tem alguma importância a partir de n's muito grandes
        //e concluimos que quanto maior o blocksize menor o tempo de execução

        // Tendo em conta os testes concluímos que quanto maior for o blocksize maior será a eficiencia dos metodos add e get,
        //no entanto desperdiçamos memória para valores muito grandes portanto diria que um bom valor default para o blocksize seria
        // por exemplo 32.



        ////////////
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
                result[counter][i] = currentnode.getItem(i);
                //System.out.println(currentnode.getItem(i));
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
            Node currentNode = this.first;
            while (currentNode.next != null)
            {
                currentNode = currentNode.next;
            }
            //chegamos ao fim dos nodes

            // if (this.last.size() < this.blockSize-1)
            if (currentNode.size() < this.blockSize)
            {
                currentNode.addInNode(item);

            }
            else {//block will be full

                Node newNode = new Node();
                //for(int i = this.last.size() / 2 + 1; i < this.last.size(); i++)
                for(int i = blockSize/2 ; i < currentNode.size(); i++)
                {
                    T citem = currentNode.getItem(i);
                    newNode.addInNode(citem);
                    //last.removeIdx(i);
                }
                currentNode.removeSince(blockSize/2,currentNode.size());
                newNode.addInNode(item);
                currentNode.next = newNode;
                //  this.last = newNode;
                this.nNodes++;

            }
        }
    }

    void rightShift(Node start, int startIdx)
    {
        Node node = start;
        @SuppressWarnings("unchecked")
        T[] newArr =  (T[])new Object[blockSize];

        int itemsToAdd =  start.size() - startIdx;

        for (int i = 0; i < start.size(); i++)
        {
            if (i >= startIdx)
            {
                newArr[i+1] = start.items[i];
            }
            else {
                newArr[i] = start.items[i];
            }
        }

        //System.out.println(Arrays.deepToString(newArr));

        System.arraycopy(newArr, 0, start.items, 0, blockSize);
    }

    private void moveHalf(Node node)
    {
        Node newNode = new Node();

        for(int i = blockSize/2; i <blockSize; i++)
        {
            T citem = node.getItem(i);
            newNode.addInNode(citem);
            // node.removeIdx(i);
        }

        node.removeSince(blockSize/2, blockSize);


        Node next = node.next;
        node.next = newNode;
        newNode.next = next;
        this.nNodes++;
    }


    void chooseTwo(Node previous, Node current, int idx, T item)
    {
        Node iterator = previous;

        int counter = 0;

        while (counter < 2)
        {
            boolean isInThisBlock = (idx) < iterator.size() && (idx) >= 0;

            if (isInThisBlock)
            {
                rightShift(iterator, idx); // right shift first
                iterator.setItem(idx, item);
                iterator.counter++;
            }
            else {
                iterator = current;
            }

            counter++;
        }

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
                boolean isInThisBlock = (idx) < currentnode.size() && (idx) >= 0;

                if (isInThisBlock)
                {

                    if (currentnode.size() == blockSize)
                    {
                        moveHalf(currentnode);
                        int counter = 0;
                        while (counter < 2)
                        {
                            boolean inTHis = (idx) < currentnode.size() && (idx) >= 0;

                            if (inTHis)
                            {
                                rightShift(currentnode, idx); // right shift first
                                currentnode.setItem(idx, item);
                                currentnode.counter++;
                                return;
                            }
                            else {
                                idx -= currentnode.size();
                                currentnode = currentnode.next;
                            }

                            counter++;
                        }
                    }
                    else {
                            rightShift(currentnode, idx); // right sh
                            currentnode.setItem(idx, item);
                    }       currentnode.counter++;





                    //ERRO AQUI
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


    //TODO: improve remove to work on all cases
    public T remove()
    {
        int counter = 0;

        if (isEmpty())
            return null;
        else {
            Node currentNode = this.first;
            Node previous = null;
            while (currentNode.next != null && counter < this.nNodes-1)
            {
                currentNode = currentNode.next;
                counter++;
            }
            //chegamos ao fim dos nodes

            if (currentNode.size() <= 1 && counter >= 1)
            {
                T result = currentNode.removeLast();
                //currentNode.next = null;
                this.nNodes--;

                currentNode = null;
                return result;

            }
            else {
                return currentNode.removeLast();
            }

        }
    }



    @Override
    public T remove(int index) {
        if (!isEmpty())
        {
            Node currentnode = this.first;
            Node previous = null;
            int cnt = currentnode.size();
            int idx = index;

            while (currentnode != null)
            {
                boolean isInThisBlock = (idx) <= currentnode.size()-1 && (idx) >= 0;

                if (isInThisBlock && currentnode.size() > 0)
                {
                    T result = currentnode.removeIdx(idx);

                    if (currentnode.size() <= 0)
                    {
                        if (currentnode == this.first)
                        {
                            this.first = this.first.next;
                        }
                        else {
                            previous.next = currentnode.next;
                        }

                        currentnode = null;
                        this.nNodes--;

                    }
                    else {
                        currentnode.leftShift(idx);
                    }
                    return result;
                }
                else
                {
                    idx -= currentnode.size();
                    previous = currentnode;
                    currentnode = currentnode.next;
                }
            }
            return null;
        }
        else {
            return null;
        }
    }

    public T get(int index) {
        if (!isEmpty())
        {
            Node currentnode = this.first;
            int cnt = currentnode.size();
            int idx = index;
            int counter = 0;

            while (currentnode != null  && counter < this.nNodes)
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
                counter++;
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
            int counter = 0;

            while (currentnode != null && counter < this.nNodes)
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
                counter++;
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


    /*public IList<T> shallowCopy() {
        UnrolledLinkedList<T>  newList = new UnrolledLinkedList<T>(this.blockSize);
        if (isEmpty())
            return null;
        else
        {
            int counter = 0;
            Node currentnode = this.first;

            newList.first = this.first;

            //Deletable delTemp = (Deletable ) del.clone();

            Node nodeNewCurrent = newList.first;

            while (currentnode != null  && counter < this.nNodes)
            {
                newList.nNodes++;
                nodeNewCurrent.next = currentnode.next;

                counter++;
                currentnode = currentnode.next;
            }

        }

        return newList;


    }*/

    /*@Override
    public IList<T> shallowCopy()  {
        UnrolledLinkedList<T>  newList = new UnrolledLinkedList<T>(this.blockSize);

        int counter = 0;

        Node originalNode = this.first;
       // Node newNode = newList.first;
        newList.first = new Node();



        while (originalNode != null && counter < this.nNodes)
        {

          //  System.arraycopy();


            originalNode = originalNode.next;
            counter++;
        }

        return newList;
    }*/

    @Override
    public IList<T> shallowCopy() {
        UnrolledLinkedList<T>  newList = new UnrolledLinkedList<T>(this.blockSize);


    }
    /* public IList<T> shallowCopy() {
        UnrolledLinkedList<T>  newList = new UnrolledLinkedList<T>(this.blockSize);

        newList.first = new Node();
        Node currentnode = this.first;

        @SuppressWarnings("unchecked")
        T[] copy2 = (T[])new Object[this.blockSize];
        System.arraycopy( currentnode.items, 0, copy2, 0, blockSize);

        for (int i = 0; i < blockSize; i++)
        {
            //  nodeNewCurrent.items[i] = copy[i];
            newList.first.setItem(i, copy2[i]);
            newList.first.counter++;
        }

        Node iteratorNode;

        iteratorNode = newList.first;
        int counter = 0;
        newList.nNodes++;

        while (counter < this.nNodes-1)
        {
            currentnode = currentnode.next;

            @SuppressWarnings("unchecked")
            T[] copy = (T[])new Object[this.blockSize];


           // System.arraycopy( currentnode.items, 0, copy, 0, blockSize);
            iteratorNode.next = new Node();
            for (int i = 0; i < blockSize; i++)
            {
                copy[i] = currentnode.getItem(i);
            }


            for (int i = 0; i < blockSize; i++)
            {
                iteratorNode.next.setItem(i, copy[i]);//.setItem(i, copy[i]);
                iteratorNode.next.counter++;
            }
            counter++;
            newList.nNodes++;
        }
    return newList;

    }*/
  /* public IList<T> shallowCopy() {
        UnrolledLinkedList<T>  newList = new UnrolledLinkedList<T>(this.blockSize);
        if (isEmpty())
            return null;
        else
        {
            Node currentnode = this.first;

            newList.first = currentnode;

            Node nodeNewCurrent = new Node();


            while (currentnode != null)
            {
                nodeNewCurrent = new Node();
                newList.nNodes++;
                for (int i = 0; i < blockSize; i++)
                {
                    nodeNewCurrent.setItem(i, currentnode.getItem(i));
                    nodeNewCurrent.counter++;
                }

                currentnode = currentnode.next;

            }

        }

        return newList;


    }*/


    //ITERADOR
    private class UnrolledLinkedListIterator implements Iterator<T>
    {
        Node it;
        public int idx;
        public int counter;
        public int currentNode;
        public int size;
        UnrolledLinkedListIterator()
        {
            this.it = first;
            this.idx = 0;
            this.counter = 0;
            this.size = size();
            // System.out.println(this.size);
        }

        public boolean hasNext() {

            return (this.counter < size && this.it != null && currentNode <= nNodes);
        }
        public T next()
        {
            T result = it.getItem(idx);

            if (this.idx < this.it.size()-1) {
                this.idx++;
            }
            else {
                this.it = this.it.next;
                currentNode++;
                this.idx = 0;
            }
            this.counter++;
            return result;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new UnrolledLinkedListIterator();
    }
}
