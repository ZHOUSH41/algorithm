public class Point implements Comparable<Point> {
    // 算是否是ccw实际用就是向量的叉积
    public Point(int x, int y)                         // constructs the point (x, y)

    public   void draw()                               // draws this point
    public   void drawTo(Point that)                   // draws the line segment from this point to that point
    public String toString()                           // string representation

    public               int compareTo(Point that)     // compare two points by y-coordinates, breaking ties by x-coordinates
    public            double slopeTo(Point that)       // the slope between this point and that point
    public Comparator<Point> slopeOrder()              // compare two points by slopes they make with this point
}