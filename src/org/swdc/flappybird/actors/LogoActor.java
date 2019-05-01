package org.swdc.flappybird.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *  像素鸟的logo
 */
public class LogoActor extends Actor {

    private Texture logo;
    private TextureRegion logoRegion;

    private static final float SEC = 0.8f;

    private boolean moved = false;

    public LogoActor () {
        logo = new Texture("textures/birdLogo.png");
        logoRegion = new TextureRegion(logo);
        this.setWidth(logo.getWidth() * SEC);
        this.setHeight(logo.getHeight() * SEC);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(logoRegion, getX(),getY() ,
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
