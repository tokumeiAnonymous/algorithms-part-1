import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private ArrayList<LineSegment> segments;
    private ArrayList<Node> slopeAndPoint;
    
    public FastCollinearPoints(Point[] points) {
        
        if (points == null) throw new IllegalArgumentException("Points can't be null or duplicate");
        
        Point[] p = new Point[points.length];
        
        System.arraycopy(points, 0, p, 0, points.length);
        checkNullEntry(p);
        
        Arrays.sort(p);
        checkDuplicateEntry(p);
        segments = new ArrayList<LineSegment>();
        slopeAndPoint = new ArrayList<Node>();
        
        // copies the array except one that serves as a base
        for (int i = 0; i < p.length; i++) {
            
            Point base = p[i];
            Point[] temp = new Point[p.length - 1 -i];
            System.arraycopy(p, i + 1, temp, 0, temp.length);
            
            // gets the points array with respect to base
            filterCollinearPoints(base, temp);
            
        }
        
        
    }
    // we created a node class since the LineSegment provided doesn't allow us to access its points
    private class Node {
        private double slope;
        private Point point;
        
        public Node(double slope, Point point) {
            this.slope = slope;
            this.point = point;
        }
        
        public double getSlope() {
            return this.slope;
        }
        public Point getPoint() {
            return this.point;
        }
    }
    
    private void filterCollinearPoints(Point base, Point[] p) {
       
        int counter = 2;
        Arrays.sort(p, base.slopeOrder());
       
        for (int i = 0; i < p.length - 1; i++) {
           
           if (Double.compare(base.slopeTo(p[i]), base.slopeTo(p[i + 1])) == 0) {
               counter++;
           }
           else {
               if (counter >= 4) {
                   
                   Node temp = new Node(base.slopeTo(p[i]), p[i]);
                   if (!isAdded(temp)) {
                       segments.add(new LineSegment(base, p[i]));
                       slopeAndPoint.add(temp);
                   }
               }
               
               counter = 2;

           }
        }
        // this catches if the last point has equal slope and won't enter the else statement
        if (counter >= 4) {
            
            Node temp = new Node(base.slopeTo(p[p.length - 1]), p[p.length - 1]);
            if (!isAdded(temp)) {
                segments.add(new LineSegment(base, p[p.length - 1]));
                slopeAndPoint.add(temp);
            }
        }
       
    }
    
    // should not use slope to determine if the line is already added since
    // two different lines can have the same slope
    private boolean isAdded(Node input) {
    
        for (Node node: slopeAndPoint) {
            if (node.getSlope() == input.getSlope() && node.getPoint() == input.getPoint()){
                return true;
            }
        }
        return false;
    }

    private void checkDuplicateEntry(Point[] p) {      
        for (int i = 1; i < p.length; i++) {
            if (p[i - 1].equals(p[i])) throw new IllegalArgumentException("Points can't be null or duplicate");
        }
        
    }
    
    private void checkNullEntry(Point[] p) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] == null) throw new IllegalArgumentException("Points can't be null or duplicate");
        }
    }
    
    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] temp = new LineSegment[segments.size()];
        int i = 0;
        for (LineSegment segment: segments) {
            temp[i++] = segment;
        }
        
        return temp;
    }
    
    public static void main(String[] args) {
        
        In in = new In(args[0]);
        
        int n = in.readInt();
        Point[] points = new Point[n];
        
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            
            points[i] = new Point(x, y);
            
            // System.out.println(points[i]);
        }
        
        /* System.out.println();  
        
        Arrays.sort(points);
        
        for (int i = 0; i < n; i++) {
            System.out.println(points[i]);
        } */
        
        
          StdDraw.enableDoubleBuffering(); 
          StdDraw.setXscale(0, 32768);
          StdDraw.setYscale(0, 32768);
          
          for (Point p : points) {
              p.draw(); 
          }
          
          StdDraw.show();
          
          FastCollinearPoints collinear = new FastCollinearPoints(points); 
           for (LineSegment line: collinear.segments()){
              
              if (line != null) {
                  StdOut.println(line); 
                  line.draw();
              }
          }
          
          StdDraw.show(); 
          
          StdOut.println(collinear.numberOfSegments());
    }

}