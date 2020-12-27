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

            // Copy array to not affect original order
            Point[] pointsCopy = Arrays.copyOf(points, points.length);

            // For each other point q, sort the points according to the slopes they make with p
            Arrays.sort(pointsCopy, p.slopeOrder());

            // Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points, together with p, are collinear.
            int j = 1;
            int adjacentCount = 1;
            double slopePQ = p.slopeTo(pointsCopy[j++]);

            while (j < pointsCopy.length){
                double slope = p.slopeTo(pointsCopy[j]);
                if(slope != slopePQ) {

                    if (adjacentCount >= 3){
                        segments.add(new LineSegment(p, pointsCopy[j-1]));
                    }

                    slopePQ = slope;
                    adjacentCount = 1;
                }
                else {
                    adjacentCount++;
                }
                j++;
            }

            if (adjacentCount >= 3){
                segments.add(new LineSegment(p, points[j-1]));
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