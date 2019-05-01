package org.swdc.flappybird.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by lenovo on 2019/1/6.
 */
public class TouchActor extends Actor {

    private Texture touch;
    private TextureRegion touchRegion;

    private static final float SEC = 0.8f;

    public TouchActor() {
        touch = new Texture("textures/birdStart.png");
        touchRegion = new TextureRegion(touch);
        setWidth(touchRegion.getRegionWidth() * SEC);
        setHeight(touchRegion.getRegionHeight() * SEC);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(touchRegion, getX(),getY() ,
                getOriginX(), getOriginY(),
                getWidth(),getHeight(),
                SEC * getScaleX(),SEC * getScaleY(),
                getRotation());
    }
}
