import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    ArrayList<LineSegment> segments = new ArrayList<>();

    /**
     * finds all line segments containing 4 or more points
     * @param points
     */
    public FastCollinearPoints(Point[] points){

        if (points == null){
            throw new IllegalArgumentException("Argument to the constructor is null.");
        }

        for (int i = 0; i < points.length; i++){
            if (points[i] == null)
                throw new IllegalArgumentException("Point in the array is null.");
        }

        // sort points by their x,y coordinates
        Arrays.sort(points);

        // check for duplicates
        for(int i = 1; i < points.length; i++){
            if (points[i].compareTo(points[i-1]) == 0)
                throw new IllegalArgumentException("Array contains repeated points.");
        }

        for(int i=0; i<points.length; i++){

            Point p = points[i];

            // copy array with every point but p
            Point[] pointsCopy = new Point[points.length-1];

            int k = 0;
            for(int j=0; j<points.length; j++){
                if(p.compareTo(points[j]) != 0)
                    pointsCopy[k++] = points[j];
            }

            // For each other point q, sort the points according to the slopes they make with p
            Arrays.sort(pointsCopy, p.slopeOrder());

            // Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points, together with p, are collinear.
            double slope = 0;
            int numberOfCollinearPoints = 1;
            for(int j=0; j<pointsCopy.length; j++){
                double newSlope = p.slopeTo(pointsCopy[j]);
                if(slope != newSlope) {

                    if (numberOfCollinearPoints > 3){
                        segments.add(new LineSegment(p, pointsCopy[j-1]));
                    }

                    slope = newSlope;
                    numberOfCollinearPoints = 2;
                }
                else {
                    numberOfCollinearPoints++;
                }

                if (j == pointsCopy.length-1 && numberOfCollinearPoints > 3){
                    segments.add(new LineSegment(p, pointsCopy[j-1]));
                }
            }
        }
    }

    /**
     * the number of line segments
     * @return
     */
    public int numberOfSegments(){
        return segments.size();
    }

    /**
     * the line segments
     * @return
     */
    public LineSegment[] segments(){
        return segments.toArray(new LineSegment[0]);
    }
}