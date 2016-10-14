package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;


public abstract class GameObject {
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int width;
    protected int height;
    protected Bitmap image;
    protected static ArrayList<GameObject> gameObjectArrayList = new ArrayList<>();

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }


    private Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    private Point getLeftTopCorner() {
        return new Point(x, y);
    }

    private Point getLeftBottomCorner() {
        return new Point(x, y + height);
    }

    private Point getRightTopCorner() {
        return new Point(x + width, y);
    }

    private Point getRightBottomCorner() {
        return new Point(x + width, y + height);
    }

    public class Line {
        private Point a;
        private Point b;
        private double A;
        private double B;
        private double C;

        Line(Point a, Point b) {
            this.a = a;
            this.b = b;
        }

        public double x1() {
            return a.x;
        }

        public double x2() {
            return b.x;
        }

        public double y1() {
            return a.y;
        }

        public double y2() {
            return b.y;
        }

        // returns length of the line
        private double getLength() {
            return Math.sqrt(((b.x - a.x) * (b.x - a.x)) + ((b.y - a.y) * (b.y - a.y)));
        }


        // returns true if lines intersects
        private boolean isIntersects(Line ln) {
            // vector cross production of given lines
            double v1 = (x2() - x1()) * (ln.y1() - y1()) - (y2() - y1()) * (ln.x1() - x1());
            double v2 = (x2() - x1()) * (ln.y2() - y1()) - (y2() - y1()) * (ln.x2() - x1());
            double v3 = (ln.x2() - ln.x1()) * (y1() - ln.y1()) - (ln.y2() - ln.y1()) * (x1() - ln.x1());
            double v4 = (ln.x2() - ln.x1()) * (y2() - ln.y1()) - (ln.y2() - ln.y1()) * (x2() - ln.x1());

            return ((v1 * v2 <= 0) && (v3 * v4 <= 0));
        }

        // returns crossing point of two given lines
        private Point crossingPoint(Line ln) {
            Point crossingPoint = new Point();
            double a1, b1, c1, a2, b2, c2;
            lineEquation(this);
            a1 = A;
            b1 = B;
            c1 = C;
            lineEquation(ln);
            a2 = A;
            b2 = B;
            c2 = C;
            double d = a1 * b2 - b1 * a2;
            double dx = -c1 * b2 + b1 * c2;
            double dy = -a1 * c2 + c1 * a2;
            crossingPoint.x = (int) (dx / d);
            crossingPoint.y = (int) (dy / d);
            return crossingPoint;
        }

        // calculates line coefficients
        private void lineEquation(Line line) {
            A = line.y2() - line.y1();
            B = line.x1() - line.x2();
            C = -line.x1() * (line.y2() - line.y1()) + line.y1() * (line.x2() - line.x1());
        }
    }

    // returns point of object rectangle geometrical center
    private Point getCenter() {
        int centerX = getRectangle().centerX();
        int centerY = getRectangle().centerY();
        return new Point(centerX, centerY);
    }

    // returns array of object rectangle side lines
    private ArrayList<Line> getObjectSides(GameObject gameObject) {
        ArrayList<Line> objectLinesList = new ArrayList<>();
        // left
        objectLinesList.add(new Line(gameObject.getLeftBottomCorner(), gameObject.getLeftTopCorner()));
        // top
        objectLinesList.add(new Line(gameObject.getLeftTopCorner(), gameObject.getRightTopCorner()));
        // right
        objectLinesList.add(new Line(gameObject.getRightBottomCorner(), gameObject.getRightTopCorner()));
        // bottom
        objectLinesList.add(new Line(gameObject.getLeftBottomCorner(), gameObject.getRightBottomCorner()));
        return objectLinesList;
    }

    // returns array of object rectangle corner points
    private ArrayList<Point> getObjectCorners() {
        ArrayList<Point> objectCornersList = new ArrayList<>();
        objectCornersList.add(getLeftBottomCorner());
        objectCornersList.add(getLeftTopCorner());
        objectCornersList.add(getRightTopCorner());
        objectCornersList.add(getRightBottomCorner());
        return objectCornersList;
    }

    // returns CLOSEST colliding object
    public GameObject getCollidingObject() {
        ArrayList<Point> objCorners = getObjectCorners();
        GameObject currentGameObject;
        GameObject resultGameObject = null;

        // variable used to store minimal distance
        double minimalDistance = Double.MAX_VALUE;

        // for every gameObject
        for (int i = 0; i < gameObjectArrayList.size(); i++) {
            // get rectangle
            Rect collObjRect = gameObjectArrayList.get(i).getRectangle();
            // for every corner point of THIS objects rectangle
            for (Point corner : objCorners) {
                // if colliding object contains this point, and colliding object != THIS
                if (collObjRect.contains(corner.x, corner.y) && gameObjectArrayList.get(i) != this) {
                    currentGameObject = gameObjectArrayList.get(i);
                    // measuring distance between centers of THIS object, and colliding object
                    double distance = new Line(currentGameObject.getCenter(), this.getCenter()).getLength();
                    // if distance is less then current value of minimal distance
                    if (distance < minimalDistance) {
                        resultGameObject = currentGameObject;
                    }
                }
            }
        }
        return resultGameObject;
    }


    public Line getCollidingSide(GameObject collidingGameObject) {
        // variable used to store minimal distance
        double minDistance = Double.MAX_VALUE;
        // result line
        Line resultLine = null;
        // list with a corners of current object
        ArrayList<Point> objectCorners = getObjectCorners();
        // list containing sides of colliding object rectangle
        ArrayList<Line> objectSideLines = getObjectSides(collidingGameObject);
        // for every corner point of current object
        for (Point pt : objectCorners) {
            // previous coordinate of current corner point
            Point previousPoint = new Point(pt.x - getDx(), pt.y - getDy());
            // for each side of rectangle
            for (Line ln : objectSideLines) {
                // trajectory line between current point coordinates, and previous (coordinates of point on previous frame)
                Line trajectory = new Line(pt, previousPoint);
                // if trajectory intersects with current side of rectangle
                if (trajectory.isIntersects(ln)) {
                    // calculating crossing point
                    Point crossingPoint = trajectory.crossingPoint(ln);
                    // line between crossing point and trajectory start
                    Line trajectoryToObject = new Line(previousPoint, crossingPoint);
                    // calculating length of this line
                    double trajectoryLength = trajectoryToObject.getLength();
                    // if its lesser than current value (it's possible, if more than 1 trajectory intersects rectangle sides)
                    if (trajectoryLength < minDistance) {
                        // replacing its value with current
                        minDistance = trajectoryLength;
                        // and setting this side of rectangle to result line
                        resultLine = ln;
                    }
                }
            }
        }
        // TODO:  FIX THIS SHIT CODE
        if (resultLine == null){
            double min = Double.MAX_VALUE;
            for (Point pt : objectCorners) {
                // previous coordinate of current corner point
                Point previousPoint = new Point(pt.x - getDx(), pt.y - getDy());
                if (collidingGameObject.getRectangle().contains(previousPoint.x, previousPoint.y)){
                    // distance to left side
                    double left = pt.x - collidingGameObject.getX();
                    if( left  < min){
                        min = left;
                        resultLine = objectSideLines.get(0);
                    }

                    // distance to left side
                    double top = pt.y - collidingGameObject.getY();
                    if( top  < min){
                        min = top;
                        resultLine = objectSideLines.get(1);
                    }

                    // distance to left side
                    double right = (collidingGameObject.getX() + collidingGameObject.getWidth()) - pt.x;
                    if( right  < min){
                        min = right;
                        resultLine = objectSideLines.get(2);
                    }

                    // distance to left side
                    double bottom = (collidingGameObject.getY() + collidingGameObject.getHeight()) - pt.y;
                    if( bottom  < min){
                        min = bottom;
                        resultLine = objectSideLines.get(3);
                    }
                }
            }
        }
        return resultLine;
    }

    // removes gameObject
    public void remove() {
        gameObjectArrayList.remove(this);
    }

    // removes all gameObjects
    public static void removeAll() {
        gameObjectArrayList.clear();
    }
}
