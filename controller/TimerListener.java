package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import model.Bullet;
import model.Shooter;
import view.GameBoard;

public class TimerListener implements ActionListener {

    public enum EventType{
        KEY_RIGHT, KEY_LEFT, KEY_SPACE
    }

    private GameBoard gameBoard;
    private LinkedList<EventType> eventQueue; //linked list of events that will be handled in the action performed method-> processEventQueue method
    private final int BOMB_DROP_FREQ = 20;
    private int frameCounter = 0;

    public TimerListener(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        eventQueue = new LinkedList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ++frameCounter;
    
        update();//updates all game elements on the canvas//calls animate
        processEventQueue();
        processCollision();//loops over array list to detect and process collisions
        gameBoard.getCanvas().repaint();

    }

    private void processEventQueue(){
        while (!eventQueue.isEmpty()) { // while que is not empty
            var e = eventQueue.getFirst(); //get first in stack
            eventQueue.removeFirst();
            Shooter shooter = gameBoard.getShooter();
            if (shooter == null) return; // if no shooter

            switch(e){
                case KEY_LEFT:
                    shooter.moveLeft();
                    break;
                case KEY_RIGHT:
                    shooter.moveRight();
                    break;
                case KEY_SPACE:
                    if(shooter.canFireMoreBullets())
                    shooter.getWeapons().add(new Bullet(shooter.x,shooter.y));
                    break;
            }
        }
        if(frameCounter == BOMB_DROP_FREQ){
            gameBoard.getEnemyComposite().dropBombs();
            frameCounter = 0;
        }
    }


    private void processCollision(){
        var shooter = gameBoard.getShooter();
        var enemyComposite = gameBoard.getEnemyComposite();

       shooter.removeBulletsOutOfBound();
       enemyComposite.removeBombsOutOfBound();
       enemyComposite.processCollision(shooter);
       if(enemyComposite.getGameOver() == true){
           gameBoard.endGame();
       }else if(enemyComposite.getisEmpty() == true){
           gameBoard.endGame();
       }
       

    }

    private void update(){
        for (var e: gameBoard.getCanvas().getGameElements())
            e.animate();
    }

    public LinkedList<EventType> getEventQueue() {
        return eventQueue;
    }

    

    
    
}
