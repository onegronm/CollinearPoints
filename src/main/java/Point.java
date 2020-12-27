/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

    public class SlopeOrder implements Comparator<Point> {

        Point p0; // the invoking Point (x0,y0)

        public SlopeOrder(Point p) {
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

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        double deltaX = that.x - this.x;
        double deltaY = that.y - this.y;

        if (deltaX == 0 && deltaY == 0)
            return Double.NEGATIVE_INFINITY;    // degenerate line segment
        else if (deltaX == 0)
            return Double.POSITIVE_INFINITY;    // the line segment is vertical
        else if (deltaY == 0)
            return 0.0;                         // the line segment is horizontal

        return deltaY / deltaX;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if(this.x == that.x && this.y == that.y)
            return 0;   // this point is equal to the argument point
        else if(this.y < that.y || (this.y == that.y && this.x < that.x))
            return -1;  // this point is less than the argument point
        else
            return 1;   // this point is greater than the argument point
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        SlopeOrder comparator = new SlopeOrder(this);

        return comparator;
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        ArrayList<Point> points = new ArrayList<>();
        try
        {
            File input10 = new File("C:\\Users\\omarn\\source\\repos\\CollinearPoints\\input\\input8.txt");
            Scanner myReader = null;
            myReader = new Scanner(input10);

            while (myReader.hasNextLine()){
                String data = myReader.nextLine();
                String[] strs = data.split("\\s+");

                if(strs.length > 1)
                    points.add(new Point(Integer.parseInt(strs[0]), Integer.parseInt(strs[1])));
            }

            myReader.close();

            // draw the points
            StdDraw.enableDoubleBuffering();
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            for (Point p : points){
                p.draw();
            }

            // print and draw the line segments
            //BruteCollinearPoints collinear = new BruteCollinearPoints(points.toArray(new Point[0]));
            FastCollinearPoints collinear = new FastCollinearPoints(points.toArray(new Point[0]));

            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
            StdDraw.show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}