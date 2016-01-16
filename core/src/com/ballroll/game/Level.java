package com.ballroll.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;

import com.ballroll.game.objects.Ball;
import com.ballroll.game.objects.Tile;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ballroll.util.AudioManager;
import com.ballroll.util.Constants;
import com.ballroll.game.Assets;
import com.ballroll.util.CameraHelper;
import java.math.*;

public class Level {

	public static final String TAG = Level.class.getName();
	
	// tile single
	public Tile tile;
	
	// tile array
	public Array<Tile> tiles;
	
	// ball single
	public Ball ball;
	
	// tile display index
	private int tileDisplayIndex;
	
	// map box pos
	public Vector2 mapBoxPos;
	
	// variable to store level number
	public int levelNumber;
	
	// variable to store score
	public int score;
	
	// variable to see if this is new game
	private boolean newGame; 
	
	// vehicle position
	private Vector2 objectPosition;
	
	// vehicle counter
	private int vehicleCounter;
	
	// game over boolean
	public boolean isGameOver;
	
	// particle pool 1
	public ParticleEffectPool pool1;
	public Array<PooledEffect> activeEffects1;
	
	// particle pool 2
	public ParticleEffectPool pool2;
	public Array<PooledEffect> activeEffects2;
	
	// tiled map
	public TiledMap map;
	
	public MapObjects mapObjects;
	public MapLayer mapObjectLayer;
	
	// Center of map
	private float mapCenterX;
	private float mapCenterY;
	
	// Top of map
	private float mapTopX;
	private float mapTopY;
	
	//Tile map layers
	TiledMapTileLayer layer1;
	TiledMapTileLayer layer2;
	TiledMapTileLayer layer3;
	TiledMapTileLayer layer4;
	TiledMapTileLayer layer5;
	
	// Tile width
	private float tileWidth;
	
	// Tile height
	private float tileHeight;
	
	// Input mode: 0 = move unit, 1 = build
	private int inputMode;
	
	// movement variable
	private float moveXAxis;
	private float moveYAxis;
	
	// last screen coords
	private Vector2 lastScrCoords;
	
	// click/touch start and end location
	private int touchStart;
	private int touchEnd;
	
	// move direction
	private int moveDirection;
	
	// save move direction
	private int saveMoveDirection;
	
	// ball map position
	private Vector2 ballMapPos;
	
	// ball screen position
	private Vector2 ballScreenPos;
	
	// loading complete
	private boolean isLoadingComplete;
	
	public Level (int levelNumberIn, Vector2 cameraIn) {

		init(levelNumberIn, cameraIn);
	}
		
