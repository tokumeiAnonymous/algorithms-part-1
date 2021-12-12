import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node last;
    private Node first;
    private int num;
    
    
    // construct an empty deque
    public Deque() {
        
        first = new Node();
        last = new Node();
        num = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        
        return num == 0;
    }

    // return the number of items on the deque
    public int size() {
        
        return num;
    }

    // add the item to the front
    public void addFirst(Item item) {
        
        if (item == null) throw new IllegalArgumentException("You can't insert a null");
        
        Node temp = first;
        first = new Node();
        first.item = item;
        first.next = temp;
        
        if (num == 0) {
                last = first;
        } else {
            first.next.prev = first;
        }
        
        num++;
    }

    // add the item to the back
    public void addLast(Item item) {
        
        if (item == null) throw new IllegalArgumentException("You can't insert a null");
        
        Node temp = last;
        last = new Node();
        last.item = item;
        last.prev = temp;
        
        if (num == 0) {
                first = last;
        } else {
            last.prev.next = last;
        }
        
        num++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        
        if (num == 0) throw new NoSuchElementException("The list is already empty!");
        
        Item item = first.item;
        num--;
        
        if (num == 0) {
            first = null;
            last = null;
            
        } else {
            
            first = first.next;
            first.prev = null;
        }
        
        
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        
        if (num == 0) throw new NoSuchElementException("The list is already empty!");
        
        Item item = last.item;
        num--;
        
        if (num == 0) {
            first = null;
            last = null;
        } else {
           last = last.prev;
           last.next = null;
        }
        
        
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        
        return new DequeIterator();
    }
    
    private class Node{
        
        private Item item;
        private Node prev;
        private Node next;
        
    }

    private class DequeIterator implements Iterator<Item>{
        
        private Node current = first;

        @Override
        public boolean hasNext() {
            
            return current != null;
        }

        @Override
        public Item next() {
            
            if(current == null) throw new NoSuchElementException("The list is empty");
            
            Item item = current.item;
            current = current.next;
            //current.prev = null;
            
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("This is not allowed");
        }
        
    }
    
    // unit testing (required)
    public static void main(String[] args) {
        
        Deque<Integer> test = new Deque<Integer>();
        
        for (int i = 0; i < 10; i++) { test.addLast(i); }
        
        System.out.println(test.size());
                 
        System.out.println(test.isEmpty());
        
        Iterator<Integer> iteratorTest = test.iterator();
        
        while (iteratorTest.hasNext()) {
            System.out.println("\t" + iteratorTest.next());
        }
        
        for (int i = 0; i < 10; i++) { System.out.println(test.removeLast()); }
        
        for (int i = 0; i < 10; i++) { test.addFirst(i); }
        
        for (int i = 0; i < 10; i++) { System.out.println(test.removeFirst()); }
        
        iteratorTest.remove();
        
    }

}