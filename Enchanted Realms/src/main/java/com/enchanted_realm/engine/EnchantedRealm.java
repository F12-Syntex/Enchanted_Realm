package com.enchanted_realm.engine;

public class EnchantedRealm {
	
	private Window window;
	private Engine engine;
	private static final EnchantedRealm instance = new EnchantedRealm();
	
	public void init() {
		this.engine = new Engine();
		this.window = new Window(this.engine);
	}
	
	public void start(){
		
		this.init();
		
		this.window.show();
		
		
	}
	
	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}
	public static EnchantedRealm getInstance() {
		return EnchantedRealm.instance;
	}
	
	public static void main(String[] args) {
		EnchantedRealm.getInstance().start();
	}
	
	public Engine getEngine() {
		return engine;
	}
	
	public void setEngine(Engine engine) {
		this.engine = engine;
	}

}
