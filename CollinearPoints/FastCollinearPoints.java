import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private LineSegment[] segments;
    private double[] slopes;
    private int n;
    
    public FastCollinearPoints(Point[] points) {
        
        if (points == null) throw new IllegalArgumentException("Points can't be null or duplicate");
        
        Arrays.sort(points);
        checkNullAndDuplicateEntry(points);
        
        segments = new LineSegment[points.length];
        slopes = new double[points.length];
        n = 0;
        
        // this part creates a new array excluding 1 point that acts as base
        for (int i = 0; i < points.length; i++) {
            
            Point source = points[i];
            Point[] targets = new Point[points.length - 1];
            int k = 0;
            
            for (int j = 0; j < points.length; j++) {
                
                if (source.equals(points[j])) continue;
                targets[k++] = points[j];
            }
           
            Arrays.sort(targets, source.slopeOrder());
           
            getCollinear(targets, source);
        }
        
        
        
    }
    
    private void getCollinear(Point[] targets, Point source) {
        
        double prevSlope = source.slopeTo(targets[0]);
        int counter = 1;
        
        for (int i = 1; i < targets.length; i++) {
            
            double slope = source.slopeTo(targets[i]);
            // System.out.print(slope + " ");
            if (slope == prevSlope) counter++;
            else {
                // System.out.println();
                if(counter >= 3) {
                    
                    
                    if (!slopeAdded(slope)) {

                    // stores the points and the source to be used as an argument later

                    slopes[n] = slope;
                    int length = counter + 1;
                    // System.out.println(length);
                    Point[] collinear = new Point[length];
                    for (int j = i; j > i - counter; j--) {
                        collinear[--length] = targets[j];
                    }
                    collinear[0] = source;
                    addCollinearSegment(collinear);
                    
                }}
                // resets the counter and slope
                counter = 0;
                prevSlope = slope;
            }
         
        }
        
    }
    
    private boolean slopeAdded(double slope) {
        for (int i = 0; i < segments.length; i++) {
            if (Double.compare(slope, slopes[i]) == 0) {
            // System.out.println(slopes[i] + "   " + slope + true);
            return true;
            }
            // System.out.println(slopes[i] + "   " + slope + false);
        }
        
        return false;
    }
    
    private void addCollinearSegment(Point[] points) {
        Arrays.sort(points);
        // System.out.println(n);
        segments[n++] = new LineSegment(points[0], points[points.length -1]);
    }
    
    private void checkNullAndDuplicateEntry(Point[] points) {
        
        if (points[0].equals(null)) throw new IllegalArgumentException("Points can't be null or duplicate");
        
        for (int i = 1; i < points.length; i++) {
            if (points[i].equals(null)) throw new IllegalArgumentException("Points can't be null or duplicate");
            if (points[i - 1].equals(points[i])) throw new IllegalArgumentException("Points can't be null or duplicate");
        }
        
    }
    
    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments;
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
              
              if (!line.equals(null)) {
                  StdOut.println(line); line.draw();
              }
          }
          
          StdDraw.show(); 
          
          StdOut.println(collinear.numberOfSegments());
    }

}
