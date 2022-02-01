
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/*
 * Get segments with 4 collinear points
 * Limitation: Does not work with points that has more than 4 collinear points
 */

public class BruteCollinearPoints {
    
    private LineSegment[] segments;
    
    public BruteCollinearPoints(Point[] points) {
        
        if (points == null) throw new IllegalArgumentException("Point can not be null");
        
        Point[] p = new Point[points.length];
        
        System.arraycopy(points, 0, p, 0, points.length);
        
        checkNullEntry(p);
        
        Arrays.sort(p);
        checkDuplicateEntry(p);
        
        double slope1, slope2, slope3;
        segments = new LineSegment[p.length];
        int counter = 0;
        
        
        
        for (int i = 0; i < p.length; i++) {
                   
            for (int j = i + 1; j < p.length; j++) {
                 
                slope1 = p[j].slopeTo(p[i]);
                
                for (int k = j + 1; k < points.length; k++) {
                    
                    slope2 = p[k].slopeTo(p[j]);
                    if (slope2 == slope1) {
                    
                        for (int ii = k + 1; ii  < p.length; ii++) {
                        
                            slope3 = p[ii].slopeTo(p[k]);
                            if (slope3 == slope2) {
                             // Point[] tempPoints = {points[i], points[j], points[k], points[ii]};
                                LineSegment tempLineSegment = new LineSegment(p[i], p[ii]);
                                // System.out.println(counter);
                                // System.out.println(p[i] + ", " + p[j] + ", " + p[k] + ", " + p[ii]);
                                segments[counter++] = tempLineSegment;
                            }
                        }
                    }
                }
            }
        }
        sanitize(segments, counter);
        
    }
    
    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        LineSegment[] temp = new LineSegment[segments.length];
        
        System.arraycopy(segments, 0, temp, 0, segments.length);
        
        return temp;
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
    
    /*
     * Removes the null entries since we declare 
     * a LineSegment array with the length equals to points
     */
    private void sanitize(LineSegment[] argSegments, int counter) {
        
        LineSegment[] temp = new LineSegment[counter];
        
        for (int i = 0; i < counter; i++) {
            temp[i] = argSegments[i];
        }
        
        this.segments = temp;
        
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
          
          BruteCollinearPoints collinear = new BruteCollinearPoints(points); 
           for (LineSegment line: collinear.segments()) {
              
              if (line != null) {
                  StdOut.println(line); 
                  line.draw();
              }
          }
          
          StdDraw.show(); 
          
          StdOut.println(collinear.numberOfSegments());
         
    }
    
}
