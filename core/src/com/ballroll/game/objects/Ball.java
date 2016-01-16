package com.ballroll.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.ballroll.game.Assets;
import com.ballroll.game.Level;
import com.ballroll.game.WorldRenderer;
import com.ballroll.util.Constants;

public class Ball extends AbstractGameObject {
	
	private static final String TAG = Ball.class.getName();
	
	private TextureRegion Ball;
	
	// box size
	private float boxXSize;
	private float boxYSize;
	
	private float moveXAxis;
	private float moveYAxis;
	
	public Ball (Vector2 ballOrigin) {
		
		setPosition(ballOrigin);
		init();
	}
	
	private void setPosition(Vector2 ballOrigin) {
		
		
		position.x = ballOrigin.x * Constants.WORLD_TO_BOX;
		position.y = ballOrigin.y * Constants.WORLD_TO_BOX;
		
		position.x -= dimension.x * .25f;
		position.y -= dimension.y;
		
	}
	
	private void init() {
		
		dimension.set(.5f,.5f);
		
		bounds.set(0,0, dimension.x, dimension.y);
		
		Ball = Assets.instance.ball.ball;
		
		// init physics values
		
		terminalVelocity.x = 4.0f;
		terminalVelocity.y = 2.0f;
		friction.x = 1f;
		friction.y = .5f;
		accleration.x = 30;
		accleration.y = 30;
		velocity.x = 0;
		velocity.y = 0;
		
		// move values
		
		moveXAxis = 0;
		moveYAxis = 0;
		
	}
	
	public void update(float deltaTime, int moveDirection) {
		
		applyMovement(deltaTime, moveDirection);
		
	
	}
	
	private void applyMovement(float deltaTime, int moveDirection) {
		
		if (moveDirection == Constants.NE) {
			
			moveXAxis += 2;
			moveYAxis += 1;
			
		}
		
		if (moveDirection == Constants.NW) {
			
			moveXAxis += -2;
			moveYAxis += 1;
			
		}
		
		if (moveDirection == Constants.SE) {
			
			moveXAxis += 2;
			moveYAxis += -1;
			
		}
		
		if (moveDirection == Constants.SW) {
			
			moveXAxis += -2;
			moveYAxis += -1;
			
		}
		
		if (moveDirection == Constants.NIL) {
			
			moveXAxis = 0;
			moveYAxis = 0;
			
		}

		if (velocity.x != 0) {
			
			// Apply friction
			if (velocity.x > 0) {
				velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
			} else {
				velocity.x = Math.min(velocity.x + friction.x * deltaTime, 0);
			}
		}
			
		velocity.x += accleration.x * deltaTime * moveXAxis;
		velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
		
		position.x += velocity.x * deltaTime;
		
		if (velocity.y != 0) {
				
			// Apply friction
			if (velocity.y > 0) {
				velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
			} else {
				velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
			}
		}
			
		velocity.y += accleration.y * deltaTime * (moveYAxis);
		velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
			
		position.y += velocity.y * deltaTime;		
		
	}
	
	public Vector2 returnBallPos() {
		
		return position;
		
	}
	
	public Vector2 returnDimension() {
		
		return dimension;
		
	}
	
	public void render (SpriteBatch batch) {
			
		// position.x = position.x + boxXSize;
		// position.y = position.y + boxYSize;
		
		batch.draw(Ball, position.x, position.y, 0, 0, dimension.x, dimension.y, 1, 1, rotation);		
		
	}	
	
}