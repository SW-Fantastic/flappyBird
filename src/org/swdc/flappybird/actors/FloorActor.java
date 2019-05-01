package org.swdc.flappybird.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.swdc.flappybird.MainStart;

/**
 * Created by lenovo on 2019/1/5.
 */
public class FloorActor extends Actor {

    private boolean isMoving;

    private TextureRegion groundRegion;
    private Texture ground;

    private int offsetX = 0;
    private int roleStep = 3;

    private static final float SEC = 0.7f;

    public FloorActor() {
        this.ground = new Texture("textures/birdGround.png");
        this.groundRegion = new TextureRegion(ground);
        this.setWidth(MainStart.WIDTH);
        this.setHeight(groundRegion.getRegionHeight() * SEC);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        offsetX += roleStep;
        int floorWidthAfterSec = (int)(groundRegion.getRegionWidth() * SEC);
        int floorRepX = 900 / floorWidthAfterSec;
        if (offsetX % (floorRepX * floorWidthAfterSec) >= 0 &&
                offsetX % (floorRepX * floorWidthAfterSec) <= roleStep){
            offsetX = floorWidthAfterSec;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        int floorWidthAfterSec = (int)(groundRegion.getRegionWidth() * SEC);
        int floorRepX = 900 / floorWidthAfterSec;
        for (int currX = 0; currX <= 2 * floorRepX; currX ++) {
            // 计算位置，绘制图片（缩放后宽度 * 当前绘制的个数 - x偏移，偏移周期性改变，达到移动的效果
            batch.draw(groundRegion,floorWidthAfterSec * currX - (isMoving ? offsetX : 0),0 ,
                    getOriginX(), getOriginY(),
                    getWidth(),getHeight(),
                    SEC,SEC,
                    getRotation());
        }
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isMoving() {
        return isMoving;
    }
}
