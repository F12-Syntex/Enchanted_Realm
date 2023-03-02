package com.enchanted_realm.engine;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

public class Window implements ComponentListener {

	private JFrame jFrame;
	private final Dimension DIMENSION = new Dimension(1247, 1036);
	
	public Window(Engine engine) {
		
		this.jFrame = new JFrame(Configuration.NAME + " v" + Configuration.VERSION);
		this.jFrame.setSize(DIMENSION);
		this.jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.jFrame.setLocationRelativeTo(null);
		this.jFrame.add(engine);
		this.jFrame.addComponentListener(this);
		
	}

	public void show() {
		this.jFrame.setVisible(true);
	}
	
	public Dimension getDimensions() {
		return DIMENSION;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		//System.out.println("new Dimension(" + this.jFrame.getSize().width + ", " + this.jFrame.getSize().height + ");");
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
