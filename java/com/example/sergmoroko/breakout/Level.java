package com.example.sergmoroko.breakout;


/**
 * Created by ssss on 18.08.2016.
 */
public class Level {

    private static char level1 [][] = new char[][]{
            {'s',' ',' ',' ',' ',' ',' ',' ',' ',' '},
            {'y','s',' ',' ',' ',' ',' ',' ',' ',' '},
            {'g','r','s',' ',' ',' ',' ',' ',' ',' '},
            {'y','g','r','s',' ',' ',' ',' ',' ',' '},
            {'g','r','g','r','s',' ',' ',' ',' ',' '},
            {'y','g','g','g','g','s',' ',' ',' ',' '},
            {'g','y','y','y','y','y','s',' ',' ',' '},
            {'y','r','r','r','r','r','r','s',' ',' '},
            {'y','g','y','g','y','g','y','g','s',' '},
            {'r','r','r','r','r','r','r','r','r','r'}
    };
    private static char level2 [][] = new char[][]{
            {'s',' ',' ',' ',' ',' ',' ',' ',' ','s'},
            {'y','s',' ',' ',' ',' ',' ',' ','s','y'},
            {'g','r','s',' ',' ',' ',' ','s','r','g'},
            {'y','g','r','s',' ',' ','s','r','g','y'},
            {'g','r','g','r',' ',' ','r','g','r','g'},
            {'y','g','g','g',' ',' ','g','g','g','y'},
            {'g','y','y','y',' ',' ','y','y','y','g'},
            {'y','r','r','r',' ',' ','r','r','r','y'},
            {'y','g','y','g','y','y','g','y','g','y'},
            {'r','r','r','r','r','r','r','r','r','r'}
    };
    private static char level3 [][] = new char[][]{
            {'y','y','y','y','y','y','y','y','y','y'},
            {'r','r','r','r','r','r','r','r','r','r'},
            {'g','g','g','g','g','g','g','g','g','g'},
            {'s','s','s','s','s','s','s','s','s','s'},
            {'y','y','y','y','y','y','y','y','y','y'},
            {'r','r','r','r','r','r','r','r','r','r'},
            {'g','g','g','g','g','g','g','g','g','g'},
            {'s','s','s','s','s','s','s','s','s','s'},
            {'y','y','y','y','y','y','y','y','y','y'},
            {'r','r','r','r','r','r','r','r','r','r'}
    };
    private static char level4 [][] = new char[][]{
            {' ',' ',' ','y','y','y','y',' ',' ',' '},
            {' ',' ','r','r','r','r','r','r',' ',' '},
            {' ',' ','g','g','g','g','g','g',' ',' '},
            {' ','s','s','s','s','s','s','s','s',' '},
            {' ','y','y','y',' ',' ','y','y','y',' '},
            {' ','r','r','r',' ',' ','r','r','r',' '},
            {' ','g','g','g','g','g','g','g','g',' '},
            {' ',' ','s','s','s','s','s','s',' ',' '},
            {' ',' ','y','y','y','y','y','y',' ',' '},
            {' ',' ',' ','r','r','r','r',' ',' ',' '}
    };

    public static char[][] getLevel(int i) {
        switch (i) {
            case 1:
                return level1;
            case 2:
                return level2;
            case 3:
                return level3;
            case 4:
                return level4;
            case 5:
                return level1;
            case 6:
                return level1;
            case 7:
                return level1;
            case 8:
                return level1;
            case 9:
                return level1;
            case 10:
                return level1;
        }
        return null;
    }

}
