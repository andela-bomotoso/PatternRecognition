import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;
    private int numberOfSegments;
    private ArrayList<LineSegment> linesegmentlist;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        checkNullity(pointsCopy);
        numberOfSegments = 0;
        linesegmentlist = new ArrayList<>();
        Point[] tempPoints;
        int length = pointsCopy.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                for (int k = j + 1; k < length; k++) {
                    for (int l = k + 1; l < length; l++) {
                        tempPoints = new Point[]{pointsCopy[i], pointsCopy[j], pointsCopy[k], pointsCopy[l]};
                        if (isCollinear(tempPoints)) {
                            numberOfSegments++;
                            linesegmentlist.add(new LineSegment(pointsCopy[i], pointsCopy[l]));
                        }
                    }
                }
            }
        }

    }   // finds all line segments containing 4 points

    public int numberOfSegments() {
        return numberOfSegments;
    }    // the number of line segments

    public LineSegment[] segments() {
        lineSegments = new LineSegment[linesegmentlist.size()];
        for (int i = 0; i < linesegmentlist.size(); i++) {
            lineSegments[i] = linesegmentlist.get(i);
        }
        return lineSegments;
    }

    private boolean isCollinear(Point[] points) {
        double slope = points[0].slopeTo(points[1]);
        for (int i = 1; i < points.length - 1; i++) {
            double currentSlope = points[i].slopeTo(points[i + 1]);
            if (slope != currentSlope)
                return false;
        }
        return true;
    }

    private void checkNullity(Point[] points) {
        ArrayList<Point> pointList = new ArrayList(Arrays.asList(points));
        if (pointList.contains(null))
            throw new IllegalArgumentException();
        Arrays.sort(points);
        Point previousPoint = points[0];
        for (int i = 1; i < points.length; i++) {
            Point point = points[i];
            if (point.compareTo(previousPoint) == 0)
                throw new IllegalArgumentException();
            previousPoint = point;
        }
    }


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}