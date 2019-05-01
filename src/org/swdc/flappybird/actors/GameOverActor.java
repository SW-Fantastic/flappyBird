package org.swdc.flappybird.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by lenovo on 2019/1/6.
 */
public class GameOverActor extends Actor{

    private Texture gameOver;
    private TextureRegion gameOverRegion;

    private static final float SEC = 1f;

    private boolean moved = false;

    public GameOverActor() {
        gameOver = new Texture("textures/birdGameOver.png");
        gameOverRegion = new TextureRegion(gameOver);
        setWidth(gameOverRegion.getRegionWidth() * SEC);
        setHeight(gameOverRegion.getRegionHeight() * SEC);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(gameOverRegion, getX(),getY() ,
                getOriginX(), getOriginY(),
                getWidth(),getHeight(),
                SEC,SEC,
                getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (moved) {
            this.addAction(Actions.moveBy(0, -42,0.2f));
        } else {
            this.addAction(Actions.moveBy(0, 42,0.2f));
        }
        moved = !moved;
    }
}
