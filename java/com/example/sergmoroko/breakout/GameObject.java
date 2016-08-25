package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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



    public void remove(){
            gameObjectArrayList.remove(this);
    }



    public GameObject getCollidingObject() {
        Rect objRect = this.getRectangle();
        GameObject currentGameObject;

        for (int i = 0; i < gameObjectArrayList.size(); i++) {
            Rect collObjRect = gameObjectArrayList.get(i).getRectangle();

            if (objRect.intersect(collObjRect) && gameObjectArrayList.get(i) != this) {
                currentGameObject = gameObjectArrayList.get(i);
                return currentGameObject;

            }
        }
        return null;
    }

    public Rect getIntersectionRect(GameObject gameObject){

        return new Rect(
                Math.max(this.getX(), gameObject.getX()), // left
                Math.max(this.getY() - this.getHeight(), gameObject.getY() - gameObject.getHeight()), // top
                Math.min(this.getX() + this.getWidth(), gameObject.getX() + gameObject.getWidth()), // right
                Math.min(this.getY(), gameObject.getY()) // bottom
        );
    }

    public static void removeAll(){
        gameObjectArrayList.clear();
    }
}
