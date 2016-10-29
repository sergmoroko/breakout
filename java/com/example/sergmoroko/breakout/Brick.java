package com.example.sergmoroko.breakout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Brick extends GameObject {

    private static Bitmap yellowBrick = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (GameView.getInstance().getResources(), R.drawable.breakout_brick_yellow_3d), GameConstants.BRICK_WIDTH, GameConstants.BRICK_HEIGHT, false);
    private static Bitmap redBrick = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (GameView.getInstance().getResources(), R.drawable.breakout_brick_red_3d), GameConstants.BRICK_WIDTH, GameConstants.BRICK_HEIGHT, false);
    private static Bitmap greenBrick = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (GameView.getInstance().getResources(), R.drawable.breakout_brick_green_3d), GameConstants.BRICK_WIDTH, GameConstants.BRICK_HEIGHT, false);
    private static Bitmap stoneBrick = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (GameView.getInstance().getResources(), R.drawable.breakout_brick_stone_3d), GameConstants.BRICK_WIDTH, GameConstants.BRICK_HEIGHT, false);
    private static Bitmap purpleBrick = Bitmap.createScaledBitmap(BitmapFactory.decodeResource
            (GameView.getInstance().getResources(), R.drawable.breakout_brick_purple_3d), GameConstants.BRICK_WIDTH, GameConstants.BRICK_HEIGHT, false);

    public Brick(String brickType, int x, int y) {
        this.x = x;
        this.y = y;
        width = GameConstants.BRICK_WIDTH;
        height = GameConstants.BRICK_HEIGHT;
        this.image = brickType(brickType);
        gameObjectArrayList.add(this);
    }

    public static Bitmap brickType(String type) {
        Bitmap brickImg = null;
        switch (type) {
            case GameConstants.BRICK_TYPE_RED:
                brickImg = redBrick;
                break;
            case GameConstants.BRICK_TYPE_YELLOW:
                brickImg = yellowBrick;
                break;
            case GameConstants.BRICK_TYPE_GREEN:
                brickImg = greenBrick;
                break;
            case GameConstants.BRICK_TYPE_STONE:
                brickImg = stoneBrick;
                break;
            case GameConstants.BRICK_TYPE_PURPLE:
                brickImg = purpleBrick;
                break;
        }
        return brickImg;
    }

    public static void placeBricks(int lvl, int brickQty){
        for(int i = 0; i < GameConstants.BRICK_ROWS_QTY; i ++){
            int y = i * (GameConstants.BRICK_HEIGHT +
                    GameConstants.BRICKS_SEPARATOR) + GameConstants.BRICKS_Y_OFFSET;
            int x = GameConstants.BRICKS_SEPARATOR;
            for (int j = 0; j < GameConstants.BRICKS_IN_ROW_QTY; j++){


                char c = Level.getLevel(lvl)[i][j];
                switch (c){
                    case ' ':
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        break;
                    case 'y':
                        new Brick(GameConstants.BRICK_TYPE_YELLOW, x, y);
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        brickQty++;
                        break;
                    case 'r':
                        new Brick(GameConstants.BRICK_TYPE_RED, x, y);
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        brickQty++;
                        break;
                    case 'g':
                        new Brick(GameConstants.BRICK_TYPE_GREEN, x, y);
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        brickQty++;
                        break;
                    case 's':
                        new Brick(GameConstants.BRICK_TYPE_STONE, x, y);
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        brickQty++;
                        break;
                    case 'p':
                        new Brick(GameConstants.BRICK_TYPE_PURPLE, x, y);
                        x = x + GameConstants.BRICKS_SEPARATOR + GameConstants.BRICK_WIDTH;
                        brickQty++;
                        break;
                }
            }
        }
    }
}
