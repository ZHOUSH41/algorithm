import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {
    private int lineNum;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points){
        lineNum = 0;
        if(points == null) throw new IllegalArgumentException();
        int N = points.length;
        //ArrayList<LineSegment> lineSegments = new ArrayList<>();

        // 使用链表结构
        List<LineSegment> lineSegments = new LinkedList<LineSegment>();
        for (int i = 0; i < N-3; i++){
            Point p0 = points[i];
            for(int j = i+1; j < N-2; j++){
                Point p1 = points[j];
                double slope0to1 = p0.slopeTo(p1);
                if(slope0to1 == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                for (int k = j+1; k < N-1; k++){
                    Point p2 = points[k];
                    double slope0to2 = p0.slopeTo(p2);
                    if(slope0to2 == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();

                    //如果两个点的斜率相等才考虑进入第四个循环
                    if(Double.compare(slope0to1,slope0to2) == 0){
                        for (int w = k+1;w < N; w++){
                            Point p3 = points[w];
                            double slope0to3 = p0.slopeTo(p3);
                            if(slope0to3 == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();

                            if(Double.compare(slope0to1,slope0to3) == 0){
                                lineSegments.add(new LineSegment(p0, p3));
                            }else continue;
                        }
                    }
                }
            }
        }
        segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }    // finds all line segments containing 4 points
    public           int numberOfSegments(){
        return lineNum;
    }        // the number of line segments
    public LineSegment[] segments(){
        return segments.clone();
    }                // the line segments

    public static void main(String[] args){
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
        //FastCollinearPoints collinear = new FastCollinearPoints(points);
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}