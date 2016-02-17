package com.guard.gof.memento;

/**
 * Created by yxwang on 16-2-17.
 */
//备忘录模式
public class Main {
    public void execute(){
        Character character=new Character();
        character.setMp(100);
        character.setHp(100);

        Caretaker caretaker=new Caretaker();
        caretaker.setMemento(character.createMemento());

        //do something in character
        character.setHp(50);


        //reset the character
        character.reset(caretaker.getMemento());

    }
}
