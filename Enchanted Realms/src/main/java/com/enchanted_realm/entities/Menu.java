package com.enchanted_realm.entities;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.enchanted_realm.datatypes.Node;
import com.enchanted_realm.engine.EnchantedRealm;
import com.enchanted_realm.engine.Window;
import com.enchanted_realm.spritesheet.SpriteSheet;

@AutoJoin
public class Menu extends GraphicEntity{

	private final int MAX_MAP_SIZE = 200;
	private final int MIN_MAP_SIZE = 10;
	
	private int MAP_SIZE = 48;
	private final int CELL_SIZE = 32;
	
	private final int SCROLL_SPEED = 15;
	
	private int offset = 0;
	private Point mapOffset = new Point(0, 0);
	
	private Dimension grid;
	private Dimension menu;
	private MouseEvent mouseEvent;
	
	private List<TileElement> imageArray = new ArrayList<>();
	
	private Optional<TileElement> selected = Optional.empty();
	
	
	@Override
	public void render(Graphics g) {
		
		Dimension dimension = EnchantedRealm.getInstance().getWindow().getDimensions();
		Dimension menu = new Dimension(dimension.width, (int)(Window.DIMENSION.height/3.5));
		
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, dimension.width, dimension.height);
		
		int startY = (int)(dimension.getHeight() - menu.getHeight());
		
		int index = 0;
		int width = dimension.width / CELL_SIZE;
		
		
		if(EnchantedRealm.getInstance().getEngine().getSpriteSheetLoader().isLoading()) {
			imageArray.clear();
			for(SpriteSheet i : SpriteSheet.values()) {
				synchronized (i.getProperties()) {
					if(i.getProperties().isSpriteSheet()) {
						i.getProperties().map.values().forEach(o -> {
							TileElement element = new TileElement(Node.of(0, 0), o, i);
							imageArray.add(element);
						});
						continue;
					}
					TileElement element = new TileElement(Node.of(0, 0), i.getImage(), i);
					imageArray.add(element);		
				}
			}
		}
		
		outer:for(int y = 0;; y++) {
			for(int x = 0; x < width; x++) {
				
				if(index >= imageArray.size()) {
					break outer;
				}
				
				TileElement element = imageArray.get(index);
				element.posRaw = Node.of(x*CELL_SIZE, y*CELL_SIZE + offset + startY);
				element.pos = Node.of(x, y);
				element.render(g);

				index++;
			}
		}
		
		
		g.setColor(Color.gray);
		g.fillRect(0, 0, dimension.width, startY);
		g.setColor(Color.black);
		g.drawRect(0, startY, dimension.width, menu.height);
		
		
		this.grid = new Dimension(dimension.width, startY);
		this.menu = menu;
		
		this.renderGrid(g, grid);
		
