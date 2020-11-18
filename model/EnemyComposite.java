package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import view.GameBoard;

public class EnemyComposite extends GameElement {

    public static final int NROW = 2;
    public static final int NCOLS = 10;
    public static final int ENEMY_SIZE = 20;
    public static final int UNIT_MOVE = 5;
    private boolean movingToRight = true;

    private ArrayList<ArrayList<GameElement>> rows; //array of array of game elements

    public EnemyComposite(){
        rows = new ArrayList<>();

        for(int r = 0; r < NROW; r++){
            var onerow = new ArrayList<GameElement>();
            rows.add(onerow);
            for(int c = 0; c < NCOLS; c++){
                onerow.add(new Enemy(
                    c * ENEMY_SIZE * 2, r * ENEMY_SIZE * 2, ENEMY_SIZE, Color.yellow, true
                ));
            }
        }
    }

    @Override
    public void render(Graphics2D g2) { //iterating through rows, 2d array list
        for (var r: rows)
            for(var e: r){
                e.render(g2);
            }
    }

    @Override
    public void animate() {
        int dx = UNIT_MOVE;
        if(movingToRight) //if moving to right and reaches end of screen
            if(rightEnd() >= GameBoard.WIDTH){
                dx = -dx; //subtract from delta x
                movingToRight = false;
            }else{
                dx = -dx;
                if(leftEnd() <= 0 ){
                dx = -dx;
                movingToRight = true;
            }
    }

    //update x location
    for(var row: rows){
        for(var e: row){
            e.x += dx;
        }
    }
        
    }


    private int rightEnd(){
        int xEnd = -100;
        for(var row: rows){
            if(row.size() == 0) continue; //no enemy in this row
            int x = row.get(row.size() -1).x + ENEMY_SIZE; //get the x location from index rowsize -1 and add enemy size to it
            if (x > xEnd) xEnd = x;
        }
        return xEnd;
    }

    private int leftEnd(){
        int xEnd = 9000;
        for(var row: rows){
            if(row.size() == 0) continue;
            int x = row.get(0).x;
            if(x < xEnd) xEnd = x;

        }
        return xEnd;
    }
}
