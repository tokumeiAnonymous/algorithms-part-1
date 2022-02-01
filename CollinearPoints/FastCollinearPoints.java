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
        
        Point[] p = new Point[points.length];
        
        System.arraycopy(points, 0, p, 0, points.length);
        Arrays.sort(p);
        checkNullAndDuplicateEntry(p);
        
        segments = new LineSegment[points.length];
        slopes = new double[p.length];
        n = 0;
        
        // this part creates a new array excluding 1 point that acts as base
        for (int i = 0; i < p.length; i++) {
            
            Point source = p[i];
            Point[] targets = new Point[p.length - 1];
            int k = 0;
            
            for (int j = 0; j < p.length; j++) {
                
                if (source.equals(p[j])) continue;
                targets[k++] = p[j];
            }
           
            Arrays.sort(targets, source.slopeOrder());
           
            collinear(targets, source);
        }
        
        
        
    }
    
    private void collinear(Point[] targets, Point source) {
        
        double prevSlope = source.slopeTo(targets[0]);
        int counter = 1;
        
        for (int i = 1; i < targets.length; i++) {
            
            double slope = source.slopeTo(targets[i]);
            // System.out.print(slope + " ");
            if (slope == prevSlope) counter++;
            else {
                // System.out.println();
                if (counter >= 3) {
                    
                    
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
                    
                }
             }
                // resets the counter and slope
                counter = 0;
                prevSlope = slope;
            }
         
        }
        
    }
    
    private boolean slopeAdded(double slope) {
        for (int i = 0; i < segments.length; i++) {
            if (slope - slopes[i] == 0) {
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
    
    private void checkNullAndDuplicateEntry(Point[] p) {
        
        if (p[0] == null) throw new IllegalArgumentException("Points can't be null or duplicate");
        
        for (int i = 1; i < p.length; i++) {
            if (p[i] == null) throw new IllegalArgumentException("Points can't be null or duplicate");
            if (p[i - 1].equals(p[i])) throw new IllegalArgumentException("Points can't be null or duplicate");
        }
        
    }
    
    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        LineSegment[] temp = new LineSegment[segments.length];
        
        System.arraycopy(segments, 0, temp, 0, segments.length);
        
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
