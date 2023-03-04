package com.enchanted_realm.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import com.enchanted_realm.engine.Engine;

public class MouseListener extends GameInput implements MouseInputListener, MouseWheelListener {

	public MouseListener(Engine engine) {
		super(engine);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		engine.getEntities().forEach(o -> o.mouseClicked(e));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		logger.info(e.getPoint().toString());
		engine.getEntities().forEach(o -> o.mousePressed(e));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		engine.getEntities().forEach(o -> o.mouseReleased(e));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		engine.getEntities().forEach(o -> o.mouseEntered(e));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		engine.getEntities().forEach(o -> o.mouseExited(e));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		engine.getEntities().forEach(o -> o.mouseDragged(e));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		engine.getEntities().forEach(o -> o.mouseMoved(e));
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		engine.getEntities().forEach(o -> o.mouseWheelMoved(e));
	}
    
}