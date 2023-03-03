package com.enchanted_realm.controller;

import com.enchanted_realm.engine.Engine;

public class InputController {
	
	private final Engine engine;
	
	private final KeyboardInputEvent keyboardInputEvent;
	private final MouseListener mouseInputListener;

	public InputController(Engine engine) {
		this.engine = engine;
		
		this.mouseInputListener = new MouseListener(engine);
		this.keyboardInputEvent = new KeyboardInputEvent(engine);
	}

	public Engine getEngine() {
		return engine;
	}

	public KeyboardInputEvent getKeyboardInputEvent() {
		return keyboardInputEvent;
	}

	public MouseListener getMouseInputListener() {
		return mouseInputListener;
	}

}
