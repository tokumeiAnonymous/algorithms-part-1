import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private int num;
    private Item[] items;
    

    // construct an empty randomized queue
    public RandomizedQueue() {
        
        num = 0;
        items = (Item[]) new Object[2];
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
                
        if ( num >= items.length) resize();
        
        items[num++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        
        if (num == 0) throw new NoSuchElementException("The list is empty");
        
        if (num <= items.length / 4) resize(); 
        
        int rand = StdRandom.uniform(num);
        
        Item temp = items[rand];
        items[rand] = items[num - 1];
        items[num - 1] = null;
        num--;
        //System.out.println( "\t" + rand + "\t" + num);
        
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        
        if (num == 0) throw new NoSuchElementException("The list is empty");
        
        int rand = StdRandom.uniform(num);
        
        return items[rand];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        
        return new RandomIterator();
    }
    
    private void resize() {
        
        int len = num * 2;
        
        Item[] temp = (Item[]) new Object[len];
        
        for (int i = 0; i < num; i++) {
            temp[i] = items[i];
        }
        
        items = temp;
        
    }
    
    private class RandomIterator implements Iterator<Item>{
        
        private int iteratorCount;
        private Item[] iteratorItems;
        
        public RandomIterator() {
            iteratorCount = 0;
            iteratorItems = (Item[]) new Object[num];
            
            for (int i = 0; i < num; i++) {
                iteratorItems[i] = items[i];
            }
        }

        @Override
        public boolean hasNext() {
            
            //System.out.println(iteratorCount);
            return iteratorCount < num;
        }

        @Override
        public Item next() {
            
            if (!hasNext()) throw new NoSuchElementException("This list is empty");
            
            int rand = StdRandom.uniform(num - iteratorCount);
            
            Item temp = iteratorItems[rand];
            //System.out.println(rand);
            iteratorItems[rand] = iteratorItems[num - 1 - iteratorCount];
            iteratorCount++;
            //iteratorItems[iteratorItems.length - iteratorCount] = null;
            

            return temp;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("This is not allowed");
        }
        
    }

    // unit testing (required)
    public static void main(String[] args) {
        
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        
        
        /*
         * for (int i = 0; i < 100; i++) { System.out.println(StdRandom.uniform(10)); }
         */
        
        
        for (int i = 0; i < 10; i++) { test.enqueue(i); }
        
        System.out.println(test.size());
         
        int sample = test.sample();
        System.out.println(sample);
        
        System.out.println(test.isEmpty());
        
        //for (int i = 0; i < 10; i++) { System.out.println(test.dequeue()); }
        
        Iterator<Integer> iteratorTest = test.iterator();
        
        while (iteratorTest.hasNext()) {
            System.out.println("\t" + iteratorTest.next());
        }
        
        for (int i = 0; i < 10; i++) { System.out.println(test.dequeue()); }
          
        iteratorTest.remove();
    }

}