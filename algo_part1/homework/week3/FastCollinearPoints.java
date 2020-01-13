import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private LineSegment[] segments = null;
    private int n;
    public FastCollinearPoints(Point[] points){
        if(points == null) throw new IllegalArgumentException();

        int N = points.length;

        Point[] aux = new Point[points.length];

        /** 检查每一个元素是否为null */
        for (int i = 0; i < N; i++){
            if(points[i] == null)
                throw new IllegalArgumentException();
            aux[i] = points[i];
        }

//        // 检查有没有重复元素
//        for(int i = 0; i < N; i++){
//            for(int j = i+1; j < N; j++){
//                if(points[i].slopeTo(points[j]) == 0){
//                    throw new IllegalArgumentException();
//                }
//            }
//        }

        /** 1. 按照y排序, 可以去重
         *  2. 必须要重新copy一份,给分的要求是保持points不变 */
        Arrays.sort(aux);
        //ArrayList<LineSegment> lineSegments = new ArrayList<>();
        // 链表结构
        List<LineSegment> lineSegments = new LinkedList<LineSegment>();
        Point prev = null;

        for (int i = 0; i < N; i++){
            Point p = aux[i];
            /** 先找有没有重复元素,因为已经排过序,所以如果有重复元素应该在相邻位置 */
            if(prev != null && p.compareTo(prev) == 0){
                throw new IllegalArgumentException();
            }else{
                prev = p;
            }

            Point[] slopeOrder = aux.clone();
            Arrays.sort(slopeOrder,p.slopeOrder());


            double lastSlope = Double.NEGATIVE_INFINITY;
            int slopeStartIndex = 0;

            for(int j = 1; j < N; j++){
                Point q = slopeOrder[j];
                double curSlope = p.slopeTo(q);

                boolean isLastPoint = j == N-1;

                /** tips:
                 * 1. j-slopeStartIndex = ?:
                 *   不是最后一个,那么j已经到下一个值了,所以是>=3;
                 *   如果是最后一个,没有下一个值,所以是>=2
                 *   */
                if(Double.compare(curSlope,lastSlope) != 0){
                    if(j - slopeStartIndex >= 3){
                        /** important: 去重的, 表示只能朝逆时针旋转 */
                        if(p.compareTo(slopeOrder[slopeStartIndex]) <= 0){
                            LineSegment segment = new LineSegment(p, slopeOrder[j-1]);
                            lineSegments.add(segment);
                        }
                    }

                    slopeStartIndex = j;

                }
                else if (isLastPoint){
                    if(j - slopeStartIndex >= 2){
                        if(p.compareTo(slopeOrder[slopeStartIndex]) <= 0){
                            LineSegment segment = new LineSegment(p,q);
                            lineSegments.add(segment);
                        }
                    }
                }

                lastSlope = curSlope;
            }
        }

        segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);

    }     // finds all line segments containing 4 or more points
    public           int numberOfSegments(){
        return segments.length;
    }        // the number of line segments
    public LineSegment[] segments(){
        return segments.clone();
    }                // the line segments
}
