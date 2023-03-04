package com.enchanted_realm.engine;

import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import javax.swing.JPanel;

import org.reflections.Reflections;

import com.enchanted_realm.configuration.Configuration;
import com.enchanted_realm.controller.InputController;
import com.enchanted_realm.entities.AutoJoin;
import com.enchanted_realm.entities.GraphicEntity;
import com.enchanted_realm.spritesheet.SpriteSheetLoader;

/**
 * this class handles the graphics of the game.
 * @author https://github.com/F12-Syntex
 */
public class Engine extends JPanel implements WindowListener{

	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getGlobal();
	
	private final InputController inputController;
	private final ConcurrentLinkedQueue<GraphicEntity> entities = new ConcurrentLinkedQueue<>();
	private final SpriteSheetLoader spriteSheetLoader;
	
	private int fps;
	private AtomicBoolean running = new AtomicBoolean(true);
	
	/* threads */
	private Thread processThread;
	private ExecutorService threadHandler = Executors.newFixedThreadPool(4);

	public Engine() {
		this.inputController = new InputController(this);
		this.spriteSheetLoader = new SpriteSheetLoader();
		
		this.addKeyListener(this.inputController.getKeyboardInputEvent());
		this.addMouseListener(this.inputController.getMouseInputListener());
		this.addMouseWheelListener(this.inputController.getMouseInputListener());
		this.addMouseMotionListener(this.inputController.getMouseInputListener());
		
		this.setFocusable(true);
		this.requestFocus();
		
		
		this.threadHandler.submit(() -> {
			this.spriteSheetLoader.loadSpriteSheets();
		});
		
		this.loadEntities();
		
		
		
		this.processThread = new Thread(() -> {
			this.run();
		});
		
		this.threadHandler.submit(this.processThread);
		
	}
	
	public Thread getProcessThread() {
		return processThread;
	}

	public void setProcessThread(Thread processThread) {
		this.processThread = processThread;
	}

	public ExecutorService getThreadHandler() {
		return threadHandler;
	}

	public void setThreadHandler(ExecutorService threadHandler) {
		this.threadHandler = threadHandler;
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    synchronized(this.entities) {
	    	this.entities.stream()
			 			 .sorted((a, b) -> a.getPriority() - b.getPriority())
			 			 .forEach(o -> o.render(g));	
		}
	    
	}
	
	public void close() {
		logger.info("exiting program");
		this.running = new AtomicBoolean(false);
		this.threadHandler.shutdown();
		try {
			this.threadHandler.awaitTermination(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		}
		System.exit(1);
	}
	
	public void run(){
	    long lastTime = System.nanoTime();
	    
	    double amountOfTicks = Configuration.MAX_FPS;
	    double ns = 1000000000/amountOfTicks;
	    double delta = 0;
	    long timer = System.currentTimeMillis();
	    this.setFps(0);

	    long renderLastTime=System.nanoTime();
	    double renderNs=1000000000/amountOfTicks;
	    double renderDelta = 0;

	    while(this.running.get()){
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
	        while(this.running.get() && renderDelta >= 1){
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
	
	public ConcurrentLinkedQueue<GraphicEntity> getEntities() {
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

	public SpriteSheetLoader getSpriteSheetLoader() {
		return spriteSheetLoader;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.close();
	}


	@Override
	public void windowOpened(WindowEvent e) {}
	
	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}


}
