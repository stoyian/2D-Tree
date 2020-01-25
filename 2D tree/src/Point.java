/**
 * Implementation of a point in Cartesian coordinated
 * @author Stogiannidis Ilias, Diamantopoulos - Pantaleon Odyssefs
 */

import java.util.*;
import java.lang.Math;
public class Point {
    private int x;
    private int y;

    public Point(){
        x = 0;
        y = 0;
    }

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }// return the x-coordinate
    public int y() {
        return y;
    }// return the y-coordinate

    public double distanceTo(Point z) // Euclidean distance
    //between two points
    {
        return StrictMath.sqrt(StrictMath.pow(this.x()- z.x(),2) + StrictMath.pow(this.y() - z.y() , 2));
    }
    public int squareDistanceTo(Point z) // square of the Euclidean
    //distance between two points
    {
        return (int) StrictMath.pow(distanceTo(z),2);
    }
    public String toString() // string representation: (x, y)
    {
        return "(" + this.x() + "," + this.y() + ")" + "\n";
    }
    public boolean equals(Point point){
        return x == point.x && y == point.y;
    }
}