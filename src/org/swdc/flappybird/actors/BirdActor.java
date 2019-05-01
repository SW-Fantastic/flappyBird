package org.swdc.flappybird.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.Optional;

/**
 * Created by lenovo on 2019/1/5.
 */
public class BirdActor extends Actor{

    private static final float SEC = 0.58f;

    /**
     * 鸟的贴图
     */
    private Texture bird ;
    /**
     * 鸟的贴图的Region，用来分割图片
     */
    private TextureRegion birdRegon;
    /**
     * 飞行动画，可以根据时间获取当前关键帧
     */
    private Animation birdFly;

    /**
     * 动画计时
     */
    private float stateTime = 0;
    /**
     * 需要绘制的关键帧
     */
    private TextureRegion currentFrame;

    /**
     * 动画速度
     */
    private float speed = 0.15f;

    /**
     * gameOver的最低高度，低于这个高度判定为gameOver
     */
    private float deathHeight;

    /**
     * 是否需要动画
     */
    private boolean moving = false;

    /**
     * 是否撞到地面
     */
    private boolean death = false;

    public BirdActor(){
        bird = new Texture("textures/birdFly.png");
        birdRegon = new TextureRegion(bird);
        int cellWidth = birdRegon.getRegionWidth() / 3;
        TextureRegion[] flyKeyframes = birdRegon.split(cellWidth, birdRegon.getRegionHeight())[0];
        birdFly = new Animation(speed,flyKeyframes);
        birdFly.setPlayMode(Animation.PlayMode.LOOP);
        setWidth(cellWidth * SEC);
        setHeight(birdRegon.getRegionHeight() * SEC);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (moving && !death) {
            stateTime = stateTime + delta;
        }
        currentFrame = birdFly.getKeyFrame(stateTime);
        if (!Gdx.input.justTouched()) {
            this.dropDown();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Optional.ofNullable(currentFrame).ifPresent(frame -> {
            batch.draw(frame,getX(),getY(),
                    getOriginX(), getOriginY(),
                    getWidth(),getHeight(),
                    SEC,SEC,
                    getRotation());
        });
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isMoving() {
        return moving;
    }

    public void flyUp(){
        if (moving && !death) {
            this.addAction(Actions.moveBy(0, 64, 0.2f));
            if(this.getRotation() < 60) {
                this.addAction(Actions.rotateTo(60, 0.2f));
            }
        }
    }

    public void dropDown () {
        if (moving && !death) {
            this.addAction(Actions.moveBy(0, -3.5f, 0.2f));
            if (this.getRotation() > -90) {
                this.addAction(Actions.rotateBy(-1.6f, 0.2f));
            }
            if(this.getY() <= deathHeight) {
                this.addAction(Actions.rotateTo(-24, 0.2f));
                this.death = true;
                this.moving = false;
            }
        }
    }

    public void setDeathHeight(float deathHeight) {
        this.deathHeight = deathHeight;
    }

    public boolean isDeath() {
        return death;
    }

    public void reset() {
        this.moving = false;
        this.death = false;
        this.stateTime = 0;
    }

}
