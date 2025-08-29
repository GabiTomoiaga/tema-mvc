    package com.example.temamvc.model;

    public interface Observable {
        void addObserver(Observer o);
        void removeObserver(Observer o);
        void notifyObservers();
    }