	private void init(int levelNumberIn, Vector2 cameraIn) {
		
		// level number
		levelNumber = levelNumberIn;
		
		// ball screen pos init
		float ballCenterAdjX = 0 * Constants.BOX_TO_WORLD;
		float ballCenterAdjY = .25f * Constants.BOX_TO_WORLD;
		
		ballScreenPos = new Vector2(0,0);
		ballScreenPos.x = Gdx.app.getGraphics().getWidth() * .5f + ballCenterAdjX;
		ballScreenPos.y = Gdx.app.getGraphics().getHeight() * .5f + ballCenterAdjY;
		
		// Ball
		ball = new Ball(ballScreenPos);
	
	
		/*
		
		// Particle Pool 1
		
		ParticleEffect explosionEffect1 = new ParticleEffect();
		
		explosionEffect1.load(Gdx.files.internal("particles/enemyExp.pfx"),
				Gdx.files.internal("particles"));
		
		float pScale = Constants.PARTICLESCALE1;

	    float scaling = explosionEffect1.getEmitters().get(0).getScale().getHighMax();
	    explosionEffect1.getEmitters().get(0).getScale().setHigh(scaling * pScale);

	    scaling = explosionEffect1.getEmitters().get(0).getScale().getLowMax();
	    explosionEffect1.getEmitters().get(0).getScale().setLow(scaling * pScale);
	    
	    scaling = explosionEffect1.getEmitters().get(0).getVelocity().getHighMax();
	    explosionEffect1.getEmitters().get(0).getVelocity().setHigh(scaling * pScale);

	    scaling = explosionEffect1.getEmitters().get(0).getVelocity().getLowMax();
	    explosionEffect1.getEmitters().get(0).getVelocity().setLow(scaling * pScale);
	    
	    pool1 = new ParticleEffectPool(explosionEffect1, 10 , 30);
	    activeEffects1 = new Array<PooledEffect>();
	    
	    */
		
		/*
	    
		// Particle Pool 2
		
		ParticleEffect explosionEffect2 = new ParticleEffect();
		
		explosionEffect2.load(Gdx.files.internal("particles/enemyExp.pfx"),
				Gdx.files.internal("particles"));
		
		pScale = Constants.PARTICLESCALE2;

	    scaling = explosionEffect2.getEmitters().get(0).getScale().getHighMax();
	    explosionEffect2.getEmitters().get(0).getScale().setHigh(scaling * pScale);

	    scaling = explosionEffect2.getEmitters().get(0).getScale().getLowMax();
	    explosionEffect2.getEmitters().get(0).getScale().setLow(scaling * pScale);
	    
	    scaling = explosionEffect2.getEmitters().get(0).getVelocity().getHighMax();
	    explosionEffect2.getEmitters().get(0).getVelocity().setHigh(scaling * pScale);

	    scaling = explosionEffect2.getEmitters().get(0).getVelocity().getLowMax();
	    explosionEffect2.getEmitters().get(0).getVelocity().setLow(scaling * pScale);
	    
	    pool2 = new ParticleEffectPool(explosionEffect2, 10 , 30);
	    activeEffects2 = new Array<PooledEffect>();
	    
	    */
		
		// set to new game
		newGame = true;
		
		// set score to 0
		score = 0;
		
		// init vehicle pos 
		objectPosition = new Vector2(0,0);
		
		// init vehicle counter
		vehicleCounter = 0;
		
		// level game over init
		isGameOver = false;
		
		loadLevel();

		// load tiled map
		map = new TmxMapLoader().load("maps/IsoTest.tmx");
		
		// fetch tiled map layers
		layer1 = (TiledMapTileLayer) map.getLayers().get(0);
		layer2 = (TiledMapTileLayer) map.getLayers().get(1);
		layer3 = (TiledMapTileLayer) map.getLayers().get(2);
		layer4 = (TiledMapTileLayer) map.getLayers().get(3);
		layer5 = (TiledMapTileLayer) map.getLayers().get(4);
		
		// assign tile width
		
		tileWidth = layer1.getTileWidth();
		
		// assign tile height
		
		tileHeight = layer1.getTileHeight();
		
		// init tiles array
		
		tiles = new Array<Tile>();
		
		// fill tile array with map data
		fillTiles();
		
		// init movement variable
		
		moveXAxis = 0;
		moveYAxis = 0;
		
		// init last screen coords
		
		lastScrCoords = new Vector2(0,0);
		
		// click touch start and end
		
		touchStart = 0;
		touchEnd = 0;
		
		// set move diretions to nil
		moveDirection = Constants.NIL;
		saveMoveDirection = Constants.NIL;
		
		// ball map pos
		ballMapPos = new Vector2(0,0);
		
		// loading complete 
		isLoadingComplete = false;
	
	}
	
	private void fillTiles() {
		
		// fill layer 1 tiles
		
		for (int x = 0; x < layer1.getWidth(); x++) {
			
			for (int y = 0; y < layer1.getHeight(); y++) {
				
				Cell cell = layer1.getCell(x, y);
				TiledMapTile mapTile = cell.getTile();
				String height = (String) mapTile.getProperties().get("Height");
				int heightInt = Integer.parseInt(height.trim());
				String slant = (String) mapTile.getProperties().get("Slant");
				int slantInt = Integer.parseInt(slant.trim());
				
				tile = null;
				tile = new Tile(x, y, heightInt, slantInt, 1);
				tiles.add((Tile)tile);	
				
			}
			
		}
		
	}
	
	public float returnMapCenterX() {
		
		return mapCenterX;
		
	}
	
	public float returnMapCenterY() {
		
		return mapCenterY;
		
	}

	public float returnMapTopX() {
		
		return mapTopX;
		
	}
	
	public float returnMapTopY() {
		
		return mapTopY;
		
	}
	
	public void update (float deltaTime, Vector2 cameraPosition, Vector2 cameraStartPosition,
			            boolean isLeftClicked, Vector2 scrCoords) {
		
		if (!isLoadingComplete) {
			
			isLoadingComplete = true;
			
		}
		
		switch (Gdx.app.getType()) {
		
			case Desktop: 
				updateBallMovementDesktop(isLeftClicked, scrCoords);
				break;
		
			case Android:
				updateBallMovementTouchScreen(isLeftClicked, scrCoords);
				break;
		
			default:
				break;
				
		}
		
		saveMoveDirection = moveDirection;
		ballUpdate(deltaTime);
		
		ballMapPos = screenToMap(cameraPosition, cameraStartPosition, 
								 ballScreenPos.x, ballScreenPos.y, saveMoveDirection, false);
		
		int mapX = (int) ballMapPos.x;
		int mapY =  Constants.MAP_HEIGHT - (int) ballMapPos.y - 1;
		
		Tile thisTile = getTile(mapX, mapY);
		
		if (thisTile != null) {
			
			Cell cell = new Cell();
			
			TiledMapTile tile = map.getTileSets().getTileSet("Tiles").getTile(12);
			cell.setTile(tile);
			layer1.setCell(mapX, mapY, cell);
			
		}
		
	}	
	
