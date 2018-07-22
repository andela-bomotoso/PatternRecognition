import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {

    private int numberOfSegments;
    private ArrayList<LineSegment> segmentLists = new ArrayList<>();
    private ArrayList<Point> segmentStartLists = new ArrayList<>();
    private ArrayList<Point> segmentEndLists = new ArrayList<>();
    private ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {

        if (points == null)
            throw new IllegalArgumentException();
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        checkNullity(pointsCopy);

        numberOfSegments = 0;
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
                    pointsList.add(pointsCopy[j]);
                    continue;
                } else {
                    if (pointsList.size() >= 3) {
                        pointsList.add(p);
                        if (isSegmentNew(segmentStartLists, segmentEndLists, pointsList)) {
                            numberOfSegments++;
                            segmentLists.add(getSegment(pointsList));
                        }
                    }
                }
                previousSlope = newSlope;
                pointsList.clear();
                pointsList.add(q);

            }
            if (pointsList.size() >= 3) {
                pointsList.add(p);
                if (isSegmentNew(segmentStartLists, segmentEndLists, pointsList)) {
                    numberOfSegments++;
                    segmentLists.add(getSegment(pointsList));
                }
            }
        }
    }// finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return numberOfSegments;
    }      // the number of line segments

    public LineSegment[] segments() {

        return segmentLists.toArray(new LineSegment[segments.size()]);
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

    private boolean isSegmentNew(ArrayList<Point> segmentStart, ArrayList<Point> segmentEnd, ArrayList<Point> pointList) {
        Collections.sort(pointList);
        Point p = pointList.get(0);
        Point q = pointList.get(pointList.size() - 1);
        for (int i = 0; i < segmentStart.size(); i++) {
            Point currSegmentStart = segmentStart.get(i);
            Point currSegmentEnd = segmentEnd.get(i);
            if (p.compareTo(currSegmentStart) == 0 && q.compareTo(currSegmentEnd) == 0) {
                return false;
            }
        }
        segmentStart.add(p);
        segmentEnd.add(q);
        return true;
    }

    private LineSegment getSegment(ArrayList<Point> pointList) {
        return new LineSegment(pointList.get(0), pointList.get(pointList.size() - 1));
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