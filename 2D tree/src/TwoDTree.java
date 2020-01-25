/** 
    This file contains implementation of a 2D Tree and simple use
    of it. The list used here is written by us and the .java document
    is added in the repository
    @author Stogiannidis Ilias, Diamantopoulos-Pantaleon Odyssefs
*/
import java.io.*;
import java.util.Comparator;
import java.util.Scanner;

public class TwoDTree {

    private class Treenode {
        Point item; // an object of the class Point
        Treenode l; // pointer to left subtree
        Treenode r; // pointer to right subtree

        public Treenode(final Point t) {
            l = null;
            r = null;
            item = t;
        }

        public Point getItem() {
            return item;
        }

        public int getX() {
            return item.x();
        }

        public int getY() {
            return item.y();
        }

        public void setL(final Treenode anode) {
            l = anode;
        }

        public void setR(final Treenode anode) {
            r = anode;
        }

        public String toString() {
            return item.toString();
        }
    }

    ;

    private final Comparator<Treenode> cmpX = new Comparator<Treenode>() {

        @Override
        public int compare(final Treenode p1, final Treenode p2) {
            return Double.compare(p1.getX(), p2.getX());
        }
    };

    private final Comparator<Treenode> cmpY = new Comparator<Treenode>() {

        @Override
        public int compare(final Treenode p1, final Treenode p2) {
            return Double.compare(p1.getY(), p2.getY());
        }
    };

    private Treenode root; //2DTree first element - root
    private Treenode nodeMin;
    private double dmin;

    public TwoDTree() // construct an empty tree
    {
        root = null;
    }

    public TwoDTree(final Treenode anode) // construct a tree with a root
    {
        root = anode;
    }

    public boolean isEmpty() // is the tree empty?
    {
        return root == null;
    }

    public int size() // number of points in the tree
    {
        return getNodeDepth(root);
    }

    public void insert(final Point p) // inserts the point p to the tree
    {
        final Treenode node = new Treenode(p);
        if (isEmpty()) root = node;
        else if (contains(p)) System.out.println("Item already in the list");
        else insertR(node, root, true);
    }

    public boolean search(final Point p) // does the tree contain p?
    {
        if (root == null)
            return false;
        final Treenode node = new Treenode(p);
        return searchR(node, root, true);
    }

    public Point nearestNeighbor(final Point p) // point in the tree that is closest to p (null if tree is empty)
    {
        if (p == null) throw new IllegalArgumentException("Empty point.");
        if (root == null) return null;
        dmin = p.distanceTo(root.item);
        nodeMin = root;
        findNearestRecur(root, p);

        return nodeMin.item;
    }

    public boolean contains(final Point p)            // does the set contain point p?
    {
        if (isEmpty()) return false;
        Treenode x = root;
        double cmp;

        while (x != null) {
            cmp = cmpX.compare(x, new Treenode(p));
            if (cmp > 0) x = x.l;
            else if (cmp < 0) x = x.r;
            else if (x.item.equals(p)) return true;
            else x = x.r;
        }
        return false;
    }

    public List<Point> rangeSearch(final Rectangle rect) {
        final List<Point> list = new List<>();
        rangeSearchR(root, rect, new Rectangle(new Point(0, 100), new Point(100, 100)), list, 0);
        return list;
    } // Returns a list
    //with the Points that are contained in the rectangle


    //-------------------------- \Private Helper Functions/ -----------------------------------//
    private void insertR(final Treenode node, final Treenode currentNode, final boolean divX) {
        if (node == null)
            return;
        final int cmpResult = (divX ? cmpX : cmpY).compare(node, currentNode);
        if (cmpResult == -1)
            if (currentNode.l == null)
                currentNode.l = node;
            else
                insertR(node, currentNode.l, !divX);
        else if (currentNode.r == null)
            currentNode.r = node;
        else
            insertR(node, currentNode.r, divX);
    }

    private int getNodeDepth(final Treenode node) {
        if (node == null)
            return 0;

        // get left and right depth
        final int leftDepth = getNodeDepth(node.l);
        final int rightDepth = getNodeDepth(node.r);

        // return max depth + 1 for current node
        return Math.max(leftDepth, rightDepth) + 1;
    }

    private boolean searchR(final Treenode node, final Treenode currentNode, final boolean divX) {
        final int cmpResult = (divX ? cmpX : cmpY).compare(node, currentNode);
        if (cmpResult == -1)
            if (currentNode.l.item.equals(node.item))
                return true;
            else
                searchR(node, currentNode.l, !divX);
        else if (currentNode.r.item.equals(node.item))
            return true;
        else
            searchR(node, currentNode.r, divX);
        return false;
    }

