package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import java.util.ArrayList;

/**
 * Created by ssss on 19.07.2016.
 */
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


    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public Point getLeftTopCorner() {
        return new Point(x, y);
    }

    public Point getLeftBottomCorner() {
        return new Point(x, y + height);
    }

    public Point getRightTopCorner() {
        return new Point(x + width, y);
    }

    public Point getRightBottomCorner() {
        return new Point(x + width, y + height);
    }

    public class Line {

        Point a;
        Point b;
        double A;
        double B;
        double C;

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

        public double getLength() {
            return Math.sqrt(((b.x - a.x) * (b.x - a.x)) + ((b.y - a.y) * (b.y - a.y)));
        }

        private boolean isIntersects(Line ln) {
            double v1 = (x2() - x1()) * (ln.y1() - y1()) - (y2() - y1()) * (ln.x1() - x1());
            double v2 = (x2() - x1()) * (ln.y2() - y1()) - (y2() - y1()) * (ln.x2() - x1());
            double v3 = (ln.x2() - ln.x1()) * (y1() - ln.y1()) - (ln.y2() - ln.y1()) * (x1() - ln.x1());
            double v4 = (ln.x2() - ln.x1()) * (y2() - ln.y1()) - (ln.y2() - ln.y1()) * (x2() - ln.x1());
            return ((v1 * v2 <= 0) && (v3 * v4 <= 0));
        }

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

        private void lineEquation(Line line) {
            A = line.y2() - line.y1();
            B = line.x1() - line.x2();
            C = -line.x1() * (line.y2() - line.y1()) + line.y1() * (line.x2() - line.x1());
        }
    }

    public Point getCenter(){
    int centerX = getRectangle().centerX();
    int centerY = getRectangle().centerY();
        return new Point(centerX,centerY);
    }

    private ArrayList<Line> getObjectSides(GameObject gameObject) {
        ArrayList<Line> objectLinesList = new ArrayList<>();
        objectLinesList.add(new Line(gameObject.getLeftBottomCorner(), gameObject.getLeftTopCorner()));
        objectLinesList.add(new Line(gameObject.getLeftTopCorner(), gameObject.getRightTopCorner()));
        objectLinesList.add(new Line(gameObject.getRightBottomCorner(), gameObject.getRightTopCorner()));
        objectLinesList.add(new Line(gameObject.getLeftBottomCorner(), gameObject.getRightBottomCorner()));
        return objectLinesList;
    }

    private ArrayList<Point> getObjectCorners() {
        ArrayList<Point> objectCornersList = new ArrayList<>();
        objectCornersList.add(getLeftBottomCorner());
        objectCornersList.add(getLeftTopCorner());
        objectCornersList.add(getRightTopCorner());
        objectCornersList.add(getRightBottomCorner());
        return objectCornersList;
    }


    public void remove() {
        gameObjectArrayList.remove(this);
    }


    public GameObject getCollidingObject() {
        ArrayList<Point> objCorners = getObjectCorners();
        GameObject currentGameObject;
        GameObject resultGameObject = null;
        double minimalDistance = Double.MAX_VALUE;

        for (int i = 0; i < gameObjectArrayList.size(); i++) {
            Rect collObjRect = gameObjectArrayList.get(i).getRectangle();

            for (Point corner : objCorners) {
                if (collObjRect.contains(corner.x, corner.y) && gameObjectArrayList.get(i) != this) {
                    currentGameObject = gameObjectArrayList.get(i);
                    double distance = new Line(currentGameObject.getCenter(), this.getCenter()).getLength();
                    if(distance < minimalDistance){
                        resultGameObject = currentGameObject;
                    }

                   // return currentGameObject;
                }
            }
        }
        return resultGameObject;
    }

//    public GameObject getCollidingObject() {
//        Rect objRect = this.getRectangle();
//        GameObject currentGameObject;
//
//        for (int i = 0; i < gameObjectArrayList.size(); i++) {
//            Rect collObjRect = gameObjectArrayList.get(i).getRectangle();
//
//            if (objRect.intersect(collObjRect) && gameObjectArrayList.get(i) != this) {
//                currentGameObject = gameObjectArrayList.get(i);
//                return currentGameObject;
//
//            }
//        }
//        return null;
//    }
    public void moveToPointByCorner(Point point, Point corner){
        int y = corner.y;
        int x = corner.x;
        //top
        if(y == this.getY()){
            if(x == this.getX()){
                this.setX(point.x);
                this.setY(point.y);
            }
            else {
                this.setX(point.x - this.getWidth());
                this.setY(point.y);
            }
        }
        else {
            if (x == this.getX()) {
                this.setX(point.x);
                this.setY(point.y - this.getHeight());
            } else {
                this.setX(point.x - this.getWidth());
                this.setY(point.y - this.getHeight());
            }
        }
    }


    public Line getCollidingSide(GameObject collidingGameObject) {
        // variable used to store minimal distance
        double minDistance = Double.MAX_VALUE;
        Point xPoint = null;
        Point corner = null;
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
                        xPoint = crossingPoint;
                        corner = pt;

                    }
                }
            }
        }
//        if(xPoint != null && resultLine != null) {
//            this.moveToPointByCorner(xPoint, corner);
//        }
        return resultLine;
    }

    public static void removeAll() {
        gameObjectArrayList.clear();
    }
}
