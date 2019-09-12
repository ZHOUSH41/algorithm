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

        if(isEmpty()){
            first = new Node();
            first.item = item;
            last = first;
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        oldfirst.prev = first;
        size++;
    }

    // add the item to the back
    public void addLast(Item item){
        if(item == null) throw new IllegalArgumentException();

        if(isEmpty()){
            last = new Node();
            last.item = item;
            first = last;
        } else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            oldlast.next = last;
            last.prev = oldlast;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if(isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if(isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev;
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
            return first == null;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }

        public Item next(){
            if(cur == null) throw new NoSuchElementException();
            Item item = cur.item;
            cur = cur.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer> test = new Deque<>();
        test.addLast(1);
        test.addLast(2);
        test.addLast(3);
        test.addFirst(4);
        System.out.println(test.removeFirst());
        System.out.println(test.removeLast());
        System.out.println(test.removeFirst());
        System.out.println(test.removeFirst());
    }

}