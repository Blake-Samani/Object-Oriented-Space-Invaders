package model.Observer;

import model.Shooter;

public interface Subject {

    void addShooterListener(Observer observer);
    void removeShooterListener(Observer observer);
    void notifyObservers(Shooter.Event event);

}
