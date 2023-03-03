package com.enchanted_realm.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.enchanted_realm.engine.Engine;

public abstract class GameInput {
	protected final Logger logger = Logger.getLogger("input");
	protected final Engine engine;
	
	private final Level MIN_LOG_LEVEL = Level.INFO;
	
	public GameInput(Engine engine) {
		this.engine = engine;
		logger.setFilter(record -> record.getLevel().intValue() > MIN_LOG_LEVEL.intValue());
	}
}
