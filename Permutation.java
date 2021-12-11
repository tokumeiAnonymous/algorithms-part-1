import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    
    public static void main(String[] args) {
        
        int num = Integer.parseInt(args[0]);
        
        RandomizedQueue<String> random = new RandomizedQueue<String>();
        
        while(!StdIn.isEmpty()) {
            
            random.enqueue(StdIn.readString());
        }
        
        for (int i = 0; i < num; i++) {
            System.out.println(random.dequeue());
        }
        
    }

}
