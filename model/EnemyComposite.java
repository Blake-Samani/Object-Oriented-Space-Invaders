package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import model.Shooter.Event;
import view.GameBoard;

public class EnemyComposite extends GameElement {

    public static final int NROW = 2;
    public static final int NCOLS = 10;
    public static final int ENEMY_SIZE = 20;
    public static final int Y_MOVE = 0;
    public static final int UNIT_MOVE = 5;
    private boolean movingToRight = true;
    private Random random = new Random();
    private boolean gameOver;
    private boolean isEmpty;

    private ArrayList<ArrayList<GameElement>> rows; //array of array of game elements
    private ArrayList<GameElement> bombs;
 
    public EnemyComposite(){
        rows = new ArrayList<>();
        bombs = new ArrayList<>();

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
        // render enemy array
        for (var r: rows)
            for(var e: r){
                e.render(g2);
            }
            
            //render bombs
        for(var b: bombs){
            b.render(g2);
        }
    
        }

    @Override
    public void animate() {
        int dx = UNIT_MOVE;
        int dy = Y_MOVE;
        if(movingToRight){ //if moving to right and reaches end of screen
            if(rightEnd() >= GameBoard.WIDTH){
                dx = -dx; //subtract from delta x
                dy = ENEMY_SIZE;
                movingToRight = false;
            } 
            
        }else{
                dx = -dx;
                if(leftEnd() <= 0 ){
                dx = -dx;
                dy = ENEMY_SIZE;
                movingToRight = true;
            }
    }

    //update x location
        for(var row: rows){
            for(var e: row){
            e.x += dx;
            e.y += dy;
            if(e.y == GameBoard.HEIGHT){
                gameOver = true;
            }
            }
        }

    //animate bombs
    for(var b: bombs)
        b.animate();
        
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

    public void dropBombs(){
        for (var row: rows){ //for each enemy
            for(var e: row){
                if(random.nextFloat() < 0.1F){ //each enemy has 10 percent chance to drop a bomb
                    bombs.add(new Bomb(e.x, e.y));
                }
            }
        }
    }

    public void removeBombsOutOfBound() {
        var remove = new ArrayList<GameElement>();
        for(var b: bombs){
            if (b.y >= GameBoard.HEIGHT){
                remove.add(b);
            }
        }
        bombs.removeAll(remove);
       
    }

    public void processCollision(Shooter shooter){
        //bullet detection vs enemy detection
        var removeBullets = new ArrayList<GameElement>(); //bullets are actually weapons so we need a reference to weapons

        for (var row: rows){
            var removeEnemies = new ArrayList<GameElement>();
            for(var enemy: row){
                for(var bullet: shooter.getWeapons()){
                    if(enemy.collideWith(bullet)){
                        removeBullets.add(bullet);
                        removeEnemies.add(enemy);
                        shooter.increaseScore();
                        
                    }
                }
            }
            row.removeAll(removeEnemies);
        }
        shooter.getWeapons().removeAll(removeBullets);

        //bullets vs bombs collision
        var removeBombs = new ArrayList<GameElement>();
        removeBullets.clear();

        for (var b: bombs){
            for(var bullet: shooter.getWeapons()){
                if(b.collideWith(bullet)){
                    removeBombs.add(b);
                    removeBullets.add(bullet);
                }
            }
        }
        shooter.getWeapons().removeAll(removeBullets);
        bombs.removeAll(removeBombs);

        // bombs vs shooter
        var removeShooterBlocks = new ArrayList<GameElement>();
        for (var blocks : shooter.getShooterComponents()) {
            for (var b : bombs) {
                if (blocks.collideWith(b)) {
                    removeShooterBlocks.add(blocks);
                    removeBombs.add(b);
                    if(shooter.getShooterComponents().size() == 4){
                        shooter.notifyObservers(Event.FOUR);
                    }else if(shooter.getShooterComponents().size() == 3){
                    shooter.notifyObservers(Event.THREE);
                    }else if(shooter.getShooterComponents().size() == 2){
                        shooter.notifyObservers(Event.TWO);
                    }else if(shooter.getShooterComponents().size() == 1){
                        shooter.notifyObservers(Event.ONE);
                    }else if(shooter.getShooterComponents().size() == 0){
                        shooter.notifyObservers(Event.ZERO);
                    }


                }
            }
        }
        shooter.getShooterComponents().removeAll(removeShooterBlocks);
        bombs.removeAll(removeBombs);
    }

    public boolean getGameOver(){
        return gameOver;
    }

    public boolean getisEmpty(){
        return isEmpty;
    }
  
    
}
