
	package com.bpa.world;

	import com.badlogic.gdx.graphics.OrthographicCamera;
	import com.badlogic.gdx.maps.tiled.TiledMap;
	import com.badlogic.gdx.maps.tiled.TmxMapLoader;
	import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

	public class TiledGameMap extends GameMap {

		TiledMap tiledMap;
		OrthogonalTiledMapRenderer tiledMapRenderer;
		
		
		public TiledGameMap() {
			//references the  map file 
			tiledMap = new TmxMapLoader().load("map2.tmx");
			tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		}
		
		@Override
		public void render(OrthographicCamera camera) {
			tiledMapRenderer.setView(camera);
			tiledMapRenderer.render();
			

		}

		@Override
		public void update(float delta) {
			

		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
		}

		@Override
		public TileType getTileTypeByCoordinate(int layer, int col, int row) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getWidth() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getLayers() {
			// TODO Auto-generated method stub
			return 0;
		}
	}
