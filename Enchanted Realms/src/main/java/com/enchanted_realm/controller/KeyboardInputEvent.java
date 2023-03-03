package com.enchanted_realm.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.enchanted_realm.engine.Engine;

public class KeyboardInputEvent extends GameInput implements KeyListener {
	
	public KeyboardInputEvent(Engine engine) {
		super(engine);
	}
	
    @Override
    public void keyTyped(KeyEvent e) {
    	engine.getEntities().forEach(o -> o.keyTyped(e));
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	engine.getEntities().forEach(o -> o.keyPressed(e));
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	engine.getEntities().forEach(o -> o.keyReleased(e)); 
    }

    
}