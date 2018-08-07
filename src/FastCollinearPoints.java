import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segmentLists = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {

        if (points == null)
            throw new IllegalArgumentException();
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        checkNullity(pointsCopy);

        double previousSlope = Integer.MIN_VALUE;
        for (Point p : points) {
            ArrayList<Point> pointsList = new ArrayList<>();

            Arrays.sort(pointsCopy, p.slopeOrder());
            for (int j = 1; j < pointsCopy.length; j++) {
                Point q = pointsCopy[j];

                //skip equal points
                double newSlope = p.slopeTo(q);

                //check if slope are adjacent
                if (previousSlope == newSlope) {
                    pointsList.add(q);
                    continue;
                } else {
                    if (pointsList.size() >= 3) {
                        pointsList.add(p);
                        Collections.sort(pointsList);
                        if (p.compareTo(pointsList.get(0)) <= 0)
                            segmentLists.add(new LineSegment(pointsList.get(0), pointsList.get(pointsList.size() - 1)));
                    }
                }
                previousSlope = newSlope;
                pointsList.clear();
                pointsList.add(q);

            }
            if (pointsList.size() >= 3) {
                pointsList.add(p);
                Collections.sort(pointsList);

                if (p.compareTo(pointsList.get(0)) <= 0)
                    segmentLists.add(new LineSegment(pointsList.get(0), pointsList.get(pointsList.size() - 1)));

//
            }
        }
    }// finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return segmentLists.size();
    }      // the number of line segments

    public LineSegment[] segments() {

        return segmentLists.toArray(new LineSegment[segmentLists.size()]);
    }


    private void checkNullity(Point[] points) {
        for (Point p : points)
            if (p == null)
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}