	private void updateBallMovementDesktop(boolean isLeftClicked, Vector2 scrCoords) {
		
		boolean right = false;
		boolean left = false;
		boolean up = false;
		boolean down = false;
		
		if (isLeftClicked &&
			scrCoords.x != 0 &&
			scrCoords.y != 0) {
			
			right = isTouchRight(scrCoords);		
			left = isTouchLeft(scrCoords);
			up = isTouchUp(scrCoords);
			down = isTouchDown(scrCoords);
			
			if (up && right)
				moveDirection = Constants.NE;
			
			if (up && left)
				moveDirection = Constants.NW;
				
			if (down && right)
				moveDirection = Constants.SE;
				
			if (down && left)
				moveDirection = Constants.SW;
				
		}
		
	}
	
	private void updateBallMovementTouchScreen(boolean isLeftClicked, Vector2 scrCoords) {
		
		boolean right = false;
		boolean left = false;
		boolean up = false;
		boolean down = false;
		
		if (isLeftClicked &&
			scrCoords.x != 0 &&
			scrCoords.y != 0 &&
			touchStart == 0) {
			
			// assign new touch start area
			 
			right = isTouchRight(scrCoords);		
			left = isTouchLeft(scrCoords);
			up = isTouchUp(scrCoords);
			down = isTouchDown(scrCoords);
			
			if (up && right)
				touchStart = Constants.NE;
			
			if (up && left)
				touchStart = Constants.NW;
				
			if (down && right)
				touchStart = Constants.SE;
				
			if (down && left)
				touchStart = Constants.SW;
			
		}
		
		// assign end touch area
		
		if (touchStart != 0) {
			
			right = isTouchRight(scrCoords);		
			left = isTouchLeft(scrCoords);
			up = isTouchUp(scrCoords);
			down = isTouchDown(scrCoords);
			
			if (up && right)
				touchEnd = Constants.NE;
			
			if (up && left)
				touchEnd = Constants.NW;
			
			if (down && right)
				touchEnd = Constants.SE;
			
			if (down && left)
				touchEnd = Constants.SW;
			
		}
		
		// if touch has ended and touch start and end have values, see the direction
		
		if (!isLeftClicked &&
			touchStart != 0 &&
			touchEnd != 0) {
			
			moveDirection = Constants.NIL;
			
			if (touchStart == Constants.NE &&
				touchEnd == Constants.SW)
				moveDirection = Constants.SW;
			
			if (touchStart == Constants.NW &&
				touchEnd == Constants.SE)
				moveDirection = Constants.SE;
			
			if (touchStart == Constants.SE &&
				touchEnd == Constants.NW)
				moveDirection = Constants.NW;
			
			if (touchStart == Constants.SW &&
				touchEnd == Constants.NE)
				moveDirection = Constants.NE;
			
			touchStart = 0;
			touchEnd = 0;
			
		}
		
	}
	
	private boolean isTouchLeft(Vector2 scrCoords) {
		
		if (scrCoords.x > Constants.VIEWPORT_GUI_WIDTH * .5f - 100 &&
			scrCoords.x < Constants.VIEWPORT_GUI_WIDTH * .5f &&
			scrCoords.y > Constants.VIEWPORT_GUI_HEIGHT - 250 &&
			scrCoords.y < Constants.VIEWPORT_GUI_HEIGHT - 0)
			
			return true;
		
		return false;
			
	}
	
	private boolean isTouchRight(Vector2 scrCoords) {
			
		if (scrCoords.x > Constants.VIEWPORT_GUI_WIDTH * .5f &&
			scrCoords.x < Constants.VIEWPORT_GUI_WIDTH * .5f + 100 &&
			scrCoords.y > Constants.VIEWPORT_GUI_HEIGHT - 250 &&
			scrCoords.y < Constants.VIEWPORT_GUI_HEIGHT - 0)
			
			return true;
		
		return false;
			
	}
	
	private boolean isTouchUp(Vector2 scrCoords) {
		
		if (scrCoords.x > Constants.VIEWPORT_GUI_WIDTH * .5f - 100 &&
			scrCoords.x < Constants.VIEWPORT_GUI_WIDTH * .5f + 100 &&
			scrCoords.y > Constants.VIEWPORT_GUI_HEIGHT - 250 &&
			scrCoords.y < Constants.VIEWPORT_GUI_HEIGHT - 125)
			
			return true;
		
		return false;
			
	}
	
