package com.enchanted_realm.entities;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

public abstract class GraphicEntity implements MouseInputListener, KeyListener, MouseWheelListener{

	public abstract void render(Graphics g);
	public abstract void tick();
	
	public int getPriority() {
		return 1;
	}
	
	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}
	public void mouseWheelMoved(MouseWheelEvent e) {}

}
