package com.enchanted_realm.engine;

import java.util.logging.Logger;

import com.enchanted_realm.configuration.Configuration;

/**
 * The singleton class responsible for acting as the global handler in this game.
 * @author https://github.com/F12-Syntex
 */
public final class EnchantedRealm {
	
	private final Window window;
	private final Engine engine;
	private static final EnchantedRealm instance = new EnchantedRealm();
	private final Logger logger = Logger.getGlobal();
	
	private EnchantedRealm() {
		this.engine = new Engine();
		this.window = new Window(this.engine);
		
	}
	
	public void start(){

		this.window.show();
		logger.info(Configuration.NAME + " v" + Configuration.VERSION + " has started");
		
	}
	
	public static void main(String[] args) {
		EnchantedRealm enchantedRealm = EnchantedRealm.getInstance();
		enchantedRealm.start();
	}
	
	public static EnchantedRealm getInstance() {
		return EnchantedRealm.instance;
	}
	
	public Window getWindow() {
		return window;
	}

	public Engine getEngine() {
		return engine;
	}
	

}
