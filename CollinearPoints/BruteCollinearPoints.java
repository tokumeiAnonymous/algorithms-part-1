
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    
    //private Point[] points;
    private LineSegment[] segments;
    
    public BruteCollinearPoints(Point[] points) {
        
        if (points == null) throw new IllegalArgumentException("Point can not be null");
        
        checkNullAndDuplicateEntry(points);
        
        double slope1, slope2, slope3;
        segments = new LineSegment[points.length];
        int counter = 0;
        
        Arrays.sort(points);
        
        for (int i = 0; i < points.length; i++) {
                   
            for (int j = i + 1; j < points.length; j++) {
                 
                slope1 = points[j].slopeTo(points[i]);
                
                for (int k = j + 1; k < points.length; k++) {
                    
                    slope2 = points[k].slopeTo(points[j]);
                    if (slope2 != slope1) continue;
                    
                    for (int ii = k + 1; ii  < points.length; ii++) {
                        
                        slope3 = points[ii].slopeTo(points[k]);
                        if (slope3 == slope2) {
                            //Point[] tempPoints = {points[i], points[j], points[k], points[ii]};
                            LineSegment tempLineSegment = new LineSegment(points[i], points[ii]);
                            System.out.println(counter);
                            segments[counter++] = tempLineSegment;
                            
                        }
                        
                    }
                }
            }
        }
        
    }
    
    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments;
    }
    
    /*private LineSegment savePoints(Point[] points) {
        
        Arrays.sort(points);
        
        
        return null;
    }*/
    
    private void checkNullAndDuplicateEntry(Point[] points) {
        
        if (points[0] == null) throw new IllegalArgumentException("Points can't be null or duplicate");
        
        for (int i = 1; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Points can't be null or duplicate");
            if (points[i - 1] == points[i]) throw new IllegalArgumentException("Points can't be null or duplicate");
        }
        
    }
    
    public static void main(String[] args) {
        
        In in = new In(args[0]);
        
        int n = in.readInt();
        Point[] points = new Point[n];
        
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            
            points[i] = new Point(x, y);
            
            //System.out.println(points[i]);
        }
        
        /*System.out.println();  
        
        Arrays.sort(points);
        
        for (int i = 0; i < n; i++) {
            System.out.println(points[i]);
        }*/
        
        
          StdDraw.enableDoubleBuffering(); StdDraw.setXscale(0, 32768);
          StdDraw.setYscale(0, 32768);
          
          for (Point p : points) { p.draw(); }
          
          StdDraw.show();
          
          BruteCollinearPoints collinear = new BruteCollinearPoints(points); for
          (LineSegment line: collinear.segments()) { StdOut.println(line); line.draw();
          }
          
          StdDraw.show();
          
          StdOut.println(collinear.numberOfSegments());
         
    }
    
}
