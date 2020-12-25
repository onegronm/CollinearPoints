import java.util.ArrayList;
import java.util.Arrays;

/*
 * Examines 4 points at a time and checks whether they all lie on the same
 * line segment, returning all such line segments. To check whether the 4
 * points p, q, r, and s are collinear, check whether the three slopes
 * between p and q, between p and r, and between p and s are all equal.
 */
public class BruteCollinearPoints {

    ArrayList<LineSegment> segments = new ArrayList<>();

    /**
     * Finds all line segments containing 4 points
     * @param points
     */
    public BruteCollinearPoints(Point[] points){

        if (points == null){
            throw new IllegalArgumentException("Argument to the constructor is null.");
        }

        for (int i = 0; i < points.length; i++){
            if (points[i] == null)
                throw new IllegalArgumentException("Point in the array is null.");
        }

        Arrays.sort(points);

        // check for duplicates
        for(int i = 1; i < points.length; i++){
            if (points[i].compareTo(points[i-1]) == 0)
                throw new IllegalArgumentException("Array contains repeated points.");
        }

        for (int i = 0; i < points.length; i++){
            for (int j = i+1; j < points.length; j++){
                double slopePQ = points[i].slopeTo(points[j]);
                for (int k = j+1; k < points.length; k++){
                    double slopePR = points[i].slopeTo(points[k]);
                    // only check fourth point if the first three points are collinear
                    if (slopePQ != slopePR)
                        continue;

                    for (int l = k+1; l < points.length; l++){
                        double slopePS = points[i].slopeTo(points[l]);
                        if (slopePS == slopePQ){
                            segments.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
    }

    /**
     * Number of line segments containing 4 points exactly once
     * @return the number of line segments
     */
    public int numberOfSegments(){
        return segments.size();
    }

    public LineSegment[] segments(){
        return segments.toArray(new LineSegment[0]);
    }
}
