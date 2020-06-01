package walkthrough.toolWindow.utils;

import java.util.ArrayList;

public class Observable {

    private ArrayList<Observer> observers;

    public Observable() {
        observers = new ArrayList<Observer>();
    }

    public void addListener(Observer observer) {
        observers.add(observer);
    }

    public void notifyAll(Event event) {
        for(Observer observer: observers) {
            observer.onEvent(event);
        }
    }
}
