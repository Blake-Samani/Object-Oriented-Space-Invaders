package view;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.KeyController;
import controller.TimerListener;
import model.Shooter;
import model.ShooterElement;

public class GameBoard {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 300;

    private JFrame window;
    private MyCanvas canvas;
    private Shooter shooter;
    private Timer timer;
    private TimerListener timerListener;

    public GameBoard(JFrame window){
        this.window = window;
    }
    
    public void init(){
        Container cp = window.getContentPane();

        canvas = new MyCanvas(this, WIDTH, HEIGHT);
        cp.add(BorderLayout.CENTER, canvas);

        JButton startButton = new JButton("Start");
        JButton quitButton = new JButton("Quit");
        
        JPanel southPanel = new JPanel();
        southPanel.add(startButton);
        southPanel.add(quitButton);
        cp.add(BorderLayout.SOUTH, southPanel);
        canvas.addKeyListener(new KeyController(this)); //for the canvas to register key presses, we add key listener to the canvas
        canvas.requestFocusInWindow(); // so canvas will regester the keypresses regardless of if we are on canvas or not
        canvas.setFocusable(true);
        startButton.setFocusable(false);
        quitButton.setFocusable(false);

        canvas.getGameElements().add(new TextDraw("Click <Start> to Play", 100, 100, Color.yellow, 30));
        
        timerListener = new TimerListener(this);
        timer = new Timer(50, timerListener); //50 ms. 

        startButton.addActionListener(event ->{
            shooter = new Shooter(GameBoard.WIDTH / 2, GameBoard.HEIGHT -  ShooterElement.SIZE); //bottom of middle of screen location , remember, x axis starts from top.
            canvas.getGameElements().clear();
            canvas.getGameElements().add(shooter);
            timer.start(); //timer class redraws the screen under actionperformed method, this will happen every 50 ms

        });

    

    }

    public MyCanvas getCanvas() {
        return canvas;
    }
   
    public TimerListener getTimerListener() {
        return timerListener;
    }

    public Timer getTimer() {
        return timer;
    }
    public Shooter getShooter() {
        return shooter;
    }

    
}
