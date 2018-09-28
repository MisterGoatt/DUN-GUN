package com.bpa.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import java.util.HashMap;

public enum TileType {
	// This is where you specify what your block types are corresponding to their number in the map file
	GRASS(1, true, "grass"),
	DIRT(2, true, "dirt"),
	SKY(3, false, "sky"),
	LAVA(4, true, "lava"),	
	CLOUD(5, false, "cloud"),
	STONE(6, true, "stone");
	
	public static final int TILE_SIZE = 16;
	
	private int id;
	private boolean collidable;
	private String name;
	private float damage;
	
	private TileType (int id, boolean collidable, String name) {
		this(id, collidable, name, 0);
		
	}
	private TileType (int id, boolean collidable, String name, float damage) {
		this.id = id;
		this.collidable = collidable;
		this.name = name;
		this.damage = damage;
		}
	public int getId() {
		return id;
	}
	public boolean isCollidable() {
		return collidable;
	}
	public String getName() {
		return name;
	}
	public float getDamage() {
		return damage;
	}
	
	private static HashMap<Integer, TileType> tileMap;
	
	static{
		for(TileType tileType : TileType.values()) {
			tileMap.put(tileType.getId(), tileType);
		}
	}
	public static TileType getTileTypeById (int id) {
		return tileMap.get(id);
	} 
	
}



