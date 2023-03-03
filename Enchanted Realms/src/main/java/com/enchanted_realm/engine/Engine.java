package com.enchanted_realm.engine;

import java.awt.Graphics;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import org.reflections.Reflections;

import com.enchanted_realm.controller.InputController;
import com.enchanted_realm.entities.AutoJoin;
import com.enchanted_realm.entities.GraphicEntity;

/**
 * this class handles the graphics of the game.
 * @author https://github.com/F12-Syntex
 */
public class Engine extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private final InputController inputController;
	
	private final List<GraphicEntity> entities = new ArrayList<>();
	
	private Thread processThread;
	
	private boolean running = true;
	
	private int fps;
	private final int MAX_FPS = 180;
	
	public Engine() {
		this.inputController = new InputController(this);
		
		this.addKeyListener(this.inputController.getKeyboardInputEvent());
		this.addMouseListener(this.inputController.getMouseInputListener());
		this.addMouseMotionListener(this.inputController.getMouseInputListener());
		
		this.setFocusable(true);
		this.requestFocus();
		
		this.loadEntities();
		
		this.processThread = new Thread(() -> {
			this.run();
		});
		
		this.processThread.start();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    for(GraphicEntity entity : entities) {
	    	entity.render(g);
	    }
	}
	
	public void run(){
	    long lastTime = System.nanoTime();
	    
	    double amountOfTicks = MAX_FPS;
	    double ns = 1000000000/amountOfTicks;
	    double delta = 0;
	    long timer = System.currentTimeMillis();
	    this.setFps(0);

	    long renderLastTime=System.nanoTime();
	    double renderNs=1000000000/amountOfTicks;
	    double renderDelta = 0;

	    while(running){
	        long now = System.nanoTime();
	        delta += (now - lastTime) / ns;
	        lastTime = now;
	        while(delta >= 1){
	            tick();
	            delta--;
	        }

	        now = System.nanoTime();
	        renderDelta += (now - renderLastTime) / renderNs;
	        renderLastTime = now;
	        while(running && renderDelta >= 1){
	            render();
	            this.setFps(this.getFps() + 1);
	            renderDelta--;
	        }

	        if(System.currentTimeMillis() - timer > 1000){
	            timer += 1000;
	            this.setFps(0);
	        }
	    }
	}
	public void tick() {
	    for(GraphicEntity entity : entities) {
	    	entity.tick();
	    }
	}
	
	public void render() {
		repaint();
	}
	
	public InputController getInputController() {
		return inputController;
	}
	
	public List<GraphicEntity> getEntities() {
		return entities;
	}


	public void loadEntities() {
		Reflections reflections = new Reflections("com.enchanted_realm.entities");

		Set<Class<? extends GraphicEntity>> entityClazzes = reflections.getSubTypesOf(GraphicEntity.class);

		for (Class<? extends GraphicEntity> clazz : entityClazzes) {
		    try {
		    	
		    	if(clazz.isAnnotationPresent(AutoJoin.class)) {
		    		
			        Constructor<? extends GraphicEntity> constructor = clazz.getDeclaredConstructor();
			        GraphicEntity entity = constructor.newInstance();
			       
			        this.entities.add(entity);
		    		
		    	}
		    	
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		}
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

}
