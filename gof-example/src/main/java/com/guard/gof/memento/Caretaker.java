package com.guard.gof.memento;

/**
 * Created by yxwang on 16-2-17.
 */
public class Caretaker {
    private Memento memento;

    public void setMemento(Memento memento) {
        this.memento = memento;
    }

    public Memento getMemento() {
        return memento;
    }
}