		if(this.selected.isPresent()) {
			g.drawImage(this.selected.get().image, mouseEvent.getX(), mouseEvent.getY(), CELL_SIZE, CELL_SIZE, null);
		}
		
	}
	
	
	public void renderGrid(Graphics g, Dimension grid) {
		
	    int rows = ( grid.width / MAP_SIZE );
	    int columns = ( grid.height / MAP_SIZE );

	    for(int y = this.mapOffset.y; y < columns + this.mapOffset.y; y++) {
		    for(int x = this.mapOffset.x; x < rows + this.mapOffset.x; x++) {
		    	Rectangle node = new Rectangle(x*MAP_SIZE, y*MAP_SIZE, MAP_SIZE, MAP_SIZE);
		    	g.drawRect((int)node.getX(), (int)node.getY(), (int)node.getWidth(), (int)node.getHeight());
		    	
		    	if(this.mouseEvent != null) {
					if(this.mouseEvent.getY() < this.grid.getHeight()) {
						if(node.contains(mouseEvent.getPoint())) {
							g.setColor(new Color(0,0,0,0.4f));
							g.fillRect((int)node.getX(), (int)node.getY(), (int)node.getWidth(), (int)node.getHeight());
						 }		
					}	
				}
		    	
		    }
	    }
	   
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		super.mouseWheelMoved(e);
		
		double increment = e.getPreciseWheelRotation() * -SCROLL_SPEED;
		
		if(e.getY() > this.grid.getHeight()) {
		
			if((offset+increment) > 0) {
				offset = 0;
				return;
			}
			
			int y = this.imageArray.stream().sorted((a,b) -> a.posRaw.y-b.posRaw.y).findFirst().get().posRaw.y;
			
			int totalRows = this.imageArray.size()/(this.menu.width / CELL_SIZE);
			int cRow = (((y / CELL_SIZE)*-1) + (this.menu.width / CELL_SIZE)) - (this.menu.height / CELL_SIZE);
			
			if(cRow < totalRows || increment > 0) {
				this.offset += increment;
			}
			
		}else {
			
			int currentSize = this.MAP_SIZE;
			
			if(increment > 0) {
				currentSize++;
			}else {
				currentSize--;
			}
			
			if(currentSize > this.MIN_MAP_SIZE && currentSize < this.MAX_MAP_SIZE) {
				this.MAP_SIZE = currentSize;
			}
			
		}
		
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		this.mouseEvent = e;
		
		if(e.getY() < this.grid.getHeight()) {
			EnchantedRealm.getInstance().getWindow().getjFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			return;
		}
		
		
		if(EnchantedRealm.getInstance().getEngine().getSpriteSheetLoader().isLoading()) return;
		
		EnchantedRealm.getInstance().getWindow().getjFrame().setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		Point loc = new Point(e.getPoint().x / this.CELL_SIZE, (int)((e.getPoint().y - grid.getHeight() - offset) / this.CELL_SIZE));

		Optional<TileElement> hoveredElement = this.imageArray.stream().filter(o -> o.pos.equals(loc)).findFirst();
		
		if(hoveredElement.isPresent()) {
			this.imageArray.forEach(o -> o.hovered = false);
			hoveredElement.get().hovered = true;
		}
		
		
	}
	
	public void mouseClicked(MouseEvent e){
		super.mouseClicked(e);
		this.mouseEvent = e;
		
		
		
	}
	public void mousePressed(MouseEvent e){
		super.mousePressed(e);
		this.mouseEvent = e;
		
		if(EnchantedRealm.getInstance().getEngine().getSpriteSheetLoader().isLoading()) return;
		
		if(e.getY() < this.grid.getHeight()) return;
		
		Point loc = new Point(e.getPoint().x / this.CELL_SIZE, (int)((e.getPoint().y - grid.getHeight() - offset) / this.CELL_SIZE));

		this.selected = this.imageArray.stream().filter(o -> o.pos.equals(loc)).findFirst();
		
	}
	public void mouseReleased(MouseEvent e){
		super.mouseReleased(e);
		this.mouseEvent = e;
	}
	public void mouseEntered(MouseEvent e){
		super.mouseEntered(e);
		this.mouseEvent = e;
	}
	public void mouseExited(MouseEvent e){
		super.mouseExited(e);
		this.mouseEvent = e;
	}
	public void mouseDragged(MouseEvent e){
		super.mouseDragged(e);
		this.mouseEvent = e;
	}


	@Override
	public void tick() {
		
	}
	
	private class TileElement {
		private Node posRaw;
		private Node pos;
		private BufferedImage image;
		private SpriteSheet src;
		private boolean hovered;
		public TileElement(Node pos, BufferedImage image, SpriteSheet src) {
			super();
			this.posRaw = pos;
			this.image = image;
			this.src = src;
			this.hovered = false;
		}
		
		public void render(Graphics g) {
			g.setColor(Color.black);
			g.drawRect(posRaw.x, posRaw.y, CELL_SIZE, CELL_SIZE);	
			g.drawImage(image, posRaw.x, posRaw.y, CELL_SIZE, CELL_SIZE, null);
			if(this.hovered) {
				g.setColor(new Color(0,0,0,0.3f));
				g.fillRect(posRaw.x, posRaw.y, CELL_SIZE, CELL_SIZE);	
			}
		}
	}


}
