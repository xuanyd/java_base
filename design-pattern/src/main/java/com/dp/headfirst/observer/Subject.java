package com.dp.headfirst.observer;

public interface Subject {
    public void registerObserver(Observer o);
    public void unRegisterObserver(Observer o);
    public void notifyObservers();
}