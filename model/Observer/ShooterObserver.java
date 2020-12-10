package model.Observer;

import model.Shooter;
import view.GameBoard;

public class ShooterObserver implements Observer {

    private GameBoard game;
    private Shooter shooter;

    public ShooterObserver(GameBoard gameboard){
        this.game = gameboard;
        shooter = game.getShooter();
    }

    

    @Override
    public void four() {
        shooter.decreaseScore();
    }

    @Override
    public void three() {
        shooter.decreaseScore(); //change to score or something else

    }

    @Override
    public void two() {
        shooter.decreaseScore();

    }

    @Override
    public void one() {
       shooter.decreaseScore();
       game.endGame();
    }

    @Override
    public void zero() {
        shooter.decreaseScore();
        game.endGame();
    }
    


}
