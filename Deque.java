import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>{

    private Item[] items;
    private int first;
    private int last;
    
    
    public Deque() {
        items =(Item[]) new Object[1];
        first = 0;
        last = 1;
    }
    
    public boolean isEmpty() {
        
        return last == first;
    }
    
    public int size() {
        
        return last - first;
    }
    
    public void addFirst(Item item) {
        
        if (first < 0) {
            resizeFirst();
        }
        
        /*
         * This is to avoid the corner case
         * when the array is empty and  the client decided
         * to addFirst and addLast or 
         * addLast and addFirst
         * resulting to replacing the former by the latter
         */
        if (first == last) last++;      
        
        items[first--] = item;
    }
    
    private void resizeFirst() {
        
        int len = (last - first) * 2;
        Item temp[] =(Item[]) new Object[len];
        int tempFirst = (int) (0.25 * len);
        
        for (int j = 0, i = tempFirst; i < (last - first); i++) {
            
            temp[i] = items[j++];
        }
        
        last = tempFirst + (last - first);
        first = tempFirst;
        items = temp;
    }
    
    public void addLast(Item item) {
        
        if (last >= items.length) {
            resizeLast();
        }
        
        /*
         * This is to avoid the corner case
         * when the array is empty and  the client decided
         * to addFirst and addLast or 
         * addLast and addFirst
         * resulting to replacing the former by the latter
         */
        if (last == first) first--; 
        
        items[last++] = item;

    }
    
    private void resizeLast() {
        
        int len = (last - first) * 2;
        Item temp[] =(Item[]) new Object[len];
        int tempFirst = (int) (0.25 * len);
        
        for (int j = 0, i = tempFirst; i < (last - first); i++) {
            
            temp[i] = items[j++];
        }
        
        last = tempFirst + (last - first);
        first = tempFirst;
        items = temp;
    }
    
    public Item removeFirst() {
        
        return items[first++];
    }
    
    public Item removeLast() {
        
        return items[last--];
    }
    
    @Override
    public Iterator<Item> iterator(){
        
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item>{

        @Override
        public boolean hasNext() {

            return false;
        }

        @Override
        public Item next() {

            return null;
        }
        
    }
    
    public static void main(String[] args) {
        
    }
    

}