	private boolean isTouchDown(Vector2 scrCoords) {
		
		if (scrCoords.x > Constants.VIEWPORT_GUI_WIDTH * .5f - 100 &&
			scrCoords.x < Constants.VIEWPORT_GUI_WIDTH * .5f + 100 &&
			scrCoords.y > Constants.VIEWPORT_GUI_HEIGHT - 125 &&
			scrCoords.y < Constants.VIEWPORT_GUI_HEIGHT - 0)
			
			return true;
		
		return false;
			
	}
	
	private void ballUpdate(float deltaTime) {
		
		// ball update
		
		ball.update(deltaTime, moveDirection);
		moveDirection = Constants.NIL;
	
	}
	
	private Vector2 screenToMap (Vector2 cameraPosition, Vector2 cameraStartPosition,
								 float screenPosX, float screenPosY, int direction, boolean clamp) {
		
		Vector2 mapPos = new Vector2(0,0);
		
		float dblWidth = tileWidth * 2;
		float dblHeight = tileHeight * 2;
		
		// adjust which corner of ball image compare based up current direction
		Vector2 ballDimension = new Vector2(0,0);
		ballDimension = ball.returnDimension();
		
		Vector2 ballCorner = new Vector2(0,0);
		
		float ballXAdj = 0;
		float ballYAdj = 0;
		
		// check center
		
		// ballXAdj = ballDimension.x * Constants.BOX_TO_WORLD * .5f;
		// ballYAdj = ballDimension.y * Constants.BOX_TO_WORLD * .5f;
		
		/*

		// check upper right corner
		
		if (direction == Constants.NE) {
			
			ballXAdj = ballDimension.x * Constants.BOX_TO_WORLD;
			ballYAdj = ballDimension.y * Constants.BOX_TO_WORLD;
			
		}
		
		// check upper left corner
		
		if (direction == Constants.NW) {
			
			ballXAdj = 0;
			ballYAdj = ballDimension.y * Constants.BOX_TO_WORLD;
			
		}
		
		// check lower left corner
		
		if (direction == Constants.SW) {
			
			ballXAdj = 0;
			ballYAdj = 0;
			
		}
		
		// check lower right corner
		
		if (direction == Constants.SE) {
			
			ballXAdj = ballDimension.x * Constants.BOX_TO_WORLD;
			ballYAdj = 0;
			
		}
		
		*/
		
		// set up relative camera position by subtracting the cam start position
		float relativeCamX =  - cameraStartPosition.x + cameraPosition.x;
		float relativeCamY =  - cameraStartPosition.y + cameraPosition.y;
		
		// adjust x position
		// screenPosX = relativeCamX * Constants.BOX_TO_WORLD - centerXAdj;
		screenPosX = screenPosX - Gdx.graphics.getWidth() * .5f;
		screenPosX += relativeCamX * Constants.BOX_TO_WORLD;
		
		// adjust y position
		// screenPosY = relativeCamY * Constants.BOX_TO_WORLD - centerYAdj;
		screenPosY = screenPosY - Gdx.graphics.getHeight() * .5f;
		screenPosY -= relativeCamY * Constants.BOX_TO_WORLD;
		
		// calculate map position
		
		mapPos.x = (screenPosX / dblWidth + screenPosY / dblHeight) * .5f;
		mapPos.y = (screenPosY / dblHeight - screenPosX / dblWidth) * .5f;
		
		// limit map positions to map dimensions
		
		if (clamp) {
		
			mapPos.x = MathUtils.clamp(mapPos.x, 0, Constants.MAP_WIDTH - 1);
			mapPos.y = MathUtils.clamp(mapPos.y, 0, Constants.MAP_HEIGHT - 1);
		}

		return mapPos;
		
	}
	
	private Tile getTile(int mapX, int mapY) {
		
		for (Tile tile : tiles) {
			
			int tileX = tile.getMapPositionX();	
			int tileY = tile.getMapPositionY();	
			
			if (mapX == tileX &&
				mapY == tileY) {
				
				return tile;
				
			}
			
		}
		
		return null;
		
	}
	
	private void loadLevel() {
		
		if (newGame) {
			
			newGame = false;
			
		}
			
	}
	
	public int returnLevelNumber() {
		
		return levelNumber;
		
	}
	
	public int returnScore() {
		
		return score;
		
	}
	
	public boolean returnIsGameOver() {
		
		return isGameOver;
	}
	
	public int returnInputMode() {
		
		return inputMode;
				
	}
	
	public void render (SpriteBatch batch, float deltaTime) {
		
		if (isLoadingComplete) {
		
			ball.render(batch);
			
		}
		
	}

	// render for just the collision boxes
	
	public void renderShapes (ShapeRenderer shapeRenderer, float deltaTime) {
		
		// draw clickbox
		
		/*
		
		shapeRenderer.setColor(Color.ORANGE);
		
		for (ClickBox roadBox : roadBoxes)
			shapeRenderer.polygon(roadBox.rectangleToVertices());
			
		*/
		
	}

}
