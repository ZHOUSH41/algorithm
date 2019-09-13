import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] rq;
    // construct an empty randomized queue
    public RandomizedQueue(){
        rq = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return size;
    }

    private void resize(int capcaity){
        Item[] temp = (Item[]) new Object[capcaity];
        for(int i = 0; i < size; i++){
            temp[i] = rq[i];
        }
        rq = temp;
    }

    // add the item
    public void enqueue(Item item){
        if (item == null) throw new IllegalArgumentException();
        if(size == rq.length) resize(2*rq.length);
        rq[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue(){
        if (size == 0) throw new NoSuchElementException();
        int rand = StdRandom.uniform(size);
        Item temp = rq[rand];
        rq[rand] = rq[size-1];
        rq[size-1] = null;
        size--;
        return temp;

    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (size == 0) throw new NoSuchElementException();
        int rand = StdRandom.uniform(size);
        Item temp = rq[rand];
        return temp;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{

        private int curNum = 0;

        public boolean hasNext(){
            return curNum < size;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }

        public Item next(){
            if(curNum >= size) throw new NoSuchElementException();
            return rq[curNum++];
        }

    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> test = new RandomizedQueue<>();
        test.enqueue(1);
        test.enqueue(2);
        test.enqueue(3);
        test.dequeue();
        test.dequeue();
        test.dequeue();

        Iterator<Integer> it = test.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }

}
