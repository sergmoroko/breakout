package com.example.sergmoroko.breakout;


/**
 * Created by ssss on 18.08.2016.
 */
public class Level {

private static char level [][] = new char[][]{
        {'s',' ',' ',' ',' ',' ',' ',' ',' ',' '},
        {'y','s',' ',' ',' ',' ',' ',' ',' ',' '},
        {'g',' ','s',' ',' ',' ',' ',' ',' ',' '},
        {'y','g',' ','s',' ',' ',' ',' ',' ',' '},
        {'g',' ','g',' ','s',' ',' ',' ',' ',' '},
        {'y','g','g','g','g','s',' ',' ',' ',' '},
        {'g','y','y','y','y','y','s',' ',' ',' '},
        {'y','r','r','r','r','r','r','s',' ',' '},
        {'y','g','y','g','y','g','y','g','s',' '},
        {'r','r','r','r','r','r','r','r','r','r'}
};
    public static char[][] getLevel(){
        return level;
    }

}
