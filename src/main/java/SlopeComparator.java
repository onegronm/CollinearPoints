import java.util.Comparator;

public class SlopeComparator implements Comparator<Point> {

    Point p0; // the invoking Point (x0,y0)

    public SlopeComparator(Point p) {
        p0 = p;
    }

    @Override
    public int compare(Point p1, Point p2) {
        double slopeP1 = p0.slopeTo(p1);
        double slopeP2 = p0.slopeTo(p2);

        if (slopeP1 < slopeP2)
            return -1;
        else if (slopeP1 > slopeP2)
            return 1;
        else
            return 0;
    }
}
