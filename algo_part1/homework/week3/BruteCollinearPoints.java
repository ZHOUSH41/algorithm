public class BruteCollinearPoints {
    private int lineNum;
    private LineSegment[] lineSegment;

    public BruteCollinearPoints(Point[] points){
        lineNum = 0;
        if(points == null) throw new IllegalArgumentException();
        int N = points.length;
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
                                lineSegment[lineNum++] = new LineSegment(p0,p3);
                            }else continue;
                        }
                    } else continue;
                }
            }
        }
    }    // finds all line segments containing 4 points
    public           int numberOfSegments(){
        return lineNum;
    }        // the number of line segments
    public LineSegment[] segments(){
        return lineSegment;
    }                // the line segments
}