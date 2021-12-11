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

    private class DequeIterator implements Iterator{
        
        private Node current = first;

        @Override
        public boolean hasNext() {
            
            current = current.next;
            current.prev = null;
            
            return current != null;
        }

        @Override
        public Object next() {
            
            if(current == null) throw new NoSuchElementException("The list is empty");
            
            Item item = current.item;
            
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("This is not allowed");
        }
        
    }
    
    // unit testing (required)
    public static void main(String[] args) {
        
        Deque<String> test = new Deque<String>();
        test.addFirst("Testing");
        //System.out.println(test.removeLast());
        test.addFirst("Testing2");
        //System.out.println(test.removeFirst());
        //for (String item : test) {
        //    System.out.println(item);
        //}
        String res1 = test.removeFirst();
        String res2 = test.removeFirst();
        test.isEmpty();
        test.size();
        
        test.iterator();
        
        System.out.println(res1 + "\t" + res2);
        
    }

}