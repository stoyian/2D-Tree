/**
 * Implementation of the a rectangle previewed like this [0,100]x[0,100]
 * which means that the rectangles four points are 0,0 0,100 100,0 100,100
 * @author Stogiannidis ILias, Diamantopoulos-Pantaleon Odyseffs
 */

import java.awt.*;

public class Rectangle {
    private int xmin;
    private int xmax;
    private int ymin;
    private int ymax;

    public Rectangle(Point A, Point B){
        xmax = Math.max(A.x(),B.x());
        xmin = Math.min(A.x(),B.x());
        ymax = Math.max(A.y(),B.y());
        ymin = Math.min(A.y(),B.y());


    }

    public Rectangle(int _xmin, int _ymin, int _xmax, int _ymax) {
        xmax = _xmax;
        xmin = _xmin;
        ymax = _ymax;
        ymin = _ymin;
    }

    public int xmin() // minimum x-coordinate of rectangle
    {
        return xmin;
    }
    public int ymin() // minimum y-coordinate of rectangle
    {
        return ymin;
    }
    public int xmax() // maximum x-coordinate of rectangle
    {
        return xmax;
    }
    public int ymax() // maximum y-coordinate of rectangle
    {
        return ymax;
    }
    public boolean contains(Point p) //does p belong to the rectangle?
    {
        return p.x() >= xmin && p.x() <= xmax && p.y() >= ymin && p.y() <= ymax;

    }
    public boolean intersects(Rectangle that) // do the two rectangles
    // intersect?
    {
        return !((xmin < that.xmin() && xmax > that.xmax() && ymin < that.ymin() && ymax > that.ymax())
                || (xmin > that.xmax()) || (xmax < that.xmin()) || (ymin > that.ymax())
                || ymax < that.ymin() || ((that.xmin()) < xmin && that.xmax() > xmax && that.ymin() < ymin && that.ymax() > ymax));
    }
    public double distanceTo(Point p) // Euclidean distance from p
    //to closest point in rectangle
    {
        if (contains(p)) return 0;
        Point LDPoint = new Point(xmin,ymin);
        Point LUPoint = new Point(xmin,ymax);
        Point RDPoint = new Point(xmax,ymin);
        Point RUPoint = new Point(xmax,ymax);
        double min1,min2 = 0;
        min1 = Math.min(p.distanceTo(LDPoint),p.distanceTo(LUPoint));
        min2 = Math.min(p.distanceTo(RDPoint),p.distanceTo(RUPoint));
        return Math.min(min1,min2);
    }
    public int squareDistanceTo(Point p) // square of Euclidean
    // distance from p to closest point in rectangle
    {
        if (contains(p)) return 0;
        return (int) StrictMath.pow(distanceTo(p),2);
    }
    @Override
    public String toString() // string representation:
    // [xmin, xmax] x [ymin, ymax]
    {
        return "["+ xmin + "," + xmax + "] x [" + ymin + "," + ymax + "]" ;
    }

}