    private void findNearestRecur(final Treenode node, final Point p) {
        double d1 = 0;
        final double d2 = 0;
        if (node.l != null && new TwoDTree(node.l).contains(p)) {
            final double d = p.distanceTo(node.l.getItem());
            if (d <= dmin) {
                dmin = d;
                nodeMin = node.l;
            }
            findNearestRecur(node.l, p);

        }

        if (node.r != null && new TwoDTree(node.r).contains(p)) {
            final double d = p.distanceTo(node.r.getItem());
            if (d <= dmin) {
                dmin = d;
                nodeMin = node.r;
            }
            findNearestRecur(node.r, p);
        }

        if (node.l != null) {
            d1 = node.l.item.distanceTo(p);
            if (0 < d1 && d1 <= dmin) {
                final double d = p.distanceTo(node.l.item);
                if (d <= dmin) {
                    dmin = d;
                    nodeMin = node.l;
                }
                findNearestRecur(node.l, p);
            }
        }
    }

    private void rangeSearchR(final Treenode node, final Rectangle rect, final Rectangle rootRect, final List<Point> list, final int lvl) {
        if (node == null) return;

        if (rect == null) throw new IllegalArgumentException();

        Rectangle NodeRegion1, NodeRegion2;
        if (lvl % 2 == 0) {
            NodeRegion1 = new Rectangle(rootRect.xmin(), rootRect.ymin(), root.item.x(), rootRect.ymax());
            NodeRegion2 = new Rectangle(root.item.x(), rootRect.ymin(), rootRect.xmax(), rootRect.ymax());
        } else {
            NodeRegion1 = new Rectangle(rootRect.xmin(), rootRect.ymin(), rootRect.xmax(), root.item.y());
            NodeRegion2 = new Rectangle(rootRect.xmin(), root.item.y(), rootRect.xmax(), rootRect.ymax());
        }

        if (rect.contains(root.item))
            list.add(root.item);
        if (rect.intersects(NodeRegion1) && root.l != null)
            rangeSearchR(root.l, rect, NodeRegion1, list, lvl + 1);

        if (rect.intersects(NodeRegion2) && root.r != null)
            rangeSearchR(root.r, rect, NodeRegion2, list, lvl + 1);
    }

    /*-------------------------===== \Main Function/ =====----------------------------------*/
    public static void main(final String[] args){
        int x,y,x2,y2;
        final File file = new File("C:/Users/user/Desktop/Sxoli/Domes/Ergasia3/src/test.txt");
        final TwoDTree buffer = new TwoDTree();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Reading the first line of the .txt file to find the number of points
            final int NumOfPoints = Integer.parseInt(br.readLine());
            for (int i = 0; i < NumOfPoints ; i++) {
                final String point_vars = br.readLine();
                final String[] split = point_vars.split(" ");
                final int pointX = Integer.parseInt(split[0]);
                final int pointY = Integer.parseInt(split[1]);
                buffer.insert(new Point(pointX,pointY));
            }
            if (br.readLine()!= null) {
                System.out.println("You gave more than "+NumOfPoints+" points, program Exterminate");
                System.exit(1);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        while(true){
            final String menu = "1. Compute the size of the Tree\n" +
                    "2. Insert a new point\n" +
                    "3. Search if a given point exists in the Tree\n" +
                    "4. Provide a query rectangle\n" +
                    "5. Provide a query point\n" +
                    "0. Exit\n";
            System.out.println(menu);
            final Scanner in = new Scanner(System.in);
            final int des = Integer.parseInt(in.next());
            switch (des){
                case 0:
                    System.out.println("Exiting Program");
                    System.exit(0);
                    break;
                case 1:
                    System.out.println(buffer.size());
                    break;
                case 2:
                    System.out.println("Please Give 2 new Coordinates for a new Point");
                    System.out.println("Please give X");
                    x = in.nextInt();
                    System.out.println("Please give Y");
                    y = in.nextInt();
                    buffer.insert(new Point(x,y));
                    break;
                case 3:
                    System.out.println("Please Give 2 Coordinates to search if a Point exists");
                    System.out.println("Please give X");
                    x = in.nextInt();
                    System.out.println("Please give Y");
                    y = in.nextInt();
                    System.out.println(buffer.search(new Point(x,y)));
                    break;
                case 4:
                    System.out.println("Please Give a Rectangle");
                    System.out.println("Please give Point with min X and Y");
                    System.out.println("Please give X");
                    x = in.nextInt();
                    System.out.println("Please give Y");
                    y = in.nextInt();
                    System.out.println("Please give Point with max X and Y");
                    System.out.println("Please give X");
                    x2 = in.nextInt();
                    System.out.println("Please give Y");
                    y2 = in.nextInt();
                    final Rectangle test = new Rectangle(new Point(x,y),new Point(x2,y2));
                    System.out.println(buffer.rangeSearch(test));
                    break;
                case 5:
                    System.out.println("Please Give a Point");
                    System.out.println("Please give X");
                    x = in.nextInt();
                    System.out.println("Please give Y");
                    y = in.nextInt();
                    System.out.println(buffer.nearestNeighbor(new Point(x,y)));
                    break;
                }
            }
        }
    }