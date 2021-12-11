import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private int num;
    private Item[] items;
    private int count;

    // construct an empty randomized queue
    public RandomizedQueue() {
        
        num = 0;
        count = 0;
        items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        
        return num == 0;    
    }

    // return the number of items on the randomized queue
    public int size() {
        
        return num;
    }

    // add the item
    public void enqueue(Item item) {
        
        if (item == null) throw new IllegalArgumentException("Cannot be null");
        
        //System.out.println(count);
        
        if (count >= items.length) {
            
            resize();
         
        }
        
        num++;
        items[count++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        
        if (num == 0) throw new NoSuchElementException("The list is empty");
        
        if (num < 0.25 * items.length) resize(); 
        
        Item temp = null;
        
        while (temp == null) {
            int rand = StdRandom.uniform(count);
            temp = items[rand];
            items[rand] = null;
        
            }
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        
        Item temp = null;
        
        while (temp == null) {
            int rand = StdRandom.uniform(count);
            temp = items[rand];
        }
        
        return temp;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        
        return new RandomIterator();
    }
    
    private void resize() {
        
        int len = num * 2;
        
        Item[] temp = (Item[])new Object[len];
        
        for (int j = 0, i = 0; i < count; i++) {
            if (items[i] == null) continue;
            temp[j++] = items[i];
        }
        
        items = temp;
        count = num;
    }
    
    private class RandomIterator implements Iterator{
        
        private int iteratorCount = 0;

        @Override
        public boolean hasNext() {
            
            iteratorCount++;

            return count < num;
        }

        @Override
        public Object next() {
            
            if (iteratorCount > num) throw new NoSuchElementException("This list is empty");
            
            Item temp = null;
            
            while (temp == null) {
                
                int rand = StdRandom.uniform(count);
                temp = items[rand];
            
            }
            return temp;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("This is not allowed");
        }
        
    }

    // unit testing (required)
    public static void main(String[] args) {
        
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        
        
        
          for (int i = 0; i < 100; i++) { test.enqueue(i); }
          
          for (int i = 0; i < 20; i++) { System.out.println(test.dequeue()); }
         
         
        test.sample();
        test.size();
        test.isEmpty();
        
        Iterator<Integer> sample = test.iterator();
        
        sample.next();
          
          
        //test.enqueue(1);
        //int ans = test.dequeue();
        //System.out.println(ans);
        //System.out.println(StdRandom.uniform(1));
        //test.enqueue(2);
        
    }

}