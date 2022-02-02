import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private LineSegment[] segments;
    private Double[] slopes;
    private int n;
    
    public FastCollinearPoints(Point[] points) {
        
        if (points == null) throw new IllegalArgumentException("Points can't be null or duplicate");
        
        Point[] p = new Point[points.length];
        
        System.arraycopy(points, 0, p, 0, points.length);
        checkNullEntry(p);
        
        Arrays.sort(p);
        checkDuplicateEntry(p);
        
        segments = new LineSegment[points.length];
        n = 0;
        slopes = new Double[points.length];
        
        // this part creates a new array excluding 1 point that acts as base
        for (int i = 0; i < p.length; i++) {
            
            Point source = p[i];
            Point[] targets = new Point[p.length - i - 1];
            int k = 0;
            
            for (int j = i + 1; j < p.length; j++) {
               
                targets[k++] = p[j];
               
            }
           
            Arrays.sort(targets, source.slopeOrder());
           //do not filter if targets has less than three points
            if (targets.length >= 3) filterCollinear(targets, source);
        }
        
        removeNull();
        
        
        
    }
    
    private void filterCollinear(Point[] targets, Point source) {
                         
        int counter = 1;
        
        for (int i = 1; i < targets.length; i++) {
            
            if (Double.compare(source.slopeTo(targets[i]), source.slopeTo(targets[i - 1])) == 0) {
                counter++;
            }
            
            else {
                if (counter >= 3) {
                    
                    
                    if (!isSlopeAdded(source.slopeTo(targets[i - 1]))) {


                    slopes[n] = source.slopeTo(targets[i - 1]);
                    
                    segments[n++] = new LineSegment(source, targets[i]);
                    
                    // stores the points
                    /* Point[] collinearPoints = new Point[counter + 1];
                    int k = 0;
                    collinearPoints[k++] = source;
                    for (int j = i - 1; j >= i - counter; j--) {
                        
                        collinearPoints[k++] = targets[j];
                        
                    } 
                    
                    addEdgePoints(collinearPoints); 
                    */
                          
                }
             }
                // resets the counter and slope
                
                counter = 1;
                // prevSlope = slope;
            }
         
        }
        // if the last point has equal slope and counter >= 3
        if (counter >= 3 && !isSlopeAdded(source.slopeTo(targets[targets.length - 1]))) {
            segments[n++] = new LineSegment(source, targets[targets.length - 1]);
        }
        
        
    }
    /*
    private void addEdgePoints(Point[] collinearPoints) {
        
        Arrays.sort(collinearPoints);
        
        segments[n++] = new LineSegment(collinearPoints[0], collinearPoints[collinearPoints.length - 1]);
        
    }
   */
    private boolean isSlopeAdded(double slope) {
        
        int i = 0;
        while (slopes[i] != null) {
            if (Double.compare(slope, slopes[i]) == 0) {
            return true;
            }
            i++;
        }
        
        return false;
    }
     
    private void removeNull() {
        LineSegment[] temp = new LineSegment[n];
        
        for (int i = 0; i < n; i++) {
            temp[i] = segments[i];
        }
        
        segments = temp;
        
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
