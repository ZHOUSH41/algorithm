import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node{
        Item item;
        Node prev;
        Node next;
    }
    private Node first, last;
    private int size;

    // construct an empty deque
    public Deque(){
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if(item == null) throw new IllegalArgumentException();

        Node oldfirst = first;
        first = new Node();
        first.item = item;

        if(isEmpty()) last = first;
        else {
            first.next = oldfirst;
            oldfirst.prev = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item){
        if(item == null) throw new IllegalArgumentException();

        Node oldlast = last;
        last = new Node();
        last.item = item;

        if(isEmpty()) first = last;
        else {
            oldlast.next = last;
            last.prev = oldlast;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if(isEmpty()) throw new NoSuchElementException();
        Item item = first.item;

        if(first.next == null) last = null;
        else first = first.next;

        //first.prev = null;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if(isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        if(last.prev == null) first = null;
        else  last = last.prev;

        last.next = null;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{
        private Node cur = first;

        public boolean hasNext(){
            return cur != null;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }

        public Item next(){
            if(!hasNext()) throw new NoSuchElementException();
            Item item = cur.item;
            cur = cur.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer> test = new Deque<>();
//        test.addLast(1);
//        test.addLast(2);
//        test.addLast(3);
//        test.addFirst(4);
//        test.addFirst(1);
//        test.addFirst(2);
        test.addFirst(3);
        test.addFirst(4);

        System.out.println(test.removeLast());
        System.out.println(test.removeLast());
//
//        test.addFirst(1);
//        test.addFirst(2);
//        System.out.println(test.removeFirst());
//        System.out.println(test.removeFirst());
        Iterator<Integer> it = test.iterator();
        while(it.hasNext()){
            //System.out.println(test.iterator().hasNext());
            System.out.println(it.next());
        }
//        System.out.println(test.removeFirst());
//        System.out.println(test.removeLast());
//        System.out.println(test.removeFirst());
//        System.out.println(test.removeFirst());
    }

}