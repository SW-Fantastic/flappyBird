package org.swdc.flappybird.actors;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import org.swdc.flappybird.MainStart;

import java.util.Random;

/**
 * Created by lenovo on 2019/1/5.
 */
public class GDActor extends Actor {

    /**
     * 上方的管道
     */
    private Texture gdTop;
    private TextureRegion gdTopRegion;

    /**
     * 下方的管道
     */
    private Texture gdBottom;
    private TextureRegion gdBottomRegion;

    /**
     * 上方管道的高度
     */
    private int topHeight;
    /**
     * 下方管道的高度
     */
    private int bottomHeight;

    /**
     * 空隙的高度
     */
    private int space = 220;

    /**
     * 图像缩放倍数
     */
    private static final float SEC = 1f;

    /**
     * 移动开关
     */
    private boolean moving = false;

    /**
     * 鸟是否已经通过
     */
    private boolean birdPassed = false;

    /**
     * 柱子是否移出
     */
    private boolean moveOut = false;

    public GDActor(){
        gdTop = new Texture("textures/birdGD.png");
        gdBottom = new Texture("textures/birdGD2.png");
        gdTopRegion = new TextureRegion(gdTop);
        gdBottomRegion = new TextureRegion(gdBottom);
        setWidth(SEC * gdTopRegion.getRegionWidth());
        reset();
    }

    public void reset() {
        Random random = new Random();
        int max = MainStart.HEIGHT  - space - (int)getY();
        int min = space;
        int prefix = random.nextInt(space/2)%(space/2-space/8+1) + space/8;
        if (prefix%2 == 0) {
            topHeight = random.nextInt(max)%(max-min+1) + min + Math.abs(prefix);
            bottomHeight = MainStart.HEIGHT - topHeight -  space/2 - Math.abs(prefix);
        } else if(prefix%3 == 0){
            topHeight = random.nextInt(max)%(max-min+1) + min - Math.abs(prefix/4) ;
            bottomHeight = MainStart.HEIGHT - topHeight -  space/2 + Math.abs(prefix/4);
        } else {
            topHeight = random.nextInt(max)%(max-min+1) + min ;
            bottomHeight = MainStart.HEIGHT - topHeight -  space/2 ;
        }
        while (bottomHeight < 0) {
            reset();
        }
        birdPassed = false;
        moveOut = false;
        this.setX(MainStart.WIDTH + getWidth() * SEC);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (moving) {
            this.addAction(Actions.moveBy(-2.6f,0,0.2f));
        }
        if (this.getX() < - getWidth() * SEC) {
            moving = false;
            moveOut = true;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(gdTopRegion,getX(),MainStart.HEIGHT - topHeight + space/2 ,
                getOriginX(), getOriginY(),
                getWidth(),topHeight,
                SEC,SEC,
                getRotation());
        batch.draw(gdBottomRegion,getX(),getY(),
                getOriginX(), getOriginY(),
                getWidth(),bottomHeight,
                SEC,SEC,
                getRotation());
    }

    public boolean isCollision(Actor otherActor, Sound whenCollision, Sound whenPass) {
        Rectangle rectTop = new Rectangle();
        Rectangle rectBottom = new Rectangle();
        Rectangle rectTarget = new Rectangle();
        Rectangle rectSpace = new Rectangle();

        rectTop.set(getX() + 28, MainStart.HEIGHT - topHeight + space/2 + 18, getWidth() * SEC - 28, topHeight * SEC);
        rectBottom.set(getX() + 28, getY() - 18,getWidth() * SEC - 20, bottomHeight * SEC);
        rectTarget.set(otherActor.getX(), otherActor.getY(), otherActor.getWidth(), otherActor.getHeight());
        rectSpace.set(getX(), MainStart.HEIGHT - topHeight + space/2 + space, getWidth() * SEC, space);

        if (rectTop.overlaps(rectTarget) || rectBottom.overlaps(rectTarget)) {
            if (moving) {
                whenCollision.play();
            }
            return true;
        } else {
            if(!birdPassed && otherActor.getX() > this.getX() + this.getWidth() * SEC) {
                whenPass.play();
                birdPassed = true;
            }
            return false;
        }
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isBirdPassed() {
        return birdPassed;
    }

    public boolean isMoveOut() {
        return moveOut;
    }

    public boolean isMoving() {
        return moving;
    }
}
