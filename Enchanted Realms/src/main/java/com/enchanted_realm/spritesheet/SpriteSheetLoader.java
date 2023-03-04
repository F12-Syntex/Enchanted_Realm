package com.enchanted_realm.spritesheet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.enchanted_realm.datatypes.Node;

public class SpriteSheetLoader {
	
	private final File root;
	private boolean loading;;
	
	public SpriteSheetLoader() {
		this.root = new File("target/classes/spritesheet"); 
	}

	public void loadSpriteSheets() {
		this.setLoading(true);
		this.generateCode(root);
		this.setLoading(false);
	}
	
	public void generateCode(File root) {
		
		if(root == null) {
			return;
		}
		
		for(File file : root.listFiles()) {
			
			if(file.isDirectory()) {
				this.generateCode(file);
				continue;
			}
			
			
			String path = file.getParent() + "\\" + file.getName();
			
			String name = path.toUpperCase().split("SPRITESHEET\\\\")[1]
											.split("[.]")[0]
											.replaceAll("[^a-zA-Z0-9]", "_");
			
			//System.out.println(name + "(\"" + path.split("spritesheet\\\\")[1].replace("\\", "\\\\") + "\", new SpriteSheetDimensions()),");
			
			try {
				
				SpriteSheet sheet = SpriteSheet.valueOf(name);
				
				if(sheet.getProperties().isSpriteSheet()) {
					
					Logger.getGlobal().info("rendering sprite sheet");
					
					
					int cell = sheet.getProperties().getSize();
					
					BufferedImage img = ImageIO.read(file);
					
					for(int y = 0; y < img.getHeight(); y+=cell) {
						for(int x = 0; x < img.getWidth(); x+=cell) {
							BufferedImage sprite = img.getSubimage(x, y, cell, cell);
							sheet.getProperties().renderSpriteLocation(Node.of(x, y), sprite);
						}
					}
					
					Logger.getGlobal().info("rendered sprite sheet");
					
				}
				
				sheet.setImage(ImageIO.read(file));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public boolean isLoading() {
		return loading;
	}

	public void setLoading(boolean loading) {
		this.loading = loading;
	}
	

	
}
