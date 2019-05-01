package org.swdc.flappybird.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by lenovo on 2019/1/6.
 */
public class ScoreActor extends Actor {

    private Texture scorePanel;
    private TextureRegion scoreRegion;

    private static final float SEC = 0.95f;

    public ScoreActor (){
        scorePanel = new Texture("textures/birdScore.png");
        scoreRegion = new TextureRegion(scorePanel);
        setWidth(SEC * scoreRegion.getRegionWidth());
        setHeight(SEC * scoreRegion.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(scoreRegion, getX(),getY() ,
                getOriginX(), getOriginY(),
                getWidth(),getHeight(),
                SEC * getScaleX(),SEC * getScaleY(),
                getRotation());
    }
}
