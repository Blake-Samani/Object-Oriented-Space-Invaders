package model;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class EnemyComposite extends GameElement {

    public static final int NROW = 2;
    public static final int NCOLS = 10;
    public static final int ENEMY_SIZE = 20;
    public static final int UNIT_MOVE = 5;

    private ArrayList<ArrayList<GameElement>> rows; //array of array of game elements

    public EnemyComposite(){
        rows = new ArrayList<>();

        for(int r = 0; r < NROW; r++){
            var onerow = new ArrayList<>();
            for(int c = 0; c < NCOLS; c++){

            }
        }
    }

    @Override
    public void render(Graphics2D g2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void animate() {
        // TODO Auto-generated method stub

    }
    
}
