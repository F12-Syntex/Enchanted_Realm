package com.enchanted_realm.spritesheet;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

import com.enchanted_realm.datatypes.Node;

public class SpriteSheetProperties {

	private final boolean spriteSheet;
	private final int size;
	
	public Map<Node, BufferedImage> map;

	public SpriteSheetProperties() {
		super();
		this.map = new LinkedHashMap<>();
		this.spriteSheet = false;
		this.size = 0;
	}
	
	public SpriteSheetProperties(boolean spriteSheet, int size) {
		super();
		this.map = new LinkedHashMap<>();
		this.spriteSheet = spriteSheet;
		this.size = size;
	}
	
	public boolean isSpriteSheet() {
		return spriteSheet;
	}
	
	public int getSize() {
		return size;
	}
	
	public void renderSpriteLocation(Node node, BufferedImage image) {
		synchronized(this.map) {
			this.map.put(node, image);	
		}
	}
	
	public BufferedImage getImageFromLocation(int x, int y) {
		return map.get(Node.of(x, y));
	}
	
	public BufferedImage getImageFromLocation(Node node) {
		return map.get(node);
	}
	
}
